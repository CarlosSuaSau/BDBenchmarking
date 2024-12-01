package org.example;

import org.openjdk.jmh.annotations.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
public class ParallelMatrixMultiplicationBenchmark {

    @Param({"128", "256", "512", "1024"})
    private static int matrixSize;

    @Param({"2", "4", "8", "16"})
    private int threadCount;

    private double[][] A;
    private double[][] B;

    @Setup(Level.Trial)
    public void setup() {
        A = new double[matrixSize][matrixSize];
        B = new double[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                A[i][j] = Math.random();
                B[i][j] = Math.random();
            }
        }
    }

    @Benchmark
    public double[][] parallelMultiplication() {
        return ParallelMatrixMultiplication.multiply(A, B, threadCount);
    }

    @TearDown(Level.Trial)
    public void saveResults() {
        try (FileWriter writer = new FileWriter("parallel_benchmark_results_pre.txt", true)) {
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
            long usedMemory = heapUsage.getUsed();

            writer.write("Parallel    Matrix Size: " + matrixSize +
                    ", Threads: " + threadCount +
                    ", Memory Used (bytes): " + usedMemory + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

