<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://fonts.sandbox.google.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
        <!-- CSS only -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <title>Principal</title>

    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
 

                <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                        <li class="nav-item active">
                            <a class="nav-link" href="index.jsp">Incio</a>
                        </li>
                        <li class="nav-item"> 
                            <!-- ListaDeCategorías ejecutará un método que fija un attributo de sesión -->
                            <a class="nav-link" href="CategoriaServlet?accion=listaDeCategorias">Listado de categorias </a>
                        </li>
                        
                        <li class="nav-item"> 
                            <!-- ListaDeCategorías ejecutará un método que fija un attributo de sesión -->
                            <a class="nav-link" href="ArticuloServlet?accion=listarArticulos">Listado de artículos </a>
                        </li>
                        

                    </ul>

                </div>

                <div class="dropdown bg-info">
                    <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                        Cerrar Sesión
                    </a>

                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <li>
                            <a class="dropdown-item" href="#">
                                <span class="material-symbols-outlined">
                                    account_circle
                                </span>
                            </a>
                        </li>
                        <li><a class="dropdown-item" href="#">${nomVar}</a></li>    <%-- Variable de sessión --%>

                        <li>
                            <a class="dropdown-item" href="UsuarioServlet?accion=logOut">
                                Salir
                            </a>
                        </li>
                    </ul>
                </div>

            </nav>

            <h1>
                
                Bienvenido <c:out value="${nomVar}"></c:out>

            </h1>

        </div>



    </body>
</html>
