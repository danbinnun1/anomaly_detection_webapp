package com.anomaly_detection.server.service.algorithms;

import java.io.IOException;
import java.util.List;

public class Line {
    public float a;
    public float b;

    public Line() {
        this.a = 0F;
        this.b = 0F;
    }

    public Line(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public final float f(float x) {
        return a * x + b;
    }

    public Line(Line other) {
        this.a = other.a;
        this.b = other.b;
    }
}