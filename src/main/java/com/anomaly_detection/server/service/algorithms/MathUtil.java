package com.anomaly_detection.server.service.algorithms;

import java.util.ArrayList;
import java.util.Random;

public class MathUtil {

    public static float avg(float[] x) {
        float sum = 0F;
        for (float num : x) {
            sum += num;
        }
        return sum / x.length;
    }

    // returns the variance of X and Y
    public static float variance(float[] x) {
        float av = avg(x);
        float sum = 0F;
        for (float num : x) {
            sum += num * num;
        }
        return sum / x.length - av * av;
    }

    // returns the covariance of X and Y
    public static float cov(float[] x, float[] y) {
        float sum = 0F;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }
        sum /= x.length;
        return sum - avg(x) * avg(y);
    }

    // returns the Pearson correlation coefficient of X and Y
    public static float pearson(float[] x, float[] y) {
        return (float) (cov(x, y) / (Math.sqrt(variance(x)) * Math.sqrt(variance(y))));
    }

    // performs a linear regression and returns the line equation
    public static Line linear_reg(Point[] points, int size) {
        float[] x = new float[size];
        float[] y = new float[size];
        for (int i = 0; i < size; i++) {
            x[i] = points[i].getX();
            y[i] = points[i].getY();
        }
        float a = cov(x, y) / variance(x);
        float b = avg(y) - a * (avg(x));
        return new Line(a, b);
    }

    // returns the deviation between point p and the line equation of the points
    public static float dev(Point p, Point[] points, int size) {
        Line l = linear_reg(points, size);
        return dev(p, l);
    }

    // returns the deviation between point p and the line
    public static float dev(Point p, Line l) {
        return Math.abs(p.getY() - l.f(p.getX()));
    }

    public static float dist(Point a, Point b) {
        float x2 = (a.getX() - b.getX()) * (a.getX() - b.getX());
        float y2 = (a.getY() - b.getY()) * (a.getY() - b.getY());
        return (float) Math.sqrt(x2 + y2);
    }

    public static Circle from2points(Point a, Point b) {
        float x = (a.getX() + b.getX()) / 2;
        float y = (a.getY() + b.getY()) / 2;
        float r = dist(a, b) / 2;
        return new Circle(new Point(x, y), r);
    }


    public static Circle from3Points(Point a, Point b, Point c) {
        // find the circumcenter of the triangle a,b,c
        Point mAB = new Point((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2); // mid point of line AB
        float slopAB = (b.getY() - a.getY()) / (b.getX() - a.getX()); // the slop of AB
        float pSlopAB = -1 / slopAB; // the perpendicular slop of AB
        // pSlop equation is:
        // y - mAB.y = pSlopAB * (x - mAB.x) ==> y = pSlopAB * (x - mAB.x) + mAB.y
        Point mBC = new Point((b.getX() + c.getX()) / 2, (b.getY() + c.getY()) / 2); // mid point of line BC
        float slopBC = (c.getY() - b.getY()) / (c.getX() - b.getX()); // the slop of BC
        float pSlopBC = -1 / slopBC; // the perpendicular slop of BC
        // pSlop equation is:
        // y - mBC.y = pSlopBC * (x - mBC.x) ==> y = pSlopBC * (x - mBC.x) + mBC.y
		/*
		pSlopAB * (x - mAB.x) + mAB.y = pSlopBC * (x - mBC.x) + mBC.y
		pSlopAB*x - pSlopAB*mAB.x + mAB.y = pSlopBC*x - pSlopBC*mBC.x + mBC.y
		
		x*(pSlopAB - pSlopBC) = - pSlopBC*mBC.x + mBC.y + pSlopAB*mAB.x - mAB.y
		x = (- pSlopBC*mBC.x + mBC.y + pSlopAB*mAB.x - mAB.y) / (pSlopAB - pSlopBC);
		
		*/
        float x = (-pSlopBC * mBC.getX() + mBC.getY() + pSlopAB * mAB.getX() - mAB.getY()) / (pSlopAB - pSlopBC);
        float y = pSlopAB * (x - mAB.getX()) + mAB.getY();
        Point center = new Point(x, y);
        float R = dist(center, a);
        return new Circle(center, R);
    }

    public static Circle trivial(ArrayList<Point> P) {
        if (P.size() == 0) {
            return new Circle(new Point(0F, 0F), 0F);
        } else if (P.size() == 1) {
            return new Circle(P.get(0), 0F);
        } else if (P.size() == 2) {
            return from2points(P.get(0), P.get(1));
        }
        // maybe 2 of the points define a small circle that contains the 3ed point
        Circle c = from2points(P.get(0), P.get(1));
        if (dist(P.get(2), c.getCenter()) <= c.getRadius()) {
            return c;
        }
        c = from2points(P.get(0), P.get(2));
        if (dist(P.get(1), c.getCenter()) <= c.getRadius()) {

            return c;
        }
        c = from2points(P.get(1), P.get(2));
        if (dist(P.get(0), c.getCenter()) <= c.getRadius()) {

            return c;
        }
        // else find the unique circle from 3 points
        return from3Points(P.get(0), P.get(1), P.get(2));
    }

/*
algorithm welzl
    input: Finite sets P and R of points in the plane |R|<= 3.
    output: Minimal disk enclosing P with R on the boundary.

    if P is empty or |R| = 3 then
        return trivial(R)
    choose p in P (randomly and uniformly)
    D := welzl(P - { p }, R)
    if p is in D then
        return D

    return welzl(P - { p }, R U { p })
 */

    public static Circle welzl(Point[] P, ArrayList<Point> R, int n) {
        if (n == 0 || R.size() == 3) {

            return trivial(R);
        }
        Random random = new Random();
        int i = random.nextInt(n);
        Point p = new Point(P[i].getX(), P[i].getY());
        Point temp = P[i];
        P[i] = P[n - 1];
        P[n - 1] = temp;
        Circle c = welzl(P, new ArrayList<Point>(R), n - 1);
        if (dist(p, c.getCenter()) <= c.getRadius()) {

            return c;
        }
        R.add(p);
        return welzl(P, new ArrayList<Point>(R), n - 1);
    }

    public static Circle findMinCircle(Point[] points, int size) {
        return welzl(points, new ArrayList<Point>(), size);
    }
}