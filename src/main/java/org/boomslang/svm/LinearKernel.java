package org.boomslang.svm;

import org.boomslang.vectors.Matrix;

public class LinearKernel implements Kernel {

    @Override
    public double k(Matrix x1, Matrix x2) {
        return x1.dotProduct(x2);
    }
}
