package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Usuario;

public class UsuarioDTO {

    private Usuario entidad;

    public UsuarioDTO() {
        this.entidad = new Usuario();
    }

    @Override
    public String toString() {
        return "-----------------------------------------------------------\n"+
                "Id Usuario: "+entidad.getIdUser()+"\n"+
                "Nombre del usuario: "+entidad.getUserName()+"\n"+
                "Password: "+entidad.getPassword()+
               "\n-----------------------------------------------------------\n\n" ;
    }

    public Usuario getEntidad() {
        return entidad;
    }
    
    
    
    
}
