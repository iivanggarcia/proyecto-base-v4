<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Datos del artículo</title>
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

            <h1>
                Datos del artículo 
            </h1>
            
            <h2>
                Id del Articulo:
                <c:out value="${registroArt.entidad.idArticulo}">
                    
                </c:out>
            </h2>
            <h2>
                Nombre del articulo:
                <c:out value="${registroArt.entidad.nombreArticulo}">
                    
                </c:out>
            </h2>
            <h2>
                Descripción del artículo:
                <c:out value="${registroArt.entidad.descripcionArticulo}"> 
                    
                </c:out>
            </h2>
                
            <h2>
                Existencias:
                <c:out value="${registroArt.entidad.existencias}"> 
                    
                </c:out>
            </h2>
                
            <h2>
                Stock Mínimo
                <c:out value="${registroArt.entidad.stockMinimo}"> 
                    
                </c:out>
            </h2>
                
            <h2>
                Stock Máximo:
                <c:out value="${registroArt.entidad.stockMaximo}"> 
                    
                </c:out>
            </h2>
                
                
            <h2>
                Precio
                <c:out value="${registroArt.entidad.precio}"> 
                    
                </c:out>
            </h2>
                
            <h2>
                Id de la categoría:
                <c:out value="${registroArt.entidad.idCategoria}"> 
                    
                </c:out>
            </h2>
            
            
    </body>
</html>