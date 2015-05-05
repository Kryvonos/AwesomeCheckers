package main;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private boolean isPlaying = true;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private Socket socket = null;

   public Client()
   {
       new Thread(this).start();
   }

    @Override
    public void run() {
        try {
         socket = new Socket("127.0.0.1", Server.PORT);
         input = new DataInputStream(socket.getInputStream());
         output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ie) {
            }
    }

  public void close(){
      try {
          input.close();
          output.close();
          socket.close();
      } catch (IOException e) {
          e.printStackTrace();
      }

  }
}
