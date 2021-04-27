package com.anomaly_detection.server.service.algorithms;

import java.util.ArrayList;
import java.util.Random;

public class GlobalMembers {
    /*
     * animaly_detection_util.cpp
     *
     *  Created on: 11 ����� 2020
     *      Author: Eli
     */


    public static float avg(float[] x, int size) {
        float sum = 0F;
        for (int i = 0; i < size; sum += x[i], i++) {
            ;
        }
        return sum / size;
    }

// returns the variance of X and Y

    // returns the variance of X and Y
    public static float variance(float[] x, int size) {
        float av = avg(x, size);
        float sum = 0F;
        for (int i = 0; i < size; i++) {
            sum += x[i] * x[i];
        }
        return sum / size - av * av;
    }

// returns the covariance of X and Y

    // returns the covariance of X and Y
    public static float cov(float[] x, float[] y, int size) {
        float sum = 0F;
        for (int i = 0; i < size; i++) {
            sum += x[i] * y[i];
        }
        sum /= size;

        return sum - avg(x, size) * avg(y, size);
    }

// returns the Pearson correlation coefficient of X and Y


    // returns the Pearson correlation coefficient of X and Y
    public static float pearson(float[] x, float[] y, int size) {
        return (float) (cov(x, y, size) / (Math.sqrt(variance(x, size)) * Math.sqrt(variance(y, size))));
    }

// performs a linear regression and returns the line equation


    // performs a linear regression and returns the line equation
    public static Line linear_reg(Point[] points, int size) {
        float[] x = new float[size];
        float[] y = new float[size];
        for (int i = 0; i < size; i++) {
            x[i] = points[i].x;
            y[i] = points[i].y;
        }
        float a = cov(x, y, size) / variance(x, size);
        float b = avg(y, size) - a * (avg(x, size));

        return new Line(a, b);
    }

// returns the deviation between point p and the line equation of the points

    // returns the deviation between point p and the line equation of the points
    public static float dev(Point p, Point[] points, int size) {
        Line l = linear_reg(points, size);

        return dev(new Point(p), l);
    }

// returns the deviation between point p and the line

    // returns the deviation between point p and the line
    public static float dev(Point p, Line l) {
        return Math.abs(p.y - l.f(p.x));
    }


    // --------------------------------------


    public static float dist(Point a, Point b) {
        float x2 = (a.x - b.x) * (a.x - b.x);
        float y2 = (a.y - b.y) * (a.y - b.y);
        return (float) Math.sqrt(x2 + y2);
    }

    public static Circle from2points(Point a, Point b) {
        float x = (a.x + b.x) / 2;
        float y = (a.y + b.y) / 2;
        float r = dist(a, b) / 2;
        return new Circle(new Point(x, y), r);
    }



    public static Circle from3Points(Point a, Point b, Point c) {
        // find the circumcenter of the triangle a,b,c
        Point mAB = new Point((a.x + b.x) / 2, (a.y + b.y) / 2); // mid point of line AB
        float slopAB = (b.y - a.y) / (b.x - a.x); // the slop of AB
        float pSlopAB = -1 / slopAB; // the perpendicular slop of AB
        // pSlop equation is:
        // y - mAB.y = pSlopAB * (x - mAB.x) ==> y = pSlopAB * (x - mAB.x) + mAB.y

        Point mBC = new Point((b.x + c.x) / 2, (b.y + c.y) / 2); // mid point of line BC
        float slopBC = (c.y - b.y) / (c.x - b.x); // the slop of BC
        float pSlopBC = -1 / slopBC; // the perpendicular slop of BC
        // pSlop equation is:
        // y - mBC.y = pSlopBC * (x - mBC.x) ==> y = pSlopBC * (x - mBC.x) + mBC.y

		/*
		pSlopAB * (x - mAB.x) + mAB.y = pSlopBC * (x - mBC.x) + mBC.y
		pSlopAB*x - pSlopAB*mAB.x + mAB.y = pSlopBC*x - pSlopBC*mBC.x + mBC.y
		
		x*(pSlopAB - pSlopBC) = - pSlopBC*mBC.x + mBC.y + pSlopAB*mAB.x - mAB.y
		x = (- pSlopBC*mBC.x + mBC.y + pSlopAB*mAB.x - mAB.y) / (pSlopAB - pSlopBC);
		
		*/

        float x = (-pSlopBC * mBC.x + mBC.y + pSlopAB * mAB.x - mAB.y) / (pSlopAB - pSlopBC);
        float y = pSlopAB * (x - mAB.x) + mAB.y;
        Point center = new Point(x, y);
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: float R=dist(center,a);
        float R = dist(new Point(center), new Point(a));

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

        if (dist(P.get(2), c.center) <= c.radius) {
            return c;
        }

        c = from2points(P.get(0), P.get(2));

        if (dist(P.get(1), c.center) <= c.radius) {

            return c;
        }

        c = from2points(P.get(1), P.get(2));
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: if(dist(P[0],c.center)<=c.radius)
        if (dist(P.get(0), new Point(c.center)) <= c.radius) {
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: return c;
            return new Circle(c);
        }
        // else find the unique circle from 3 points
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: return from3Points(P[0],P[1],P[2]);
        return new Circle(from3Points(P.get(0), P.get(1), P.get(2)));
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
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: return trivial(R);
            return new Circle(trivial(R));
        }

        // remove random point p
        // swap is more efficient than remove
        //srand (time(NULL));
        Random random=new Random();
        int i = random.nextInt(n);
        Point p = new Point(P[i].x, P[i].y);
        //swap(P[i],P[n - 1]);
        Point temp = P[i];
        P[i] = P[n - 1];
        P[n - 1] = temp;

        Circle c = welzl(P, new ArrayList<Point>(R), n - 1);

//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: if(dist(p,c.center)<=c.radius)
        if (dist(new Point(p), new Point(c.center)) <= c.radius) {
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: return c;
            return new Circle(c);
        }

        R.add(p);

//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: return welzl(P,R,n-1);
        return new Circle(welzl(P, new ArrayList<Point>(R), n - 1));
    }

    public static Circle findMinCircle(Point[] points, int size) {
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: return welzl(points,{},size);
        return new Circle(welzl(points, new ArrayList<Point>(), size));
    }


}