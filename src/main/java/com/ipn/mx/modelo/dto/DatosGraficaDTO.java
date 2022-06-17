package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.DatosGrafica;
import java.io.Serializable;

public class DatosGraficaDTO implements Serializable{
    private DatosGrafica entidad;
    
    public DatosGraficaDTO(){
        
        this.entidad= new DatosGrafica();
    
    }

    public DatosGrafica getEntidad() {
        return entidad;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n---------------------------------------------------------");
        sb.append("\nNombre de la categoría: ").append(entidad.getNombreCategoria());
        sb.append("\nNúmero de elementos por categoría: ").append(entidad.getNoElementos());
        sb.append("\n------------------------------------------------------------\n");
        return sb.toString();
    }
    
}
