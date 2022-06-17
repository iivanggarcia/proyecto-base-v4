<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- CSS only -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

        <title>Tienda de Artículos</title>
    </head>
    <body>

        <div class="container"> 
            <h1>Log In</h1>
            <form action="UsuarioServlet" method="post">
                <div class="mb-3">
                    <label for="txtName" class="form-label">Nombre Del usuario: </label>
                    <input type="text" class="form-control" id="txtName" name="txtName" 
                           maxlength="50" required
                           placeholder="Ingrese su Nombre"
                           >

                </div>
                <div class="mb-3">
                    <label for="txtPassword" class="form-label">Password: </label>
                    <input type="password" class="form-control" name="txtPassword" id="txtPassword"
                           maxlength="50" required placeholder="Ingrese su contraseña">
                </div>
                <input type="hidden" name="accion" value="logIn">
                <button type="submit" class="btn btn-primary">Submit</button>

            </form>

            <c:if test="${DatosIncorrectos==true}">
                <div class='mt-3'>
                    <div class="btn btn-danger">Credenciales Incorrectas, intente de nuevo</div>
                </div>
            </c:if>



        </div>
    </body>
</html>
