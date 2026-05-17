package com.mirea.demo;

import com.badlogic.gdx.math.Rectangle;

import java.util.*;

public class HallGenerator {


    private Random random = new Random();
    private Map<Pair<Room, Room>, Hall> roomHallMapping = new HashMap<>();
    private List<Room> rooms;
    private Collection<Hall> halls;
    private int hallWidth;

    public HallGenerator(List<Room> rooms, int hallWidth) {
        this.rooms = rooms;
        this.hallWidth = hallWidth;
    }

    public void createHalls() {
        for (int i = 0; i < rooms.size(); i++) {
            Room room1 = rooms.get(i);

            for (int j = i+1; j < rooms.size(); j++) {
                Room room2 = rooms.get(j);

                if (overlapRoomRegions(room1, room2)) {
                    HallDirection direction = getHallDirection(room1, room2);

                    if (direction != HallDirection.NONE) {
                        roomHallMapping.put(new Pair<>(room1, room2), createHall(room1, room2, direction));
                    }
                }
            }
        }
        halls = roomHallMapping.values();
    }

    private HallDirection getHallDirection(Room room1, Room room2) {
        Rectangle r1 = room1.bounds, r2 = room2.bounds;
        int yOverlap = (int)(Math.min(r1.y + r1.height, r2.y + r2.height) - Math.max(r1.y, r2.y));
        boolean hasHorizontalGap = (r1.x + r1.width <= r2.x) || (r2.x + r2.width <= r1.x);

        if (hasHorizontalGap && yOverlap >= hallWidth) {
            return HallDirection.HORIZONTAL;
        }

        int xOverlap = (int)(Math.min(r1.x + r1.width, r2.x + r2.width) - Math.max(r1.x, r2.x));
        boolean hasVerticalGap = (r1.y + r1.height <= r2.y) || (r2.y + r2.height <= r1.y);

        if (hasVerticalGap && xOverlap >= hallWidth) {
            return HallDirection.VERTICAL;
        }
        return HallDirection.NONE;
    }

    private Hall createHall(Room room1, Room room2, HallDirection direction) {
        Hall hall = new Hall(direction);

        if (direction == HallDirection.HORIZONTAL) {
            Room left = (room1.bounds.x < room2.bounds.x) ? room1 : room2;
            Room right = (room1.bounds.x >= room2.bounds.x) ? room1 : room2;

            int x = (int)(left.bounds.x+left.bounds.width)-1;
            System.out.println(Math.max(room1.bounds.y, room2.bounds.y) + " " +
                    (Math.min(room1.bounds.y+room1.bounds.height, room2.bounds.y+room2.bounds.height) - hallWidth));

            int y = random.nextInt(
                    (int)Math.max(room1.bounds.y, room2.bounds.y),
                    (int)Math.min(room1.bounds.y+room1.bounds.height, room2.bounds.y+room2.bounds.height) - hallWidth + 1
            );

            hall.setPosition(x, y);
            hall.setSize(right.bounds.x - x+1, hallWidth);
        }
        else if (direction == HallDirection.VERTICAL) {
            Room top = (room1.bounds.y >= room2.bounds.y) ? room1 : room2;
            Room down = (room1.bounds.y < room2.bounds.y) ? room1 : room2;

            System.out.println(Math.max(room1.bounds.x, room2.bounds.x) + " " +
                    (Math.min(room1.bounds.x+room1.bounds.width, room2.bounds.x+room2.bounds.width) - hallWidth));

            int x = random.nextInt(
                    (int)Math.max(room1.bounds.x, room2.bounds.x),
                    (int)Math.min(room1.bounds.x+room1.bounds.width, room2.bounds.x+room2.bounds.width) - hallWidth + 1
            );
            int y = (int)(down.bounds.y+down.bounds.height)-1;

            hall.setPosition(x, y);
            hall.setSize(hallWidth, top.bounds.y - y+1);
        }
        return hall;
    }

    private boolean overlapRoomRegions(Room room1, Room room2)  {
        Rectangle r1 = room1.region;
        Rectangle r2 = room2.region;

        boolean xOverlap = (r1.x <= r2.x + r2.width) && (r2.x <= r1.x + r1.width);
        boolean yOverlap = (r1.y <= r2.y + r2.height) && (r2.y <= r1.y + r1.height);
        return xOverlap && yOverlap;
    }

    public Hall getHall(Room room1, Room room2) {
        return roomHallMapping.getOrDefault(new Pair<>(room1, room2), null);
    }

    public Collection<Hall> getHalls() {
        return halls;
    }
}
