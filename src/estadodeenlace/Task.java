/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estadodeenlace;

import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
public class Task extends TimerTask {
    private Router1 cliente;
    
    public Task(Router1 r){
        this.cliente=r;
    }
    
    @Override
    public void run() {
        System.out.println("hello");
        this.cliente.enviarinfoHello();
        this.cliente.addtask(new Task(this.cliente), 7000);
    }

}
