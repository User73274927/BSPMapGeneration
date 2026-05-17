package com.mirea.demo;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.HashMap;
import java.util.Map;

public class ContentMap {
    private Map<Integer, Content> contentTypes = new HashMap<>();
    private Content[] contentMap;

    public ContentMap() {
    }

    public void addContent(Content content, int id) {
        contentTypes.put(id, content);
    }

    public void resizeMap(int size) {
        contentMap = new Content[size];
    }

    public void set(Content contentId, int i) {
        contentMap[i] = contentId;
    }

    public void draw(Batch batch) {
        for (Content content : contentMap) {
            if (content != null) {
                content.draw(batch);
            }
        }
    }

    public void scale(float scale) {
        for (Content content : contentMap) {
            content.bounds.x *= scale;
            content.bounds.y *= scale;
            content.bounds.width *= scale;
            content.bounds.height *= scale;
        }
    }

    public Content getContentType(int contentId) {
        if (contentTypes.containsKey(contentId)) {
            return contentTypes.get(contentId).copy();
        }
        return null;
    }

    public Map<Integer, Content> getContentTypes() {
        return contentTypes;
    }

    public Content[] getContentMap() {
        return contentMap;
    }

}
