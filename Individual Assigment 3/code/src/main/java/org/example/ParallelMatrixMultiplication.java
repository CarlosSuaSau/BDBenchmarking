package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelMatrixMultiplication {

    public static double[][] multiply(double[][] A, double[][] B, int numThreads) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;

        if (colsA != B.length) {
            throw new IllegalArgumentException("Number of columns in A must match number of rows in B");
        }

        double[][] result = new double[rowsA][colsB];

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int chunkSize = (rowsA + numThreads - 1) / numThreads;

        for (int threadId = 0; threadId < numThreads; threadId++) {
            int startRow = threadId * chunkSize;
            int endRow = Math.min(startRow + chunkSize, rowsA);

            executor.submit(() -> {
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < colsB; j++) {
                        for (int k = 0; k < colsA; k++) {
                            result[i][j] += A[i][k] * B[k][j];
                        }
                    }
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        return result;
    }
}

