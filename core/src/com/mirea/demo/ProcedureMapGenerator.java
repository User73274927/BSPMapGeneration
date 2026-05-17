package com.mirea.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Collection;
import java.util.List;

public class ProcedureMapGenerator {
    private int width, height;
    private int minRoomSize;
    private int indent;
    private int hallWidth;
    private int partitionCount;

    private TileMap tileMap;
    private ContentMap contentMap;

    private BSPGenerator bspGenerator;
    private RoomGenerator roomGenerator;
    private HallGenerator hallGenerator;

    private WallGenerator wallGenerator;
    private ContentGenerator contentGenerator;

    public ProcedureMapGenerator(TileMap tileMap, ContentMap contentMap, int minRoomSize, int indent, int partitionCount, int hallWidth) {
        this.tileMap = tileMap;
        this.contentMap = contentMap;
        this.width = tileMap.getWidth();
        this.height = tileMap.getHeight();
        this.minRoomSize = minRoomSize;
        this.indent = indent;
        this.partitionCount = partitionCount;
        this.hallWidth = hallWidth;
    }

    public void generate() {
        bspGenerator = new BSPGenerator(width, height, indent*2+minRoomSize, partitionCount);
        bspGenerator.binaryPartition();

        roomGenerator = new RoomGenerator(bspGenerator.getSubSpaces(), indent);
        roomGenerator.createRooms();

        hallGenerator = new HallGenerator(roomGenerator.getRooms(), hallWidth);
        hallGenerator.createHalls();

        MapGeometry mapGeometry = new MapGeometry(roomGenerator.getRooms(), hallGenerator.getHalls());
        wallGenerator = new WallGenerator(mapGeometry, tileMap);
        wallGenerator.createWalls();

        contentGenerator = new ContentGenerator(mapGeometry, contentMap);
        contentGenerator.createContent();

        bspGenerator.printBSPTree();
    }

    public void draw(ShapeRenderer shapes, int scale) {
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.BLUE);
        for (Rectangle sector : bspGenerator.getSubSpaces()) {
            shapes.rect(sector.x*scale, sector.y*scale, sector.width*scale, sector.height*scale);
        }
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (Room room : roomGenerator.getRooms()) {
            room.draw(shapes, scale);
        }
        for (Hall hall : hallGenerator.getHalls()) {
            hall.draw(shapes, scale);
        }
        shapes.end();
    }

    public void drawOverlay(ShapeRenderer shapes, float scale) {
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.RED);
        for (Rectangle sector : bspGenerator.getSubSpaces()) {
            shapes.rect(sector.x*scale, sector.y*scale, sector.width*scale, sector.height*scale);
        }
        shapes.setColor(Color.BLUE);
        for (List<Rectangle> chunks : contentGenerator.getChunks()) {
            for (Rectangle chunk : chunks) {
                shapes.rect(chunk.x*scale, chunk.y*scale, chunk.width*scale, chunk.height*scale);
            }
        }
        shapes.end();
    }

    public Collection<Room> getRooms() {
        return roomGenerator.getRooms();
    }

    public Collection<Hall> getHalls() {
        return hallGenerator.getHalls();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMinRoomSize() {
        return minRoomSize;
    }

    public int getIndent() {
        return indent;
    }

    public record MapGeometry(Collection<Room> rooms, Collection<Hall> halls) {}
}
