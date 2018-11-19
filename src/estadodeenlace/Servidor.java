/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estadodeenlace;

import Modelo.Router;
import Modelo.RouterAnimation;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author willi
 */
public final class Servidor {

    private ServerSocket echoServer;
    private ArrayList<Router> Routers;
    private Ventana v;
    private animation animacion;

    public Servidor() {
        this.Routers = new ArrayList<>();
        v = new Ventana();
        echoServer = null;
        try {
            echoServer = new ServerSocket(9999);
        } catch (IOException e) {
            System.out.println(e);
        }
// Create a socket object from the ServerSocket to listen and accept 
// connections.
// Open input and output streams
        System.out.println(" activo ");
        nuevaAnimacion();
        this.v.start();
        try {
            while (true) {

                Socket socket;
                socket = echoServer.accept();
                //System.out.println("Nueva conexión entrante: " + socket);
                ((ServidorHilo) new ServidorHilo(socket)).start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public String validarPeticion(String val) {
        String res = "";
        String[] peticion = val.split(" ");
        if (peticion.length != 0) {
            if (peticion[0].equals("router")) {
                Router e = new Router();
                e.setName(peticion[1]);
                e.setDireccion(peticion[2]);
                e.setMascara(peticion[3]);
                ArrayList<String> conecciones = new ArrayList<>();
                String[] con = peticion[4].split(",");
                for (int i = 0; i < con.length; i++) {
                    conecciones.add(con[i]);
                }
                e.setCenecciones(conecciones);
                ArrayList<Integer> costos = new ArrayList<>();
                String[] cos = peticion[5].split(",");
                for (int i = 0; i < cos.length; i++) {
                    costos.add(Integer.parseInt(cos[i]));
                }
                e.setCostos(costos);
                e.iniciarTabla();
                this.Routers.add(e);
                res = "router conectado";
                nuevoRouter(e);
            } else if (peticion[0].equals("hello")) {
                Router e = new Router();
                e.setName(peticion[1]);
                e.setDireccion(peticion[2]);
                e.setMascara(peticion[3]);
                res = sendHello(e.getName());
            } else if (peticion[0].equals("updatetabla")) {
                UpdateRouter(peticion[1], peticion[2].split(","), peticion[3].split(","));
            }

        } else {
            System.out.println(" vacio ");
        }

        return res;
    }

    public String sendHello(String name) {
        String res = "inforouting";
        for (int i = 0; i < this.Routers.size(); i++) {
            if (this.Routers.get(i).getName().equals(name)) {
                for (int j = 0; j < Routers.get(i).getConecciones().size(); j++) {
                    this.animacion.addMensaje(name, name + ":hello", this.animacion.getPosxRouter(Routers.get(i).getConecciones().get(j)), this.animacion.getPosyRouter(Routers.get(i).getConecciones().get(j)));
//                    for (int k = 0; k < this.Routers.size(); k++) {
//                        if (this.Routers.get(k).getName().equals(Routers.get(i).getCenecciones().get(j))) {
//                            res += " " + this.Routers.get(k).getName() + " ";
//                            for (int l = 0; l < this.Routers.get(k).getCenecciones().size(); l++) {
//                                res += this.Routers.get(k).getCenecciones().get(l);
//                                if (l < this.Routers.get(k).getCenecciones().size() - 1) {
//                                    res += ",";
//                                }
//                            }
//                            for (int l = 0; l < this.Routers.get(k).getCostos().size(); l++) {
//                                res += this.Routers.get(k).getCostos().get(l);
//                                if (l < this.Routers.get(k).getCostos().size() - 1) {
//                                    res += ",";
//                                }
//                            }
//                            break;
//                        }
//                    }
                }
                break;
            }

        }
        System.out.println("res: " + res);
        animacion.revalidate();
        animacion.repaint();
        return res;
    }

    public void nuevaAnimacion() {
        this.animacion = new animation(this);
        this.animacion.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.animacion.setBounds(0, 0, 500, 500);
        this.animacion.setLayout(null);
        v.frame.add(this.animacion);
    }

    public void nuevoRouter(Router r) {
        int x = 0, y = 0;
        if (this.Routers.size() == 1) {
            x = 100;
            y = 100;
        } else if (this.Routers.size() == 2) {
            x = 100;
            y = 350;
        } else if (this.Routers.size() == 3) {
            x = 350;
            y = 350;
        } else {
            x = 350;
            y = 100;

        }
        RouterAnimation r2 = new RouterAnimation(r, x, y);
        this.animacion.addRouter(r2);
        animacion.revalidate();
        animacion.repaint();
    }

    public void UpdateRouter(String name, String[] rout, String[] cost) {
        for (int i = 0; i < this.Routers.size(); i++) {
            Router r = this.Routers.get(i);
            if (r.getName().equals(name)) {
                ArrayList<ArrayList<String>> t = r.getTablaEnrrutamiento();
                for (int j = 0; j < rout.length; j++) {
                    if (j < t.get(0).size() - 1) {
                        t.get(0).set(i, rout[j]);
                        t.get(1).set(i, cost[j]);
                    } else {
                        t.get(0).add(rout[j]);
                        t.get(1).add(cost[j]);
                    }
                }
                this.Routers.get(i).setTablaEnrrutamiento(t);
                this.animacion.updateRouter(this.Routers.get(i));
                break;
            }
        }

        animacion.revalidate();
        animacion.repaint();
    }

    public void validarTabla(RouterAnimation a, RouterAnimation b) {
        if (a.getRouter().updateTabla(b.getRouter())) {
            for (int i = 0; i < a.getRouter().getConecciones().size(); i++) {
                if (!a.getRouter().getConecciones().get(i).equals(b.getRouter().getName())) {
                    ArrayList<RouterAnimation> r = this.animacion.getRouters();
                    for (int j = 0; j < r.size(); j++) {
                        if (r.get(j).getRouter().getName().equals(a.getRouter().getConecciones().get(i))) {
                            this.animacion.addMensaje(a.getRouter().getName(), a.getRouter().getName() + ":Link State Packets",
                                     r.get(j).getX(), r.get(j).getY());
                        }
                    }

                }
            }
            animacion.updateRouter(a.getRouter());
            animacion.revalidate();
            animacion.repaint();
        } else {
            System.out.println(" no actualizo");
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
    }

    /*
    clase hilo servidor 
    
     */
    public class ServidorHilo extends Thread {

        private Socket socket;
        private DataOutputStream dos;
        private DataInputStream dis;

        public ServidorHilo(Socket socket) {
            this.socket = socket;
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void desconnectar() {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            String accion = "";
            try {
                accion = dis.readUTF();
                System.out.println("cliente: " + accion);
                String response = validarPeticion(accion);
                System.out.println("res: " + response);
                dos.writeUTF(response);
//            accion = dis.readUTF();
//            if(accion.equals("hola")){
//                System.out.println("El cliente con idSesion "+this.idSessio+" saluda");
//                dos.writeUTF("adios");
//            }
            } catch (IOException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
            //desconnectar();
        }

    }

}
