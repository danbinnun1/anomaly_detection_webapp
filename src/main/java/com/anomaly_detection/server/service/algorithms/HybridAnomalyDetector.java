package com.anomaly_detection.server.service.algorithms;

public class HybridAnomalyDetector extends SimpleAnomalyDetector
{
	public HybridAnomalyDetector()
	{
		// TODO Auto-generated constructor stub

	}



	@Override
	public void learnHelper(final TimeSeries ts, float p, String f1, String f2, Point[] ps)
	{
		super.learnHelper(ts, p, f1, f2, ps);
		if (p > 0.5F && p < threshold)
		{
			Circle cl = MathUtil.findMinCircle(ps,ts.getRowSize());
			correlatedFeatures c = new correlatedFeatures();
			c.feature1 = f1;
			c.feature2 = f2;
			c.correlation = p;
			c.threshold = (float) (cl.getRadius() * 1.1); // 10% increase
			c.cx = cl.getCenter().getX();
			c.cy = cl.getCenter().getY();
			cf.add(c);
		}
	}

	@Override
	public boolean isAnomalous(float x, float y, correlatedFeatures c)
	{
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: return (c.corrlation>=threshold && SimpleAnomalyDetector::isAnomalous(x,y,c)) || (c.corrlation>0.5 && c.corrlation<threshold && dist(Point(c.cx,c.cy),Point(x,y))> c.threshold);
		return (c.correlation >= threshold && super.isAnomalous(x, y, c)) || (c.correlation > 0.5F && c.correlation < threshold && MathUtil.dist(new Point(c.cx, c.cy),new Point(x, y))> c.threshold);
	}
}