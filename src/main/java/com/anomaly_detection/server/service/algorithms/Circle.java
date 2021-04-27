package com.anomaly_detection.server.service.algorithms;// ------------ DO NOT CHANGE -----------


public class Circle {
    private Point center;
    private float radius;

    public Point getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    public Circle(Point c, float r) {
        this.center = c;
        this.radius = r;
    }
}