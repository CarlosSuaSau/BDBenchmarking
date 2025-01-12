from pyspark import SparkContext
import numpy as np

# Function to multiply two matrices
def matrix_multiply(matrix_a, matrix_b):
    result = np.dot(matrix_a, matrix_b)
    return result

def main():
    # Initialize SparkContext
    sc = SparkContext(master="spark://192.168.1.42:7077", appName="MatrixMultiplication")

    # Size of the matrix
    n = 10000  # 1000x1000 matrix

    # Create matrices with random numbers
    matrix_a = np.random.rand(n, n)
    matrix_b = np.random.rand(n, n)

    # Distribute the rows of Matrix A and columns of Matrix B as RDDs
    rdd_a = sc.parallelize(matrix_a.tolist(), numSlices=n)
    rdd_b = sc.parallelize(matrix_b.T.tolist(), numSlices=n)  # Transpose B for easier mapping

    # Perform matrix multiplication using map and reduce
    rdd_result = rdd_a.cartesian(rdd_b).map(
        lambda x: np.dot(x[0], x[1])
    ).groupByKey().map(
        lambda x: list(x[1])
    )

    # Collect the result from the workers
    result = np.array(rdd_result.collect())

    # Print the result dimensions to verify correctness (not the full result, as it's very large)
    print("Matrix A shape:", matrix_a.shape)
    print("Matrix B shape:", matrix_b.shape)
    print("Resulting Matrix shape:", result.shape)

    # Stop the SparkContext
    sc.stop()

if __name__ == "__main__":
    main()
