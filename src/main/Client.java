package main;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private boolean isPlaying = true;
    private int stepX;
    private int stepY;
   public Client(){
       new Thread(this).start();
   }

    @Override
    public void run(){

        try {
            Socket socket = new Socket("127.0.0.1", Server.PORT);
           DataInputStream input = null;
            DataOutputStream output = null;
            try{
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                while(isPlaying){
                    stepX = input.readInt();
                    stepY = input.readInt();
                    isPlaying = input.readBoolean();
                    System.out.println(stepX);
                    System.out.println(stepY);
                    getPlayerStep();
                    output.writeInt(stepX);
                    output.writeInt(stepY);
                }
            }finally{
                System.out.println("socket close");
                output.close();
                input.close();
                socket.close();
                }
            }catch(IOException ie) {
        }
     }

    private void getPlayerStep() {

    }

    public static void main(String[] args){
        Client client =  new Client();
   }

}
