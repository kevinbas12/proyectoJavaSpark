package model;

import java.util.ArrayList;

public class Especial {
    private int id;
    private String nombreEspecial;
    private String juegoEspecial;
    private Integer precioEspecial;

    public Especial() {
    }

    public Especial(int id, String nombreEspecial, String juegoEspecial, Integer precioEspecial) {
        this.id = id;
        this.nombreEspecial = nombreEspecial;
        this.juegoEspecial = juegoEspecial;
        this.precioEspecial = precioEspecial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEspecial() {
        return nombreEspecial;
    }

    public void setNombreEspecial(String nombreEspecial) {
        this.nombreEspecial = nombreEspecial;
    }

    public String getJuegoEspecial() {
        return juegoEspecial;
    }

    public void setJuegoEspecial(String juegoEspecial) {
        this.juegoEspecial = juegoEspecial;
    }

    public Integer getPrecioEspecial() {
        return precioEspecial;
    }

    public void setPrecioEspecial(Integer precioEspecial) {
        this.precioEspecial = precioEspecial;
    }

    public static ArrayList<Especial> Filter(ArrayList<Especial> especialArrayList, String especial, String precio){
        if(!precio.equals("null")){
            for(int i = 0;i<especialArrayList.size();i++){
                if(!especialArrayList.get(i).detalleEspecial(especial).equals(precio)){
                    especialArrayList.remove(i--);
                }
            }
        }
        return especialArrayList;
    }

    public static ArrayList<Especial> FilterPrecio(ArrayList<Especial> especialArrayList,float min,float max){
        for(int i = 0;i<especialArrayList.size();i++){
            if(especialArrayList.get(i).precioEspecial<min || especialArrayList.get(i).precioEspecial>max){
                especialArrayList.remove(i--);
            }
        }
        return especialArrayList;
    }
    public String detalleEspecial(String especial){
        switch (especial){
            case "nombreEspecial": return this.nombreEspecial;
            case "juegoEspecial": return this.juegoEspecial;
            case "precioEspecial": return this.precioEspecial+"";
            default: return "";
        }
    }

    public static void especialDes(ArrayList<Especial> especialArrayList,String detalle){
        String [] detalleEspecial = {"nombreEspecial","juegoEspecial","precioEspecial"};
        for(int i = 0;i<especialArrayList.size();i++){
            int j = 0;
            for(j = 0;j<detalleEspecial.length;j++){
                if(especialArrayList.get(i).detalleEspecial(detalleEspecial[j]).toLowerCase().contains(detalle.toLowerCase())){
                    break;
                }
            }
            if(j==detalleEspecial.length){
                especialArrayList.remove(i--);
            }
        }
    }
    public static void ModificarEspecial(ArrayList<Especial> especialArrayList,int id,String nombreEspecial,String juegoEspecial, int precioEspecial){
        for(int i = 0;i<especialArrayList.size();i++){
            if(id==especialArrayList.get(i).getId()){
                especialArrayList.get(i).nombreEspecial = nombreEspecial;
                especialArrayList.get(i).juegoEspecial = juegoEspecial;
                especialArrayList.get(i).precioEspecial = precioEspecial;
                break;
            }
        }
    }
}


