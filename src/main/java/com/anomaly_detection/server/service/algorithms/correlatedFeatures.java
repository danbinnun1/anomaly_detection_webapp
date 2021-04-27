package com.anomaly_detection.server.service.algorithms;

/*
 * SimpleAnomalyDetector.cpp
 *
 *  Created on: 8 ����� 2020
 *      Author: Eli
 */

/*
 * SimpleAnomalyDetector.h
 *
 *  Created on: 8 ����� 2020
 *      Author: Eli
 */


public class correlatedFeatures
{
	public int feature1; // names of the correlated features
	public int feature2;
	public float correlation;
	public Line lin_reg = new Line();
	public float threshold;
	public float cx;
	public float cy;


}