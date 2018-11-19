/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estadodeenlace;

import Modelo.Router;
import Modelo.RouterAnimation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author willi
 */
public class animation extends JPanel implements ActionListener {
    
    private Timer timer;
    private final int DELAY = 15;
    ArrayList<RouterAnimation> routers;
    ArrayList<Image> rout;
    Servidor s;
    
    public animation(Servidor s) {
        this.s = s;
        rout = new ArrayList<Image>();
        routers = new ArrayList<RouterAnimation>();
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D h = (Graphics2D) g;
        g.setColor(Color.BLACK);
        
        for (int i = 0; i < routers.size(); i++) {
            Router a = routers.get(i).getRouter();
            for (int j = 0; j < a.getConecciones().size(); j++) {
                int x2 = 0, y2 = 0;
                for (int k = 0; k < routers.size(); k++) {
                    if (a.getConecciones().get(j).equals(routers.get(k).getRouter().getName())) {
                        x2 = routers.get(k).getX();
                        y2 = routers.get(k).getY();
                        g.drawLine(routers.get(i).getX() + 20, routers.get(i).getY() + 20, x2 + 20, y2 + 20);
                    }
                }
            }
        }

        //paint images
        for (int i = 0; i < routers.size(); i++) {
            g.drawString(routers.get(i).getRouter().getName(), routers.get(i).getX() + 25, routers.get(i).getY() - 10);
            g.drawImage(routers.get(i).getR(), routers.get(i).getX(), routers.get(i).getY(), this);
        }

        //paint TABLES
        g.setColor(Color.RED);
        for (int i = 0; i < routers.size(); i++) {
            g.drawString(routers.get(i).getTablaCon(), routers.get(i).getXt(), routers.get(i).getYt());
            g.drawString(routers.get(i).getTablaCos(), routers.get(i).getXt() - 10, routers.get(i).getYt() + 10);
        }

        //paint Mensaje
        g.setColor(Color.BLUE);
        for (int i = 0; i < routers.size(); i++) {
            ArrayList<Mensaje> msg = routers.get(i).getMensajes();
            for (int j = 0; j < msg.size(); j++) {
                if (msg.get(j).isActivo()) {
                    g.drawString(msg.get(j).getMsg(), msg.get(j).getX(), msg.get(j).getY());
                    if (msg.get(j).getX() < msg.get(j).getX2()) {
                        msg.get(j).setX(msg.get(j).getX() + 1);
                    } else if (msg.get(j).getX() > msg.get(j).getX2()) {
                        msg.get(j).setX(msg.get(j).getX() - 1);
                    }
                    if (msg.get(j).getY() < msg.get(j).getY2()) {
                        msg.get(j).setY(msg.get(j).getY() + 1);
                    } else if (msg.get(j).getY() > msg.get(j).getY2()) {
                        msg.get(j).setY(msg.get(j).getY() - 1);
                    }
                    if (msg.get(j).getX() == msg.get(j).getX2() && msg.get(j).getY() == msg.get(j).getY2()) {
                        msg.get(j).setActivo(false);
                        if (msg.get(j).getMsg().split(":")[1].equals("hello")) {
                            for (int k = 0; k < routers.size(); k++) {
                                if (routers.get(k).getX() == msg.get(j).getX() && routers.get(k).getY() == msg.get(j).getY()) {
                                    this.s.validarTabla(routers.get(k), routers.get(i));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    public ArrayList<RouterAnimation> getRouters() {
        return routers;
    }
    
    public void setRouters(ArrayList<RouterAnimation> routers) {
        this.routers = routers;
    }
    
    public void addRouter(RouterAnimation r) {
        routers.add(r);
    }
    
    public void addMensaje(String name, String msg, int x2, int y2) {
        for (int i = 0; i < this.routers.size(); i++) {
            if (this.routers.get(i).getRouter().getName().equals(name)) {
                this.routers.get(i).addMensaje(msg, x2, y2);
            }
        }
    }
    
    public int getPosxRouter(String name) {
        int posx = 0;
        for (int i = 0; i < this.routers.size(); i++) {
            if (this.routers.get(i).getRouter().getName().equals(name)) {
                posx = this.routers.get(i).getX();
            }
        }
        return posx;
    }
    
    public int getPosyRouter(String name) {
        int posy = 0;
        for (int i = 0; i < this.routers.size(); i++) {
            if (this.routers.get(i).getRouter().getName().equals(name)) {
                posy = this.routers.get(i).getY();
            }
        }
        return posy;
    }
    
    public void updateRouter(Router r) {
        for (int i = 0; i < this.routers.size(); i++) {
            if (this.routers.get(i).getRouter().getName().equals(r.getName())) {
                this.routers.get(i).setRouter(r);
                break;
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
}
