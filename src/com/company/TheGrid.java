package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TheGrid {
    private int turn = 0;
    private Random random;
    private Organism[][] playField;
    private ArrayList<Organism> mice;
    private ArrayList<Organism> bugs;
    private ArrayList<Organism> organisms;
    private ArrayList<Organism> newMice;
    private ArrayList<Organism> newBugs;
    private Scanner scanner;


    public TheGrid() {
        this.playField = new Organism[20][20];
        this.random = new Random();
        this.organisms = new ArrayList<>();
        this.mice = new ArrayList<>();
        this.bugs = new ArrayList<>();
        this.newMice = new ArrayList<>();
        this.newBugs = new ArrayList<>();
        this.scanner = new Scanner(System.in);
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

    public void addMouse(Organism organism) {
        this.mice.add(organism);
    }

    public void addBug(Organism organism) {
        this.bugs.add(organism);
    }

    public void addOrganism(Organism organism) {
        if (organism instanceof Mouse) {
            this.mice.add(organism);
        } else {
            this.bugs.add(organism);
        }
    }


    private void checkDirections(Organism organism) {
        organism.getAdjacentFields().put(directions.NORTH, getCoordinateStatus(organism.getCoordinateY() - 1, organism.getCoordinateX()));
        organism.getAdjacentFields().put(directions.SOUTH, getCoordinateStatus(organism.getCoordinateY() + 1, organism.getCoordinateX()));
        organism.getAdjacentFields().put(directions.EAST, getCoordinateStatus(organism.getCoordinateY(), organism.getCoordinateX() + 1));
        organism.getAdjacentFields().put(directions.WEST, getCoordinateStatus(organism.getCoordinateY(), organism.getCoordinateX() - 1));
    }

    private int randomCoordinate() {
        return random.nextInt(21);
    }

    public void spawn(int amount, Organism organism) {

    }

    public void updatePlayField() {
        this.playField = new Organism[20][20];
        for (Organism organism : organisms) {
            if(organism.isAlive()) {
                playField[organism.getCoordinateY()][organism.getCoordinateX()] = organism;
            }
        }
    }

    public void eatenBugs(Organism mouse) {
        for (Organism bug : bugs) {
            if (mouse.getCoordinateX() == bug.getCoordinateX() && mouse.getCoordinateY() == bug.getCoordinateY()) {
                bug.setAlive(false);
            }
        }
    }


    public void purgeDeadMice() {
        ArrayList<Organism> updated = new ArrayList<>();
        for (int i = 0; i < mice.size(); i++) {
            if (mice.get(i).isAlive()) {
                updated.add(mice.get(i));
            }
        }
        this.mice = updated;
    }

    public void purgeDeadBugs() {
        ArrayList<Organism> updated = new ArrayList<>();
        for (int i = 0; i < bugs.size(); i++) {
            if (bugs.get(i).isAlive()) {
                updated.add(bugs.get(i));
            }
        }
        this.bugs = updated;
    }

    public void updateOrganismList() {
        this.organisms.clear();
        organisms.addAll(mice);
        organisms.addAll(bugs);
    }





    private void takeATurn() {
        updateOrganismList();
        purgeDeadMice(); // Both of these checks for mice and bugs should probably be performed within the following for-each cycle.
        for (Organism mouse : mice) {
            checkDirections(mouse); // Each organism needs to check its surrounding BEFORE moving, otherwise they might unintentionally 'step' on one another.
            mouse.move();
            eatenBugs(mouse);
            if (mouse.getTurnsToBreed() == 0) {
                checkDirections(mouse);
                mouse.emptyFields();
//                mouse.emptyFieldTester();  // Debug
                Organism tempMouse = mouse.breed(); // Just in case there was no space to place the new mouse.
                if (tempMouse != null) {            //
                    organisms.add(tempMouse);       //
                    newMice.add(tempMouse);         //
                }
            }
            updatePlayField();
        }
        mice.addAll(newMice);
        newMice.clear();
        updateOrganismList();

        purgeDeadBugs();
        for (Organism bug : bugs) {
            checkDirections(bug);
            bug.move();
            if (bug.getTurnsToBreed() == 0) {
                checkDirections(bug);
                bug.emptyFields();
                Organism tempBug = bug.breed();
                if (tempBug != null) {
                    organisms.add(tempBug);
                    newBugs.add(tempBug);
                }
            }
            updatePlayField();
        }
        bugs.addAll(newBugs);
        newBugs.clear();
//        updateOrganismList();
    }

    // TODO
    // Move mice first. Check if any bugs share the same space as mice. Kill those bugs. Spawn new mice. Do the same for bugs. When moving bugs, make sure they don't step on mice.
    // All organisms probably should just keep the status of adjacent fields and not trim down the list before moving (as the move itself checks the status of the chosen direction)).

    public void printPlayField() {
        for (int i = 0; i < playField.length; i++) {
            for (int j = 0; j < 20; j++) {
                if (playField[i][j] == null) {
                    System.out.print(" — ");
                } else if (playField[i][j] instanceof Bug) {
                    System.out.print(" x ");
                } else if (playField[i][j] instanceof Mouse) {
                    System.out.print(" o ");
                } else {
                    System.out.print("ERR");
                }
            }
            System.out.println();
        }
    }

    public void start() {
        System.out.println("Press 1 to take a turn.\n" +
                "Press 2 to take X turns.\n" +
                "Press anything else to quit.");


        boolean loops = true;
        while (loops) {
            if (scanner.hasNextInt()) {
                switch (scanner.nextInt()) {
                    case 1:
                        takeATurn();
                        printPlayField();
                        break;
                    case 2:
                        System.out.println("How many turns do you want to take? Press Q to cancel.");
                        int amount = scanner.nextInt();
                        for (int i = 0; i < amount; i++) {
                            takeATurn();
                        }
                        printPlayField();
                        break;
                    default:
                        System.out.println("Bye");
                        loops = false;
                        break;
                }
            } else {
                System.out.println("Bye");
                break;
            }
        }
    }

 /*
 Buglist:
 *Creatures cannot spawn their babies on the field they were just on.
  */


}
