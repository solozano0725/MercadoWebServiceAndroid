package com.example.solmayra.mercadofit;

/**
 * Created by Sol Mayra on 11/06/2017.
 */

public class Mercado {
    private int id;
    private String fecha;
    private String nombre;
    private String marca;
    private int cantidad;
    private String almacen;

    public Mercado(int id, String fecha, String nombre, String marca, int cantidad, String almacen) {
        this.id = id;
        this.fecha = fecha;
        this.nombre = nombre;
        this.marca = marca;
        this.cantidad = cantidad;
        this.almacen = almacen;
    }

    public Mercado(String fecha, String nombre, String marca, int cantidad, String almacen) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.marca = marca;
        this.cantidad = cantidad;
        this.almacen = almacen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }
}
