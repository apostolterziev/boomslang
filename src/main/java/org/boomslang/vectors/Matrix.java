package org.boomslang.vectors;

public class Matrix {

    public double[][] elements;
    public int m;
    public int n;

    public Matrix(int m) {
        this.m = m;
        this.n = 1;
        elements = new double[1][m];
    }

    public Matrix(int n, int m) {
        this.m = m;
        this.n = n;
        elements = new double[n][m];
    }

    public Matrix(double[] elements) {
        this.m = elements.length;
        this.n = 1;
        this.elements = new double[1][];
        this.elements[0] = elements;

    }

    public Matrix(double[][] elements) {
        this.n = elements.length;
        this.m = elements[0].length;
        this.elements = elements;

    }

    public Matrix getRow(int n) {
        return new Matrix(elements[n]);
    }

    public double get(int n, int m) {
        return elements[n][m];
    }

    public void set(int n, int m, double value) {
        elements[n][m] = value;
    }

    public Matrix multiplyElements(Matrix matrix) {
        if (m != matrix.m && n != matrix.n) {
            throw new IllegalArgumentException("Cannot multiply elements of matrices with different dimentions!");
        }
        double[][] result = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = elements[i][j] * matrix.elements[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix addElements(Matrix matrix) {
        if (m != matrix.m && n != matrix.n) {
            throw new IllegalArgumentException("Cannot add elements of matrices with different dimentions!");
        }
        double[][] result = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = elements[i][j] + matrix.elements[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix substractElements(Matrix matrix) {
        if (m != matrix.m && n != matrix.n) {
            throw new IllegalArgumentException("Cannot substract elements of matrices with different dimentions!");
        }
        double[][] result = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = elements[i][j] - matrix.elements[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix multiply(Matrix matrix) {
        double[][] c = new double[n][matrix.m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < matrix.m; j++) {
                for (int k = 0; k < m; k++) {
                    c[i][j] = c[i][j] + elements[i][k] * matrix.elements[k][j];
                }
            }
        }
        return new Matrix(c);
    }

    public double dotProduct(Matrix matrix) {
        if (n != 1 && matrix.n != 1) {
            throw new IllegalArgumentException("Cannot get dot product of matrices!");
        }

        if (m != matrix.m) {
            throw new IllegalArgumentException("Cannot get dot product of vectors of different dimentions!");
        }
        double dotProduct = 0;
        for (int i = 0; i < m; i++) {
            dotProduct += elements[0][i] * matrix.elements[0][i];
        }
        return dotProduct;
    }

    public Matrix transponse() {
        double[][] result = new double[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[j][i] = elements[i][j];
            }
        }
        return new Matrix(result);
    }

    public double mean() {
        if (n != 1) {
            throw new IllegalArgumentException("Need a vector to compute mean!");
        }
        double result = 0;
        for (int i = 0; i < m; i++) {
            result += elements[0][i];
        }
        return result / m;
    }

    public Matrix sign() {
        double[][] result = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = Math.signum(elements[i][j]);
            }
        }
        return new Matrix(result);
    }

    @Override
    public Matrix clone() {
        Matrix result = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            System.arraycopy(elements[i], 0, result.elements[i], 0, elements[i].length);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n + "x" + m).append(System.lineSeparator());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(elements[i][j] + "\t");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }


}
