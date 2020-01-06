package com.company;

public class Main {


    public static void main(String[] args) {
        TheGrid grid = new TheGrid();
//        System.out.println(grid.getPlayField()[0][0]);
        Mouse testMouse = new Mouse(0, 0);
        grid.addMouse(testMouse);
        grid.addMouse(new Mouse(0, 1));
        grid.addMouse(new Mouse(1, 1));
        grid.addBug(new Bug(2, 2));
        grid.addBug(new Bug(1, 0));

        grid.updateOrganismList();
        grid.updatePlayField();
        grid.printPlayField();


        grid.moveOrganisms();
        grid.printPlayField();


        grid.moveOrganisms();
        grid.printPlayField();


        grid.moveOrganisms();
        grid.printPlayField();


        grid.moveOrganisms();
        grid.printPlayField();

        grid.moveOrganisms();
        grid.printPlayField();

    }
}
