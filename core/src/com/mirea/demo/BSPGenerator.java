package com.mirea.demo;

import com.badlogic.gdx.math.Rectangle;

import java.util.*;

public class BSPGenerator {
    private Random random = new Random();
    private List<Rectangle> subSpaces;

    private BSPVertex root;
    private int minSize;
    private int width, height;

    private int partitionCount;


    public BSPGenerator(int width, int height, int minSize, int partitionCount) {
        this.minSize = minSize;
        this.width = width;
        this.height = height;
        this.partitionCount = partitionCount;
    }

    public void binaryPartition() {
        if (root == null) root = new BSPVertex(0,0, width,height);
        PriorityQueue<BSPVertex> BSPLeaves = new PriorityQueue<>(partitionCount+1, Collections.reverseOrder());
        BSPLeaves.add(root);
        boolean canSplit = true;
        int partCounter = 0;

        while (canSplit && partCounter < partitionCount) {
            canSplit = false;
            BSPVertex BSPLeaf = BSPLeaves.peek();

            if (BSPLeaf.left == null && BSPLeaf.right == null && split(BSPLeaf)) {
                BSPLeaves.poll();
                BSPLeaves.add(BSPLeaf.left);
                BSPLeaves.add(BSPLeaf.right);

                canSplit = true;
                partCounter++;
            }
        }

        subSpaces = new ArrayList<>(BSPLeaves.size());
        for (BSPVertex BSPLeaf : BSPLeaves) {
            subSpaces.add(new Rectangle(BSPLeaf.x, BSPLeaf.y, BSPLeaf.width, BSPLeaf.height));
        }
    }

    public void printBSPTree() {
        printBSPTree(root, 0);
    }

    private void printBSPTree(BSPVertex BSPLeaf, int level) {
        if (BSPLeaf == null) return;
        System.out.println("  ".repeat(level) + "["+ BSPLeaf.x+", "+ BSPLeaf.y+"] "+"["+ BSPLeaf.width+", "+ BSPLeaf.height+"]");
        printBSPTree(BSPLeaf.left, level+1);
        printBSPTree(BSPLeaf.right, level+1);
    }

    private boolean split(BSPVertex BSPLeaf) {
        boolean isHorizontal = BSPLeaf.width < BSPLeaf.height;
        return (isHorizontal) ? horizontalSplit(BSPLeaf) : verticalSplit(BSPLeaf);
    }

    private boolean horizontalSplit(BSPVertex BSPLeaf) {
        if (BSPLeaf.height < 2*minSize) return false;
        int split = random.nextInt(minSize, BSPLeaf.height-minSize+1);

        BSPLeaf.left = new BSPVertex(BSPLeaf.x, BSPLeaf.y, BSPLeaf.width, split);
        BSPLeaf.right = new BSPVertex(BSPLeaf.x, BSPLeaf.y+split, BSPLeaf.width, BSPLeaf.height-split);
        return true;
    }

    private boolean verticalSplit(BSPVertex BSPLeaf) {
        if (BSPLeaf.width < 2*minSize) return false;
        int split = random.nextInt(minSize, BSPLeaf.width-minSize+1);

        BSPLeaf.left = new BSPVertex(BSPLeaf.x, BSPLeaf.y, split, BSPLeaf.height);
        BSPLeaf.right = new BSPVertex(BSPLeaf.x+split, BSPLeaf.y, BSPLeaf.width-split, BSPLeaf.height);
        return true;
    }

    public List<Rectangle> getSubSpaces() {
        return subSpaces;
    }

    public static class BSPVertex implements Comparable<BSPVertex> {
        private BSPVertex left, right;
        private int width, height;
        private int x, y;

        public BSPVertex(int x, int y, int width, int height) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(BSPVertex o) {
            return (int)Math.pow(Math.max(width,height), 2) - (int)Math.pow(Math.max(o.width,o.height), 2);
        }
    }
}
