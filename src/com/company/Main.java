package com.company;

public class Main {


    public static void main(String[] args) {
        TheGrid grid = new TheGrid();

        grid.addMouse(new Mouse(0, 1));
        grid.addMouse(new Mouse(1, 1));
        grid.addBug(new Bug(2, 2));
        grid.addBug(new Bug(1, 0));
        grid.addBug(new Bug(1, 3));
        grid.addBug(new Bug(3, 0));

        grid.updateOrganismList();
        grid.updatePlayField();
        grid.printPlayField();

        grid.start();

    }
}
