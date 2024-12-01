package org.example;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class VectorizedMatrixMultiplication {

    public static double[][] multiply(double[][] A, double[][] B) {
        RealMatrix matrixA = MatrixUtils.createRealMatrix(A);
        RealMatrix matrixB = MatrixUtils.createRealMatrix(B);

        RealMatrix result = matrixA.multiply(matrixB);

        return result.getData();
    }
}

