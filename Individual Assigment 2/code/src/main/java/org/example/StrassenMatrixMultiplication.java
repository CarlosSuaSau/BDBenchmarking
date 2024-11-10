package org.example;

public class StrassenMatrixMultiplication {

    private static final int CLASSIC_THRESHOLD = 32;

    public static double[][] strassenMultiply(double[][] A, double[][] B, boolean sparsity) {
        int n = A.length;
        int m = nextPowerOfTwo(n);

        double[][] paddedA = padMatrix(A, m);
        double[][] paddedB = padMatrix(B, m);

        double[][] paddedC = strassenMultiplyRecursive(paddedA, paddedB, sparsity);

        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(paddedC[i], 0, C[i], 0, n);
        }

        return C;
    }

    private static double[][] strassenMultiplyRecursive(double[][] A, double[][] B, boolean sparsity) {
        int n = A.length;

        if (n <= CLASSIC_THRESHOLD) {
            return classicMultiply(A, B, sparsity);
        }

        if (n == 1) {
            return new double[][]{{A[0][0] * B[0][0]}};
        }

        int newSize = n / 2;
        double[][] A11 = new double[newSize][newSize];
        double[][] A12 = new double[newSize][newSize];
        double[][] A21 = new double[newSize][newSize];
        double[][] A22 = new double[newSize][newSize];
        double[][] B11 = new double[newSize][newSize];
        double[][] B12 = new double[newSize][newSize];
        double[][] B21 = new double[newSize][newSize];
        double[][] B22 = new double[newSize][newSize];

        splitMatrix(A, A11, 0, 0);
        splitMatrix(A, A12, 0, newSize);
        splitMatrix(A, A21, newSize, 0);
        splitMatrix(A, A22, newSize, newSize);
        splitMatrix(B, B11, 0, 0);
        splitMatrix(B, B12, 0, newSize);
        splitMatrix(B, B21, newSize, 0);
        splitMatrix(B, B22, newSize, newSize);

        double[][] M1 = strassenMultiplyRecursive(add(A11, A22, sparsity), add(B11, B22, sparsity), sparsity);
        double[][] M2 = strassenMultiplyRecursive(add(A21, A22, sparsity), B11, sparsity);
        double[][] M3 = strassenMultiplyRecursive(A11, subtract(B12, B22, sparsity), sparsity);
        double[][] M4 = strassenMultiplyRecursive(A22, subtract(B21, B11, sparsity), sparsity);
        double[][] M5 = strassenMultiplyRecursive(add(A11, A12, sparsity), B22, sparsity);
        double[][] M6 = strassenMultiplyRecursive(subtract(A21, A11, sparsity), add(B11, B12, sparsity), sparsity);
        double[][] M7 = strassenMultiplyRecursive(subtract(A12, A22, sparsity), add(B21, B22, sparsity), sparsity);

        double[][] C11 = add(subtract(add(M1, M4, sparsity), M5, sparsity), M7, sparsity);
        double[][] C12 = add(M3, M5, sparsity);
        double[][] C21 = add(M2, M4, sparsity);
        double[][] C22 = add(subtract(add(M1, M3, sparsity), M2, sparsity), M6, sparsity);

        double[][] C = new double[n][n];
        joinMatrix(C11, C, 0, 0);
        joinMatrix(C12, C, 0, newSize);
        joinMatrix(C21, C, newSize, 0);
        joinMatrix(C22, C, newSize, newSize);

        return C;
    }

    private static double[][] classicMultiply(double[][] A, double[][] B, boolean sparsity) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (sparsity && A[i][k] == 0) continue;
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    private static double[][] add(double[][] A, double[][] B, boolean sparsity) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = sparsity && A[i][j] == 0 ? 0 : A[i][j] + B[i][j];
            }
        }
        return C;
    }

    private static double[][] subtract(double[][] A, double[][] B, boolean sparsity) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = sparsity && A[i][j] == 0 ? 0 : A[i][j] - B[i][j];
            }
        }
        return C;
    }

    private static double[][] padMatrix(double[][] matrix, int size) {
        int n = matrix.length;
        double[][] paddedMatrix = new double[size][size];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, paddedMatrix[i], 0, n);
        }
        return paddedMatrix;
    }
    private static int nextPowerOfTwo(int n) {
        int power = 1;
        while (power < n) {
            power *= 2;
        }
        return power;
    }
    private static void splitMatrix(double[][] parent, double[][] child, int iB, int jB) {
        for (int i = 0, i2 = iB; i < child.length; i++, i2++)
            for (int j = 0, j2 = jB; j < child.length; j++, j2++)
                child[i][j] = parent[i2][j2];
    }
    private static void joinMatrix(double[][] child, double[][] parent, int iB, int jB) {
        for (int i = 0, i2 = iB; i < child.length; i++, i2++)
            for (int j = 0, j2 = jB; j < child.length; j++, j2++)
                parent[i2][j2] = child[i][j];
    }
}





