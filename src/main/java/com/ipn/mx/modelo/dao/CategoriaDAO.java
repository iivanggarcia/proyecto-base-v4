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

/**
 *
 * @author L450
 */
public class CategoriaDAO {

    private static final String SQL_INSERT = "{call spInsert(?,?)}";
    private static final String SQL_INSERT_2 = "insert into categoria(nombreCategoria,descripcionCategoria)values(?,?);";

    // ----> ACTUALIZAR <---
    private static final String SQL_UPDATE = "{call spUpdate(?,?,?)}";
    private static final String SQL_UPDATE_2 = "update categoria set nombreCategoria=?,descripcionCategoria=? where idCategoria=?; ";
    

    private static final String SQL_DELETE = "{call spEliminar(?)}";
    private static final String SQL_DELETE_2 = "delete from categoria where idCategoria=?;";
    

    private static final String SQL_SELECT = "{call spSelect(?)}";
    private static final String SQL_SELECT_2 = "select * from categoria where idCategoria=?;";
    
    
    private static final String SQL_SELECT_ALL = "{call spSelectAll()}";
    private static final String SQL_SELECT_ALL_2 = "select * from categoria;";
    
    private static final String SQL_GRAFICAR="select categoria.nombrecategoria, count(articulo.idarticulo) as cantidaitems from categoria inner join articulo" +
    " on categoria.idcategoria  = articulo.idcategoria group by categoria.idcategoria ; ";
    

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

    public void update(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
        PreparedStatement cs = null;
        try {
            cs = conexion.prepareCall(SQL_UPDATE_2);
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

    public void insert(CategoriaDTO dto) throws SQLException {
        obtenerConexion();
            PreparedStatement cs = null;
        try {
            cs = conexion.prepareCall(SQL_INSERT_2);
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
        PreparedStatement cs = null;
        try {
            cs = conexion.prepareCall(SQL_DELETE_2);
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
        PreparedStatement cs = null;
        ResultSet rs = null;
        List lista = null;
        try {
            cs = conexion.prepareCall(SQL_SELECT_2);

            //dto recibido
            cs.setInt(1, dto.getEntidad().getIdCategoria());
            rs = cs.executeQuery();
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
        List lista = null;
        try {
            cs = conexion.prepareCall(SQL_SELECT_ALL_2);
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
            System.out.println(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
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
            System.out.println(ex);
        }
        return null;
        
    }
    

    public static void main(String[] args) throws SQLException {

//        // ---> INSERTAR ---
//        CategoriaDTO dto=new CategoriaDTO();
//        CategoriaDAO dao=new CategoriaDAO();
//        dto.getEntidad().setNombreCategoria("Línea Blanca2");
//        dto.getEntidad().setDescripcionCategoria("TV2");     
//        dao.insert(dto);


//        //-->Actualizar<--
//        CategoriaDTO dto=new CategoriaDTO();
//        CategoriaDAO dao=new CategoriaDAO();
//        dto.getEntidad().setNombreCategoria("Hogar");
//        dto.getEntidad().setDescripcionCategoria("Aspiradora");
//        dto.getEntidad().setIdCategoria(23);       
//        dao.update(dto);



//        //-->ELIMINAR<--
//        CategoriaDTO dto=new CategoriaDTO();
//        CategoriaDAO dao=new CategoriaDAO();
//        dto.getEntidad().setIdCategoria(23); //Se indica el id nada más 
//        dao.delete(dto);


//        //-->SELECCIONAR UNO<--
//        CategoriaDTO dto=new CategoriaDTO();
//        CategoriaDAO dao=new CategoriaDAO();
//        dto.getEntidad().setIdCategoria(17); //Se indica el id nada más        
//        System.out.println(dao.read(dto));



//// ----> SELECCIONAR TODO <---
        CategoriaDAO dao = new CategoriaDAO();
        dao.imprimeLista(dao.readAll());
    
        
        // --> GRAFICANDO <---
//        CategoriaDTO dto=new CategoriaDTO();
//        CategoriaDAO dao=new CategoriaDAO();
//        System.out.println(dao.graficar());
        

    }

}
