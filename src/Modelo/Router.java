/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author willi
 */
public class Router {

    private String name;
    private String direccion;
    private String mascara;

    private ArrayList<String> conecciones;
    private ArrayList<Integer> costos;
    private ArrayList<ArrayList<String>> tablaEnrrutamiento;

    public Router() {
        this.name = "";
        this.direccion = "";
        this.mascara = "";
        this.conecciones = new ArrayList<>();
        this.costos = new ArrayList<>();
        this.tablaEnrrutamiento = new ArrayList<>();
    }

    public Router(String name, String direccion, String mascara, ArrayList<String> conecciones, ArrayList<Integer> costos) {
        this.name = name;
        this.direccion = direccion;
        this.mascara = mascara;
        this.conecciones = conecciones;
        this.costos = costos;
        this.tablaEnrrutamiento = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public ArrayList<String> getCenecciones() {
        return conecciones;
    }

    public void setCenecciones(ArrayList<String> cenecciones) {
        this.conecciones = cenecciones;
    }

    public ArrayList<Integer> getCostos() {
        return costos;
    }

    public void setCostos(ArrayList<Integer> costos) {
        this.costos = costos;
    }

    public ArrayList<String> getConecciones() {
        return conecciones;
    }

    public void setConecciones(ArrayList<String> conecciones) {
        this.conecciones = conecciones;
    }

    public ArrayList<ArrayList<String>> getTablaEnrrutamiento() {
        return tablaEnrrutamiento;
    }

    public void setTablaEnrrutamiento(ArrayList<ArrayList<String>> tablaEnrrutamiento) {
        this.tablaEnrrutamiento = tablaEnrrutamiento;
    }

    
    
    
}
