package com.anomaly_detection.server.service.algorithms;// ------------ DO NOT CHANGE -----------
//class Point{
//public:
//	float x,y;
//	Point(float x,float y):x(x),y(y){}
//};

public class Circle
{
	private Point center;

	public Point getCenter() {
		return center;
	}

	public float getRadius() {
		return radius;
	}

	private float radius;
	public Circle(Point c, float r)
	{
		this.center = c;
		this.radius = r;
	}

}