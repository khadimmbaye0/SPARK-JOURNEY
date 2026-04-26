package org.example;

public class IntegerWithSquare {
    private int originalNumber;
    private double squareNumber;

    public IntegerWithSquare(int i) {
        this.originalNumber = i;
        this.squareNumber = Math.sqrt(i);
    }
}
