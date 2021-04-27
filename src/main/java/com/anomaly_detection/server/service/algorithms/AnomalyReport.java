package com.anomaly_detection.server.service.algorithms;

public class AnomalyReport
{
	public final String description;
	public final int timeStep;
	public AnomalyReport(String description, int timeStep)
	{
		this.description = description;
		this.timeStep = timeStep;
	}
}