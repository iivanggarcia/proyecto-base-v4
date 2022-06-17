package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Articulo;
import java.io.Serializable;

/**
 *
 * @author L450
 */
public class ArticuloDTO implements Serializable{
    
    private Articulo entidad; 

    public ArticuloDTO() {
        this.entidad = new Articulo();
    }

    public Articulo getEntidad() {
        return entidad;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n-----------------------------------------------------------------------------\n");
        sb.append(" Clave del Artículo: ").append(entidad.getIdArticulo()).append("\n");
        sb.append(" Clave de la Categoría: ").append(entidad.getIdCategoria()).append("\n");
        sb.append(" Nombre de la Artículo: ").append(entidad.getNombreArticulo()).append("\n");
        sb.append(" Descripción Artículo: ").append(entidad.getDescripcionArticulo()).append("\n");
        sb.append(" Existencia: ").append(entidad.getExistencias()).append("\n");
        sb.append(" Stock minimo: ").append(entidad.getStockMinimo()).append("\n");
        sb.append(" Stock máximo: ").append(entidad.getStockMaximo()).append("\n");
        sb.append(" Precio: ").append(entidad.getPrecio()).append("\n");
        sb.append("-----------------------------------------------------------------------------\n");
        
        return sb.toString();
    }
    
    
    
    
    
    
}
