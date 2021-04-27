package com.anomaly_detection.server.service.algorithms;

public class Point {
    private float x;

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
}