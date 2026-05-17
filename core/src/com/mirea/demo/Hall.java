package com.mirea.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Hall {
    public Rectangle bounds;
    public HallDirection direction;

    public Hall(HallDirection direction) {
        this(0, 0, 0, 0);
        this.direction = direction;
    }

    public Hall(float x, float y, float width, float height) {
        this.bounds = new Rectangle();
        this.bounds.setPosition(x, y);
        this.bounds.setSize(width, height);
    }

    public void draw(ShapeRenderer shapes, float scale) {
        shapes.setColor(Color.DARK_GRAY);
        shapes.rect(bounds.x*scale, bounds.y*scale, bounds.width*scale, bounds.height*scale);
    }

    public void setPosition(float x, float y) {
        this.bounds.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        this.bounds.setSize(width, height);
    }

}
