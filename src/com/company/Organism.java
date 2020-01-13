package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Organism {
    private int turnsToBreed = 0;
    private int coordinateY;
    private int coordinateX;
    private HashMap<Enum, Enum> adjacentFields;
    private Random random;
    private boolean alive = true;
    private ArrayList<HashMap.Entry> viableMoves;  // All directions the creature can move that won't put it out of bounds, contained in ArrayList for easy randomization.
    private ArrayList<HashMap.Entry> emptyFields;

    public Organism(int coordinateY, int coordinateX) {
        this.random = new Random();
        this.coordinateY = coordinateY;
        this.coordinateX = coordinateX;
        this.adjacentFields = new HashMap<>();
        this.viableMoves = new ArrayList<>();
        this.emptyFields = new ArrayList<>();

    }

    abstract void move();

    abstract void defaultTurnsToBreed();

    public int getCoordinateY() {
        return this.coordinateY;
    }

    public int getCoordinateX() {
        return this.coordinateX;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getTurnsToBreed() {
        return turnsToBreed;
    }

    public ArrayList<HashMap.Entry> getViableMoves() {
        return viableMoves;
    }

    public HashMap<Enum, Enum> getAdjacentFields() {
        return adjacentFields;
    }

    public Random getRandom() {
        return random;
    }

    public void setTurnsToBreed(int turnsToBreed) {
        this.turnsToBreed = turnsToBreed;
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

    public void viableMoves() {
        this.viableMoves = new ArrayList<>();
        for (HashMap.Entry direction : getAdjacentFields().entrySet()) {
            if (direction.getValue() != TheGrid.FieldStatus.OUT_OF_BOUNDS) {
                viableMoves.add(direction);
            }
        }
    }

    public ArrayList<HashMap.Entry> emptyFields() {
        this.emptyFields = new ArrayList<>();
        for (HashMap.Entry direction : adjacentFields.entrySet()) {
            if (direction.getValue() == TheGrid.FieldStatus.EMPTY) {
                emptyFields.add(direction);
            }
        }
        return emptyFields;
    }


    abstract Organism breed();

    //// Debugging

    public void adjacentFieldsTester() {
        for (HashMap.Entry direction : adjacentFields.entrySet()) {
            System.out.println(direction.getKey() + " " + direction.getValue());
        }
    }

    public void emptyFieldTester() {
        for (HashMap.Entry direction : emptyFields) {
            System.out.println(direction.getKey() + " " + direction.getValue());
        }
    }


    @Override
    public String toString() {
        return "Organism{" +
                "turnsToBreed=" + turnsToBreed +
                ", coordinateY=" + coordinateY +
                ", coordinateX=" + coordinateX +
                ", adjacentFields=" + adjacentFields +
                ", alive=" + alive +
                ", viableMoves=" + viableMoves +
                ", emptyFields=" + emptyFields +
                '}';
    }
}
