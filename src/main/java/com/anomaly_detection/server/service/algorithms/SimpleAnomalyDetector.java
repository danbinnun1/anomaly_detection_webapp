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
        ArrayList<String> atts = ts.gettAttributes();
        int len = ts.getRowSize();
        float[][] vals = new float[atts.size()][len];
        for (int i = 0; i < atts.size(); i++) {
            ArrayList<Float> x = ts.getAttributeData(atts.get(i));
            for (int j = 0; j < len; j++) {
                vals[i][j] = x.get(j);
            }
        }

        for (int i = 0; i < atts.size(); i++) {
            String f1 = atts.get(i);
            float max = 0F;
            int jmax = 0;
            for (int j = i + 1; j < atts.size(); j++) {
                float p = Math.abs(MathUtil.pearson(vals[i], vals[j]));
                if (p > max) {
                    max = p;
                    jmax = j;
                }
            }
            String f2 = atts.get(jmax);
            Point[] ps = toPoints(ts.getAttributeData(f1), ts.getAttributeData(f2));

            learnHelper(ts, max, f1, f2, ps);
        }
    }

    @Override
    public ArrayList<AnomalyReport> detect(final TimeSeries ts) {
        ArrayList<AnomalyReport> v = new ArrayList<AnomalyReport>();
        for (correlatedFeatures c : cf) {
            ArrayList<Float> x = ts.getAttributeData(c.feature1);
            ArrayList<Float> y = ts.getAttributeData(c.feature2);
            for (int i = 0; i < x.size(); i++) {
                if (isAnomalous(x.get(i), y.get(i), c)) {
                    v.add(new AnomalyReport(c.feature1, c.feature2, (i + 1)));
                }
            }
        }
        return v;
    }

    public ArrayList<correlatedFeatures> getCf() {
        return cf;
    }

    public float getThreshold() {
        return threshold;
    }


    // helper methods
    protected void learnHelper(final TimeSeries ts, float p, String f1, String f2, Point[] ps) {
        if (p > threshold) {
            int len = ts.getRowSize();
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