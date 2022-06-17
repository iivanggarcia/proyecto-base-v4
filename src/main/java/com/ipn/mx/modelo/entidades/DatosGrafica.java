package com.ipn.mx.modelo.entidades;

import java.io.Serializable;

//ENTIDAD que sirve para guardar los datos de una CONSULTA
public class DatosGrafica implements Serializable{
    
    private int noElementos;            //Número de elementos por categoría
    private String nombreCategoria;     //Nombre de la categoría 

    public int getNoElementos() {
        return noElementos;
    }

    public void setNoElementos(int noElementos) {
        this.noElementos = noElementos;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n---------------------------------------------------------\n");
        sb.append("Número de elementos por categoría: ").append(noElementos);
        sb.append("\nNombre de la categoría: ").append(nombreCategoria);
        sb.append("\n------------------------------------------------------------");
        return sb.toString();
    }
    
}
