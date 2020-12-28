package model;

import java.util.ArrayList;

public class Videojuego {

        private int id;
        private String nombre;
        private String desarrolladora;
        private Integer añoSalida;
        private String genero;
        private String consola;
        private float precio;

    public Videojuego() {
    }

    public Videojuego(int id, String nombre, String desarrolladora, Integer añoSalida, String genero,String consola, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.desarrolladora = desarrolladora;
        this.añoSalida = añoSalida;
        this.genero = genero;
        this.consola=consola;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public Integer getAñoSalida() {
        return añoSalida;
    }

    public void setAñoSalida(Integer año) {
        this.añoSalida = año;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public static ArrayList<Videojuego> Filter(ArrayList<Videojuego> videojuegoArrayList, String especial, String precio){
            if(!precio.equals("null")){
                for(int i = 0;i<videojuegoArrayList.size();i++){
                    if(!videojuegoArrayList.get(i).detalleVideojuego(especial).equals(precio)){
                        videojuegoArrayList.remove(i--);
                    }
                }
            }
            return videojuegoArrayList;
        }
        public static ArrayList<Videojuego> FilterPrecio(ArrayList<Videojuego> videojuegoArrayList,float min,float max){
            for(int i = 0;i<videojuegoArrayList.size();i++){
                if(videojuegoArrayList.get(i).precio<min || videojuegoArrayList.get(i).precio>max){
                    videojuegoArrayList.remove(i--);
                }
            }
            return videojuegoArrayList;
        }

    public String detalleVideojuego(String especial){
        switch (especial){
            case "nombre": return this.nombre;
            case "desarrolladora": return this.desarrolladora;
            case "añoSalida": return this.añoSalida+"";
            case "genero": return this.genero;
            case "consola":return this.consola;
            case "precio": return this.precio+"";
            default: return "";
        }
    }public static void videojuegoDes(ArrayList<Videojuego> videojuegoArrayList,String detalle){
            String [] detalleVideojuego = {"nombre","desarrolladora","añoSalida","genero","consola","precio"};
            for(int i = 0;i<videojuegoArrayList.size();i++){
                int j = 0;
                for(j = 0;j<detalleVideojuego.length;j++){
                    if(videojuegoArrayList.get(i).detalleVideojuego(detalleVideojuego[j]).toLowerCase().contains(detalle.toLowerCase())){
                        break;
                    }
                }
                if(j==detalleVideojuego.length){
                    videojuegoArrayList.remove(i--);
                }
            }
        }
        public static void Modificar(ArrayList<Videojuego> videojuegoArrayList,int id,String nombre,String genero,String desarrolladora,Integer añoSalida,String consola, float precio){
            for(int i = 0;i<videojuegoArrayList.size();i++){
                if(id==videojuegoArrayList.get(i).getId()){
                    videojuegoArrayList.get(i).nombre = nombre;
                    videojuegoArrayList.get(i).genero = genero;
                    videojuegoArrayList.get(i).añoSalida = Integer.valueOf(añoSalida + "");
                    videojuegoArrayList.get(i).desarrolladora = desarrolladora;
                    videojuegoArrayList.get(i).consola=consola;
                    videojuegoArrayList.get(i).precio = precio;
                    break;
                }
            }
        }
    }

