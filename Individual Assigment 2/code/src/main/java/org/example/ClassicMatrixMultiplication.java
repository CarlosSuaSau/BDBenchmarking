package org.example;


public class ClassicMatrixMultiplication {

    public static double[][] multiply(double[][] A, double[][] B, boolean sparsity) {

        double[][] C = new double[A.length][B[0].length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                C[i][j] = 0;
                for (int k = 0; k < A[0].length; k++) {
                    if (sparsity && (A[i][k] == 0)) {
                        continue;
                    }
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
}

