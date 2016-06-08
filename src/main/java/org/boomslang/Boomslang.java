package org.boomslang;

import org.boomslang.svm.Kernel;
import org.boomslang.svm.LinearKernel;
import org.boomslang.svm.SVMProblem;
import org.boomslang.svm.solvers.SMO;
import org.boomslang.vectors.Matrix;

public class Boomslang {

    public static void main(String[] args) {
        Matrix x = new Matrix(new double[][] {
                                  {1,2},
                                  {2,1},
                                  {2,4},
                                  {3,4},
                                  {7,8},
                                  {7,9},
                                  {6,9}
                             });

        Matrix test = new Matrix(new double[][] {
            {2.5,3}
       });

        Matrix y = new Matrix(new double[] { -1, -1, -1, -1, 1, 1, 1 });
        SVMProblem problem = new SVMProblem(x, y);
        Kernel kernel = new LinearKernel();
        SMO smo = new SMO(problem, kernel, 0.01, 0.001);
        smo.solve();
        System.out.println(smo.predict(test));
    }

}
