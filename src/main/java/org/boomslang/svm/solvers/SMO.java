package org.boomslang.svm.solvers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.boomslang.svm.Kernel;
import org.boomslang.svm.SVMProblem;
import org.boomslang.vectors.Matrix;

public class SMO {

    private class Bounds {
        double H;
        double L;
    }

    private static final int MAX_ITERATIONS = 1000;
    private SVMProblem problem;
    private Kernel kernel;
    private double C;
    private double epsilon;
    Matrix w;
    double b;
    Random random = new Random(System.currentTimeMillis());

    public SMO(SVMProblem problem, Kernel kernel, double C, double epsilon) {
        this.problem = problem;
        this.kernel = kernel;
        this.C = C;
        this.epsilon = epsilon;
    }

    public Matrix solve() {
        double error = Double.MAX_VALUE;
        int iterations = 0;
        int n = problem.getX().n;
        Matrix alpha = new Matrix(n);
        while (epsilon < error && iterations < MAX_ITERATIONS) {
            iterations++;
            Matrix oldAlpha = alpha.clone();
            for (int j = 0; j < n; j++) {
                int i = getRandomIndex(n, j);
                Matrix xi = problem.getX().getRow(i);
                Matrix xj = problem.getX().getRow(j);
                double yi = problem.getY().get(0, i);
                double yj = problem.getY().get(0, j);
                double kij = kernel.k(xi, xi) + kernel.k(xj, xj) - 2 * kernel.k(xi, xj);
                if (kij == 0) {
                    continue;
                }
                double alphaj = alpha.get(0, j);
                double alphai = alpha.get(0, i);
                Bounds bounds = getBounds(C, alphai, alphaj, yi, yj);
                w = getW(alpha, problem.getY(), problem.getX());
                b = getB(problem.getX(), problem.getY(), w);
                double ei = getPredictionError(xi, yi, w, b);
                double ej = getPredictionError(xj, yj, w, b);

                alpha.set(0, j, alphaj + yj * (ei - ej) / kij);
                alpha.set(0, j, Math.max(alpha.get(0, j), bounds.L));
                alpha.set(0, j, Math.min(alpha.get(0, j), bounds.H));
                alpha.set(0, i, alphai + yi * yj * (alphaj - alpha.get(0, j)));
            }
            Matrix diff = alpha.substractElements(oldAlpha);
            error = diff.dotProduct(diff);
            System.out.println("error\t" + error);
        }
        Matrix supportVectors = getSupportVectors(alpha, problem.getX());
        return supportVectors;
    }

    public double predict(Matrix x) {
        return Math.signum(w.dotProduct(x) + b);
    }

    private Matrix getSupportVectors(Matrix alpha, Matrix x) {
        List<Integer> supportIndexes = new ArrayList<>();
        for (int i = 0; i < alpha.m; i++) {
            if (alpha.get(0, i) != 0) {
                supportIndexes.add(i);
            }
        }
        double[][] supports = new double[supportIndexes.size()][];
        for (int i = 0; i < supportIndexes.size(); i++) {
            supports[i] = x.getRow(supportIndexes.get(i)).elements[0];
        }
        return new Matrix(supports);
    }

    private int getRandomIndex(int to, int butNot) {
        int current = butNot;
        while (current == butNot) {
            current = random.nextInt(to);
        }
        return current;
    }

    private double getPredictionError(Matrix x, double y, Matrix w, double b) {
        return Math.signum(w.dotProduct(x) + b) - y;
    }

    private double getB(Matrix x, Matrix y, Matrix w) {
        return y.substractElements(w.transponse().multiply(x.transponse())).mean();
    }

    private Matrix getW(Matrix alpha, Matrix y, Matrix x) {
        return alpha.multiplyElements(y).multiply(x);
    }

    private Bounds getBounds(double C, double alphai, double alphaj, double yi, double yj) {
        Bounds bounds = new Bounds();
        if (yi != yj) {
            bounds.L = Math.max(0, alphaj - alphai);
            bounds.H = Math.min(C, C - alphai + alphaj);
        } else {
            bounds.L = Math.max(0, alphai + alphaj - C);
            bounds.H = Math.min(C, alphai + alphaj);
        }
        return bounds;
    }
}
