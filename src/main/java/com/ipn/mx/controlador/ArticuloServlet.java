package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.ArticuloDAO;
import com.ipn.mx.modelo.dao.CategoriaDAO;
import com.ipn.mx.modelo.dto.ArticuloDTO;
import com.ipn.mx.modelo.dto.CategoriaDTO;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author L450
 */
@WebServlet(name = "ArticuloServlet", urlPatterns = {"/ArticuloServlet"})
public class ArticuloServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String accionToDo = request.getParameter("accion");

        if (accionToDo.equals("listarArticulos")) {
            System.out.println("Listando articulos...");
            listarArticulos(request, response);
        } else if (accionToDo.equals("ver")) {
            System.out.println("Viendo datos...");
            verArticulo(request, response);
        } else if (accionToDo.equals("eliminar")) {
            System.out.println("Eliminando datos...");
            eliminarArticulo(request, response);
        } else if (accionToDo.equals("nuevo")) {
            System.out.println("Creando nuevo formulario de articulo...");
            dirigirANuevo(request, response);
        } else if (accionToDo.equals("guardar")) {
            System.out.println("Guardando nuevo REGISTRO...");
            guardarRegistro(request, response);
        } //Lleva al formulario de registro
        else if (accionToDo.equals("actualizar")) {
            System.out.println("Actualizando REGISTRO....");
            actualizar(request, response);
        } //Aquí sí se actualiza
        else if (accionToDo.equals("actualizarDatos")) {
            System.out.println("Actualizando DATOS...");
            actualizarDatos(request, response);

        }
        else if(accionToDo.equals("verReporte")){
            System.out.println("Abriendo reporte...");
            verReporte(request,response);
        }   

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listarArticulos(HttpServletRequest request, HttpServletResponse response) {
        ArticuloDAO dao = new ArticuloDAO();
        try {
            List lista = dao.readAll();
            //Este atributo es VISIBLE, sólo para el RECURSO destino, es decir, para "listaDeCategorias.jsp. Además es visible para "index.jsp"
            //El alcance REQUEST, está disponible para un RECURSO A(Origen) y un RECURSO B(Destino) 
            request.setAttribute("listadoArt", lista);    //listado==NOMBRE DEL ATRIBUTO en REQUEST
            //lista==Valor del ATRIBUTO listado
            RequestDispatcher rd = request.getRequestDispatcher("/articulo/listaArticulos.jsp");

            rd.forward(request, response);

        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ServletException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private void verArticulo(HttpServletRequest request, HttpServletResponse response) {
        ArticuloDAO dao = new ArticuloDAO();
        ArticuloDTO dto = new ArticuloDTO();
        ArticuloDTO regEncontrado = new ArticuloDTO();
        dto.getEntidad().setIdArticulo(Integer.parseInt(request.getParameter("id")));  //Se fija el valor del parámetro accion, mandado desde "listaDeCategorías.jsp" línea 65 y SE CASTEA, por que el valor del parámetro se recibe como un STRING  
        try {
            regEncontrado = dao.read(dto);
            if (regEncontrado != null) {
                System.out.println("Se encontró el registro exitosamente");
            }
            request.setAttribute("registroArt", regEncontrado);        //registro= nombre del atributo
            //regEncontrado= valor que se le carga a ese atributo
            // registro y regEncontrado, lo ocuparemos en el recurso destino que es "verCategorias.jsp" línea 62
            // Más específicamente, se utiliza el nombre del atributo que es "registro"
            RequestDispatcher rd = request.getRequestDispatcher("/articulo/verArticulo.jsp");  //Es un recurso al que se irá

            rd.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Eliminar CATEGORÍA, se parece a VER CATEGORÍA
        ArticuloDAO dao = new ArticuloDAO();
        ArticuloDTO dto = new ArticuloDTO();
        ArticuloDTO regEncontrado = new ArticuloDTO();
        dto.getEntidad().setIdArticulo(Integer.parseInt(request.getParameter("id")));  //Se fija el valor del parámetro accion, mandado desde "listaDeCategorías.jsp" línea 65 y SE CASTEA, por que el valor del parámetro se recibe como un STRING  
        try {
            regEncontrado = dao.read(dto);
            dao.delete(regEncontrado);
            listarArticulos(request, response);   //Se ejecuta este método, es decir, se REFRESCA la página con los REGISTROS ELIMINADOS

        } catch (SQLException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void dirigirANuevo(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher("/articulo/nuevoArticulo.jsp");
            rd.forward(request, response);

        } catch (ServletException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void guardarRegistro(HttpServletRequest request, HttpServletResponse response) {
        try {
            ArticuloDAO dao = new ArticuloDAO();
            ArticuloDTO dto = new ArticuloDTO();

            dto.getEntidad().setNombreArticulo(request.getParameter("nombre"));
            dto.getEntidad().setDescripcionArticulo(request.getParameter("descripcion"));
            dto.getEntidad().setExistencias(Integer.parseInt(request.getParameter("existencia")));
            dto.getEntidad().setStockMinimo(Integer.parseInt(request.getParameter("stockMin")));
            dto.getEntidad().setStockMaximo(Integer.parseInt(request.getParameter("stockMax")));
            dto.getEntidad().setPrecio(Double.parseDouble(request.getParameter("precio")));
            dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("categoria")));
            dao.insert(dto);

            listarArticulos(request, response);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response) {
        try {
            ArticuloDAO dao = new ArticuloDAO();
            ArticuloDTO dto = new ArticuloDTO();
            ArticuloDTO dtoToModify;
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println(id);
            dto.getEntidad().setIdArticulo(Integer.parseInt(request.getParameter("id")));

            dtoToModify = dao.read(dto);
            System.out.println(dtoToModify);
            request.setAttribute("dtoArt", dtoToModify);  //Fijando un objeto a LA SOLICITUD
            RequestDispatcher rd = request.getRequestDispatcher("/articulo/actualizarArticulo.jsp");
            rd.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void actualizarDatos(HttpServletRequest request, HttpServletResponse response) {
        try {
            ArticuloDAO dao = new ArticuloDAO();
            ArticuloDTO dto = new ArticuloDTO();
            dto.getEntidad().setIdArticulo(Integer.parseInt(request.getParameter("idArt")));
            dto.getEntidad().setNombreArticulo(request.getParameter("nombre"));
            dto.getEntidad().setDescripcionArticulo(request.getParameter("descripcion"));
            dto.getEntidad().setExistencias(Integer.parseInt(request.getParameter("existencia")));
            dto.getEntidad().setStockMinimo(Integer.parseInt(request.getParameter("stockMin")));
            dto.getEntidad().setStockMaximo(Integer.parseInt(request.getParameter("stockMax")));
            dto.getEntidad().setPrecio(Double.parseDouble(request.getParameter("precio")));
            dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("categoria")));
            dao.update(dto);
            listarArticulos(request, response);
            
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void verReporte(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream sos=null;
        try {
            //Se necesita lo siguiente
            
            ArticuloDAO dao= new ArticuloDAO();
            sos=response.getOutputStream(); //Necesitamos un flujo o cadeana de salida de la respuesta del SERVLET 
            File reporte;                   //Especificaremos la ubicación del reporte
            byte b[];                       //Arreglo de bytes donde se cargaran datos de SALIDA, para después obtener el LENGHT 
            
            reporte= new File(getServletConfig().getServletContext().getRealPath("/reporte/reporte5.jasper")); //Se indica la ruta del REPORTE dentro del proyecto
            b=JasperRunManager.runReportToPdf(reporte.getPath(),null,dao.obtenerConexion());  //Convirtiendo el reporte jasper compilado
                                              //ubicación del reporte,,Conexión de la BD
            
            response.setContentType("application/pdf"); //EL contenido de la respuesta, que es un PDF
            response.setContentLength(b.length);        //Tamaño del reporte PDF
            sos.write(b, 0,b.length);                   //Escribiendo el arreglo en el flujo de SALIDA
            sos.flush();
            sos.close();
            
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (JRException ex) {
            System.out.println(ex);
        }finally{
        try {
            sos.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        }
    }

}
