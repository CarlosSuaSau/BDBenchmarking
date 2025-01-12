from pyspark import SparkContext
import numpy as np

def matrix_multiply(matrix_a, matrix_b):
    result = np.dot(matrix_a, matrix_b)
    return result

def main():
    sc = SparkContext(master="spark://192.168.1.42:7077", appName="MatrixMultiplication")

    # Size of the matrix
    n = 10000

    matrix_a = np.random.rand(n, n)
    matrix_b = np.random.rand(n, n)

    rdd_a = sc.parallelize(matrix_a.tolist(), numSlices=n)
    rdd_b = sc.parallelize(matrix_b.T.tolist(), numSlices=n) 

    rdd_result = rdd_a.cartesian(rdd_b).map(
        lambda x: np.dot(x[0], x[1])
    ).groupByKey().map(
        lambda x: list(x[1])
    )

    result = np.array(rdd_result.collect())

    print("Matrix A shape:", matrix_a.shape)
    print("Matrix B shape:", matrix_b.shape)
    print("Resulting Matrix shape:", result.shape)

    sc.stop()

if __name__ == "__main__":
    main()
