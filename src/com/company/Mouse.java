package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Mouse extends Organism {
    private static int instance = 0;
    private int id;
    private int foodReserve = 3;
    private int bugsEaten = 0;
    private ArrayList<HashMap.Entry> viableMoves;  // All directions the creature can move that won't put it out of bounds, contained in ArrayList for easy randomization.


    public Mouse(int coordinateY, int coordinateX) {
        super(coordinateY, coordinateX);
        this.viableMoves = new ArrayList<>();
        instance++;
        this.id = instance;
    }

    @Override
    void move() {
        if (isAlive()) {
            // todo breed
            viableMoves();
            super.setTurnAlive(getTurnAlive() + 1);
            HashMap.Entry pickedDirection = viableMoves.get(super.getRandom().nextInt(viableMoves.size()));  // Randomly pick viable direction
            System.out.println("Mouse " + id + " picked direction " + pickedDirection); // Debugging
            if (pickedDirection.getValue() == TheGrid.FieldStatus.EMPTY) {
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
            } else if (pickedDirection.getValue() == TheGrid.FieldStatus.BUG) {
                System.out.println("Delicious bug!");
                // TODO destroy bug before it moves.
                this.bugsEaten++;
                this.foodReserve = 3;
            } else if (pickedDirection.getValue() == TheGrid.FieldStatus.MOUSE) {
                System.out.println("Mouse " + id + " bumped into another mouse.");
            } else {
                System.out.println("Nothing to do for mouse " + id + ". Standing still.");
            }

            foodReserve--;
            if (foodReserve < -1) {
                super.setAlive(false);
                System.out.println("Oh no, mouse " + id + " starved.");
            }
        }
    }


    public void viableMoves() {
        for (HashMap.Entry direction : super.getAdjacentFields().entrySet()) {
            if (direction.getValue() != TheGrid.FieldStatus.OUT_OF_BOUNDS) {
                viableMoves.add(direction);
            }
        }
    }

}
