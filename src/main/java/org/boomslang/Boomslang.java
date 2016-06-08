package org.boomslang;

import org.boomslang.svm.Kernel;
import org.boomslang.svm.LinearKernel;
import org.boomslang.svm.RBFKernel;
import org.boomslang.svm.SVMProblem;
import org.boomslang.svm.solvers.SMO;
import org.boomslang.vectors.Matrix;

public class Boomslang {

    public static void main(String[] args) {
        Matrix x = new Matrix(new double[][] {
                                  {4,4},
                                  {4,5},
                                  {5,4},
                                  {4.5,4.5},
                                  {3.5,4},
                                  {1,1},
                                  {2,8},
                                  {4,6},
                                  {6,4},
                                  {4,1}

                             });

        Matrix test = new Matrix(new double[][] {
            {6,8}
       });

        Matrix y = new Matrix(new double[] { -1, -1, -1, -1, -1, 1, 1, 1, 1, 1 });
        SVMProblem problem = new SVMProblem(x, y);
        Kernel kernel = new RBFKernel(1.0);
        SMO smo = new SMO(problem, kernel, 1, 0.001);
        smo.solve();
        System.out.println(smo.predict(test));
    }

}
