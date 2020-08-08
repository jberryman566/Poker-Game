import java.net.*;
import java.io.*;
import java.util.*;

public class GameServer{
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter pout;
	private BufferedReader bin;
	private int serverPORT = 6666;
	private Player player; 
	//private CardDeck deck;
	
	//Can be changed as needed. Number of players needed for match
	private int numPlayers = 5;
	private int playerCounter = 0;
	private boolean debugMode = true;
	private boolean readyToPlay = false;
	private boolean firstRound = true;
	
	//Blind variables for betting
	private Player littleBlind;
	private Player bigBlind;
	
	//Array of Clients
	private Player[] Clients;
	private Queue<String[]> Tasks;
	
	private void start() throws IOException, NoCardsException {
		
		//Create array of players for the match
		Clients = new Player[numPlayers];
		
		//Create a queue object for the server to deal with requests
		Tasks = new LinkedList<>();
		
		serverSocket = new ServerSocket(serverPORT);
		serverSocket.setSoTimeout(500);
		run();
		
	}
	
	private void run() throws IOException{
		
		boolean inMenu = true;
		while(inMenu){
			
			
			//Check for any client connections
			Clients[playerCounter] = fetchPlayer();
			
			//Check for tasks
			if (playerCounter > 1){
				checkForUpdates();
			}
			
			//Do a task
			if (Tasks.size() != 0){
				String[] task = Tasks.remove();
				if(task.length > 2){
					System.out.println("Command: " + task[0] + ", Operand: " + task[1] + ", Value: " + task[2]);
				} else {
					System.out.println("Command: "+ task[0] + ", Operand: " + task[1]);
				}
			}
			
		}		
	}
	
	private void checkForUpdates(){
		
		for (Player player : Clients){
			String response = player.getBufferedReader().readLine();
			String[] task = response.split(" ");
			Tasks.add(task);
		}
	}
	
	
	
	//Method for waiting and accepting a new player connection. Returns Player.
	private Player fetchPlayer() throws IOException {
		
		try {
			System.out.println("Waiting for player to connect...");

			clientSocket = serverSocket.accept();
			System.out.println("Creating new player...");
			player = createPlayer(clientSocket);
			playerCounter++;
				
		} catch(IOException e) {
			System.out.println("Failed to retrieve new player...");
		}
		return player;
	}
	
	//Method for sending generic string methods to players and returns a response.
	private String sendPlayerMessage(String message, Player player) throws IOException{
		String response;
		try{
			player.getPrintWriter().println(message);
			response = player.getBufferedReader().readLine();
			
		} catch(IOException e) {
			response = "Failed to connect to client...";
		}
		
		return response;
	}
	
	//Method for creating new player objects for client connections. Returns Player object.
	private Player createPlayer(Socket client) throws IOException {
		
		//Create Player object
		player = new Player();
		player.setSocket(client);
		
		try {
			player.setPrintWriter(new PrintWriter(client.getOutputStream(), true));
			player.setBufferedReader(new BufferedReader(new InputStreamReader(client.getInputStream())));
		} catch(IOException e) {
			System.out.println("Failed to connect to client...");
		}
		return player;
	}
	
	//Method for safely stopping the server
	private void stop() throws IOException {
        
		try {
			bin.close();
			pout.close();
			clientSocket.close();
			serverSocket.close();
		} catch(IOException e) {
			System.out.println("Failed to close Server correctly...");
		}
    }
	
	public static void main(String[] args) throws IOException, NoCardsException{
		GameServer server = new GameServer();
		
		try {
			server.start();
			//DealCards();
			//Initial conditions of match have been met, proceed to play match.
			//playGame();
			
		} catch(IOException e) {
			System.out.println("Failed to start server...");	
		}
		
	}
}
