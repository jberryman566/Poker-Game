import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client{
	
	private Socket clientSocket;
	private PrintWriter pout;
	private BufferedReader bin;
	private Scanner reader = new Scanner(System.in);
	private boolean hasConnection = false;
	
	public void startConnection(String ip, int port) throws IOException {
		try {
			clientSocket = new Socket(ip, port);
			pout = new PrintWriter(clientSocket.getOutputStream(), true);
			bin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch(IOException e) {
			System.out.println("Failed to connect to server...");
		}
	}
	
	public void start() throws IOException {
		try {
			startConnection("127.0.0.1", 6666);
			hasConnection = true;
			
			//Verify connection
			String response = getMessage();
			System.out.println(response);
			
			//Send player name
			System.out.println("Enter Player Name:");
			String name = reader.next();
			pout.println("SET NAME " + name);
			
		} catch(IOException e) {
			System.out.println("Failed to connect to server...");
		}
		
		printMenu();
		
		while (hasConnection) {
			//Begin Game Conversation
			//Create menu to deal with specific string messages
			String response = getMessage();
			System.out.println(response);
			//PRINT MENU
			
			if(response.equals("GAME_OVER!")){
				System.out.println("Game Over, Closing Connection!");
				pout.println("GoodBye");
				pout.close();
				bin.close();
				clientSocket.close();
				hasConnection = false;
			} else if(response.equals("RESPOND")){
				System.out.println("System Checking timeout, responding!");
				pout.println("HERE");
			}
		}
	}
	
	private void printMenu(){
		
		//
		System.out.println("\n\nMENU:");
	}
	
	
	public String getMessage () throws IOException {
		String response;
		
		try {
			response = bin.readLine();
		} catch(IOException e) {
			response = "Failed to get message from server...";
		}
		
		return response;
	}
	
	/*
	public void initializeData() throws IOException {
		//Start Conversation with Server
		String response = getMessage();
		
		try {
			answerServer(response);
		} catch(IOException e) {
			System.out.println("Failed to respond to server...");
		}
	}
	*/
	/*
	public void answerServer(String message) throws IOException {
		System.out.println(message);
		String answer = reader.next();
		pout.println(answer);
		
	}
	*/
	public static void main(String[] args) throws IOException{
		Client client = new Client();
		
		try {
			client.start();
		} catch(IOException e) {
			System.out.println("Failed to start client...");
		}
	}
}