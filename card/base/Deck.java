/*
Cards are internally represented as strings
The first 1 or 2 characters is the value (A, 2-10, J, Q, K)
The last character is the suit (C, D, H, S)
Why? Mostly because I already wrote an implementation like this, but it does have some benefits:
It's easily extensible to games not using the standard 52-card deck, or to games that combine multiple decks.
I originally wrote it to include jokers with two copies of string "JOKER".
As far as Deck is concerned, those strings could be anything.
Shuffled multiple decks together? Just add more strings! Want to play Mille Bornes? Use a different set of strings!
*/

package card.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck{
    private ArrayList<String> deck;
    private int pos;

    public Deck(){
        deck = new ArrayList<String>(Arrays.asList(new String[]{"4C","7D","AD","5C","9H","QC","JC","JH","7S","7C","10S","QD","JD","QS","2C","5D","8D","3D","6H","5H","9C","8S","4H","8C","9S","7H","4S","2D","9D","3S","6S","KS","3C","10H","JS","8H","2H","KD","KC","10C","6D","6C","10D","KH","AS","AC","4D","5S","2S","3H","QH","AH"}));
        shuffle();
    }

    public void shuffle(){
        Collections.shuffle(deck);
        pos = 0;
    }

    //Note: If we're out of cards, deck will throw an IndexOutOfBoundsException.
    public String draw(){
        return deck.get(pos++);
    }

    public int remaining(){
        return deck.size()-pos;
    }

    public boolean isEmpty(){
        return (pos+1)>=deck.size();
    }

    public boolean hasAtLeast(int cards){
        return (pos+cards)<deck.size();
    }
    
}