package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static final int PORT = 3001;
	public static final String HOST = "127.0.0.1";

	private static ArrayList<Room> rooms = new ArrayList<Room>();

	public static ArrayList<Room> getRoomsList(){
		return rooms;
	}

	public static void main(String []args) throws IOException{ 
		ServerSocket serverSocket = new ServerSocket(PORT);
		try{
			while(true){
				Socket socket = serverSocket.accept();
				
				System.out.println("socket: " + socket);
				
				new JabberServer(socket);
//				if (rooms.size() > 1){
//					deleteRoom();
//				}
			}
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			serverSocket.close();
		} 
	}
	

	private void deleteRoom() {
		for (Room r: rooms){
			if (!r.isOver()){
				rooms.remove(r);
			}
		}
		
	}

}