package com.mirea.demo;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class TileMap {
    private Map<Integer, TextureRegion> blockIds = new HashMap<>();
    private int[][] tileMap;
    private float tileSize = 0;
    private int width, height;

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        tileMap = new int[height][width];
    }

    public void draw(Batch batch) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!blockIds.containsKey(tileMap[i][j])) continue;
                batch.draw(blockIds.get(tileMap[i][j]), tileSize*j, tileSize*i, tileSize, tileSize);
            }
        }
    }

    public void addBlockTexture(TextureRegion tx, int id) {
        blockIds.put(id, tx);
    }

    public void setBlock(int id, int i, int j) {
        tileMap[i][j] = id;
    }

    public float getTileSize() {
        return tileSize;
    }

    public void setTileSize(float tileSize) {
        this.tileSize = tileSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
