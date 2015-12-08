package com.epam.maze.util.entity;

import java.util.LinkedList;
import java.util.List;

public class Maze {
    private final int wx;
    private final int wy;
    private final int[][] mazeMap;
    private final List<Coordinates> resolvePath;

    public Maze(int wx, int wy, int[][] mazeMap, List<Coordinates> resolvePath) {
        this.wx = wx;
        this.wy = wy;
        this.mazeMap = mazeMap;
        this.resolvePath = resolvePath;
    }

    public int getWx() {
        return wx;
    }

    public int getWy() {
        return wy;
    }

    public int[][] getMazeMap() {
        return mazeMap;
    }

    public List<Coordinates> getResolvePath() {
        return resolvePath;
    }

}
