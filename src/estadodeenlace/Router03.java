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
public class Router03 {
    public static void main(String args[]) {
        Router r = new Router();
        r.setName("C");
        r.setDireccion("200.1.3.1");
        r.setMascara("24");
        ArrayList<String> conecciones = new ArrayList<>();
        conecciones.add("A");
        conecciones.add("B");
        r.setCenecciones(conecciones);
        ArrayList<Integer> costos = new ArrayList<>();
        costos.add(10);
        costos.add(20);
        r.setCostos(costos);
        Router1 a = new Router1(r);
    }
}
