package com.company;

public class Main {

    public static void main(String[] args) {
        TheGrid grid = new TheGrid();
//        System.out.println(grid.getPlayField()[0][0]);
        Mouse testMouse = new Mouse(0, 0);
        grid.addOrganism(testMouse);
        grid.addOrganism(new Mouse(0, 1));
        grid.addOrganism(new Mouse(1, 1));


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
