package com.CVC.sudoku;

import java.util.Random;

/**
 * Modelo que contiene la lógica del juego Sudoku 6x6
 */

public class modelo {
    private int[][] tablero;
    private boolean[][] celdasFijas;
    private Random random;

    public modelo() {
        tablero = new int[6][6];
        celdasFijas = new boolean[6][6];
        random = new Random();
        inicializarJuego();
    }

    /**
     * Inicializa el juego con números predefinidos
     */
    private void inicializarJuego() {
        // Limpiar tablero
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tablero[i][j] = 0;
                celdasFijas[i][j] = false;
            }
        }

        // Patrón inicial válido para Sudoku 6x6
        int[][] patronInicial = {
                {1, 0, 2, 0, 0, 0},
                {0, 2, 0, 5, 0, 0},
                {0, 3, 4, 0, 0, 0},
                {5, 0, 0, 6, 0, 0},
                {0, 6, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 3}
        };

        // Aplicar patrón y marcar celdas fijas
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (patronInicial[i][j] != 0) {
                    tablero[i][j] = patronInicial[i][j];
                    celdasFijas[i][j] = true;
                }
            }
        }
    }

    /**
     * Establece un valor en una celda si el movimiento es válido
     */
    public boolean establecerValor(int fila, int columna, int valor) {
        if (esMovimientoValido(fila, columna, valor)) {
            tablero[fila][columna] = valor;
            return true;
        }
        return false;
    }

    /**
     * Verifica si un movimiento es válido según las reglas del Sudoku
     */
    public boolean esMovimientoValido(int fila, int columna, int valor) {
        // Verificar si la celda es fija
        if (celdasFijas[fila][columna]) {
            return false;
        }

        // Verificar rango
        if (valor < 1 || valor > 6) {
            return false;
        }

        // Verificar fila
        for (int c = 0; c < 6; c++) {
            if (c != columna && tablero[fila][c] == valor) {
                return false;
            }
        }

        // Verificar columna
        for (int r = 0; r < 6; r++) {
            if (r != fila && tablero[r][columna] == valor) {
                return false;
            }
        }

        // Verificar bloque 2x3
        int bloqueFila = (fila / 2) * 2;
        int bloqueColumna = (columna / 3) * 3;

        for (int i = bloqueFila; i < bloqueFila + 2; i++) {
            for (int j = bloqueColumna; j < bloqueColumna + 3; j++) {
                if ((i != fila || j != columna) && tablero[i][j] == valor) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Obtiene una sugerencia para una celda vacía
     */
    public int obtenerSugerencia(int fila, int columna) {
        for (int num = 1; num <= 6; num++) {
            if (esMovimientoValido(fila, columna, num)) {
                return num;
            }
        }
        return 0; // No hay sugerencia válida
    }

    /**
     * Verifica si el tablero está completo y correcto
     */
    public boolean estaCompletoYCorrecto() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (tablero[i][j] == 0) {
                    return false; // Hay celdas vacías
                }
                if (!esMovimientoValido(i, j, tablero[i][j]) && !celdasFijas[i][j]) {
                    return false; // Hay números inválidos
                }
            }
        }
        return true;
    }

    /**
     * Verifica si el tablero está completo
     */
    public boolean estaCompleto() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (tablero[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // Getters
    public int obtenerValor(int fila, int columna) {
        return tablero[fila][columna];
    }

    public boolean esCeldaFija(int fila, int columna) {
        return celdasFijas[fila][columna];
    }

    public int[][] obtenerTablero() {
        return tablero.clone();
    }

    /**
     * Reinicia el tablero, manteniendo solo las celdas fijas
     */
    public void reiniciarTablero() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (!celdasFijas[i][j]) {
                    tablero[i][j] = 0;
                }
            }
        }
    }
}
