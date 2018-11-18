/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

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
    private int x, y;
    private Image r;
    public RouterAnimation(Router router,int x,int y) {
        this.router = router;
        this.x = x;
        this.y = y;
        this.r = new ImageIcon("imagRouter.png").getImage();
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
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

    
}
