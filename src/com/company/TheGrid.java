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
    private int initialMiceSpawn;
    private int initialBugSpawn;


    public TheGrid(int mice, int bugs) {
        this.playField = new Organism[20][20];
        this.random = new Random();
        this.organisms = new ArrayList<>();
        this.mice = new ArrayList<>();
        this.bugs = new ArrayList<>();
        this.newMice = new ArrayList<>();
        this.newBugs = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.initialBugSpawn = bugs;
        this.initialMiceSpawn = mice;
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

    private FieldStatus getCoordinateStatus(int y, int x) {
        if (y >= 20 || x >= 20 || y < 0 || x < 0) {
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

    private void addMouse(Organism organism) {
        this.mice.add(organism);
    }

    private void addBug(Organism organism) {
        this.bugs.add(organism);
    }

    private void checkDirections(Organism organism) {
        organism.getAdjacentFields().put(directions.NORTH, getCoordinateStatus(organism.getCoordinateY() - 1, organism.getCoordinateX()));
        organism.getAdjacentFields().put(directions.SOUTH, getCoordinateStatus(organism.getCoordinateY() + 1, organism.getCoordinateX()));
        organism.getAdjacentFields().put(directions.EAST, getCoordinateStatus(organism.getCoordinateY(), organism.getCoordinateX() + 1));
        organism.getAdjacentFields().put(directions.WEST, getCoordinateStatus(organism.getCoordinateY(), organism.getCoordinateX() - 1));
    }

    private void updatePlayField() {
        this.playField = new Organism[20][20];
        for (Organism organism : organisms) {
            if (organism.isAlive()) {
                playField[organism.getCoordinateY()][organism.getCoordinateX()] = organism;
            }
        }
    }

    private void eatenBugs(Organism mouse) {
        for (Organism bug : bugs) {
            if (mouse.getCoordinateX() == bug.getCoordinateX() && mouse.getCoordinateY() == bug.getCoordinateY()) {
                bug.setAlive(false);
            }
        }
    }


    private void purgeDeadMice() {
        ArrayList<Organism> updated = new ArrayList<>();
        for (int i = 0; i < mice.size(); i++) {
            if (mice.get(i).isAlive()) {
                updated.add(mice.get(i));
            }
        }
        this.mice = updated;
    }

    private void purgeDeadBugs() {
        ArrayList<Organism> updated = new ArrayList<>();
        for (int i = 0; i < bugs.size(); i++) {
            if (bugs.get(i).isAlive()) {
                updated.add(bugs.get(i));
            }
        }
        this.bugs = updated;
    }

    private void updateOrganismList() {
        this.organisms.clear();
        organisms.addAll(mice);
        organisms.addAll(bugs);
    }

    private void takeATurn() {          // Move mice first. Check if any bugs share the same space as mice. Kill those bugs. Spawn new mice. Do the same for bugs. When moving bugs, make sure they don't step on mice.
        updateOrganismList();
        purgeDeadMice();                // Both of these checks for mice and bugs should probably be performed within the following for-each cycle.
        for (Organism mouse : mice) {
            checkDirections(mouse);     // Each organism needs to check its surrounding BEFORE moving, otherwise they might unintentionally 'step' on one another.
//            System.out.println(mouse.toString()); // Debug
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
//            System.out.println(bug.toString()); // Debug
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
        turn++;
    }

    private void printPlayField() {
        for (int i = 0; i < playField.length; i++) {
            for (int j = 0; j < 20; j++) {
                if (playField[i][j] == null) {
                    System.out.print("   ");
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
        spawnOrganisms(initialMiceSpawn, initialBugSpawn);
        updateOrganismList();
        updatePlayField();
        printPlayField();
        boolean loops = true;
        while (loops) {
            System.out.println("Press 1 to take a turn.\n" +
                    "Press 2 to take X turns.\n" +
                    "Press anything else to quit.");
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
                        System.out.println("Bye. Here are some stats");
                        printStats();
                        loops = false;
                        break;
                }
            } else {
                System.out.println("Bye. Here are some stats");
                printStats();
                break;
            }
        }
    }

    private void printStats() {
        System.out.printf("%nTurns taken: %d%nMice spawned: %d%nBugs spawned: %d%nTotal starved mice: %d%nTotal bugs eaten: %d%n", turn, Mouse.getInstance(), Bug.getInstance(), Mouse.getMiceStarved(), Mouse.getBugsEaten());
    }

    //
    // Methods for randomly spawning organisms on the field.
    //

    private ArrayList<int[]> emptyFields() {
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                result.add(new int[]{i, j});
            }
        }
        return result;
    }


    private void spawnOrganisms(int mice, int bugs) {
        if (mice + bugs > 400) {
            System.out.println("Not enough space to spawn that many organisms. Not spawning organisms.");
        } else {

            ArrayList<int[]> emptyFields = emptyFields();

            for (int i = 0; i < mice; i++) {
                int random = this.random.nextInt(emptyFields.size());
                int[] randomEntry = emptyFields.get(random);
                emptyFields.remove(random);
                addMouse(new Mouse(randomEntry[0], randomEntry[1]));
            }

            for (int i = 0; i < bugs; i++) {
                int random = this.random.nextInt(emptyFields.size());
                int[] randomEntry = emptyFields.get(random);
                emptyFields.remove(random);
                addBug(new Bug(randomEntry[0], randomEntry[1]));
            }
        }
    }
}
