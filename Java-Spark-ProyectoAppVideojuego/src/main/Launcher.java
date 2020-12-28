package main;

import model.Cliente;
import model.Consola;
import model.Especial;
import model.Videojuego;
import com.google.gson.Gson;

import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Float.parseFloat;
import static spark.Spark.*;

public class Launcher {
    public static void main(String[] args) {
        Spark.staticFileLocation("public");
        port(5000);
        HashMap<String, Object> videojuegosTotal = new HashMap<>();
        HashMap<String, Object> clienteTotal = new HashMap<>();
        HashMap<String, Object> especialTotal = new HashMap<>();
        HashMap<String, Object> consolaTotal = new HashMap<>();
        String path = "videojuego.json";
        String pathCliente = "cliente.json";
        String pathEspecial = "especial.json";
        String pathConsola = "consola.json";
        get("/", (request, response) -> {
            if (request.session().attribute("user") != null) {
                response.redirect("/admin");
                return null;
            }
            return new ModelAndView(null, "index.hbs");
        }, new HandlebarsTemplateEngine());
        post("/videojuegos", (request, response) -> {
            return new Gson().toJson(Datos.readFromJson(path));
        });
        post("/videojuego", (request, response) -> {
            return new Gson().toJson(Datos.readFromJson(path).stream().filter(p -> request.queryParams("id").equals(p.getId() + "")).toArray()[0]);
        });
        post("/seleccionar", (request, response) -> {
            String param = request.queryParams("parametar");
            String val = request.queryParams("val");
            HashSet<String> set = new HashSet<>();
            ArrayList<Videojuego> videojuegoArrayList = Datos.readFromJson(path);
            if (param.equals("Nombre")) {
                for (int i = 0; i < videojuegoArrayList.size(); i++) {
                    if (videojuegoArrayList.get(i).getNombre().equals(val))
                        set.add(videojuegoArrayList.get(i).getDesarrolladora());
                }
            } else {
                for (int i = 0; i < videojuegoArrayList.size(); i++) {
                    if (videojuegoArrayList.get(i).getGenero().equals(val))
                        set.add(String.valueOf(videojuegoArrayList.get(i).getAñoSalida()));
                }
            }
            return new Gson().toJson(set.toArray());
        });
        get("/admin", (request, response) -> {
            if (request.session().attribute("user") == null) {
                response.redirect("/");
                return null;
            }
            videojuegosTotal.put("admin", "admin");
            return new ModelAndView(videojuegosTotal, "index.hbs");
        }, new HandlebarsTemplateEngine());
        post("/acceder", (request, response) -> {
            String name = request.queryParams("name");
            String password = request.queryParams("password");
            if (name.equals("admin") && password.equals("admin")) {
                request.session().attribute("user", "admin");
                return new Gson().toJson("/admin");
            }
            return new Gson().toJson("/");
        });
        post("/revisar", (request, response) -> {
            request.session().removeAttribute("user");
            return new Gson().toJson("/");
        });
        post("/eliminar", (request, response) -> {
            ArrayList<Videojuego> videojuegoArrayList = Datos.readFromJson(path);
            for (int i = 0; i < videojuegoArrayList.size(); i++) {
                if (request.queryParams("id").equals(videojuegoArrayList.get(i).getId() + "")) {
                    videojuegoArrayList.remove(i);
                }
            }
            Datos.writeToJSON(videojuegoArrayList, path);
            return new Gson().toJson("eliminado");
        });
        post("/consultar", (request, response) -> {
            float min = parseFloat(request.queryParams("min"));
            float max = parseFloat(request.queryParams("max"));
            ArrayList<Videojuego> videojuegoArrayList = Datos.readFromJson(path);
            Videojuego.Filter(videojuegoArrayList, "Nombre", request.queryParams("nombre"));
            Videojuego.Filter(videojuegoArrayList, "Desarrolladora", request.queryParams("desarrolladora"));
            Videojuego.Filter(videojuegoArrayList, "Genero", request.queryParams("genero"));
            Videojuego.Filter(videojuegoArrayList, "añoSalida", request.queryParams("añoSalida"));
            Videojuego.FilterPrecio(videojuegoArrayList, min, max);
            return new Gson().toJson(videojuegoArrayList);
        });
        post("/cambiar", (request, response) -> {
            ArrayList<Videojuego> videojuegoArrayList = Datos.readFromJson(path);
            int id;
            id = Integer.parseInt(request.queryParams("id"));
            Videojuego.Modificar(videojuegoArrayList, id, request.queryParams("nombre"), request.queryParams("genero"), request.queryParams("desarrolladora"), Integer.parseInt(request.queryParams("añoSalida")),request.queryParams("consola"), parseFloat(request.queryParams("precio")));
            Datos.writeToJSON(videojuegoArrayList, path);
            return new Gson().toJson("Videojuego Modificado correctamente");
        });
        post("/aceptar", (request, response) -> {
            ArrayList<Videojuego> videojuegoArrayList = Datos.readFromJson(path);
            int id = videojuegoArrayList.get(videojuegoArrayList.size() - 1).getId() + 1;
            videojuegoArrayList.add(new Videojuego(id, request.queryParams("nombre"), request.queryParams("desarrolladora"), Integer.parseInt(request.queryParams("añoSalida")), request.queryParams("genero"),request.queryParams("consola"), parseFloat(request.queryParams("precio"))));
            Datos.writeToJSON(videojuegoArrayList, path);
            return new Gson().toJson("nuevo Videojuego guardado correctamente");
        });
        post("/buscarTodo", (request, response) -> {
            ArrayList<Videojuego> videojuegoArrayList = Datos.readFromJson(path);
            String detalle = request.queryParams("detalle");
            Videojuego.videojuegoDes(videojuegoArrayList, detalle);
            return new Gson().toJson(videojuegoArrayList);
        });
        get("/videojuegoEditar", (request, response) -> {
            videojuegosTotal.put("videojuego", Datos.readFromJson(path));
            return new ModelAndView(videojuegosTotal, "cambios.hbs");
        }, new HandlebarsTemplateEngine());


        get("/cliente", (request, response) -> {
            return new ModelAndView(null, "cliente.hbs");
        }, new HandlebarsTemplateEngine());

        post("/clientes", (request, response) -> {
            return new Gson().toJson(Datos.readFromJsonCliente(pathCliente));
        });
        post("/cliente", (request, response) -> {
            return new Gson().toJson(Datos.readFromJsonCliente(pathCliente).stream().filter(p -> request.queryParams("id").equals(p.getId() + "")).toArray()[0]);
        });
        post("/seleccionarCliente", (request, response) -> {
            String param = request.queryParams("parametar");
            String val = request.queryParams("val");
            HashSet<String> set = new HashSet<>();
            ArrayList<Cliente> clienteArrayList = Datos.readFromJsonCliente(pathCliente);
            if (param.equals("NombreCliente")) {
                for (int i = 0; i < clienteArrayList.size(); i++) {
                    if (clienteArrayList.get(i).getNombreCliente().equals(val))
                        set.add(clienteArrayList.get(i).getDireccionCliente());
                }
            } else {
                for (int i = 0; i < clienteArrayList.size(); i++) {
                    if (clienteArrayList.get(i).getDireccionCliente().equals(val))
                        set.add(String.valueOf(clienteArrayList.get(i).getFechaNacimientoCliente()));
                }
            }
            return new Gson().toJson(set.toArray());
        });


        post("/eliminarCliente", (request, response) -> {
            ArrayList<Cliente> clienteArrayList = Datos.readFromJsonCliente(pathCliente);
            for (int i = 0; i < clienteArrayList.size(); i++) {
                if (request.queryParams("id").equals(clienteArrayList.get(i).getId() + "")) {
                    clienteArrayList.remove(i);
                }
            }
            Datos.writeToJSONCliente(clienteArrayList, pathCliente);
            return new Gson().toJson("Eliminado");
        });
        post("/consultarCliente", (request, response) -> {
            float min = parseFloat(request.queryParams("min"));
            float max = parseFloat(request.queryParams("max"));
            ArrayList<Cliente> clienteArrayList = Datos.readFromJsonCliente(pathCliente);
            Cliente.Filter(clienteArrayList, "nombreCliente", request.queryParams("nombreCliente"));
            Cliente.Filter(clienteArrayList, "direccionCliente", request.queryParams("direccionCliente"));
            Cliente.Filter(clienteArrayList, "fechaNacimientoCliente", request.queryParams("fechaNacimientoCliente"));

            Cliente.FilterComprasRealizadas(clienteArrayList, min, max);
            return new Gson().toJson(clienteArrayList);
        });
        post("/cambiarCliente", (request, response) -> {
            ArrayList<Cliente> clienteArrayList = Datos.readFromJsonCliente(pathCliente);
            int id;

            id = Integer.parseInt(request.queryParams("id"));
            Cliente.ModificarCliente(clienteArrayList, id, request.queryParams("nombreCliente"), request.queryParams("direccionCliente"), Integer.parseInt(request.queryParams("fechaNacimientoCliente")), Integer.parseInt(request.queryParams("comprasRealizadas")));
            Datos.writeToJSONCliente(clienteArrayList, pathCliente);
            return new Gson().toJson("Modificado correctamente");
        });
        post("/aceptarCliente", (request, response) -> {
            ArrayList<Cliente> clienteArrayList = Datos.readFromJsonCliente(pathCliente);
            int id = clienteArrayList.get(clienteArrayList.size() - 1).getId() + 1;

            clienteArrayList.add(new Cliente(id, request.queryParams("nombreCliente"), request.queryParams("direccionCliente"), Integer.parseInt(request.queryParams("fechaNacimientoCliente")), Integer.parseInt(request.queryParams("comprasRealizadas"))));
            Datos.writeToJSONCliente(clienteArrayList, pathCliente);
            return new Gson().toJson("nuevo Cliente guardado correctamente");
        });
        post("/buscarTodoCliente", (request, response) -> {
            ArrayList<Cliente> clienteArrayList = Datos.readFromJsonCliente(pathCliente);
            String detalle = request.queryParams("detalle");
            Cliente.clienteDes(clienteArrayList, detalle);
            Cliente.clienteDes(clienteArrayList, detalle);
            return new Gson().toJson(clienteArrayList);
        });
        get("/clienteEditar", (request, response) -> {
            clienteTotal.put("cliente", Datos.readFromJsonCliente(pathCliente));
            return new ModelAndView(clienteTotal, "cambiosCliente.hbs");
        }, new HandlebarsTemplateEngine());


        get("/especial", (request, response) -> {
        return new ModelAndView(null, "especial.hbs");
    }, new HandlebarsTemplateEngine());

    post("/especials", (request, response) -> {
        return new Gson().toJson(Datos.readFromJsonEspecial(pathEspecial));
    });
    post("/especial", (request, response) -> {
        return new Gson().toJson(Datos.readFromJsonEspecial(pathEspecial).stream().filter(p -> request.queryParams("id").equals(p.getId() + "")).toArray()[0]);
    });
    post("/seleccionarEspecial", (request, response) -> {
        String param = request.queryParams("parametar");
        String val = request.queryParams("val");
        HashSet<String> set = new HashSet<>();
        ArrayList<Especial> especialArrayList = Datos.readFromJsonEspecial(pathEspecial);
        if (param.equals("NombreEspecial")) {
            for (int i = 0; i < especialArrayList.size(); i++) {
                if (especialArrayList.get(i).getNombreEspecial().equals(val))
                    set.add(especialArrayList.get(i).getJuegoEspecial());
            }
        } else {
            for (int i = 0; i < especialArrayList.size(); i++) {
                if (especialArrayList.get(i).getJuegoEspecial().equals(val))
                    set.add(String.valueOf(especialArrayList.get(i).getNombreEspecial()));
            }
        }
        return new Gson().toJson(set.toArray());
    });


    post("/eliminarEspecial", (request, response) -> {
        ArrayList<Especial> especialArrayList = Datos.readFromJsonEspecial(pathEspecial);
        for (int i = 0; i < especialArrayList.size(); i++) {
            if (request.queryParams("id").equals(especialArrayList.get(i).getId() + "")) {
                especialArrayList.remove(i);
            }
        }
        Datos.writeToJSONEspecial(especialArrayList, pathEspecial);
        return new Gson().toJson("Eliminado");
    });
    post("/consultarEspecial", (request, response) -> {
        float min = parseFloat(request.queryParams("min"));
        float max = parseFloat(request.queryParams("max"));
        ArrayList<Especial> especialArrayList = Datos.readFromJsonEspecial(pathEspecial);
        Especial.Filter(especialArrayList, "nombreEspecial", request.queryParams("nombreEspecial"));
        Especial.Filter(especialArrayList, "juegoEspecial", request.queryParams("juegoEspecial"));

        Especial.FilterPrecio(especialArrayList, min, max);
        return new Gson().toJson(especialArrayList);
    });
    post("/cambiarEspecial", (request, response) -> {
        ArrayList<Especial> especialArrayList = Datos.readFromJsonEspecial(pathEspecial);
        int id;

        id = Integer.parseInt(request.queryParams("id"));
        Especial.ModificarEspecial(especialArrayList, id, request.queryParams("nombreEspecial"), request.queryParams("juegoEspecial"), Integer.parseInt(request.queryParams("precioEspecial")));
        Datos.writeToJSONEspecial(especialArrayList, pathEspecial);
        return new Gson().toJson("Modificado correctamente");
    });
    post("/aceptarEspecial", (request, response) -> {
        ArrayList<Especial> especialArrayList = Datos.readFromJsonEspecial(pathEspecial);
        int id = especialArrayList.get(especialArrayList.size() - 1).getId() + 1;

        especialArrayList.add(new Especial(id, request.queryParams("nombreEspecial"), request.queryParams("juegoEspecial"), Integer.parseInt(request.queryParams("precioEspecial"))));
        Datos.writeToJSONEspecial(especialArrayList, pathEspecial);
        return new Gson().toJson("nuevo Producto Especial guardado correctamente");
    });
    post("/buscarTodoEspecial", (request, response) -> {
        ArrayList<Especial> especialArrayList = Datos.readFromJsonEspecial(pathEspecial);
        String detalle = request.queryParams("detalle");
        Especial.especialDes(especialArrayList, detalle);
        Especial.especialDes(especialArrayList, detalle);
        return new Gson().toJson(especialArrayList);
    });
    get("/especialEditar", (request, response) -> {
        especialTotal.put("especial", Datos.readFromJsonEspecial(pathEspecial));
        return new ModelAndView(especialTotal, "cambiosEspecial.hbs");
    }, new HandlebarsTemplateEngine());

        get("/consola", (request, response) -> {
            return new ModelAndView(null, "consola.hbs");
        }, new HandlebarsTemplateEngine());

        post("/consolas", (request, response) -> {
            return new Gson().toJson(Datos.readFromJsonConsola(pathConsola));
        });
        post("/consola", (request, response) -> {
            return new Gson().toJson(Datos.readFromJsonConsola(pathConsola).stream().filter(p -> request.queryParams("id").equals(p.getId() + "")).toArray()[0]);
        });
        post("/seleccionarConsola", (request, response) -> {
            String param = request.queryParams("parametar");
            String val = request.queryParams("val");
            HashSet<String> set = new HashSet<>();
            ArrayList<Consola> consolaArrayList = Datos.readFromJsonConsola(pathConsola);
            if (param.equals("NombreConsola")) {
                for (int i = 0; i < consolaArrayList.size(); i++) {
                    if (consolaArrayList.get(i).getNombreConsola().equals(val))
                        set.add(consolaArrayList.get(i).getFabricanteConsola());
                }
            } else {
                for (int i = 0; i < consolaArrayList.size(); i++) {
                    if (consolaArrayList.get(i).getFabricanteConsola().equals(val))
                        set.add(String.valueOf(consolaArrayList.get(i).getAñoSalidaConsola()));
                }
            }
            return new Gson().toJson(set.toArray());
        });


        post("/eliminarConsola", (request, response) -> {
            ArrayList<Consola> consolaArrayList = Datos.readFromJsonConsola(pathConsola);
            for (int i = 0; i < consolaArrayList.size(); i++) {
                if (request.queryParams("id").equals(consolaArrayList.get(i).getId() + "")) {
                    consolaArrayList.remove(i);
                }
            }
            Datos.writeToJSONConsola(consolaArrayList, pathConsola);
            return new Gson().toJson("Eliminado");
        });
        post("/consultarConsola", (request, response) -> {
            float min = parseFloat(request.queryParams("min"));
            float max = parseFloat(request.queryParams("max"));
            ArrayList<Consola> consolaArrayList = Datos.readFromJsonConsola(pathConsola);
            Consola.Filter(consolaArrayList, "nombreConsola", request.queryParams("nombreConsola"));
            Consola.Filter(consolaArrayList, "fabricanteConsola", request.queryParams("fabricanteConsol"));
            Consola.Filter(consolaArrayList, "añoSalidaConsola", request.queryParams("añoSalidaConsola"));

            Consola.FilterPrecioConsola(consolaArrayList, min, max);
            return new Gson().toJson(consolaArrayList);
        });
        post("/cambiarConsola", (request, response) -> {
            ArrayList<Consola> consolaArrayList = Datos.readFromJsonConsola(pathConsola);
            int id;

            id = Integer.parseInt(request.queryParams("id"));
            Consola.ModificarConsola(consolaArrayList, id, request.queryParams("nombreConsola"), request.queryParams("fabricanteConsola"), Integer.parseInt(request.queryParams("añoSalidaConsola")), Integer.parseInt(request.queryParams("precioConsola")));
            Datos.writeToJSONConsola(consolaArrayList, pathConsola);
            return new Gson().toJson("Modificado correctamente");
        });
        post("/aceptarConsola", (request, response) -> {
            ArrayList<Consola> consolaArrayList = Datos.readFromJsonConsola(pathConsola);
            int id = consolaArrayList.get(consolaArrayList.size() - 1).getId() + 1;

            consolaArrayList.add(new Consola(id, request.queryParams("nombreConsola"), request.queryParams("fabricanteConsola"), Integer.parseInt(request.queryParams("añoSalidaConsola")), Integer.parseInt(request.queryParams("precioConsola"))));
            Datos.writeToJSONConsola(consolaArrayList, pathConsola);
            return new Gson().toJson("nueva Consola guardada correctamente");
        });
        post("/buscarTodoConsola", (request, response) -> {
            ArrayList<Consola> consolaArrayList = Datos.readFromJsonConsola(pathConsola);
            String detalle = request.queryParams("detalle");
            Consola.consolaDes(consolaArrayList, detalle);
            Consola.consolaDes(consolaArrayList, detalle);
            return new Gson().toJson(consolaArrayList);
        });
        get("/consolaEditar", (request, response) -> {
            consolaTotal.put("consola", Datos.readFromJsonConsola(pathConsola));
            return new ModelAndView(consolaTotal, "cambiosConsola.hbs");
        }, new HandlebarsTemplateEngine());



    }



}


