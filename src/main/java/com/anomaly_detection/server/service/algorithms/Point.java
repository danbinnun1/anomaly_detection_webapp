package com.anomaly_detection.server.service.algorithms;

public class Point
{
	public float x;
	public float y;
	public Point(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	public Point(Point other){
		this.x=other.x;
		this.y=other.y;
	}
}