package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TheGrid {
    private Scanner scanner;
    private int turn = 0;
    private Random random;
    private Organism[][] playField;
    private ArrayList<Organism> organisms;


    public TheGrid() {
        this.scanner = new Scanner(System.in);
        this.playField = new Organism[20][20];
        this.random = new Random();
        this.organisms = new ArrayList<>();
    }


    public enum FieldStatus {
        OUT_OF_BOUNDS,
        EMPTY,
        MOUSE,
        BUG,
    }

    public enum directions {
        NORTH,
        SOUTH,
        EAST,
        WEST,
    }

    public FieldStatus getCoordinateStatus(int y, int x) {
        if (y > 20 || x > 20 || y < 0 || x < 0) {
            return FieldStatus.OUT_OF_BOUNDS;
        }
        if (playField[y][x] instanceof Mouse) {
            return FieldStatus.MOUSE;
        } else if (playField[y][x] instanceof Bug) {
            return FieldStatus.BUG;
        } else {
            return FieldStatus.EMPTY;
        }
    }

    public void addOrganism(Organism organism) {
        this.organisms.add(organism);
    }


    private void checkDirections(Organism organism) {
        organism.getAdjacentFields().put(directions.NORTH, getCoordinateStatus(organism.getCoordinateY() - 1, organism.getCoordinateX()));
        organism.getAdjacentFields().put(directions.SOUTH, getCoordinateStatus(organism.getCoordinateY() + 1, organism.getCoordinateX()));
        organism.getAdjacentFields().put(directions.EAST, getCoordinateStatus(organism.getCoordinateY(), organism.getCoordinateX() + 1));
        organism.getAdjacentFields().put(directions.WEST, getCoordinateStatus(organism.getCoordinateY(), organism.getCoordinateX() - 1));
    }

    public Organism[][] getPlayField() {
        return playField;
    }

    private int randomCoordinate() {
        return random.nextInt(21);
    }

    public void spawn(int amount, Organism organism) {

    }


    public void updatePlayField() {
        this.playField = new Organism[20][20];
        for (Organism organism : organisms) {
            if (organism.isAlive()) {
                playField[organism.getCoordinateY()][organism.getCoordinateX()] = organism;
            }
        }
    }

//    public void updateDirections() {
//        for (Organism organism : organisms) {
//            if (organism.isAlive()) {
//                checkDirections(organism);
//            }
//        }
//    }
// Each organism needs to check its surrounding BEFORE moving, otherwise they might 'step' on one another.

    public void moveOrganisms() {
        for (Organism organism : organisms) {
            if (organism.isAlive()) {
                checkDirections(organism);
                organism.move();
                updatePlayField();
            }
        }
    }

    public void printPlayField() {
        for (int i = 0; i < playField.length; i++) {
            for (int j = 0; j < 20; j++) {
                if (playField[i][j] == null) {
                    System.out.print(" — ");
                } else if (playField[i][j] instanceof Mouse) {
                    System.out.print(" o ");
                } else if (playField[i][j] instanceof Bug) {
                    System.out.print(" x ");
                } else {
                    System.out.print("ERR");
                }
            }
            System.out.println();
        }
    }


}
