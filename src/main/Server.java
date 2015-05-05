package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    public static final int PORT = 3001;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private ServerSocket serverSocket = null;
    private Socket socket = null;

    public Server(){
        new Thread(this).start();
    }

    @Override
  public void  run(){
    try {
         serverSocket = new ServerSocket(PORT);
         socket = serverSocket.accept();

        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
    }catch(IOException ei){
        ei.printStackTrace();
    }
   }

    public void sendCoor(int x, int y, boolean isOver){

    }

  public void close(){
      try {
          input.close();
          output.close();
          socket.close();
          serverSocket.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

}