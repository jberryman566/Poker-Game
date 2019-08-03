/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carddeck;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jakobvalen 
 *
 */ 
public class CardDeck { 
     
    private String [] carddeck;  
    
    /** CardDeck when called creates an array of length 52
     * It then creates the various suites of cards and finally shuffles the  
     * cards
     */
    public CardDeck() { 
    carddeck = new String [51];//Creates an empty array of length 52 
    //This for loop will create the heart suits  
    // The numbers besdides the letter will determine the card value 
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
    
    public String Draw() throws NoCardsException{  
        if(carddeck[0]==null){ 
            throw new NoCardsException("No more cards in deck"); 
        }else{
        String card; 
        if(carddeck[51]==null){ 
            card = carddeck[51]; 
            carddeck[51]=null;
        }else{ 
            int i=0; 
            while(carddeck[i]!=null){ 
                i++;
            } 
            card=carddeck[i]; 
            carddeck[i]=null;
        }
        return card;
    }  
 
       
}  
    public Void Shuffle() throws NoCardsException{ 
    if(carddeck[0]==null){ 
            throw new NoCardsException("No more cards in deck"); 
        }else{   
       List <String> l = Arrays.asList(carddeck); 
       Collections.shuffle(l); 
    }  
        return null;
    
    }
        
       
    
}
