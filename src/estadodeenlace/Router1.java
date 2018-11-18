
package estadodeenlace;

import Modelo.Router;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;

/**
 *
 * @author willi
 *
 */
public class Router1 {

    private Socket smtpSocket;
    private DataOutputStream os;
    private DataInputStream is;
    private Router router;
    private Timer timer;

    public Router1(Router r) {
        this.router = new Router();
        this.router=r;
        this.timer = new Timer();
        // declaration section:
        // smtpClient: our client socket
        // os: output stream
        // is: input stream
        smtpSocket = null;
        os = null;
        is = null;
        // Initialization section:
        // Try to open a socket on port 25
        // Try to open input and output streams
        try {
            smtpSocket = new Socket("127.0.0.1", 9999);
            os = new DataOutputStream(smtpSocket.getOutputStream());
            is = new DataInputStream(smtpSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: hostname");
        }
        // If everything has been initialized then we want to write some data
        // to the socket we have opened a connection to on port 25
        addtask(new Task(this), 2000);
        if (smtpSocket != null && os != null && is != null) {
            try {
                //conect router 

                os.writeUTF(enviarinfoRouter() + "\n");
                // keep on reading from/to the socket till we receive the "Ok" from SMTP,
                // once we received that then we want to break.
                String responseLine;
                while ((responseLine = is.readUTF()) != null) {
                    System.out.println("Servidor: " + responseLine);

                    if (responseLine.indexOf("helloresponse") != -1) {
                        break;
                    }
                }
                // clean up:
                // close the output stream
                // close the input stream
                // close the socket
                os.close();
                is.close();
                smtpSocket.close();
            } catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    private String enviarinfoRouter() {
        String info = "router ";
        info += this.router.getName() + " ";
        info += this.router.getDireccion() + " ";
        info += this.router.getMascara() + " ";
        
        for (int i = 0; i < this.router.getConecciones().size(); i++) {
            info+=this.router.getConecciones().get(i);
            if(i!=this.router.getConecciones().size()-1){
                info+=",";
            }else{
                info+=" ";
            }
        }
        for (int i = 0; i < this.router.getCostos().size(); i++) {
            info+=this.router.getCostos().get(i);
            if(i!=this.router.getCostos().size()-1){
                info+=",";
            }else{
                info+=" ";
            }
        }
        return info;
    }

    public String enviarinfoHello() {
        String info = "hello ";
        info += this.router.getName() + " ";
        info += this.router.getDireccion() + " ";
        info += this.router.getMascara() + " ";
        
        return info;
    }

    public void addtask(Task task, int time) {
        this.timer.schedule(task, time);
    }
    public void purgeTimer() {
        this.timer = new Timer();
    }

    public void cancelTimer() {
        this.timer.cancel();
    }
}
