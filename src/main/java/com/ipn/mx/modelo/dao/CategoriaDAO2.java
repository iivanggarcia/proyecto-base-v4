package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.CategoriaDTO;
import com.ipn.mx.modelo.dto.DatosGraficaDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//Esta clase es un Data Access Object para la entidad CATEGORIA
//Se usa POSTGRESQL
public class CategoriaDAO2 {
    private static final String SQL_INSERT = "call spInsert(?,?)";    //Procedimineto almacenado insertar, SIN "{" "}"
    private static final String SQL_INSERT_COMMON="INSERT INTO categoria(nombreCategoria,descripcionCategoria)VALUES(?,?);";

    // ----> ACTUALIZAR <---
    private static final String SQL_UPDATE = "call spUpdate(?,?,?)"; 

    private static final String SQL_DELETE = "call spDelete(?)";

    private static final String SQL_SELECT = "select * from categoria where idCategoria=?";
    private static final String SQL_SELECT_ALL = "select * from categoria";
    
    
    // -----> OBTENER DATOS PARA GRAFICAR <----
    private static final String SQL_GRAFICAR="select categoria.nombrecategoria, count(articulo.idarticulo) as cantidaitems from categoria inner join articulo" +
"on categoria.idcategoria  = articulo.idcategoria group by categoria.idcategoria ; ";

    private Connection conexion;
    private static final String URL = "jdbc:postgresql://ec2-44-194-4-127.compute-1.amazonaws.com:5432/d75tqva2dv5t8t";
    private static final String USERNAME = "apknnskjjuyzul";
    private static final String PASSWORD = "4afe9a0b13648497770d36578fadb313d758ac9815b7160d2266015eade55c28";

    public Connection obtenerConexion() {  //Es importante retornar la conexión para que funcione la generación
                                            //de reportes         
        //obtener conexion
        String driverBDPostgreSQL="org.postgresql.Driver"; //Esto se ve en dependecies >postgresql-42.3.4.jar>org.postgresql>Driver.class
        
        try {
            Class.forName(driverBDPostgreSQL);
            //DirverManager, carga el Driver
            conexion= DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }

    public void update(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = conexion.prepareCall(SQL_UPDATE);
            cs.setString(1, dto.getEntidad().getNombreCategoria());
            cs.setString(2, dto.getEntidad().getDescripcionCategoria());
            cs.setInt(3, dto.getEntidad().getIdCategoria());
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

    public void insertNormal(CategoriaDTO dto){
        obtenerConexion();
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(SQL_INSERT_COMMON);
            ps.setString(1, dto.getEntidad().getNombreCategoria());
            ps.setString(2, dto.getEntidad().getDescripcionCategoria());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void insert(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = conexion.prepareCall(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getNombreCategoria());
            cs.setString(2, dto.getEntidad().getDescripcionCategoria());
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

    public void delete(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = conexion.prepareCall(SQL_DELETE);
            cs.setInt(1, dto.getEntidad().getIdCategoria());
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

    public CategoriaDTO read(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = null;
        try {
            ps = conexion.prepareStatement(SQL_SELECT);

            //dto recibido
            ps.setInt(1, dto.getEntidad().getIdCategoria());
            rs = ps.executeQuery();
            lista = obtenerResultados(rs);
            if (!lista.isEmpty()) {
                return (CategoriaDTO) lista.get(0); //Se retorna sólo el primer elemento de la LISTA
            } else {
                return null;
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

    public List readAll() throws SQLException {
        obtenerConexion();
        PreparedStatement cs = null;
        ResultSet rs = null;
        List lista = null;
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

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            CategoriaDTO dto = new CategoriaDTO();
            //Se fija en "dto", el idCategoria que se tiene en el registro de la Base De Datos
            dto.getEntidad().setIdCategoria(rs.getInt("idCategoria"));
            dto.getEntidad().setNombreCategoria(rs.getString("nombreCategoria"));
            dto.getEntidad().setDescripcionCategoria(rs.getString("descripcionCategoria"));

            //Ese dto se asigna en una lista 
            resultados.add(dto);
            
        }
        
        return resultados;
    }
    
    public void imprimeLista(List registros){
        
        for (Object registro : registros) {
            System.out.println(registro);
            
        }
        
        
        
    }
    
    private List obtenerResultadosDeGrafica(ResultSet rs) {
        try {
            List resultados = new ArrayList();
            while (rs.next()) {
                DatosGraficaDTO dto = new DatosGraficaDTO();
                //Se fija en "dto", el idCategoria que se tiene en el registro de la Base De Datos
                dto.getEntidad().setNombreCategoria(rs.getString(1));
                dto.getEntidad().setNoElementos(rs.getInt(2));  
                
                //Ese dto se asigna en una lista
                resultados.add(dto);
                
            }
            
            return resultados;
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public List graficar(){
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = null;
        try {
            ps = conexion.prepareStatement(SQL_GRAFICAR);
            rs = ps.executeQuery();
            lista = obtenerResultadosDeGrafica(rs);
            if (lista.size() > 0) {
                return lista;   // La lísta tiene al menos 1 registro
            } else {
                return null;    // La lista no tiene nada 
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoriaDAO2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
        
    }

    public static void main(String[] args) throws SQLException {

        // ---> INSERTAR ---
//        CategoriaDTO dto=new CategoriaDTO();
//        CategoriaDAO2 dao=new CategoriaDAO2();
//        dto.getEntidad().setNombreCategoria("B");
//        dto.getEntidad().setDescripcionCategoria("B");
//        dao.insert(dto);
        

        //-->Actualizar<--
//        CategoriaDTO dto=new CategoriaDTO();
//        CategoriaDAO2 dao=new CategoriaDAO2();
//        dto.getEntidad().setNombreCategoria("Bebés");
//        dto.getEntidad().setDescripcionCategoria("Biberón");
//        dto.getEntidad().setIdCategoria(4);        
//        dao.update(dto);


        //-->ELIMINAR<--
//        CategoriaDTO dto=new CategoriaDTO();
//        CategoriaDAO2 dao=new CategoriaDAO2();
//        dto.getEntidad().setIdCategoria(5); //Se indica el id nada más 
//        dao.delete(dto);

        
//        //-->SELECCIONAR UNO<--
//        CategoriaDTO dto=new CategoriaDTO();
//        CategoriaDAO2 dao=new CategoriaDAO2();
//        dto.getEntidad().setIdCategoria(1); //Se indica el id nada más      
//        System.out.println(dao.read(dto));  //Recuperando el registro necesitado


// ----> SELECCIONAR TODO <---
//        CategoriaDAO2 dao = new CategoriaDAO2();
//        dao.imprimeLista(dao.readAll());


// ---> CONSULTA PARA GRAFICAR <----

    CategoriaDAO2 dao= new CategoriaDAO2();
    dao.imprimeLista(dao.graficar());

    }

    
    
}
