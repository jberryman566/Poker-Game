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
	
	//Can be changed as needed. Number of players needed for match
	private int numPlayers = 3;
	private int playerCounter = 0;
	private boolean debugMode = true;
	private boolean readyToPlay = false;
	private boolean firstRound = true;
	
	//Blind variables for betting
	private Player littleBlind;
	private Player bigBlind;
	
	//Array of Clients
	private Player[] Clients;
	
	public void start() throws IOException {
		
		//Create array of players for the match
		Clients = new Player[numPlayers];
		
		serverSocket = new ServerSocket(serverPORT);
		
		while (!readyToPlay) {
			try{
				//Store Player in array
				Clients[playerCounter] = fetchPlayer();
				playerCounter++;
				
				if (playerCounter == numPlayers) { readyToPlay = true;}
				
			} catch(IOException e) {
				System.out.println("Failed to connect to client...");
			}
		}
		//Deal the deck
		DealCards();
		//Initial conditions of match have been met, proceed to play match.
		playGame();
	}
	
	//Plays a round of bets
	private void PlayRound(Player littleB, Player bigB) {
		//Retrieve little blind
		
		//Retrieve Big blind
		
		//While there is next player, Ask next player RAISE, CALL, FOLD? 
	}
	
	//Preflop round. Betting is done before the flop
	private void playPreFlop(Player littleB, Player bigB) {
		
	}
	//Flop is special first round. 3 community cards are flipped.
	private void playFlop(Player littleB, Player bigB) {

	}
	
	//Method for dealing players hands. 2 cards to each player.
	//Will store the players hands locally in server in the player objects.
	private void DealCards() {
		//Deal the players their hands and 3 cards for house
	}
	
	//Player raises a bet
	private void playerRaise(Player player) {
		
	}
	
	//Player folds their hand and leaves the round.
	private void playerFold(Player player) {
		
	}
	
	//Player matches the bet
	private void playerCall(Player player) {
		
	}
	
	//Method for waiting and accepting a new player connection. Returns Player.
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
	
	//Method for sending generic string methods to players and returns a response.
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
	
	//Method for creating new player objects for client connections. Returns Player object.
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
	
	//Method for safely stopping the server
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
	
	//Starts the game of poker.
	private void playGame() {
		//TODO
		
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
			
			if (firstRound) {
				playFlop(littleBlind, bigBlind);
			} else {
				PlayRound(littleBlind, bigBlind);
			}
			//Puts little blind in back of queue
			BlindQueue.add(littleBlind);
		}
	}
	
	//Method for after a game has ended, declares winner amd ends connections safely.
	private void endGame() {
		
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