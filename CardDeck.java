
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.*;
/**
 *
 * @author jakobvalen 
 *
 */ 
public class CardDeck { 
     
    private String [] carddeck;  
    private int top; 
    
    /** CardDeck when called creates an array of length 52
     * It then creates the various suites of cards and finally shuffles the  
     * cards
     */
    public CardDeck() { 
    carddeck = new String [51];//Creates an empty array of length 52 
    //This for loop will create the heart suits  
    // The numbers besdides the letter will determine the card value  
         top = 52; // set top pointer to the top of the card-deck
    for (int i=0;i<12;i++){  
        int n=1;
        carddeck[i] = "H"+n; 
        n++;
    } 
    for (int i=13;i<25;i++){ //For loop for Diamonds suits
        int n=1; 
        carddeck[i]= "D"+n; 
        n++;
    } 
    for (int i=26;i<38;i++){ //For loop for Clubs suits
        int n=1; 
        carddeck[i]="C"+n; 
        n++;
    } 
    for (int i=39;i<51;i++){ //For loop for Spades suits
        int n=1; 
        carddeck[i]="S"+n; 
        n++;             
    } 
    //Finally, shuffle the array just like a deck of cards
    List <String> l = Arrays.asList(carddeck); 
       Collections.shuffle(l);  
}
    // Method draw checks to see if we have any cards left, then pops 
    // the top card off the deck using variable "top"
    public String draw(){   
        String card = "";
        if(top==0){ // If top = 0 , then there are no more cards in deck
            //throw new EmptyStackException(); 
        }else{
        card = carddeck[top]; // pop the top card out of the deck
             top--; // set top to point at the next card
        }
        return card; // return the popped card 
    }  
    
    // Method shuffle checks to see if we have any cards left, 
    // then shuffle the deck using method shuffle from the collections library 
    public void shuffle(){
		if(top==0){ // If top = 0 , then there are no more cards in deck     
			//throw new EmptyStackException();
        }else{   
			List <String> l = Arrays.asList(carddeck); 
			Collections.shuffle(l); 
		}  
	}   
}
