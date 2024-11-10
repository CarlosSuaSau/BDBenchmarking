package org.example;

public class BlockedMatrixMultiplication {


    public static double[][] multiplyBlocked(double[][] A, double[][] B, int blockSize, boolean sparsity) {
        int n = A.length;
        double[][] C = new double[n][n];

        for (int i = 0; i < n; i += blockSize) {
            for (int j = 0; j < n; j += blockSize) {
                for (int k = 0; k < n; k += blockSize) {

                    for (int ii = i; ii < Math.min(i + blockSize, n); ii++) {
                        for (int jj = j; jj < Math.min(j + blockSize, n); jj++) {
                            double sum = 0.0;
                            for (int kk = k; kk < Math.min(k + blockSize, n); kk++) {
                                if (sparsity && (A[ii][kk] == 0)) {
                                    continue;
                                }
                                sum += A[ii][kk] * B[kk][jj];
                            }
                            C[ii][jj] += sum;
                        }
                    }
                }
            }
        }
        return C;
    }
}


