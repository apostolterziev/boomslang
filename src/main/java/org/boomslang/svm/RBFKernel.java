package org.boomslang.svm;

import org.boomslang.vectors.Matrix;

public class RBFKernel implements Kernel {

    private double gamma;

    public RBFKernel(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public double k(Matrix x1, Matrix x2) {
        return gamma * x1.addElements(x2).norm();
    }

}
