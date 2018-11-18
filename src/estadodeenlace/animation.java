/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estadodeenlace;

import Modelo.Router;
import Modelo.RouterAnimation;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author willi
 */
public class animation extends JPanel implements ActionListener {

    private Timer timer;
    private final int DELAY = 12;
    ArrayList<RouterAnimation> routers;
    ArrayList<Image> rout;

    public animation() {
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
            for (int j = 0; j < a.getCenecciones().size(); j++) {
                int x2=0,y2=0;
                for (int k = 0; k < routers.size(); k++) {
                    if(a.getCenecciones().get(j).equals(routers.get(k).getRouter().getName()) ){
                        x2=routers.get(k).getX();
                        y2=routers.get(k).getY();
                         g.drawLine(routers.get(i).getX()+20, routers.get(i).getY()+20, x2+20, y2+20);
                    }
                }
            }
        }
        
        
        //paint images
        for (int i = 0; i < routers.size(); i++) {
            g.drawString(routers.get(i).getRouter().getName(), routers.get(i).getX() + 25, routers.get(i).getY() - 10);
            g.drawImage(routers.get(i).getR(), routers.get(i).getX(), routers.get(i).getY(), this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
