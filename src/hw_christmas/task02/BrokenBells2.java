package hw_christmas.task02;

import java.util.Arrays;
import java.util.Scanner;

public class BrokenBells2 {

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);

        int a, b, m;
        long t;

        a = sysIn.nextInt();
        b = sysIn.nextInt();
        t = sysIn.nextLong();
        m = sysIn.nextInt();

        int x, y;

        x = sysIn.nextInt();
        y = sysIn.nextInt();

        Matrix A = new Matrix(a, b);

        int i, j, k;

        for (int l = 0; l < x; ++l) {
            i = sysIn.nextInt();
            j = sysIn.nextInt();
            k = sysIn.nextInt();

            A.matrix[i - 1][j - 1] = k;
        }

        Matrix B = new Matrix(b, a);

        for (int l = 0; l < y; ++l) {
            i = sysIn.nextInt();
            j = sysIn.nextInt();
            k = sysIn.nextInt();

            B.matrix[i - 1][j - 1] = k;
        }

        Matrix J = new Matrix(a, 1);

        for (int l = 0; l < a; ++l) {
            J.matrix[l][0] = sysIn.nextInt();
        }

        Arrays.stream(A.multiply(B, m)
                       .transpose()
                       .power(t, m)
                       .multiply(J, m)
                       .matrix)
              .forEach(row -> System.out.println(row[0]));
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

        public Matrix transpose() {
            Matrix T = new Matrix(cols, rows);

            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    T.matrix[j][i] = matrix[i][j];
                }
            }

            return T;
        }
    }
}
