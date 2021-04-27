package com.anomaly_detection.server.service.algorithms;

import java.util.ArrayList;

public class SimpleAnomalyDetector extends TimeSeriesAnomalyDetector {
    protected ArrayList<correlatedFeatures> cf = new ArrayList<correlatedFeatures>();
    protected float threshold;

    public SimpleAnomalyDetector() {
        threshold = 0.9F;
    }


    @Override
    public void learnNormal(final TimeSeries ts) {
        int len = ts.getTs().get(0).size();
        float[][] values = new float[ts.getTs().size()][len];
        for (int i = 0; i < ts.getTs().size(); i++) {
            ArrayList<Float> x = ts.getTs().get(i);
            for (int j = 0; j < len; j++) {
                values[i][j] = x.get(j);
            }
        }

        for (int i = 0; i < ts.getTs().size(); i++) {
            int f1 = i;
            float max = 0F;
            int jmax = 0;
            for (int j = i + 1; j < ts.getTs().size(); j++) {
                float p = Math.abs(MathUtil.pearson(values[i], values[j], len));
                if (p > max) {
                    max = p;
                    jmax = j;
                }
            }
            Point[] ps = toPoints(ts.getTs().get(f1), ts.getTs().get(jmax));
            learnHelper(ts, max, f1, jmax, ps);
        }
    }

    @Override
    public ArrayList<AnomalyReport> detect(final TimeSeries ts) {
        ArrayList<AnomalyReport> v = new ArrayList<AnomalyReport>();
        for (correlatedFeatures c : cf) {
            ArrayList<Float> x = ts.getTs().get(c.feature1);
            ArrayList<Float> y = ts.getTs().get(c.feature2);
            for (int i = 0; i < x.size(); i++) {
                if (isAnomalous(x.get(i), y.get(i), c)) {
                    String d = c.feature1 + "-" + c.feature2;
                    v.add(new AnomalyReport(d, (i + 1)));
                }
            }
        }
        return v;
    }

    public final ArrayList<correlatedFeatures> getNormalModel() {
        return new ArrayList<correlatedFeatures>(cf);
    }

    public final void setCorrelationThreshold(float threshold) {
        this.threshold = threshold;
    }

    // helper methods
    protected void learnHelper(final TimeSeries ts, float p, int f1, int f2, Point[] ps) {
        if (p > threshold) {
            int len = ts.getTs().get(0).size();
            correlatedFeatures c = new correlatedFeatures();
            c.feature1 = f1;
            c.feature2 = f2;
            c.correlation = p;
            c.lin_reg = MathUtil.linear_reg(ps, len);
            c.threshold = (float) (findThreshold(ps, len, new Line(c.lin_reg)) * 1.1); // 10% increase
            cf.add(c);
        }
    }

    protected boolean isAnomalous(float x, float y, correlatedFeatures c) {
        return (Math.abs(y - c.lin_reg.f(x)) > c.threshold);
    }

    protected final Point[] toPoints(ArrayList<Float> x, ArrayList<Float> y) {
        Point[] ps = new Point[x.size()];
        for (int i = 0; i < x.size(); i++) {
            ps[i] = new Point(x.get(i), y.get(i));
        }
        return ps;
    }

    protected final float findThreshold(Point[] ps, int len, Line rl) {
        float max = 0F;
        for (int i = 0; i < len; i++) {
            float d = Math.abs(ps[i].getY() - rl.f(ps[i].getX()));
            if (d > max) {
                max = d;
            }
        }
        return max;
    }
}