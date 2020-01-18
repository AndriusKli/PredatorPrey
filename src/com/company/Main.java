package com.company;

public class Main {

    public static void main(String[] args) {

        TheGrid grid = new TheGrid(5, 15);
        grid.start();
    }
}



/*

 Buglist:
 *  Organisms cannot spawn their babies on the field they were just on.

*/