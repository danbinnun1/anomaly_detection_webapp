package com.anomaly_detection.server.service.algorithms;// ------------ DO NOT CHANGE -----------
//class Point{
//public:
//	float x,y;
//	Point(float x,float y):x(x),y(y){}
//};

public class Circle
{
	public Point center = new Point(0,0);
	public float radius;
	public Circle(Point c, float r)
	{
		this.center = new Point(c);
		this.radius = r;
	}
	public Circle(Circle circle){
		this.center=new Point(circle.center);
		this.radius=circle.radius;
	}
}