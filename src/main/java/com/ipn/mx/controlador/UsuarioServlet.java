package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author L450
 */
@WebServlet(name = "UsuarioServlet", /*urlPatterns*/ value = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    //Aquí se procesa la SOLICITUD
    protected void processRequest(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            
            String accionPorRealizar=request.getParameter("accion");  //Obteniendo una VARIABLE POST o GET "accion" de la PÁGINA HTML
            if(accionPorRealizar.equalsIgnoreCase("logIn")){
                
                System.out.println(accionPorRealizar);
                validar(request,response);
                  
            }
            else{
                if(accionPorRealizar.equals("logOut")){
                    request.getSession().removeAttribute("nomVar"); 
                    request.getRequestDispatcher("logIn.jsp").forward(request, response);//Página destino
                }
            }
            
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void validar(HttpServletRequest request, HttpServletResponse response) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            UsuarioDTO dto = new UsuarioDTO();
            dto.getEntidad().setUserName(request.getParameter("txtName"));     //Obteniendo el valor de la CELDA dado el nombre de la COLUMNA de la BD
            dto.getEntidad().setPassword(request.getParameter("txtPassword"));
            String nombreBuscado= dto.getEntidad().getUserName();
            String passwordBuscado= dto.getEntidad().getPassword();
            
            System.out.println(dto.getEntidad().getUserName());
            System.out.println(dto.getEntidad().getPassword());
            
            List listaUsuarios;
            
            listaUsuarios= dao.readAll();   //Lista de un objeto, de tipo DTO
            
            dao.imprimeLista(listaUsuarios);
            
            if(findUser(listaUsuarios, nombreBuscado,passwordBuscado)){    //Si es TRUE, Las CREDENCIALES SON CORRECTAS 
                System.out.println("Usuario encontrado");
                request.getSession().setAttribute("nomVar",nombreBuscado);      //.setAttribute(clave, valor)
                request.getRequestDispatcher("index.jsp").forward(request, response);//Página destino
            }
            else{ //Credenciales INCORRECTAS O INEXISTENTES
                System.out.println("Usuario no encontrado");
                boolean indicador=true;
                request.setAttribute("DatosIncorrectos",indicador);
                request.getRequestDispatcher("logIn.jsp").forward(request, response);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    public boolean findUser(List<UsuarioDTO>lista,String userNameWanted, String userPassWanted){

        //Dato genérico que es un OBJECT
        for (UsuarioDTO usuario : lista) {
            if(usuario.getEntidad().getUserName().equals(userNameWanted) && usuario.getEntidad().getPassword().equals(userPassWanted)){
                return true;
            }
            
        }
        return false;
    }

}
