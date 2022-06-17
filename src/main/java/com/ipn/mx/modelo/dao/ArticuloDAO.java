package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.ArticuloDTO;
import com.ipn.mx.modelo.dto.CategoriaDTO;
import com.ipn.mx.utileria.NombreYExistenciaArticulo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticuloDAO {
    
    // ---> INSERTAR <----
    private static final String SQL_INSERT = "call spInsertArticulo(?,?,?,?,?,?,?)";    //Procedimineto almacenado insertar, SIN "{" "}"
    private static final String SQL_INSERT_2 = "insert into articulo(idCategoria,nombreArticulo,descripcion,"+
            "existencias,stockMinimo,stockMaximo,precio) values (?,?,?,?,?,?,?)";
    
    // ----> ACTUALIZAR: PENDIENTE***** <---
    private static final String SQL_UPDATE = "call spUpdate(?,?,?)"; 
    private static final String SQL_UPDATE_2 = "UPDATE articulo set nombreArticulo=?,"
            + "descripcion=?,existencias=?,stockMinimo=?,stockMaximo=?,precio=?,"
            + "idCategoria=? where idArticulo=?;"; 
    
    
    private static final String SQL_DELETE = "call spDelete(?)";

    private static final String SQL_SELECT = "select * from categoria where idCategoria=?";
    private static final String SQL_SELECT_ALL = "select * from articulo";

    private Connection conexion;
//    private static final String URL = "jdbc:mysql://localhost:3306/proyectobase4";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:mysql://remotemysql.com:3306/aGw7gCxNMt";
    private static final String USERNAME = "aGw7gCxNMt";
    private static final String PASSWORD = "mlDzKnkC5U";
    
    
    
    public Connection obtenerConexion() {  //Es importante retornar la conexión para que funcione la generación
                                            //de reportes         
        //obtener conexion
        String driverBDPostgreSQL="org.postgresql.Driver"; //Esto se ve en dependecies >postgresql-42.3.4.jar>org.postgresql>Driver.class
        String driverBDMySQL = "com.mysql.cj.jdbc.Driver";
        
        try {
            Class.forName(driverBDMySQL);
            //DirverManager, carga el Driver
            conexion= DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    public void insert(ArticuloDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement cs = null;
        try {
            cs = conexion.prepareCall(SQL_INSERT_2);
            cs.setInt(1, dto.getEntidad().getIdCategoria());
            cs.setString(2, dto.getEntidad().getNombreArticulo());
            cs.setString(3, dto.getEntidad().getDescripcionArticulo());
            cs.setInt(4, dto.getEntidad().getExistencias());
            cs.setInt(5, dto.getEntidad().getStockMinimo());
            cs.setInt(6, dto.getEntidad().getStockMaximo());
            cs.setDouble(7, dto.getEntidad().getPrecio());           
            cs.executeUpdate();
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }

    }
    
    public void update(ArticuloDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement cs = null;

        try {
            cs = conexion.prepareCall(SQL_UPDATE_2);
            cs.setString(1, dto.getEntidad().getNombreArticulo());
            cs.setString(2, dto.getEntidad().getDescripcionArticulo());
            cs.setInt(3, dto.getEntidad().getExistencias());
            cs.setInt(4, dto.getEntidad().getStockMinimo());
            cs.setInt(5, dto.getEntidad().getStockMaximo());
            cs.setDouble(6, dto.getEntidad().getPrecio());
            cs.setInt(7,dto.getEntidad().getIdCategoria());
            cs.setInt(8, dto.getEntidad().getIdArticulo());
            cs.executeUpdate();
            System.out.println("--->REGISTRO ACTUALIZADO <---");
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
    
    public void delete(ArticuloDTO dto) throws SQLException{
        obtenerConexion();
        PreparedStatement cs = null;
        String SQL_DELETE_2="DELETE from articulo where idArticulo=?";
        try {
            cs = conexion.prepareCall(SQL_DELETE_2);
            cs.setInt(1, dto.getEntidad().getIdArticulo());
            cs.executeUpdate();
            System.out.println("-->REGISTRO ELIMINADO<--");
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
    
    
    public List readAll() throws SQLException {
        obtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs = null;
        List <ArticuloDTO> lista = null;
        try {
            cs = conexion.prepareStatement(SQL_SELECT_ALL);
            rs = cs.executeQuery();
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
            if (cs != null) {
                cs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
    
    
    public ArticuloDTO read(ArticuloDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs = null;
        List lista = null;
        String SQL_SELECT_2="SELECT * FROM articulo where idArticulo=?;";
        try {
            cs = conexion.prepareCall(SQL_SELECT_2);

            //dto recibido
            cs.setInt(1, dto.getEntidad().getIdArticulo());
            rs = cs.executeQuery();
            lista = obtenerResultados(rs);
            if (!lista.isEmpty()) {
                return (ArticuloDTO) lista.get(0); //Se retorna sólo el primer elemento de la LISTA
            } else {
                return null;
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List <ArticuloDTO> resultados = new ArrayList();
        while (rs.next()) {
            ArticuloDTO dto = new ArticuloDTO();
            //Se fija en "dto", el idCategoria que se tiene en el registro de la Base De Datos
            dto.getEntidad().setIdArticulo(rs.getInt("idArticulo"));
            dto.getEntidad().setIdCategoria(rs.getInt("idCategoria"));
            dto.getEntidad().setNombreArticulo(rs.getString("nombreArticulo"));
            dto.getEntidad().setDescripcionArticulo(rs.getString("descripcion"));
            
            
            dto.getEntidad().setExistencias(rs.getInt("existencias"));
            dto.getEntidad().setStockMinimo(rs.getInt("stockMinimo"));
            dto.getEntidad().setStockMaximo(rs.getInt("stockMaximo"));
            dto.getEntidad().setPrecio(rs.getDouble("precio"));
            //Ese dto se asigna en una lista 
            resultados.add(dto);
            
        }
        
        return resultados;
    }
    
    private List<String> obtenNombresDistintosDeArticulos(List <ArticuloDTO> articulosRepetidos){
        String nombreArticulo;
        List <String> articulosNoRepetidos= new ArrayList();
        
        for (ArticuloDTO articuloRepetido : articulosRepetidos) {
            nombreArticulo=articuloRepetido.getEntidad().getNombreArticulo();
            if(!(articulosNoRepetidos.contains(nombreArticulo))){
                articulosNoRepetidos.add(nombreArticulo);
            }
        }
        
        return articulosNoRepetidos;
    }
    
    
    // ---> Muestra ExistenciaDeArticulo y fija Datos de existencia son para RECUPERAR
    // NOMBRE DEL ARTICULO Y EXISTENCIA para GRAFICAR DICHOS DATOS 
    public List<NombreYExistenciaArticulo> muestraExistenciaDeArticulo() throws SQLException {
        obtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs = null;
        List <NombreYExistenciaArticulo> lista = null;
        try {
            cs = conexion.prepareStatement(SQL_SELECT_ALL); 
            rs = cs.executeQuery(); //Se obtiene un conjunto de resulatados
            lista = fijaDatosDeExistencia(rs);  
            if (lista.size() > 0) {
                return lista;   // La lísta tiene al menos 1 registro
            } else {
                return null;    // La lista no tiene nada 
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
    
    private List fijaDatosDeExistencia(ResultSet rs) throws SQLException {
        List <NombreYExistenciaArticulo> nombreYExistencia = new ArrayList();
        String nombreArticulo;
        int existencia;
        while (rs.next()) { //Se recorren todos los registros
            ArticuloDTO dto = new ArticuloDTO();
            NombreYExistenciaArticulo articulo= new NombreYExistenciaArticulo();    //Lo que se guardará en la lista 
            //Se fija en "dto", el idCategoria que se tiene en el registro de la Base De Datos
            //se recupera con rs.getInt o getString
            dto.getEntidad().setNombreArticulo(rs.getString("nombreArticulo"));
            dto.getEntidad().setExistencias(rs.getInt("existencias"));
            
            nombreArticulo=dto.getEntidad().getNombreArticulo();
            existencia=dto.getEntidad().getExistencias();
            
            
            articulo.setNombreArticulo(nombreArticulo);
            articulo.setExistencia(existencia);

            //Ese dto se asigna en una lista 
            nombreYExistencia.add(articulo);
            
        }
        
        return nombreYExistencia;
    }
    
    public static void main(String[] args) {
        ArticuloDAO dao= new ArticuloDAO();
        ArticuloDTO dto= new ArticuloDTO();
        
        try {
            
            // --> SELECT ALL <---
//            System.out.println(dao.readAll());
            
            
            /*
            // ---> INSERTAR <----
            dto.getEntidad().setIdCategoria(1);
            dto.getEntidad().setNombreArticulo("C");
            dto.getEntidad().setDescripcionArticulo("C");
            dto.getEntidad().setExistencias(20);
            dto.getEntidad().setStockMinimo(10);
            dto.getEntidad().setStockMaximo(100);
            dto.getEntidad().setPrecio(51.6d);
            dao.insert(dto);
            */
            
            
            // ----> ACTUALIZAR <----
//            dto.getEntidad().setIdCategoria(1);
//            dto.getEntidad().setNombreArticulo("C1");
//            dto.getEntidad().setDescripcionArticulo("C1");
//            dto.getEntidad().setExistencias(201);
//            dto.getEntidad().setStockMinimo(101);
//            dto.getEntidad().setStockMaximo(1001);
//            dto.getEntidad().setPrecio(190.6d);
//            dto.getEntidad().setIdArticulo(7);
//            dao.update(dto);
            

            // ---> ELIMINAR <----
//            dto.getEntidad().setIdArticulo(7);
//            dao.delete(dto);


            // ---> SELECT ONE <---
            dto.getEntidad().setIdArticulo(3);
            System.out.println(dao.read(dto));
            
            
            // ---> ARTICULOS QUE NO SE REPITEN <---
//           System.out.println(dao.obtenNombresDistintosDeArticulos(dao.readAll())); //No tendría sentido EN LA BD
//            

            // ---> Imprimiendo nombre y existencia de artículos <----
            // Para generar los datos de la gráfica 
//            System.out.println(dao.muestraExistenciaDeArticulo());

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
}
