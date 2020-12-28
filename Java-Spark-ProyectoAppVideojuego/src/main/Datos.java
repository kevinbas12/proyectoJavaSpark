package main;

import model.Cliente;
import model.Consola;
import model.Especial;
import model.Videojuego;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

public class Datos {

        public static boolean writeToJSON(ArrayList<Videojuego> list, String path){
            try {
                Writer writer=new FileWriter(path);
                Gson gson = new Gson();
                gson.toJson(list,writer);
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

    public static boolean writeToJSONCliente(ArrayList<Cliente> list, String pathCliente){
        try{
            Writer writer=new FileWriter(pathCliente);
            Gson gson = new Gson();
            gson.toJson(list,writer);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean writeToJSONEspecial(ArrayList<Especial> list, String pathEspecial){
        try{
            Writer writer=new FileWriter(pathEspecial);
            Gson gson = new Gson();
            gson.toJson(list,writer);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean writeToJSONConsola(ArrayList<Consola> list, String pathConsola){
        try{
            Writer writer=new FileWriter(pathConsola);
            Gson gson = new Gson();
            gson.toJson(list,writer);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
        public static ArrayList<Videojuego> readFromJson(String path){
            if(!new File(path).exists()){
                return new ArrayList<>();
            }
            try {
                JsonReader reader=new JsonReader(new FileReader(path));
                Gson gson = new Gson();
                return gson.fromJson(reader,new TypeToken<ArrayList<Videojuego>>(){}.getType());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    public static ArrayList<Cliente> readFromJsonCliente(String pathCliente){
        if(!new File(pathCliente).exists()){
            return new ArrayList<>();
        }
        try {
            JsonReader reader=new JsonReader(new FileReader(pathCliente));
            Gson gson = new Gson();
            return gson.fromJson(reader,new TypeToken<ArrayList<Cliente>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public static ArrayList<Especial> readFromJsonEspecial(String pathEspecial){
        if(!new File(pathEspecial).exists()){
            return new ArrayList<>();
        }
        try {
            JsonReader reader=new JsonReader(new FileReader(pathEspecial));
            Gson gson = new Gson();
            return gson.fromJson(reader,new TypeToken<ArrayList<Especial>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public static ArrayList<Consola> readFromJsonConsola(String pathConsola){
        if(!new File(pathConsola).exists()){
            return new ArrayList<>();
        }
        try {
            JsonReader reader=new JsonReader(new FileReader(pathConsola));
            Gson gson = new Gson();
            return gson.fromJson(reader,new TypeToken<ArrayList<Consola>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    }

