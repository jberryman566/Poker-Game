
//A class to manage a single game of poker
public class PokerGame{
	
	
	private CardDeck deck;
	private Player[] Clients;
	
	public void PokerGame(Player[] clients){
		this.Clients = clients;
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
	private void DealCards() throws NoCardsException {
		//Deal the players their hands
		//Create Deck
		deck = new CardDeck();
		//Deal to players
		for (Player player : Clients) {
			
			try {
				player.Hand[0] = deck.draw();
				player.Hand[1] = deck.draw();
				System.out.println(player.Name + " has a hand of " + player.Hand[0] + ", " + player.Hand[1]);
			} catch (NoCardsException e){
				System.out.println("Failed to draw a card...");
			}
		
		}
		
		return;
		
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
	
	//Starts the game of poker.
	private void playGame() throws IOException {
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
	private void endGame() throws IOException {
		
		String response;
		//Tell Clients Game Over
		System.out.println("Ending Game...");
		for(Player client : Clients){
			
			try {
				response = sendPlayerMessage("GAME_OVER!", client);
				client.clientSocket.close();
			} catch (IOException e){
				System.out.println("Failed to close connection with client...");
			}
		}
		stop();
	}
}