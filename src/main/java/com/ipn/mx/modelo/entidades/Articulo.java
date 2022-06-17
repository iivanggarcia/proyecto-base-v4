package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  //Para no agregar getters and setters 
public class Articulo implements Serializable{
    
    private int idArticulo;
    private String nombreArticulo;
    private String descripcionArticulo;
    private int existencias;
    private int stockMinimo;
    private int stockMaximo;
    private double precio;
    
    
    private int idCategoria;        //Otra forma private Categoria idCategoria; // Es opcional

}
