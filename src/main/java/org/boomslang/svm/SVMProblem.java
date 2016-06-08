package org.boomslang.svm;

import org.boomslang.vectors.Matrix;

public class SVMProblem {
    private Matrix x;
    private Matrix y;

    public SVMProblem(Matrix x, Matrix y) {
        this.x = x;
        this.y = y;
    }

    public Matrix getX() {
        return x;
    }

    public Matrix getY() {
        return y;
    }

}
