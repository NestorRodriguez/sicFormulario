<%-- 
    Document   : Encuesta
    Created on : 31/01/2020, 05:41:05 PM
    Author     : aiya
--%>

<%@page import="java.util.List"%>
<%@page import="Entidades.Marcapc"%>
<%@page import="Controladores.MarcapcJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro de formulario</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <form>
            <div id="marcoencuesta">
                <div id="titulo">Registrar formulario de encuesta</div>
                <div id="mensaje">
                    <div class="form-group row">
                        <label for="staticEmail" class="col-sm-2 col-form-label"> Número de documento: </label>
                        <div class="col-sm-10">
                          <input type="text" class="form-control" id="documento" placeholder="Documento">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="staticEmail" class="col-sm-2 col-form-label"> Correo electrónico: </label>
                        <div class="col-sm-10">
                          <input type="text" class="form-control" id="email" placeholder="ejemplo@mail.com">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="staticEmail" class="col-sm-2 col-form-label"> Comentario: </label>
                        <div class="col-sm-10">
                            <textarea type="text" class="form-control" id="comentario" placeholder="Ingrese su comentario"></textarea>
                        </div>
                    </div>
                     <div class="form-group row">
                        <label for="marca" class="col-sm-2 col-form-label"> Marca: </label>
                        <div class="col-sm-10">
                            <select>
                                <% 
                                    MarcapcJpaController marca = new MarcapcJpaController();
                                    Object obj [] = null;
                                    int i=0;
                                    List<Marcapc> listM = marca.findMarcapcEntities();
                                    for(i=0; i<listM.size(); i++)
                                    {
                                        out.println("<option>"+listM.get(i).getDescripción()+"</option>");
                                    }

                                %>
                            </select>
                        </div>
                    </div>
                    <button class="btn btn-primary" type="submit">Registar</button>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>
