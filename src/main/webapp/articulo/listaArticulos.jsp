<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Articulos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <a class="navbar-brand" href="#">Navbar</a>

                <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                        <li class="nav-item active">
                            <a class="nav-link" href="index.jsp">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="CategoriaServlet?accion=listaDeCategorias">Listado de categorias </a>
                        </li>
                        
                        <li class="nav-item"> 
                            <!-- ListaDeCategorías ejecutará un método que fija un attributo de sesión -->
                            <a class="nav-link" href="ArticuloServlet?accion=listarArticulos">Listado de artículos </a>
                        </li>

                    </ul>

                </div>
            </nav>
            <div class="mb-3"></div>

            <div class="card">
                <div class="card-header">
                    <h1 class="card-title text-primary text-center">
                        Listado de articulos 
                    </h1>
                    <a href="ArticuloServlet?accion=nuevo"  class="btn btn-outline-primary">Nuevo</a>
                    <a href="CategoriaServlet?accion=graficar" target="_blank"  class="btn btn-outline-warning">Graficar</a>
                    <a href="ArticuloServlet?accion=verReporte"  target="_blank" class="btn btn-outline-info">Reporte</a>
                </div>
                <div class="card-body">
                    <table class="table table-striped">
                        <!-- Aquí irán los datos de Categoría, almacenados en la Base de Datos -->
                        <tr>
                            <th>Id del Articulo</th>
                            <th>Nombre del Articulo</th>
                            <th>Id de la categoría</th>
                            <th>Descripción</th>
                            <th>Eliminar</th>
                            <th>Actualizar</th>
                        </tr>
                        
                        <!-- 
                            listado == es el objeto que está en algún alcance(En este caso REQUEST)
                            El signo de pesos es necesario para indicar que es un OBJETO
                         -->
                        
                        <!-- Para que este forEach funcione, primero se debe hacer la solicitud con el "index.jsp" línea 27 
                        y que "CategoriaServlet" en la línea 116 cargue el Attributo "listado" al ALCANCE REQUEST
                        -->
                        <c:forEach items="${listadoArt}" var="dto">
                        <tr>
                            <td>
                                <!-- Para ver la info de cada REGISTRO de la base de datos 
                                    Debemos pasar el id a UN SERVLET CONTROLADOR  
                                 -->
                                <a href="ArticuloServlet?accion=ver&id=${dto.entidad.idArticulo}" class="btn btn-outline-success">
                                    <c:out value="${dto.entidad.idArticulo}" />
                                </a>
                                    
                            </td>
                            <td>
                                <!-- 
                                    dto es UNA LISTA Ver(CategoríaServlet 112 )
                                    Luego seguimos esta ruta 
                                    LISTA es el resultado de un readAll()
                                    readAll es el resultado de llamar a obtenerResultados()
                                    obtenerResultados() tiene como elementos los DTOs, entonces puedo acceder 
                                    a la var=dto de la línea 59 cómo si de un objeto DTO se tratara  
                                
                                -->
                                <c:out value="${dto.entidad.nombreArticulo}" />
                            </td>
                            <td class="text-center">
                                <c:out value="${dto.entidad.idCategoria}" />
                            </td>
                            <td>
                                <c:out value="${dto.entidad.descripcionArticulo}" />
                            </td>
                            <td>
                                <a href="ArticuloServlet?accion=eliminar&id=${dto.entidad.idArticulo}" class="btn btn-outline-danger">Eliminar</a>
                            </td>
                            <td>
                                <a href="ArticuloServlet?accion=actualizar&id=${dto.entidad.idArticulo}" class="btn btn-outline-info">Actualizar</a>
                            </td>
                        </tr>
                        </c:forEach>
                        
                        
                    </table>
                </div>
            </div>

        </div>
    </body>
</html>