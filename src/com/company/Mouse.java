package com.company;

import java.util.HashMap;

public class Mouse extends Organism {
    private int id;
    private int foodReserve = 3;
    private static int instance = 0;
    private static int bugsEaten = 0;
    private static int miceStarved = 0;


    public Mouse(int coordinateY, int coordinateX) {
        super(coordinateY, coordinateX);
        instance++;
        this.id = instance;
        super.setTurnsToBreed(8);
    }

    @Override
    void defaultTurnsToBreed() {
        super.setTurnsToBreed(8);
    }

    public static int getInstance() {
        return instance;
    }

    public static int getBugsEaten() {
        return bugsEaten;
    }

    public static int getMiceStarved() {
        return miceStarved;
    }

    @Override
    void move() {
        if (isAlive()) {
            setTurnsToBreed(getTurnsToBreed() - 1);
            super.viableMoves();
            if (super.getViableMoves().size() > 0) {
                HashMap.Entry pickedDirection = super.getViableMoves().get(super.getRandom().nextInt(super.getViableMoves().size()));  // Randomly pick viable direction
                System.out.println("Mouse " + id + " picked direction " + pickedDirection); // Debugging

                if (pickedDirection.getValue() == TheGrid.FieldStatus.EMPTY || pickedDirection.getValue() == TheGrid.FieldStatus.BUG) {
                    Object direction = pickedDirection.getKey();
                    if (direction == TheGrid.directions.NORTH) {
                        super.setCoordinateY(super.getCoordinateY() - 1);
                    } else if (direction == TheGrid.directions.SOUTH) {
                        super.setCoordinateY(super.getCoordinateY() + 1);
                    } else if (direction == TheGrid.directions.EAST) {
                        super.setCoordinateX(super.getCoordinateX() + 1);
                    } else {
                        super.setCoordinateX(super.getCoordinateX() - 1);
                    }

                    if (pickedDirection.getValue() == TheGrid.FieldStatus.BUG) {
                        System.out.println("Delicious bug! Mouse " + id + " ate a bug.");
                        bugsEaten++;
                        this.foodReserve = 3;
                    }
                } else if (pickedDirection.getValue() == TheGrid.FieldStatus.MOUSE) {
                    System.out.println("Mouse " + id + " bumped into another mouse.");
                } else {
                    System.out.println("Shouldn't show this result. Nothing to do for mouse " + id + ". Standing still.");
                }
            } else {
                System.out.println("Nowhere to go for mouse " + id + ". Standing still.");
            }
            foodReserve--;
            if (foodReserve < 0) {
                super.setAlive(false);
                miceStarved++;
                System.out.println("Oh no, mouse " + id + " starved.");
            }
        }
    }


    @Override
    Organism breed() {
        if (emptyFields().size() > 0) {
            HashMap.Entry randomEntry = emptyFields().get(getRandom().nextInt(emptyFields().size()));
            Object direction = randomEntry.getKey();
            if (direction == TheGrid.directions.NORTH) {
                defaultTurnsToBreed();
                System.out.println("Mouse " + id + " spawned a mouse north."); // Debug
                return new Mouse(super.getCoordinateY() - 1, super.getCoordinateX());
            } else if (direction == TheGrid.directions.SOUTH) {
                defaultTurnsToBreed();
                System.out.println("Mouse " + id + " spawned a mouse south."); // Debug
                return new Mouse(super.getCoordinateY() + 1, super.getCoordinateX());
            } else if (direction == TheGrid.directions.EAST) {
                defaultTurnsToBreed();
                System.out.println("Mouse " + id + " spawned a mouse east."); // Debug
                return new Mouse(super.getCoordinateY(), super.getCoordinateX() + 1);
            } else if (direction == TheGrid.directions.WEST){
                defaultTurnsToBreed();
                System.out.println("Mouse " + id + " spawned a mouse west."); // Debug
                return new Mouse(super.getCoordinateY(), super.getCoordinateX() - 1);
            } else {
                System.out.println("This statement should be unreachable"); // Debug
                return null;
            }

        } else {
            System.out.println("Nowhere to spawn babies.");
            setTurnsToBreed(1);
            return null;
        }
    }

    //// Debug

    public void adjacentFieldsTesterMouse() {
        for (HashMap.Entry direction : super.getViableMoves()) {
            System.out.println("Mouse " + id + " " + direction.getKey() + " " + direction.getValue());
        }
    }
}
