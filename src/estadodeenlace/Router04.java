/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estadodeenlace;

import Modelo.Router;
import java.util.ArrayList;

/**
 *
 * @author willi
 */
public class Router04 {
    public static void main(String[] args) {
         Router r = new Router();
        r.setName("D");
        r.setDireccion("200.1.4.1");
        r.setMascara("24");
        ArrayList<String> conecciones = new ArrayList<>();
        conecciones.add("A");
        r.setCenecciones(conecciones);
        ArrayList<Integer> costos = new ArrayList<>();
        costos.add(1);
        r.setCostos(costos);
        Router1 a = new Router1(r);
    }
   
}
