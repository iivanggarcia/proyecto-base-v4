package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.ArticuloDTO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {
    
    private static final String SQL_INSERT = "{call spInsert(?,?)}";

    // ----> ACTUALIZAR <---
    private static final String SQL_UPDATE = "{call spUpdate(?,?,?)}";

    private static final String SQL_DELETE = "{call spEliminar(?)}";

    private static final String SQL_SELECT = "select * from usuario where idUser=?";
    private static final String SQL_SELECT_ALL = "select * from usuario";

    private Connection conexion;
//    private static final String URL = "jdbc:mysql://localhost:3306/proyectobase4";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:mysql://remotemysql.com:3306/aGw7gCxNMt";
    private static final String USERNAME = "aGw7gCxNMt";
    private static final String PASSWORD = "mlDzKnkC5U";
//    
    
    public Connection obtenerConexion() {  //Es importante retornar la conexión para que funcione la generación
                                            //de reportes         
        //obtener conexion

        String driverBD = "com.mysql.cj.jdbc.Driver";   //Esto se ve en dependecies > mysql-connector> nombreDelDriverDeLaBD
        String driverBDPostgreSQL="com.org.postgresql.Driver";
        try {
            Class.forName(driverBD);
            //DirverManager, carga el Driver
            conexion
                    = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    
    public List readAll() throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <UsuarioDTO>lista = null;
        try {
            ps = conexion.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            lista = obtenerResultados(rs);
            if (lista.size() > 0) {
                return lista;   // La lísta tiene al menos 1 registro
            } else {
                return null;    // La lista no tiene nada 
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List <UsuarioDTO>resultados = new ArrayList();
        while (rs.next()) { //Recorriendo cada registro
            UsuarioDTO dto = new UsuarioDTO();  //Creando un DTO para cada registro
            //Se fija en "dto", el idCategoria que se tiene en el registro de la Base De Datos
            dto.getEntidad().setIdUser(rs.getInt("idUser"));   //Obteniendo con el nombre del REGISTRO
            dto.getEntidad().setUserName(rs.getString("userName"));
            dto.getEntidad().setPassword(rs.getString("password"));
            
            //Ese dto se asigna en una lista 
            resultados.add(dto);
            
        }
        
        return resultados;
    }
    
    //
    
    
    public void imprimeLista(List registros){
        
        for (Object registro : registros) {
            System.out.println(registro);
            
        }
        
    }
    
    public void update(ArticuloDTO dto){
        
    }
    
    public static void main(String[] args) throws SQLException {
        UsuarioDAO dao= new UsuarioDAO();
        UsuarioDTO dto= new UsuarioDTO();
        
        // ----> SELECT <------
        dao.imprimeLista(dao.readAll());
        
        
        
    }
    
    
    
}
