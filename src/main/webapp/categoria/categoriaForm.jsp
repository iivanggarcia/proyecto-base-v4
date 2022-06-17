<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                Datos de la categoría 
            </h1>
            
            <!-- Lo procesará un SERVLET con el PARÁMETRO acción se decide a dónde ir-->
            <form method="post" action="CategoriaServlet?accion=guardar&newOrUpdate=${dto.entidad.idCategoria}">   
                                                                   <!--  newOrUpdate tiene el valor de un ID de un registro sí y sólo sí "dto" 
                                                                         ha sido cargado a REQUEST, previamente, a través del método "actualizarCategoria" de "CategoriaServlet.java"
                                                                         Esto como resultado de que en "listadoDeCategorias.jsp" se haya clickeado la opción "actualizar" l.93, ANTES 
                                                                         
                                                                         Si no tiene cargado un DTO, esto significa que a este formulario lo mandó a llamar 
                                                                         "crearCategoria" de  "CategoriaServlet.java" (Línea 121) 
                                                                         Esto como resultado de que en "listadoDeCategorias.jsp" se haya clickeado la opción "nuevo" l.40, ANTES
                                                                   
                                                                         Este atributo(newOrUpdate) sirve para que el método de "CategoriaServlet" que procesará este formulario 
                                                                         que es "almacenarCategoria" línea 177, sepa si tiene que actualizar o agregar un NUEVO REGISTRO
                                                                   
                                                                         
                                                                   -->
                <div class="mb-3">
                    <label for="txtNombre" class="form-label">Nombre: </label>
                    <input type="text" class="form-control" name="txtNombre" id="txtNombre" placeholder="Ingrese el nombre"
                           value="${dto.entidad.nombreCategoria}"   
                           required
                           >
                    
                    <!-- 
                        "value" sólo se VE si el RECURSO "dto" ha sido cargado en REQUEST 
                        desde el método "actualizarCategoría.jsp" de la línea 147 de CategoriaServlet.java"
                        
                        Los "value" no se verán cuando se QUIERA CREAR UN NUEVO FORMULARIO 
                        debido a que el atributo "dto", no ESTARÁ cargado todavía
                    -->
                    
                </div>
                
         
                
                <div class="mb-3">
                    <label for="txtDescripcion" class="form-label">Descripción:  </label>
                    <input type="text" class="form-control" name="txtDescripcion" id="txtDescripcion" placeholder="Ingrese el nuevo correo"
                           value="${dto.entidad.descripcionCategoria}"
                           required>
                    
                </div>
                
                
                <button type="submit" class="btn btn-primary">Enviar</button>
            </form>


    </body>
</html>
