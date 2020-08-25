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
	private Queue<Tuple> Tasks;
	
	private void start() throws IOException, NoCardsException {
		
		System.out.println("Starting up Game Server...");
		//Create array of players for the match
		Clients = new Player[numPlayers];
		
		//Create a queue object for the server to deal with requests
		Tasks = new LinkedList<>();
		
		serverSocket = new ServerSocket(serverPORT);
		serverSocket.setSoTimeout(1000);
		run();
		
	}
	
	private void run() throws IOException{
		
		System.out.println("Initializing game lobby, waiting for players...");
		
		boolean inMenu = true;
		
		while(inMenu){
			
			//Check for any client connections
			Player player = fetchPlayer();
			if (player != null){
				Clients[playerCounter] = player;
				playerCounter++;
			}
			
			//Check for tasks
			if (playerCounter >= 1){
				checkForUpdates();
			}
			
			//Do a task
			if (Tasks.size() != 0){
				Tuple task = Tasks.remove();
				String[] command = task.get_command();
				if(command.length > 2){
					//SET command has been issued
					System.out.println("Command: " + command[0] + ", Operand: " + command[1] + ", Value: " + command[2]);
					if (command[0].equals("SET")){
						
						//SET NAME
						if (command[1].equals("NAME")){
							player = task.get_player();
							player.setName(command[2]);
							System.out.println("Player name set to: " + player.getName());
						}
					}
				} else {
					//GET command has been issued
					System.out.println("Command: "+ command[0] + ", Operand: " + command[1]);
				}
			}
			
		}		
	}
	
	private void checkForUpdates() throws IOException{
		
		try {
			for (Player player : Clients){
				
				if (player != null){
					String response = player.getBufferedReader().readLine();
					String[] task = response.split(" ");
					Tasks.add(new Tuple(task, player));
				}
			}
		} catch (IOException e) {
			System.out.println("Error: Failed to retrieve tasks");
		}
	}
	
	
	
	//Method for waiting and accepting a new player connection. Returns Player.
	private Player fetchPlayer() throws IOException {
		
		player = null;
		
		try {
			//System.out.println("Waiting for player to connect...");

			clientSocket = serverSocket.accept();
			System.out.println("Creating new player...");
			player = createPlayer(clientSocket);
			System.out.println("New player added...");
				
		} catch(IOException e) {
			//System.out.println("Failed to retrieve new player...");
		}
		return player;
	}
	
	//Method for sending generic string methods to players and returns a response.
	private void sendPlayerMessage(String message, Player player){
		
		player.getPrintWriter().println(message);
		//response = player.getBufferedReader().readLine();
	}
	
	//Method for creating new player objects for client connections. Returns Player object.
	private Player createPlayer(Socket client) throws IOException {
		
		//Create Player object
		player = new Player();
		player.setSocket(client);
		
		try {
			player.setPrintWriter(new PrintWriter(client.getOutputStream(), true));
			player.setBufferedReader(new BufferedReader(new InputStreamReader(client.getInputStream())));
			
			//Message player to confirm added
			sendPlayerMessage("Successfully added to game.", player);
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

class Tuple{
	
	private String[] command;
	private Player player;
	
	public Tuple(String[] item_a, Player item_b){
	
		command = item_a;
		player = item_b;
	}
	
	public String[] get_command(){
		return command;
	}
	
	public Player get_player(){
		return player;
	}
		

}

