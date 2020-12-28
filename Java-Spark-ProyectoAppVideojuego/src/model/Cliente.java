package model;

import java.util.ArrayList;

public class Cliente {
    private int id;
    private String nombreCliente;
    private String direccionCliente;
    private Integer fechaNacimientoCliente;
    private Integer comprasRealizadas;



    public Cliente() {

    }

    public Cliente(int id, String nombreCliente, String direccionCliente, Integer fechaNacimientoCliente,Integer comprasRealizadas) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.fechaNacimientoCliente = fechaNacimientoCliente;

        this.comprasRealizadas = comprasRealizadas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public int getFechaNacimientoCliente() {
        return fechaNacimientoCliente;
    }

    public void setFechaNacimientoCliente(int fechaNacimientoCliente) {
        this.fechaNacimientoCliente = fechaNacimientoCliente;
    }



    public static ArrayList<Cliente> Filter(ArrayList<Cliente> clienteArrayList, String clienteEspecial, String comprasRealizadas) {
        if (!comprasRealizadas.equals("null")) {
            for (int i = 0; i < clienteArrayList.size(); i++) {
                if (!clienteArrayList.get(i).detalleCliente(clienteEspecial).equals(comprasRealizadas)) {
                    clienteArrayList.remove(i--);
                }
            }
        }
        return clienteArrayList;
    }

    public static ArrayList<Cliente> FilterComprasRealizadas(ArrayList<Cliente> clienteArrayList, float min, float max) {
        for (int i = 0; i < clienteArrayList.size(); i++) {
            if (clienteArrayList.get(i).comprasRealizadas < min || clienteArrayList.get(i).comprasRealizadas > max) {
                clienteArrayList.remove(i--);
            }
        }
        return clienteArrayList;
    }

    public String detalleCliente(String clienteEspecial) {
        switch (clienteEspecial) {
            case "nombreCliente":
                return this.nombreCliente;
            case "direccionCliente":
                return this.direccionCliente;
            case "fechaNacimientoCliente":
                return this.fechaNacimientoCliente + "";

            case "comprasRealizadas":
                return this.comprasRealizadas + "";
            default:
                return "";
        }
    }

    public static void clienteDes(ArrayList<Cliente> clienteArrayList, String descripcionCliente) {
        String[] detalleCliente = {"nombreCliente", "direccionCliente", "fechaNacimientoCliente", "comprasRealizadas"};
        for (int i = 0; i < clienteArrayList.size(); i++) {
            int j = 0;
            for (j = 0; j < detalleCliente.length; j++) {
                if (clienteArrayList.get(i).detalleCliente(detalleCliente[j]).toLowerCase().contains(descripcionCliente.toLowerCase())) {
                    break;
                }
            }
            if (j == detalleCliente.length) {
                clienteArrayList.remove(i--);
            }
        }
    }

    public static void ModificarCliente(ArrayList<Cliente> clienteArrayList, int id, String nombreCliente, String direccionCliente, Integer fechaNacimientoCliente,Integer comprasRealizadas) {
        for (int i = 0; i < clienteArrayList.size(); i++) {
            if (id == clienteArrayList.get(i).getId()) {
                clienteArrayList.get(i).nombreCliente = nombreCliente;
                clienteArrayList.get(i).direccionCliente = direccionCliente;
                clienteArrayList.get(i).fechaNacimientoCliente = Integer.valueOf(fechaNacimientoCliente + "");

                clienteArrayList.get(i).comprasRealizadas = Integer.valueOf(comprasRealizadas + "");

                break;
            }
        }

    }
}