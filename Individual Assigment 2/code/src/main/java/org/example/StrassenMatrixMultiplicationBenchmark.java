package org.example;

import org.openjdk.jmh.annotations.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.concurrent.TimeUnit;

import static org.example.StrassenMatrixMultiplication.strassenMultiply;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
public class StrassenMatrixMultiplicationBenchmark {

    @Param({"2048"})
    private static int matrixSize;

    @Param({"0.0", "0.2", "0.5"})
    private double sparsityLevel;
    private double[][] A;
    private double[][] B;

    @Setup(Level.Trial)
    public void setup() {
        A = new double[matrixSize][matrixSize];
        B = new double[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                A[i][j] = (Math.random() > sparsityLevel) ? Math.random() : 0;
                B[i][j] = (Math.random() > sparsityLevel) ? Math.random() : 0;
            }
        }
    }

    @Benchmark
    public double[][] strassenMultiplication() {
        return strassenMultiply(A, B, sparsityLevel > 0);
    }

    @TearDown(Level.Trial)
    public void saveResults() {
        try (FileWriter writer = new FileWriter("benchmark_results.txt", true)) {
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
            long usedMemory = heapUsage.getUsed();

            writer.write("Strassen´s    Matrix Size: " + matrixSize + ", Block Size: " + "32" +
                    ", Sparsity Level: " + sparsityLevel + ", Memory Used (bytes): " + usedMemory + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

