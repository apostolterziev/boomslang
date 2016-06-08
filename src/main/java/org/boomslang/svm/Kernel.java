package org.boomslang.svm;

import org.boomslang.vectors.Matrix;

public interface Kernel {
    public double k(Matrix x1, Matrix x2);
}
