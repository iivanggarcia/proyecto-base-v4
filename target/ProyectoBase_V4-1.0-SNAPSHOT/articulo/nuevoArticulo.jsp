<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo articulo</title>
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
                Formulario de Nuevo Articulo
            </h1>
            
            <!-- Lo procesará un SERVLET con el PARÁMETRO acción se decide a dónde ir-->
            <form method="post" action="ArticuloServlet?accion=guardar">
                <div class="mb-3">
                    <label for="a" class="form-label">Nombre: </label>
                    <input type="text" class="form-control" name="nombre" id="a" placeholder="Ingrese el nombre"
                           required>
                </div>
                
         
                
                <div class="mb-3">
                    <label for="b" class="form-label">Descripción:  </label>
                    <input type="text" class="form-control" name="descripcion" id="b" placeholder="Ingrese el nuevo correo"
                           required>
                    
                </div>
                
                <div class="mb-3">
                    <label for="c" class="form-label">Existencia:  </label>
                    <input type="text" class="form-control" name="existencia" id="c" placeholder="Ingrese el nuevo correo"
                           required>
                    
                </div>
                
                <div class="mb-3">
                    <label for="d" class="form-label">Stock Mínimo:  </label>
                    <input type="text" class="form-control" name="stockMin" id="d" placeholder="Ingrese el nuevo correo"
                           required>
                    
                </div>
                
                <div class="mb-3">
                    <label for="e" class="form-label">Stock Máximo:  </label>
                    <input type="text" class="form-control" name="stockMax" id="e" placeholder="Ingrese el nuevo correo"
                           required>
                    
                </div>
                
                <div class="mb-3">
                    <label for="e" class="form-label">Precio:  </label>
                    <input type="text" class="form-control" name="precio" id="e" placeholder="Ingrese el nuevo correo"
                           required>
                    
                </div>
                
                <div class="mb-3">
                    <label for="e" class="form-label">Id de la Categoría:  </label>
                    <input type="text" class="form-control" name="categoria" id="e" placeholder="Ingrese el nuevo correo"
                           required>
                    
                </div>
                
                <button type="submit" class="btn btn-primary">Crear</button>
            </form>


    </body>
</html>
