package com.mirea.demo;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class RoomGenerator {
    private List<Room> rooms;
    private List<Rectangle> subSpaces;
    private float indent;

    public RoomGenerator(List<Rectangle> subSpaces, float indent) {
        this.subSpaces = subSpaces;
        this.indent = indent;
    }

    public void createRooms() {
        rooms = new ArrayList<>(subSpaces.size());
        for (Rectangle subSpace : subSpaces) {
            Room room = new Room();
            room.setPosition(subSpace.x+indent, subSpace.y+indent);
            room.setSize(subSpace.width-2*indent, subSpace.height-2*indent);
            room.setRegion(subSpace);
            rooms.add(room);
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
