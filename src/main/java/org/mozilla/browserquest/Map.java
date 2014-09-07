package org.mozilla.browserquest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.vertx.java.core.file.FileSystem;

public class Map {

    private int width;
    private int height;

    private double groupWidth;
    private double groupHeight;

    private int zoneWidth = 28;
    private int zoneHeight = 12;

    public Map(FileSystem fs, String filePath) {
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
            JsonNode root = mapper.readTree(bytes);

            width = root.findValue("width").asInt();
            height = root.findValue("height").asInt();

            groupWidth = Math.floor(width / zoneWidth);
            groupHeight = Math.floor(height / zoneHeight);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
