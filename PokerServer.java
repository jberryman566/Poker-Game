import java.net.*;
import java.io.*;
import java.util.*;

public class PokerServer{
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter pout;
	private BufferedReader bin;
	private int serverPORT = 6666;
	private Player player;
	private int numPlayers = 3;
	private int playerCounter = 0;
	private boolean debugMode = true;
	private boolean readyToPlay = false;
	
	private Player littleBlind;
	private Player bigBlind;
	
	//Array of Clients
	private Player[] Clients;
	
	public void start() throws IOException {
		Clients = new Player[numPlayers];
		
		serverSocket = new ServerSocket(serverPORT);
		while (!readyToPlay) {
			try{
				//Store Player in array
				Clients[playerCounter] = fetchPlayer();
				playerCounter++;
				
				if (playerCounter == numPlayers) { readyToPlay = true;}
				
				// //Get Player Name
				// System.out.println("Retrieving player data...");
				// player.Name = sendPlayerMessage("What is your Name?", player);
				
				// if(debugMode) {System.out.println(player.Name);}
				
			} catch(IOException e) {
				System.out.println("Failed to connect to client...");
			}
		}
		//Deal the deck
		DealCards();
		
		//Start Game
		while (readyToPlay) {
			//Begin Game Conversation
			
			//-----GET DECK OF CARDS-----//
			//remove for lil blind, peek for big blind
			Queue<Player> BlindQueue = new LinkedList<Player>();

			for(Player client : Clients) {
				BlindQueue.add(client);
			}
			
			littleBlind = BlindQueue.remove();
			bigBlind = BlindQueue.peek();
			
			PlayRound(littleBlind, bigBlind);
			BlindQueue.add(littleBlind);
		}
	}
	
	private void PlayRound(Player littleB, Player bigB) {
		//Play a round of poker
		
	}
	
	private void DealCards() {
		//Deal the players their hands and 3 cards for house
	}
	
	private Player fetchPlayer() throws IOException {
		
		try {
			System.out.println("Waiting for player to connect...");
			// serverSocket = new ServerSocket(serverPORT);
			clientSocket = serverSocket.accept();
			System.out.println("Creating new player...");
			player = createPlayer(clientSocket);
			
			//Get Player Name
			System.out.println("Retrieving player data...");
			player.Name = sendPlayerMessage("What is your Name?", player);
			
			if(debugMode) {System.out.println("Added new Player: " + player.Name);}
			
		} catch(IOException e) {
			System.out.println("Failed to retrieve new player...");
		}
		return player;
	}
	
	private String sendPlayerMessage(String message, Player player) throws IOException{
		String response;
		try{
			player.pout.println(message);
			response = player.bin.readLine();
			
		} catch(IOException e) {
			response = "Failed to connect to client...";
		}
		
		return response;
	}
	
	private Player createPlayer(Socket client) throws IOException {
		
		//Create Player object
		player = new Player();
		player.clientSocket = client;
		
		try {
			player.pout = new PrintWriter(client.getOutputStream(), true);
			player.bin = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch(IOException e) {
			System.out.println("Failed to connect to client...");
		}
		return player;
	}
	
	public void stop() throws IOException {
        
		try {
			bin.close();
			pout.close();
			clientSocket.close();
			serverSocket.close();
		} catch(IOException e) {
			System.out.println("Failed to close Server correctly...");
		}
    }
	
	public void playGame() {
		//TODO
	}
	
	public static void main(String[] args) throws IOException{
		PokerServer server = new PokerServer();
		
		try {
			server.start();
		} catch(IOException e) {
			System.out.println("Failed to start server...");	
		}
		
	}
}