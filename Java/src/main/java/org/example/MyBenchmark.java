package org.example;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MyBenchmark {
    @Param({"10", "20", "50", "100", "200", "500"})
    private int n;

    @Benchmark
    @Measurement(iterations = 5, time = 1)
    @Warmup(iterations = 2, time = 1)
    public int[][] testMatrixMultiply() {
        return MatrixMultiplier.multiply(n);
    }

    @Benchmark
    @Measurement(iterations = 5, time = 1)
    @Warmup(iterations = 2, time = 1)
    public void measureMemory() {
        System.gc();
        int[][] result = MatrixMultiplier.multiply(n);
    }

    @TearDown(Level.Trial)
    public void tearDown() {
    }
}

