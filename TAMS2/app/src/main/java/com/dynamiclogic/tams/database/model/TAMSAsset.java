package com.dynamiclogic.tams.database.model;

public class TAMSAsset {

    private String id;
    private Coordinates coords;

    public TAMSAsset(Coordinates coords) {
        this.coords = coords;
    }
    public Coordinates getCoordinates() {
        return coords;
    }

    class Coordinates {

        double x, y;

        public Coordinates(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() { return x; }

        public void setX(double x) { this.x = x; }

        public double getY() { return y; }

        public void setY(double y) { this.y = y; }
    }
}
