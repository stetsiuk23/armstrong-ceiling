package com.example.myarmstrongapplication.ceiling;

public class Ceiling {
    private final double width;
    private final double height;

    public Ceiling(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getSquare() {
        return countSquare(this.width, this.height);
    }

    public double getPerimeter() {
        return countPerimeter(this.width, this.height);
    }

    private double countSquare(double width, double height) {
        return width * height;
    }

    private double countPerimeter(double width, double height) {
        return (width + height) * 2;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

}
