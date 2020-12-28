package model;

import java.util.ArrayList;


    public class Consola{
        private int id;
        private String nombreConsola;
        private String fabricanteConsola;
        private Integer añoSalidaConsola;
        private Integer precioConsola;

        public Consola(){

        }

        public Consola(int id, String nombreConsola, String fabricanteConsola, Integer añoSalidaConsola, Integer precioConsola) {
            this.id = id;
            this.nombreConsola = nombreConsola;
            this.fabricanteConsola = fabricanteConsola;
            this.añoSalidaConsola = añoSalidaConsola;
            this.precioConsola = precioConsola;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombreConsola() {
            return nombreConsola;
        }

        public void setNombreConsola(String nombreConsola) {
            this.nombreConsola = nombreConsola;
        }

        public String getFabricanteConsola() {
            return fabricanteConsola;
        }

        public void setFabricanteConsola(String fabricanteConsola) {
            this.fabricanteConsola = fabricanteConsola;
        }

        public Integer getAñoSalidaConsola() {
            return añoSalidaConsola;
        }

        public void setAñoSalidaConsola(Integer añoSalidaConsola) {
            this.añoSalidaConsola = añoSalidaConsola;
        }

        public Integer getPrecioConsola() {
            return precioConsola;
        }

        public void setPrecioConsola(Integer precioConsola) {
            this.precioConsola = precioConsola;
        }

        public static ArrayList<Consola> Filter(ArrayList<model.Consola> consolaArrayList, String consolaEspecial, String precioConsola) {
            if (!precioConsola.equals("null")) {
                for (int i = 0; i < consolaArrayList.size(); i++) {
                    if (!consolaArrayList.get(i).detalleConsola(consolaEspecial).equals(precioConsola)) {
                        consolaArrayList.remove(i--);
                    }
                }
            }
            return consolaArrayList;
        }

        public static ArrayList<Consola> FilterPrecioConsola(ArrayList<Consola> consolaArrayList, float min, float max) {
            for (int i = 0; i < consolaArrayList.size(); i++) {
                if (consolaArrayList.get(i).precioConsola < min || consolaArrayList.get(i).precioConsola > max) {
                    consolaArrayList.remove(i--);
                }
            }
            return consolaArrayList;
        }
        public String detalleConsola(String consolaEspecial) {
            switch (consolaEspecial) {
                case "nombreConsola":
                    return this.nombreConsola;
                case "fabricanteConsola":
                    return this.fabricanteConsola;
                case "añoSalidaConsola":
                    return this.añoSalidaConsola + "";

                case "precioConsola":
                    return this.precioConsola + "";
                default:
                    return "";
            }
        }
        public static void consolaDes(ArrayList<Consola> consolaArrayList, String descripcionConsola) {
            String[] detalleConsola = {"nombreConsola", "fabricanteConsola", "añoSalidaConsola", "precioConsola"};
            for (int i = 0; i < consolaArrayList.size(); i++) {
                int j = 0;
                for (j = 0; j < detalleConsola.length; j++) {
                    if (consolaArrayList.get(i).detalleConsola(detalleConsola[j]).toLowerCase().contains(descripcionConsola.toLowerCase())) {
                        break;
                    }
                }
                if (j == detalleConsola.length) {
                    consolaArrayList.remove(i--);
                }
            }
        }
        public static void ModificarConsola(ArrayList<Consola> consolaArrayList, int id, String nombreConsola, String fabricanteConsola, Integer añoSalidaConsola, Integer precioConsola) {
            for (int i = 0; i < consolaArrayList.size(); i++) {
                if (id == consolaArrayList.get(i).getId()) {
                    consolaArrayList.get(i).nombreConsola= nombreConsola;
                    consolaArrayList.get(i).fabricanteConsola = fabricanteConsola;
                    consolaArrayList.get(i).añoSalidaConsola = Integer.valueOf(añoSalidaConsola + "");

                    consolaArrayList.get(i).precioConsola = Integer.valueOf(precioConsola + "");

                    break;
                }
            }

        }
    }


