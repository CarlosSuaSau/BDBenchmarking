import random


def multiply(n):
    A = generate_matrix(n)
    B = generate_matrix(n)

    R = [[0 for _ in range(n)] for _ in range(n)]

    for i in range(n):
        for j in range(n):
            for k in range(n):
                R[i][j] += A[i][k] * B[k][j]

    return R

def generate_matrix(n):
    return [[random.random() for _ in range(n)] for _ in range(n)]