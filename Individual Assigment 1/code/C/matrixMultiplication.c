#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/resource.h>

#define MAX 99

void generate_matrix(int n, int matrix[n][n]) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            matrix[i][j] = (rand() % MAX) + 1; 
        }
    }
}

void multiply(int n) {
    int A[n][n], B[n][n], C[n][n];

    generate_matrix(n, A);
    generate_matrix(n, B);

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            C[i][j] = 0;
        }
    }

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        }
    }

    if (C[0][0] == 0) {
        printf("Matrix C was not computed successfully.\n");
    } 
}

void print_memory_usage() {
    struct rusage usage;
    getrusage(RUSAGE_SELF, &usage);
    printf("Max Resident Set Size: %ld KB\n", usage.ru_maxrss);
}

int main(int argc, char *argv[]) {
    srand(time(NULL)); 

    if (argc != 2) {
        fprintf(stderr, "Usage: %s <size_of_matrices>\n", argv[0]);
        return 1; 
    }

    int n = atoi(argv[1]);

    if (n <= 0) {
        fprintf(stderr, "Error: The size of the matrices must be a positive integer.\n");
        return 1;
    }

    multiply(n);

    print_memory_usage();

    return 0;
}

