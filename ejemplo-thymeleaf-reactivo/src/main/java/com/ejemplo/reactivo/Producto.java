package com.ejemplo.reactivo;

public class Producto {

    private int numero;
    private String concepto;
    private int importe;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public Producto(int numero, String concepto, int importe) {
        this.numero = numero;
        this.concepto = concepto;
        this.importe = importe;
    }

    public Producto() {
    }
}
