
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.*;
import java.util.Stack;
/**
 *
 * @author jakobvalen, modified by joshberryman Dec 3rd 2019
 *
 */ 
public class CardDeck { 
     
    private Stack<String> carddeck;
	private Stack<String> deckTemplate;	
    private int numberOfCardsLeft; 
    
    /** CardDeck when called creates a stack of size 52
     * It then creates the various suites of cards and finally shuffles the  
     * cards
     */
    public CardDeck() { 
		
		carddeck = new Stack<String>();//Creates an empty array of length 52 
    
		//The numbers beside the letter will determine the card value  (Ace = 1, King = 13)
		numberOfCardsLeft = 52;
    
		int n=1;
		for (int i=0;i<=13;i++){  
        
			carddeck.push("H"+n); 
			n++;
		} 
	
		n=1;
		for (int i=13;i<=26;i++){
         
			carddeck.push("D"+n); 
			n++;
		}

		n=1;	
		for (int i=26;i<=39;i++){
        
			carddeck.push("C"+n); 
			n++;
		}

		n=1;	
		for (int i=39;i<=52;i++){ //For loop for Spades suits
         
			carddeck.push("S"+n);
			n++;             
		} 
		
		//Template for base deck
		deckTemplate = carddeck;
		//Finally, shuffle the array just like a deck of cards 
		Collections.shuffle(carddeck);  
	}
    
	/* Draw is used to draw the top card from the stack
	*  Throws emty card exception if stack is empty.
	*/
    public String draw() throws NoCardsException{   
        String card = "";
        if(numberOfCardsLeft==0){
            throw new NoCardsException("Error: The Card deck is empty."); 
        }else{
			card = carddeck.pop();
            numberOfCardsLeft--;
        }
        return card;
    }  
    
    /* Shuffle is used to restart the deck and shuffle the contents
	*/
    public void shuffle(){
		
		carddeck = deckTemplate; 
		numberOfCardsLeft = 52;
		Collections.shuffle(carddeck); 	  
	}   
}
