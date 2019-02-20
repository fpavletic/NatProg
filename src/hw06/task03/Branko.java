package hw06.task03;

import java.util.Scanner;

public class Branko {

    /**
     * Matrix form of the recurrence relation:
     *
     *   a_n = a_{n - 1} + a_{n - 2} + a_{n - 4} + a_{n - 8}
     */
    private final static Matrix TASK_MATRIX = new Matrix(new long[][]{
            {1, 1, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0}
    });

    /**
     * Initial values calculated using the recurrence relation and assuming:
     *
     *   * a_n = 0, for n < 0
     *   * a_0 = 1
     */
    private final static Matrix INITIAL_VALUES = new Matrix(new long[][]{
            {31},
            {18},
            {10},
            {6},
            {3},
            {2},
            {1},
            {1}
    });

    public static void main(String[] args) {
        Scanner sysIn = new Scanner(System.in);

        long n = sysIn.nextLong();

        if (n < 8) {
            System.out.println(INITIAL_VALUES.matrix[7 - (int) n][0]);
        }
        else {
            System.out.println(TASK_MATRIX.power(n - 7, 1000000007)
                                          .multiply(INITIAL_VALUES, 1000000007)
                                          .matrix[0][0]);
        }
    }

    public static class Matrix {
        private final long[][] matrix;
        private final int rows, cols;

        public Matrix(long[][] matrix) {
            this.matrix = matrix;
            this.rows = matrix.length;
            this.cols = matrix[0].length;
        }

        public Matrix(int rows, int cols) {
            this(new long[rows][cols]);
        }

        public static Matrix identityMatrix(int size) {
            Matrix I = new Matrix(size, size);

            for (int i = 0; i < size; ++i) {
                I.matrix[i][i] = 1;
            }

            return I;
        }

        public Matrix multiply(Matrix B, int m)
        {
            Matrix A = this;

            if (A.cols != B.rows) {
                throw new IllegalArgumentException("Matrices are not aligned");
            }

            Matrix C = new Matrix(A.rows, B.cols);

            for (int i = 0; i < A.rows; ++i) {
                for (int j = 0; j < B.cols; ++j) {
                    for (int k = 0; k < A.cols; ++k) {
                        C.matrix[i][j] += A.matrix[i][k] * B.matrix[k][j];
                        C.matrix[i][j] %= m;
                    }
                }
            }

            return C;
        }

        public Matrix power(long power, int m) {
            if (this.cols != this.rows) {
                throw new IllegalArgumentException("Matrix is not square");
            }

            long currentPower = 1;
            Matrix currentMatrix = this;
            Matrix returnValue = identityMatrix(rows);

            while (currentPower <= power) {
                if ((power & currentPower) != 0) {
                    returnValue = returnValue.multiply(currentMatrix, m);
                }

                currentMatrix = currentMatrix.multiply(currentMatrix, m);
                currentPower *= 2;
            }

            return returnValue;
        }
    }
}
