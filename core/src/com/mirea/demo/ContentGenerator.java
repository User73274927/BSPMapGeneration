package com.mirea.demo;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContentGenerator {
    private float indent = 1;
    private Random random = new Random();
    private ProcedureMapGenerator.MapGeometry mapGeometry;
    private ContentMap contentMap;
    private List<List<Rectangle>> chunks;

    public ContentGenerator(ProcedureMapGenerator.MapGeometry mapGeometry, ContentMap contentMap) {
        this.mapGeometry = mapGeometry;
        this.contentMap = contentMap;
    }

    public void createContent() {
        chunks = new ArrayList<>(mapGeometry.rooms().size());
        int mapSize = 0;

        for (Room room : mapGeometry.rooms()) {
            BSPGenerator contentGenerator = new BSPGenerator(
                    (int)room.bounds.width, (int)room.bounds.height, 5, 10
            );
            contentGenerator.binaryPartition();
            chunks.add(contentGenerator.getSubSpaces());

            for (Rectangle chunk : chunks.get(chunks.size()-1)) {
                chunk.x += room.bounds.x;
                chunk.y += room.bounds.y;
            }
            mapSize += chunks.get(chunks.size()-1).size();
        }

        List<Integer> contentIds = new ArrayList<>(contentMap.getContentTypes().keySet());
        contentMap.resizeMap(mapSize);
        int i = 0;
        for (List<Rectangle> roomChunks : chunks) {
            for (Rectangle chunk : roomChunks) {
                int contentId = contentIds.get(random.nextInt(contentIds.size()));
                Content content = contentMap.getContentType(contentId);

                float indentPercent = 0.1f;

                float scale = Math.min(
                        (chunk.width - indent * 2) / content.sprite.getRegionWidth(),
                        (chunk.height - indent * 2) / content.sprite.getRegionHeight()
                );

                content.bounds.width = content.sprite.getRegionWidth() * scale;
                content.bounds.height = content.sprite.getRegionHeight() * scale;

                content.bounds.x = chunk.x + (chunk.width - content.bounds.width) / 2f;
                content.bounds.y = chunk.y + (chunk.height - content.bounds.height) / 2f;                contentMap.set(content, i);
                i++;
            }
        }
    }

    public List<List<Rectangle>> getChunks() {
        return chunks;
    }
}
