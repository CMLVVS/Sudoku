package com.CVC.sudoku;



public class Cell {
    private int valor;
    private boolean esFija;
    private int fila;
    private int columna;
    private boolean esIncorrecta;

    public Cell(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.valor = 0;
        this.esFija = false;
        this.esIncorrecta = false;
    }

    public Cell(int fila, int columna, int valor, boolean esFija) {
        this.fila = fila;
        this.columna = columna;
        this.valor = valor;
        this.esFija = esFija;
        this.esIncorrecta = false;
    }

    // Getters y Setters
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isEsFija() {
        return esFija;
    }

    public void setEsFija(boolean esFija) {
        this.esFija = esFija;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean isEsIncorrecta() {
        return esIncorrecta;
    }

    public void setEsIncorrecta(boolean esIncorrecta) {
        this.esIncorrecta = esIncorrecta;
    }

    public boolean estaVacia() {
        return valor == 0;
    }

    @Override
    public String toString() {
        return valor == 0 ? "" : String.valueOf(valor);
    }
}
