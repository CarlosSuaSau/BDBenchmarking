from matrix_multiplication import multiply
from memory_profiler import memory_usage


def test_matrix_multiplication(benchmark):
    n = 10

    def benchmarked_multiply():
        return multiply(n)

    mem_usage_before = memory_usage(-1, interval=0.1, timeout=1)[0]

    result = benchmark.pedantic(benchmarked_multiply, iterations=100, rounds=10)

    mem_usage_after = memory_usage(-1, interval=0.1, timeout=1)[0]

    assert result is not None

    memory_used = mem_usage_after - mem_usage_before

    with open("memory_usage_log.txt", "a") as log_file:
        log_file.write(f"Memory usage before: {mem_usage_before:.2f} MiB\n")
        log_file.write(f"Memory usage after: {mem_usage_after:.2f} MiB\n")
        log_file.write(f"Memory used: {memory_used:.2f} MiB\n\n")


