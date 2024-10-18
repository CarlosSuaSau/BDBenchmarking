from matrix_multiplication import multiply

def test_matrix_multiplication(benchmark):
    n = 1000
    result = benchmark.pedantic(multiply, args=(n,), iterations=1, rounds=1)
    assert result is not None