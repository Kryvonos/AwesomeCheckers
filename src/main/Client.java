package main;

<<<<<<< HEAD
=======
<<<<<<< HEAD
import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

	private Socket socket = null;
	private String host;
	private int port;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		new Thread(this).start();
	}
	
	public Move getMove(){
	Move move = null;
		try {
			move = (Move)in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return move;
	}

	public void sendMove(Move move){
		try {
			out.writeObject(move);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			socket = new Socket(host, port);
			in = new ObjectInputStream(socket.getInputStream());
			out= new ObjectOutputStream(socket.getOutputStream());
			try{
			//	while(true){
				Move move = new Move(3,4,5,6);
					sendMove(move);
					System.out.println(getMove().toString());
			//	}
			}finally{
				socket.close();
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		System.out.println("client");
	 new Client(Server.HOST, Server.PORT);
	}
}

