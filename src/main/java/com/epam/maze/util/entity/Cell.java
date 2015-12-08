package com.epam.maze.util.entity;

public class Cell {
    private final int cx;
    private final int cy;
    private String charPlaceHolder;
    private int walls;

    public Cell(int cx, int cy, int walls) {
        this.cx = cx;
        this.cy = cy;
        this.walls = walls;
    }

    public int getCx() {
        return cx;
    }

    public int getCy() {
        return cy;
    }

    public String getCharPlaceHolder() {
        return charPlaceHolder;
    }

    public void setCharPlaceHolder(String charPlaceHolder) {
        this.charPlaceHolder = charPlaceHolder;
    }

    public int getWalls() {
        return walls;
    }

    public void setWalls(int walls) {
        this.walls = walls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (cx != cell.cx) return false;
        return cy == cell.cy;

    }

    @Override
    public int hashCode() {
        int result = cx;
        result = 31 * result + cy;
        return result;
    }
}
