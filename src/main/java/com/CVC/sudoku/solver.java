package com.CVC.sudoku;

/**
 * Algoritmo para resolver tableros de Sudoku 6x6
 */

public class solver {

    /**
     * Resuelve un tablero de Sudoku usando backtracking
     */
    public boolean resolver(int[][] tablero) {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                if (tablero[fila][columna] == 0) {
                    for (int num = 1; num <= 6; num++) {
                        if (esNumeroValido(tablero, fila, columna, num)) {
                            tablero[fila][columna] = num;

                            if (resolver(tablero)) {
                                return true;
                            } else {
                                tablero[fila][columna] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Verifica si un número puede ser colocado en una posición específica
     */
    private boolean esNumeroValido(int[][] tablero, int fila, int columna, int num) {
        // Verificar fila
        for (int c = 0; c < 6; c++) {
            if (tablero[fila][c] == num) {
                return false;
            }
        }

        // Verificar columna
        for (int r = 0; r < 6; r++) {
            if (tablero[r][columna] == num) {
                return false;
            }
        }

        // Verificar bloque 2x3
        int bloqueFila = (fila / 2) * 2;
        int bloqueColumna = (columna / 3) * 3;

        for (int i = bloqueFila; i < bloqueFila + 2; i++) {
            for (int j = bloqueColumna; j < bloqueColumna + 3; j++) {
                if (tablero[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Genera un tablero de Sudoku válido
     */
    public int[][] generarTablero() {
        int[][] tablero = new int[6][6];

        // Llenar la diagonal de bloques 2x3 con números válidos
        llenarBloque(tablero, 0, 0);
        llenarBloque(tablero, 2, 0);
        llenarBloque(tablero, 4, 0);

        // Resolver el resto del tablero
        resolver(tablero);

        return tablero;
    }

    /**
     * Llena un bloque 2x3 con números del 1-6 sin repetir
     */
    private void llenarBloque(int[][] tablero, int filaInicio, int columnaInicio) {
        boolean[] usados = new boolean[7]; // índices 1-6

        for (int i = filaInicio; i < filaInicio + 2; i++) {
            for (int j = columnaInicio; j < columnaInicio + 3; j++) {
                int num;
                do {
                    num = (int) (Math.random() * 6) + 1;
                } while (usados[num]);

                tablero[i][j] = num;
                usados[num] = true;
            }
        }
    }
}
