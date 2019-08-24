
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.*;
import java.util.Stack;
/**
 *
 * @author jakobvalen 
 *
 */ 
public class CardDeck { 
     
    private Stack<String> carddeck;
	private Stack<String> tempdeck;	
    private int numberOfCardsLeft; 
    
    /** CardDeck when called creates an array of length 52
     * It then creates the various suites of cards and finally shuffles the  
     * cards
     */
    public CardDeck() { 
    carddeck = new Stack<String>();//Creates an empty array of length 52 
    //This for loop will create the heart suits  
    // The numbers besdides the letter will determine the card value  
         numberOfCardsLeft = 51; // set numberOfCardsLeft pointer to the numberOfCardsLeft of the card-deck
    
	int n=0;
	for (int i=0;i<=13;i++){  
        
        carddeck.push("H"+n); 
        
		System.out.println("H"+n);
		
		n++;
    } 
	
	n=0;
    for (int i=13;i<=26;i++){ //For loop for Diamonds suits
         
        carddeck.push("D"+n); 
        
		System.out.println("D"+n);
		
		n++;
    }

	n=0;	
    for (int i=26;i<=39;i++){ //For loop for Clubs suits
        
        carddeck.push("C"+n); 
        
		System.out.println("C"+n);
		
		n++;
    }

	n=0;	
    for (int i=39;i<=52;i++){ //For loop for Spades suits
         
        carddeck.push("S"+n); 
		
		System.out.println("S"+n);
		
        n++;             
    } 
	tempdeck = carddeck;
    //Finally, shuffle the array just like a deck of cards
    //List <String> l = Arrays.asList(carddeck); 
    Collections.shuffle(carddeck);  
}
    // Method draw checks to see if we have any cards left, then pops 
    // the numberOfCardsLeft card off the deck using variable "numberOfCardsLeft"
    public String draw(){   
        String card = "";
        if(numberOfCardsLeft==0){ // If numberOfCardsLeft = 0 , then there are no more cards in deck
            //throw new EmptyStackException(); 
        }else{
        card = carddeck.pop(); // pop the numberOfCardsLeft card out of the deck
             numberOfCardsLeft--; // set numberOfCardsLeft to point at the next card
        }
        return card; // return the popped card 
    }  
    
    // Method shuffle checks to see if we have any cards left, 
    // then shuffle the deck using method shuffle from the collections library 
    public void shuffle(){
		if(numberOfCardsLeft==0){ // If numberOfCardsLeft = 0 , then there are no more cards in deck     
			//throw new EmptyStackException();
        }else{
			carddeck = tempdeck; 
			Collections.shuffle(carddeck); 
		}  
	}   
}
