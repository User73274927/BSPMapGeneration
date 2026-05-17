package com.mirea.demo;

import com.badlogic.gdx.math.Rectangle;

public class WallGenerator {
    private ProcedureMapGenerator.MapGeometry mapGeometry;
    private TileMap tileMap;

    public WallGenerator(ProcedureMapGenerator.MapGeometry map, TileMap tileMap) {
        this.tileMap = tileMap;
        this.mapGeometry = map;
    }

    public void createWalls() {
        for (Room room : mapGeometry.rooms()) {
            Rectangle bounds = room.bounds;
            for (int i = 0; i < bounds.height; i++) {
                for (int j = 0; j < bounds.width; j++) {
                    if (i == 0 || j == 0 || i == bounds.height-1 || j == bounds.width-1) {
                        tileMap.setBlock(2, (int)bounds.y+i, (int)bounds.x+j);
                    }
                    else {
                        tileMap.setBlock(1, (int)bounds.y+i, (int)bounds.x+j);
                    }
                }
            }
        }
        for (Hall hall : mapGeometry.halls()) {
            Rectangle bounds = hall.bounds;
            for (int i = 0; i < bounds.height; i++) {
                for (int j = 0; j < bounds.width; j++) {
                    if (hall.direction == HallDirection.HORIZONTAL) {
                        if (i == 0 || i == bounds.height-1) {
                            tileMap.setBlock(2, (int) bounds.y + i, (int) bounds.x + j);
                        }
                        else {
                            tileMap.setBlock(1, (int) bounds.y + i, (int) bounds.x + j);
                        }
                    }
                    else if (hall.direction == HallDirection.VERTICAL) {
                        if (j == 0 || j == bounds.width-1) {
                            tileMap.setBlock(2, (int) bounds.y + i, (int) bounds.x + j);
                        }
                        else {
                            tileMap.setBlock(1, (int) bounds.y + i, (int) bounds.x + j);
                        }
                    }
                }
            }
        }
    }

}
