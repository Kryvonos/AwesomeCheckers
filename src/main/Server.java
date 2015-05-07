package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
	public static final int PORT = 3001;
	public static final String HOST = "127.0.0.1";

	private ArrayList<Room> rooms = new ArrayList<Room>();

	public ArrayList<Room> getRoomsList(){
		return rooms;
	}
	
	public Server() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		try{
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println(serverSocket);
		try{
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println(socket);
				new JabberServer(socket, this);
				if (rooms.size() > 1){
					deleteRoom();
				}
			}
		}finally{
			serverSocket.close();
			
		}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

	private void deleteRoom() {
		for (Room r: rooms){
			if (!r.isOver()){
				rooms.remove(r);
			}
		}
		
	}

	public static void main(String []args){
		System.out.println("server");
		new Server();
	}
}