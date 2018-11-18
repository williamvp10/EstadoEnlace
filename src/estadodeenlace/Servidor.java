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
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
    private String line;
    private DataInputStream is;
    private PrintStream os;
    private Socket clientSocket;
    private ArrayList<Router> Routers;
    private Ventana v;
    private animation animacion;

    public Servidor() {
        this.Routers = new ArrayList<>();
        v = new Ventana();
        // declaration section:
// declare a server socket and a client socket for the server
// declare an input and an output stream
        echoServer = null;
        clientSocket = null;
// Try to open a server socket on port 9999
// Note that we can't choose a port less than 1023 if we are not
// privileged users (root)
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
//            clientSocket = echoServer.accept();
//            is = new DataInputStream(clientSocket.getInputStream());
//            os = new PrintStream(clientSocket.getOutputStream());
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
                this.Routers.add(e);
                res = "router conectado";
                nuevoRouter(e);
            } else if (peticion[0].equals("Hello")) {
                Router e = new Router();
                e.setName(peticion[1]);
                e.setDireccion(peticion[2]);
                e.setMascara(peticion[3]);
                res = sendHello(e.getName());
            }

        } else {
            System.out.println(" vacio ");
        }

        return res;
    }

    public String sendHello(String name) {
        String res = "";
        for (int i = 0; i < this.Routers.size(); i++) {

        }
        return res;
    }

    public void nuevaAnimacion() {
        this.animacion = new animation();
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
            x = 350;
            y = 100;
        } else if (this.Routers.size() == 2){
            x = 100;
            y = 300;
        }else{
            x = 350;
            y = 300;
        }
        RouterAnimation r2 = new RouterAnimation(r, x, y);
        this.animacion.addRouter(r2);
        animacion.revalidate();
        animacion.repaint();
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
