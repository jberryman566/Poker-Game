import java.net.*;
import java.io.*;

public class Player{
	
	//Socket object to keep track of connection with this client
	private Socket clientSocket;
	
	//PrintWriter object to communicate with this client
	private PrintWriter pout;
	
	//BufferedReader object to recieve messages from this client
	private BufferedReader bin;
	
	//Players custom name for display
	private String Name;
	
	//Int object to keep track of players money
	private int Cash;
	
	//String array to keep track of a players hand (A hand is made of 2 cards)
	private String[] Hand = new String[2];
	
	//A public method for retrieving a socket object for this client
	public Socket getSocket(){
		return this.clientSocket;
	}
	
	//A public method for setting a socket object for this client
	public void setSocket(Socket socket){
		this.clientSocket = socket;
	}
	
	//A public method for retrieving a printwriter object for this client
	public PrintWriter getPrintWriter(){
		return this.pout;
	}
	
	//A public method for setting a printwriter object for this client
	public void setPrintWriter(PrintWriter pw){
		this.pout = pw;
	}
	
	//A public method for retrieving a BufferedReader object for this client
	public BufferedReader getBufferedReader(){
		return this.bin;
	}
	
	//A public method for setting a Buffered Reader object for this client
	public void setBufferedReader(BufferedReader br){
		this.bin = br;
	}
	
	//A public method for retrieving a name for this client
	public String getName(){
		return this.Name;
	}
	
	//A public method for setting a name for this client
	public void setName(String name){
		this.Name = name;
	}
	
	//A public method for retrieving a int object storing cash for this client
	public int getCash(){
		return this.Cash;
	}
	
	//A public method for setting a int object storing cash for this client
	public void setCash(int cash){
		this.Cash = cash;
	}
	
	//A public method for adding winnings to int object storing cash for this client
	public void addWinnings(int winnings){
		this.Cash += winnings;
	}
	
	//A public method for retrieving a hand of cards from this client
	public String[] getHand(){
		return this.Hand;
	}
	
	//A public method for setting a hand of cards for this client
	public void setHand(String[] hand){
		this.Hand = hand;
	}
}