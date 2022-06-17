package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Categoria;
import java.io.Serializable;

public class CategoriaDTO implements Serializable{
    private Categoria entidad;

    public CategoriaDTO() {
        this.entidad = new Categoria();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Clave de la Categoría : ").append(entidad.getIdCategoria()).append("\n");
        sb.append(" Nombre de la Categoría : ").append(entidad.getNombreCategoria()).append("\n");
        sb.append(" Descripción de la Categoría  : ").append(entidad.getDescripcionCategoria()).append("\n");
        sb.append("\n");
        return sb.toString();
    }

    public Categoria getEntidad() {
        return entidad;
    }
    
    
}
