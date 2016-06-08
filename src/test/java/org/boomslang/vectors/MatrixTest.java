package org.boomslang.vectors;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MatrixTest {

    @Test
    public void compute_dot_product() {
        // GIVEN
        Matrix x = new Matrix(new double[] {4,5});
        Matrix y = new Matrix(new double[] {6,3});

        //WHEN
        double product = x.dotProduct(y);

        //THEN
        Assert.assertEquals(product, 39.0d);
    }

    @Test
    public void matrix_multiplication() {
        // GIVEN
        Matrix x = new Matrix(new double[][] {{4,5},{6,9}});
        Matrix y = new Matrix(new double[][] {{6,3},{6,2}});

        //WHEN
        Matrix product = x.multiply(y);

        //THEN
        Assert.assertEquals(product.elements[0][0], 54.0d);
        Assert.assertEquals(product.elements[0][1], 22.0d);
        Assert.assertEquals(product.elements[1][0], 90.0d);
        Assert.assertEquals(product.elements[1][1], 36.0d);
    }
}
