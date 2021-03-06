package org.mozilla.browserquest.world;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mozilla.browserquest.Location;
import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.template.CheckpointTemplate;
import org.mozilla.browserquest.template.DoorTemplate;
import org.mozilla.browserquest.template.MapTemplate;
import org.mozilla.browserquest.template.RoamingAreaTemplate;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.file.FileSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

public class WorldMap {

    private static final int ZONE_WIDTH = 28;
    private static final int ZONE_HEIGHT = 12;

    private MapTemplate mapData;

    private Map<String, List<Position>> connectedGroups = new HashMap<>();
    private Map<Integer, Location> checkpoints = new HashMap<>();
    private List<Location> startingAreas = new ArrayList<>();

    private int[][] collisionGrid;

    public WorldMap(FileSystem fs, String filePath) {
        if (fs.existsSync(filePath)) {
            Buffer buffer = fs.readFileSync(filePath);
            byte[] bytes = buffer.getBytes();
            initMap(bytes);
        }
    }

    private void initMap(byte[] bytes) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapData = mapper.readValue(bytes, MapTemplate.class);
            initConnectedGroups(mapData.getDoors());
            initCheckpoints(mapData.getCheckpoints());
            generateCollisionGrid();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public List<RoamingAreaTemplate> getRoamingAreas() {
        return mapData.getRoamingAreas();
    }

    private void initConnectedGroups(Iterable<DoorTemplate> doors) {
        doors.forEach(door -> {
            String groupId = getGroupIdFromPosition(door.getX(), door.getY());
            String connectedGroupId = getGroupIdFromPosition(door.getTx(), door.getTy());
            Position connectedPosition = groupIdToGroupPosition(connectedGroupId);
            if (!connectedGroups.containsKey(groupId)) {
                connectedGroups.put(groupId, new ArrayList<>());
            }
            connectedGroups.get(groupId).add(connectedPosition);
        });
    }

    private Position groupIdToGroupPosition(String groupId) {
        String[] pos = groupId.split("-");
        return new Position(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
    }

    public String getGroupIdFromPosition(int x, int y) {
        return (int) Math.floor((x - 1) / ZONE_WIDTH) + "-" + (int) Math.floor((y - 1) / ZONE_HEIGHT);
    }

    private void initCheckpoints(Iterable<CheckpointTemplate> checkpoints) {
        checkpoints.forEach(ch -> {
            Location location = new Location(ch.getId(), ch.getX(), ch.getY(), ch.getW(), ch.getH());
            this.checkpoints.put(location.getId(), location);
            if (ch.getS() == 1) {
                startingAreas.add(location);
            }
        });
    }

    public void forEachGroup(BiConsumer<? super String, ? super List<Position>> action) {
        connectedGroups.forEach(action);
    }

    private void generateCollisionGrid() {
        int tileIndex = 0;

        collisionGrid = new int[mapData.getHeight()][mapData.getWidth()];
        for (int i = 0; i < mapData.getHeight(); i++) {
            for (int j = 0; j < mapData.getWidth(); j++) {
                collisionGrid[i][j] = mapData.getCollisions().contains(tileIndex) ? 1 : 0;
                tileIndex++;
            }
        }
    }

    public boolean isOutOfBounds(int x, int y) {
        return x <= 0 || x >= mapData.getWidth() || y <= 0 || y >= mapData.getHeight();
    }

    public boolean isColliding(int x, int y) {
        if (isOutOfBounds(x, y)) {
            return false;
        }
        return collisionGrid[y][x] == 1;
    }

    public Position getRandomStartingPosition() {
        Random rand = new Random();
        int pos = rand.nextInt(startingAreas.size());
        return startingAreas.get(pos).getRandomPosition();
    }

    public Position getPositionFromTileIndex(int tileIndex) {
        int width = mapData.getWidth();
        int x = (tileIndex % width == 0) ? width - 1 : (tileIndex % width) - 1;
        int y = (int) Math.floor(tileIndex / width);
        return new Position(x, y);
    }

    public Map<Integer, String> getStaticEntities() {
        return mapData.getStaticEntities();
    }
}
