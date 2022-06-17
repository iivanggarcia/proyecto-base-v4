package com.ipn.mx.utileria;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

// 

public class NombreYExistenciaArticulo implements Serializable{
    private String nombreArticulo;
    private int existencia;

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n---------------------------------------------------------\n");
        sb.append("Nombre del Articulo: ").append(nombreArticulo);
        sb.append("\nExistencia: ").append(existencia);
        sb.append("\n------------------------------------------------------------");
        return sb.toString();
    }
    
    
}
