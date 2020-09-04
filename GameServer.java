import java.net.*;
import java.io.*;
import java.util.*;

public class GameServer{
	
	//Variables for player objects
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter pout;
	private BufferedReader bin;
	private int serverPORT = 6666;
	private Player player; 
	
	//Can be changed as needed. Number of players needed for match
	private int numPlayers = 5;
	private int playerCounter = 0;
	private boolean debugMode = true;
	private boolean readyToPlay = false;
	private boolean firstRound = true;
	

	//Array of Clients
	protected static Player[] Clients;
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
		
		new ConnectionThread();
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
				} else if (command.length == 2){
					//GET command has been issued
					System.out.println("Command: "+ command[0] + ", Operand: " + command[1]);
				
				} else {
					
					//Player started match
					if (command.equals("PLAY")){
						System.out.println("Starting match...");
					}
				}
			}
		}		
	}
	
	//Method will be used to check status of all player connections
	//Check for timeouts.
	protected void checkConnections(){
		
		for (Player player : Clients){
				
			if (player != null) {
				
				
				//Need to ping a message to each client and wait for a response
				if(messagePlayerWithResponse("RESPOND", player)){
						
						//Player is reachable, connection is good.
					System.out.println("Player is here...");
				} else {
						
						//Player is not reachable, connection is bad, drop the connection and reorder clients
					System.out.println("Player quit...");
					
				}
				
			}
		}	
	}
	
	private void checkForUpdates() throws IOException{
		
		System.out.println("Checking for updates");
		
		try {
			
			for (Player player : Clients){
				
				System.out.println("Checking a client...");
				
				if (player != null){
					
					System.out.println("Asking for response...");
					String response = player.getBufferedReader().readLine();
					System.out.println("Response given...");
					String[] task = response.split(" ");
					Tasks.add(new Tuple(task, player));
				}
			}
		} catch (IOException e) {
			System.out.println("Error: Failed to retrieve tasks...");
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
			System.out.println("Failed to retrieve new player...");
		}
		return player;
	}
	
	//Method for sending generic string methods to players and returns a response.
	private void sendPlayerMessage(String message, Player player) throws IOException{
		
		player.getPrintWriter().println(message);
	}
	
	protected boolean messagePlayerWithResponse(String message, Player player){
		
		try {
			player.getPrintWriter().println(message);
			String resp = player.getBufferedReader().readLine();
			
			System.out.println(resp);
			
			if(resp.equals("HERE")){
				return true;
			}
			return false;
		} catch (IOException e){
			return false;
		}
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

class ConnectionThread implements Runnable {
	
	Thread t;
	ConnectionThread() {
		t = new Thread(this, "Thread");
		System.out.println("Child thread: " + t);
		t.start();
	}
	
	public void run(){
		
		//try {
			Timer connectionTimer = new Timer();
			connectionTimer.schedule(new TimerTask() {
				
				public void run(){
					
					try{
						check();
					} catch (Exception e){
					
					}
				}
			}, 0, 60*1000);
		//} catch (InterruptedException e) {
		//	System.out.println("The child thread is interrupted.");
		//}
    }
	
	public void check() throws IOException{
		Player[] clients = GameServer.Clients;
		for (Player player : clients){
			
			if (player != null) {
				
				try {
					player.getPrintWriter().println("RESPOND");
					String resp = player.getBufferedReader().readLine();
	
					System.out.println(resp);
					//Need to ping a message to each client and wait for a response
					if(resp.equals("HERE")){
				
						//Player is reachable, connection is good.
						System.out.println("Player is here...");
					} else {
				
						//Player is not reachable, connection is bad, drop the connection and reorder clients
						System.out.println("Player quit...");
					}
				} catch (IOException e){
					
				}
			}
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

