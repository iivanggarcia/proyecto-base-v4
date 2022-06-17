package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.CategoriaDAO;
import com.ipn.mx.modelo.dto.CategoriaDTO;
import com.ipn.mx.modelo.dto.DatosGraficaDTO;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author L450
 */
@WebServlet(name = "CategoriaServlet", /*urlPatterns*/value = {"/CategoriaServlet"})
public class CategoriaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");  //Con esto insertamos CARACTERES UTF-8, en la Base de DATOS(Como la ñ,acentos, etc)
        String accion = request.getParameter("accion");

        if (accion.equalsIgnoreCase("listaDeCategorias")) {
            listadoCategorias(request, response);
        } else if (accion.equalsIgnoreCase("nuevo")) {
            crearCategoria(request, response);
        } else if (accion.equalsIgnoreCase("actualizar")) {
            System.out.println("Actualizando...");
            actualizarCategoria(request, response);
        } else if (accion.equalsIgnoreCase("eliminar")) {
            eliminarCategoria(request, response);
        } else if (accion.equalsIgnoreCase("guardar")) {

            almacenarCategoria(request, response);
        } else if (accion.equalsIgnoreCase("ver")) {
            mostarCategoria(request, response);
        } else if (accion.equalsIgnoreCase("verReporte")) {
            mostarReporte(request, response);
        } else if (accion.equalsIgnoreCase("graficar")) {
            mostarGrafica(request, response);
        } else {
            System.out.println("No hacer nada..."); //ESTO NO DEBERÍA IR AQUÍ, PARA ESO TENEMOS A LOS IF-ELSE ANIDADOS (Corregir)
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

    private void listadoCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaDAO dao = new CategoriaDAO();
        try {
            List lista = dao.readAll();
            //Este atributo es VISIBLE, sólo para el RECURSO destino, es decir, para "listaDeCategorias.jsp. Además es visible para "index.jsp"
            //El alcance REQUEST, está disponible para un RECURSO A(Origen) y un RECURSO B(Destino) 
            request.setAttribute("listado", lista);    //listado==NOMBRE DEL ATRIBUTO en REQUEST
            //lista==Valor del ATRIBUTO listado
            RequestDispatcher rd = request.getRequestDispatcher("/categoria/listaDeCategorias.jsp");

            rd.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //Se va a un formulario para crear un NUEVO REGISTRO
    private void crearCategoria(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher("/categoria/categoriaForm.jsp");

        try {
            rd.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // ---> ACTUALIZAR CATEGORÍA <---
        // Se parece a mostrar Categoría, igual necesito que me den un ID
        //Se hace una busqueda de un registro dado ese ID
        //Se carga un atributo al REQUEST para poder utilizarlo en  "caregoríaForm.jsp", lo estamos reciclando por que ese  
        //formulario SE OCUPA tanto para CREAR UN NUEVO registro, como para ACTUALIZARLO
        //De la línea 151 a la 169 es la primera parte, ahora yo quiero mandar a llamar al método ALMACENAR 
        //Debo modificar el método ALMACENAR, para saber si ACTUALIZO o GUARDO un nuevo registro
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        CategoriaDTO regEncontrado = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));  //Se fija el valor del parámetro accion, mandado desde "listaDeCategorías.jsp" línea 65 y SE CASTEA, por que el valor del parámetro se recibe como un STRING  
        try {
            regEncontrado = dao.read(dto);    //Con este método, se busca un SÓLO registro con el ID específicado

            request.setAttribute("dto", regEncontrado);             //dto= nombre del atributo
            //regEncontrado= valor que se le carga a ese atributo
            // dto y regEncontrado, lo ocuparemos en el recurso destino que es "categoriaForm.jsp" líneas 
            // Más específicamente, se utiliza el nombre del atributo que es "dto"
            RequestDispatcher rd = request.getRequestDispatcher("/categoria/categoriaForm.jsp");  //Es un recurso al que se irá

            rd.forward(request, response);  //Ve hacia adelante, hacia el recurso solicitado

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Eliminar CATEGORÍA, se parece a VER CATEGORÍA
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        CategoriaDTO regEncontrado = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));  //Se fija el valor del parámetro accion, mandado desde "listaDeCategorías.jsp" línea 65 y SE CASTEA, por que el valor del parámetro se recibe como un STRING  
        try {
            regEncontrado = dao.read(dto);
            dao.delete(regEncontrado);
            listadoCategorias(request, response);   //Se ejecuta este método, es decir, se REFRESCA la página con los REGISTROS ELIMINADOS

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Este método sirve para CREAR UN NUEVO REGISTRO O PARA ACTUALIZARLO 
    
    private void almacenarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //Se sabe si SE ACTUALIZA o se CREA un nuevo registro con "variable" 
        // "variable" se recupera desde "categoriaForm.jsp" l.38 
        
        // "variable" trae un ID, si es que DTO se cargó, como resultado de clickear <a>"actualizar" de "listaDeCategorias.jsp" l.93
        // y luego "CategoriaServlet" mandó a llamar al método "actualizarCategoría" 
        
        // "variable" es vacía si el recuros DTO no ha sido cargado, o dicho de otra forma, si en "listaDeCategorias.jsp" 
        // se presionó <a>"nuevo" en la línea 40 y "CategoriaServlet" mandó a llamar al método "crearCategoria" 
        
        String variable = request.getParameter("newOrUpdate");      //trae un id si se carga el recurso DTO 
        System.out.println("******************************" + variable.isEmpty());
        
        if (variable.isEmpty()) {       //Significa que es un registro nuevo
            CategoriaDAO dao = new CategoriaDAO();
            CategoriaDTO dto = new CategoriaDTO();
            //--> Se recuperan las entradas del formulario que fueron pasadas <---
            dto.getEntidad().setNombreCategoria(request.getParameter("txtNombre"));
            dto.getEntidad().setDescripcionCategoria(request.getParameter("txtDescripcion"));
            try {
                dao.insert(dto);
                listadoCategorias(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {    //Significa que el registro se va a ACTUALIZAR

            CategoriaDAO dao = new CategoriaDAO();
            CategoriaDTO dto = new CategoriaDTO();
            //--> Se recuperan las entradas del formulario que fueron pasadas <---
            dto.getEntidad().setNombreCategoria(request.getParameter("txtNombre"));
            dto.getEntidad().setDescripcionCategoria(request.getParameter("txtDescripcion"));
            dto.getEntidad().setIdCategoria(Integer.parseInt(variable)); //Se recupera el id con "variable"
            try {
                dao.update(dto);
                listadoCategorias(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void mostarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        CategoriaDTO regEncontrado = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));  //Se fija el valor del parámetro accion, mandado desde "listaDeCategorías.jsp" línea 65 y SE CASTEA, por que el valor del parámetro se recibe como un STRING  
        try {
            regEncontrado = dao.read(dto);

            request.setAttribute("registro", regEncontrado);        //registro= nombre del atributo
            //regEncontrado= valor que se le carga a ese atributo
            // registro y regEncontrado, lo ocuparemos en el recurso destino que es "verCategorias.jsp" línea 62
            // Más específicamente, se utiliza el nombre del atributo que es "registro"
            RequestDispatcher rd = request.getRequestDispatcher("/categoria/verCategorias.jsp");  //Es un recurso al que se irá

            rd.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostarReporte(HttpServletRequest request, HttpServletResponse response) {
        
    ServletOutputStream sos=null;
        try {
            //Se necesita lo siguiente
            
            CategoriaDAO dao= new CategoriaDAO();
            sos=response.getOutputStream(); //Necesitamos un flujo o cadeana de salida de la respuesta del SERVLET 
            File reporte;                   //Especificaremos la ubicación del reporte
            byte b[];                       //Arreglo de bytes donde se cargaran datos de SALIDA, para después obtener el LENGHT 
            
            reporte= new File(getServletConfig().getServletContext().getRealPath("/reporte/reporte.jasper")); //Se indica la ruta del REPORTE dentro del proyecto
            b=JasperRunManager.runReportToPdf(reporte.getPath(),null,dao.obtenerConexion());  //Convirtiendo el reporte jasper compilado
                                              //ubicación del reporte,,Conexión de la BD
            
            response.setContentType("application/pdf"); //EL contenido de la respuesta, que es un PDF
            response.setContentLength(b.length);        //Tamaño del reporte PDF
            sos.write(b, 0,b.length);                   //Escribiendo el arreglo en el flujo de SALIDA
            sos.flush();
            sos.close();
            
        } catch (IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        try {
            sos.close();
        } catch (IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        
    }

    private void mostarGrafica(HttpServletRequest request, HttpServletResponse response){
        try {
            JFreeChart chart= ChartFactory.createPieChart(
                    "Articulos por categoría ",
                    getDatosGrafica(),true,true, Locale.getDefault()
            );
            
            String archivo= getServletConfig().getServletContext().getRealPath("/grafica.png");
            System.out.println("GUARDADO DESTINO");
            
            System.out.println("*******************"+archivo);
            ChartUtils.saveChartAsPNG(new File(archivo), chart, 800, 600);
            
            //Redireccionando a otro SERVLET
            RequestDispatcher rd=request.getRequestDispatcher("/grafica.jsp");
            rd.forward(request, response);
            
        } catch (IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private PieDataset getDatosGrafica() {
        
        DefaultPieDataset pie = new DefaultPieDataset();
        
        //CategoriaDAO2 tiene la base de datos en la nube, de HEROKU
        CategoriaDAO dao= new CategoriaDAO();
        List datos= dao.graficar();
        for (int i = 0; i < datos.size(); i++) {
            //Graficando 
            DatosGraficaDTO dto = (DatosGraficaDTO) datos.get(i);
//            System.out.println(datos.get(i));
            pie.setValue(dto.getEntidad().getNombreCategoria(), dto.getEntidad().getNoElementos());
        }
        return pie;
        
    }
}
