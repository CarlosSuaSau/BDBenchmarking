# Distributed Matrix Multiplication using Pyspakr

Big Data
January 2025
ULPGC

## Summary

This code shows the implementation of matrix multiplication as a distributed architecture. The idea is to divide the task between different nodes locally to improve the performance.

## Requirements

-Python: 3.8 or higher
-Apache Spark
-Pyspark
-Numpy

## Code functionality

Two matrices of the fixed size are randomly created.
The rows of matrix A and the columns of matrix B are converted into Spark partitions depending on the number of nodes.
All combinations of rows and columns are distributed between nodes. Each pair of row-column is calculated with np.dot().
Once all dot products are calculated, they are combined back to master.
The resulting matrix size is printed to verify the result.

## Setup and execution

Connect all devices to the same subnet of a router.
Configure the master: $SPARK_HOME/sbin/start-master.sh
Configure the workers: $SPARK_HOME/sbin/start-worker.sh spark://<master ip>:7077
Run in master: spark-submit --master spark://<master ip>:7077  DistributedMatrixMultiplication.py
Check execution at http://<master ip>:8080

## Observations

The main focus of a distributed architecture is to be able to scalate the workload. In this case, to perform matrix multiplication reducing execution time, and to increase the size of the matrix solving memory problems.
Small matrices are not going to take less time than with the basic or parallel algorithms, as the distributed architecture adds an overhead time to connect and distribute the workload between the nodes.
This architecture allows us to perform matrix multiplication with larger sizes that would not normally fit in the RAM of a single device.
