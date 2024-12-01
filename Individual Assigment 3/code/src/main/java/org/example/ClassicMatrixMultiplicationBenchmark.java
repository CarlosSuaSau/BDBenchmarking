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
public class ClassicMatrixMultiplicationBenchmark {

    @Param({"128", "256", "512", "1024"})
    private static int matrixSize;

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
    public double[][] classicMultiplication() {

        return ClassicMatrixMultiplication.multiply(A, B);
    }

    @TearDown(Level.Trial)
    public void saveResults() {
        try (FileWriter writer = new FileWriter("classic_benchmark_results.txt", true)) {
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
            long usedMemory = heapUsage.getUsed();

            writer.write("Classic     Matrix Size: " + matrixSize +
                     ", Memory Used (bytes): " + usedMemory + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
