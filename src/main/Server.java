package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    public static final int PORT = 3001;
    private boolean isPlaying = true;
    private int stepX = 0;
    private int stepY = 0;

    public Server(){
        new Thread(this).start();
    }

    @Override
  public void  run(){
    try {
        ServerSocket serverSocket = new ServerSocket(PORT);
       try{
           Socket socket = serverSocket.accept();
           System.out.println(socket);
           DataInputStream input = null;
           DataOutputStream output = null;
           try{
               input = new DataInputStream(socket.getInputStream());
               output = new DataOutputStream(socket.getOutputStream());
               while(isPlaying){
                   getPlaerStep();
                  output.writeInt(stepX);
                   output.writeInt(stepY);
                   output.writeBoolean(true);
                   stepX = input.readInt();
                   stepY = input.readInt();
                   System.out.println(stepX);
                   System.out.println(stepY);


               }
           }finally{
              output.close();
               input.close();
               socket.close();
           }
       }finally{
           serverSocket.close();
       }
    }catch(IOException ei){
        ei.printStackTrace();
    }
   }

    private void getPlaerStep() {

    }

    public static void main(String[] args){

        Server server = new Server();
    }

}