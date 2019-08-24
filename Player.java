import java.net.*;
import java.io.*;

public class Player{
	
	public Socket clientSocket;
	public PrintWriter pout;
	public BufferedReader bin;
	
	//Players name they supply
	public String Name;
	
	//How much money player has
	public int Cash;
	
	//Players Handler
	public String[] Hand = new String[2];

}