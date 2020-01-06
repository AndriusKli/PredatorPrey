package com.company;

public class Bug extends Organism {
    private static int instance;
    private int id;

    public Bug(int coordinateY, int coordinateX) {
        super(coordinateY, coordinateX);
        instance++;
        id = instance;
    }

    @Override
    void move() {

    }
}
