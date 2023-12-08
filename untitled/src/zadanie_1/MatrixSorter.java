package zadanie_1;

import java.util.Arrays;
import java.util.Scanner;

public class MatrixSorter {
    public static void sortMatrixColumns(int[][] matrix) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        int[] columnSums = new int[colCount];

        // Вычисляем суммы модулей элементов для каждого столбца
        for (int j = 0; j < colCount; j++) {
            int sum = 0;
            for (int i = 0; i < rowCount; i++) {
                sum += Math.abs(matrix[i][j]);
            }
            columnSums[j] = sum;
        }

        // Создаем массив индексов столбцов и сортируем их по суммам
        Integer[] columnIndices = new Integer[colCount];
        for (int i = 0; i < colCount; i++) {
            columnIndices[i] = i;
        }
        Arrays.sort(columnIndices, (a, b) -> Integer.compare(columnSums[b], columnSums[a]));

        // Перестраиваем столбцы в матрице
        int[][] sortedMatrix = new int[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                sortedMatrix[i][j] = matrix[i][columnIndices[j]];
            }
        }

        // Записываем отсортированную матрицу обратно
        for (int i = 0; i < rowCount; i++) {
            System.arraycopy(sortedMatrix[i], 0, matrix[i], 0, colCount);
        }
    }
}

