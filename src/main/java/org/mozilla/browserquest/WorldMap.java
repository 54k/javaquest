package org.mozilla.browserquest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mozilla.browserquest.map.MapCheckpoint;
import org.mozilla.browserquest.map.MapData;
import org.mozilla.browserquest.map.MapDoor;
import org.vertx.java.core.file.FileSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WorldMap {

    private static final int ZONE_WIDTH = 28;
    private static final int ZONE_HEIGHT = 12;

    private MapData mapData;

    private double groupWidth;
    private double groupHeight;

    private Map<String, List<Position>> connectedGroups = new HashMap<>();
    private Map<Integer, Checkpoint> checkpoints = new HashMap<>();
    private List<Checkpoint> startingAreas = new ArrayList<>();

    public WorldMap(FileSystem fs, String filePath) {
        fs.exists(filePath, booleanAsyncResult -> {
            if (booleanAsyncResult.result()) {
                fs.readFile(filePath, bufferAsyncResult -> {
                    byte[] bytes = bufferAsyncResult.result().getBytes();
                    initMap(bytes);
                });
            }
        });
    }

    private void initMap(byte[] bytes) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapData = mapper.readValue(bytes, MapData.class);

            groupWidth = Math.floor(mapData.getWidth() / ZONE_WIDTH);
            groupHeight = Math.floor(mapData.getHeight() / ZONE_HEIGHT);

            initConnectedGroups(mapData.getDoors());
            initCheckpoints(mapData.getCheckpoints());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void initConnectedGroups(Iterable<MapDoor> doors) {
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

    private String getGroupIdFromPosition(int x, int y) {
        return (int) Math.floor((x - 1) / ZONE_WIDTH) + "-" + (int) Math.floor((y - 1) / ZONE_HEIGHT);
    }

    private void initCheckpoints(Iterable<MapCheckpoint> checkpoints) {
        checkpoints.forEach(ch -> {
            Checkpoint checkpoint = new Checkpoint(ch.getId(), ch.getX(), ch.getY(), ch.getW(), ch.getH());
            this.checkpoints.put(checkpoint.getId(), checkpoint);
            if (ch.getS() == 1) {
                startingAreas.add(checkpoint);
            }
        });
    }

    public Position getRandomStartingPosition() {
        Random rand = new Random();
        int pos = rand.nextInt(startingAreas.size());
        return startingAreas.get(pos).getRandomPosition();
    }
}
