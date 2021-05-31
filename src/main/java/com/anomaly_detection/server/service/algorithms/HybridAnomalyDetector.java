package com.anomaly_detection.server.service.algorithms;

public class HybridAnomalyDetector extends SimpleAnomalyDetector {

    @Override
    public void learnHelper(final TimeSeries ts, float p, String f1, String f2, Point[] ps) {
        super.learnHelper(ts, p, f1, f2, ps);
        if (p > 0.5F && p < threshold) {
            Circle cl = MathUtil.findMinCircle(ps, ts.getRowSize());
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
    public boolean isAnomalous(float x, float y, correlatedFeatures c) {
        return (c.correlation >= threshold && super.isAnomalous(x, y, c)) || (c.correlation > 0.5F && c.correlation < threshold && MathUtil.dist(new Point(c.cx, c.cy), new Point(x, y)) > c.threshold);
    }
}