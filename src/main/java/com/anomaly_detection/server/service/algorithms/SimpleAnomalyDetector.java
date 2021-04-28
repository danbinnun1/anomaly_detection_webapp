package com.anomaly_detection.server.service.algorithms;

import java.util.ArrayList;

public class SimpleAnomalyDetector extends TimeSeriesAnomalyDetector
{
	protected ArrayList<correlatedFeatures> cf = new ArrayList<correlatedFeatures>();
	protected float threshold;
	public SimpleAnomalyDetector()
	{
		threshold = 0.9F;

	}


	@Override
	public void learnNormal(final TimeSeries ts)
	{
		ArrayList<String> atts = ts.gettAttributes();
		int len = ts.getRowSize();
		float[][] vals = new float[atts.size()][len];
		for (int i = 0;i < atts.size();i++)
		{
			ArrayList<Float> x = ts.getAttributeData(atts.get(i));
			for (int j = 0;j < len;j++)
			{
				vals[i][j] = x.get(j);
			}
		}

		for (int i = 0;i < atts.size();i++)
		{
			String f1 = atts.get(i);
			float max = 0F;
			int jmax = 0;
			for (int j = i + 1;j < atts.size();j++)
			{
				float p = Math.abs(MathUtil.pearson(vals[i],vals[j],len));
				if (p > max)
				{
					max = p;
					jmax = j;
				}
			}
			String f2 = atts.get(jmax);
			Point[] ps = toPoints(ts.getAttributeData(f1), ts.getAttributeData(f2));

			learnHelper(ts, max, f1, f2, ps);

			// delete points
			for (int k = 0;k < len;k++)
			{
				ps[k] = null;
			}
			ps = null;
		}
	}

	@Override
	public ArrayList<AnomalyReport> detect(final TimeSeries ts)
	{
		/*ArrayList<AnomalyReport> v = new ArrayList<AnomalyReport>();
//C++ TO JAVA CONVERTER TODO TASK: Only lambda expressions having all locals passed by reference can be converted to Java:
//ORIGINAL LINE: for_each(cf.begin(),cf.end(),[&v,&ts,this](correlatedFeatures c)
		for_each(cf.iterator(),cf.end(),(correlatedFeatures c) ->
		{
			ArrayList<Float> x = ts.getAttributeData(c.feature1);
			ArrayList<Float> y = ts.getAttributeData(c.feature2);
			for (int i = 0;i < x.size();i++)
			{
				if (isAnomalous(x.get(i), y.get(i), new correlatedFeatures(c)))
				{
					String d = c.feature1 + "-" + c.feature2;
					v.add(new AnomalyReport(d, (i + 1)));
				}
			}
		});
		return new ArrayList<AnomalyReport>(v);*/
		ArrayList<AnomalyReport> v = new ArrayList<AnomalyReport>();
		for (correlatedFeatures c :cf){
			ArrayList<Float> x = ts.getAttributeData(c.feature1);
			ArrayList<Float> y = ts.getAttributeData(c.feature2);
			for (int i = 0;i < x.size();i++)
			{
				if (isAnomalous(x.get(i), y.get(i), c))
				{
					String d = c.feature1 + "-" + c.feature2;
					v.add(new AnomalyReport(d, (i + 1)));
				}
			}
		}
		return v;
	}

	public final ArrayList<correlatedFeatures> getNormalModel()
	{
		return new ArrayList<correlatedFeatures>(cf);
	}
	public final void setCorrelationThreshold(float threshold)
	{
		this.threshold = threshold;
	}

	// helper methods
	protected void learnHelper(final TimeSeries ts, float p, String f1, String f2, Point[] ps)
	{
		if (p > threshold)
		{
			int len = ts.getRowSize();
			correlatedFeatures c = new correlatedFeatures();
			c.feature1 = f1;
			c.feature2 = f2;
			c.correlation = p;
			c.lin_reg = MathUtil.linear_reg(ps,len);
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: c.threshold=findThreshold(ps,len,c.lin_reg)*1.1;
			c.threshold = (float) (findThreshold(ps, len, new Line(c.lin_reg)) * 1.1); // 10% increase
			cf.add(c);
		}
	}

	protected boolean isAnomalous(float x, float y, correlatedFeatures c)
	{
		return (Math.abs(y - c.lin_reg.f(x)) > c.threshold);
	}

	protected final Point[] toPoints(ArrayList<Float> x, ArrayList<Float> y)
	{
		Point[] ps = new Point[x.size()];
		for (int i = 0;i < x.size();i++)
		{
			ps[i] = new Point(x.get(i), y.get(i));
		}
		return ps;
	}

	protected final float findThreshold(Point[] ps, int len, Line rl)
	{
		float max = 0F;
		for (int i = 0;i < len;i++)
		{
			float d = Math.abs(ps[i].getY() - rl.f(ps[i].getX()));
			if (d > max)
			{
				max = d;
			}
		}
		return max;
	}
}