/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import estadodeenlace.Mensaje;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author willi
 */
public class RouterAnimation {

    private Router router;
    private ArrayList<Mensaje> mensajes;
    private String tablaCon;
    private String tablaCos;
    private int x, y, xt, yt;
    private Image r;

    public RouterAnimation(Router router, int x, int y) {
        this.tablaCon="";
        this.tablaCos="";
        this.mensajes = new ArrayList<Mensaje>();
        this.r = new ImageIcon("imagRouter.png").getImage();
        this.router = router;
        this.x = x;
        this.y = y;

        if (x < 150) {
            this.xt = x - 80;
            this.yt = y + 20;
        } else {
            this.xt = x + 90;
            this.yt = y + 20;
        }

        if (this.router.getTablaEnrrutamiento().get(0).size() != 0) {
            updateTablaCon();
            updateTablaCos();
        }

    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
        updateTablaCon();
        updateTablaCos();
    }

    public void updateTablaCon() {
        this.tablaCon = "     ";
        for (int i = 0; i < this.router.getTablaEnrrutamiento().get(0).size(); i++) {
            this.tablaCon += "|  " + this.router.getTablaEnrrutamiento().get(0).get(i) + " ";
        }
    }

    public void updateTablaCos() {
        this.tablaCos = "VAL";
        for (int i = 0; i < this.router.getTablaEnrrutamiento().get(1).size(); i++) {
            this.tablaCos += "| " + this.router.getTablaEnrrutamiento().get(1).get(i) + " ";
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getR() {
        return r;
    }

    public void setR(Image r) {
        this.r = r;
    }

    public int getXt() {
        return xt;
    }

    public void setXt(int xt) {
        this.xt = xt;
    }

    public int getYt() {
        return yt;
    }

    public void setYt(int yt) {
        this.yt = yt;
    }

    public String getTablaCon() {
        return tablaCon;
    }

    public void setTablaCon(String tablaCon) {
        this.tablaCon = tablaCon;
    }

    public String getTablaCos() {
        return tablaCos;
    }

    public void setTablaCos(String tablaCos) {
        this.tablaCos = tablaCos;
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public void addMensaje(String msg, int x2, int y2) {
        if (x2 != 0 && y2 != 0) {
            Mensaje e = new Mensaje(msg, this.x, x2, this.y, y2);
            this.mensajes.add(e);
        }
    }

}
