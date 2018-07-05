package main.tfn;

public class TFN {

    double[] t = new double[3];

    public TFN(double t1, double t2, double t3) {
        t[0] = t1;
        t[1] = t2;
        t[2] = t3;
    }

    public double[] getT() {
        return t;
    }

    public TFN add(TFN number) {
        double t1 = t[0] + number.t[0];
        double t2 = t[1] + number.t[1];
        double t3 = t[2] + number.t[2];
        return new TFN(t1, t2, t3);
    }

    public TFN minus(TFN number) {
        double t1 = t[0] - number.t[0];
        double t2 = t[1] - number.t[1];
        double t3 = t[2] - number.t[2];
        return new TFN(t1, t2, t3);
    }

    public TFN min(TFN number) {
        double t1 = Double.min(t[0],number.t[0]);
        double t2 = Double.min(t[1],number.t[1]);
        double t3 = Double.min(t[2],number.t[2]);
        return new TFN(t1, t2, t3);
    }

    public double expectedValue() {
        return (t[0]+2*t[1]+t[2])/6;
    }
}
