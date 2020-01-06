package com.company;

import java.util.HashMap;
import java.util.Random;

public abstract class Organism {
    private int turnAlive = 0;
    private int coordinateY;
    private int coordinateX;
    private HashMap<Enum, Enum> adjacentFields;
    private Random random;
    private boolean alive = true;

    public Organism(int coordinateY, int coordinateX) {
        this.coordinateY = coordinateY;
        this.coordinateX = coordinateX;
        this.adjacentFields = new HashMap<>();
        this.random = new Random();
    }

    abstract void move();

    public int getCoordinateY() {
        return this.coordinateY;
    }

    public int getCoordinateX() {
        return this.coordinateX;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getTurnAlive() {
        return turnAlive;
    }

    public HashMap<Enum, Enum> getAdjacentFields() {
        return adjacentFields;
    }

    public Random getRandom() {
        return random;
    }

    public void setTurnAlive(int turnAlive) {
        this.turnAlive = turnAlive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void adjacentFieldsTester() {
        for (HashMap.Entry direction : adjacentFields.entrySet()) {
            System.out.println(direction.getKey() + " " + direction.getValue());
        }
    }


}
