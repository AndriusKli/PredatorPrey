package com.company;

import java.util.HashMap;

public class Bug extends Organism {
    private static int instance;
    private int id;


    public Bug(int coordinateY, int coordinateX) {
        super(coordinateY, coordinateX);
        instance++;
        id = instance;
        super.setTurnsToBreed(3);
    }

    public static int getInstance() {
        return instance;
    }

    @Override
    void defaultTurnsToBreed() {
        super.setTurnsToBreed(3);
    }

    @Override
    void move() {
        if (isAlive()) {
            setTurnsToBreed(getTurnsToBreed() - 1);
            super.viableMoves();
            if (super.getViableMoves().size() > 0) {
                HashMap.Entry pickedDirection = super.getViableMoves().get(super.getRandom().nextInt(super.getViableMoves().size()));  // Randomly pick viable direction
                System.out.println("Bug " + id +  " on coordinate " + getCoordinateY() + "y " + getCoordinateX() + "x picked direction " + pickedDirection); // Debugging

                if (pickedDirection.getValue() == TheGrid.FieldStatus.EMPTY) {
                    Object direction = pickedDirection.getKey();
                    if (direction == TheGrid.directions.NORTH) {
                        System.out.println("Bug " + id + " went north."); // Debug
                        super.setCoordinateY(super.getCoordinateY() - 1);
                    } else if (direction == TheGrid.directions.SOUTH) {
                        System.out.println("Bug " + id + " went south."); // Debug
                        super.setCoordinateY(super.getCoordinateY() + 1);
                    } else if (direction == TheGrid.directions.EAST) {
                        System.out.println("Bug " + id + " went east."); // Debug
                        super.setCoordinateX(super.getCoordinateX() + 1);
                    } else {
                        System.out.println("Bug " + id + " went west."); // Debug
                        super.setCoordinateX(super.getCoordinateX() - 1);
                    }
                } else {
                    System.out.println("Nowhere to go for bug " + id + ". Standing still.");  // Debug
                }

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
                System.out.println("Bug " + id + " spawned a Bug north."); // Debug
                return new Bug(super.getCoordinateY() - 1, super.getCoordinateX());
            } else if (direction == TheGrid.directions.SOUTH) {
                defaultTurnsToBreed();
                System.out.println("Bug " + id + " spawned a Bug south."); // Debug
                return new Bug(super.getCoordinateY() + 1, super.getCoordinateX());
            } else if (direction == TheGrid.directions.EAST) {
                defaultTurnsToBreed();
                System.out.println("Bug " + id + " spawned a Bug east."); // Debug
                return new Bug(super.getCoordinateY(), super.getCoordinateX() + 1);
            } else if (direction == TheGrid.directions.WEST) {
                defaultTurnsToBreed();
                System.out.println("Bug " + id + " spawned a Bug west."); // Debug
                return new Bug(super.getCoordinateY(), super.getCoordinateX() - 1);
            } else {
                System.out.println("This statement should be unreachable"); // Debug
                return null;
            }

        } else {
            System.out.println("Nowhere to spawn babies. Waiting a turn."); // Debug
            setTurnsToBreed(1);
            return null;
        }
    }
}
