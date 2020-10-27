package card.blackjack;

public class Deck extends card.base.Deck {

    public Card draw(){
        return new Card(super.drawStr(), false);
    }

    public Card draw(boolean facedown){
        return new Card(super.drawStr(), facedown);
    }
    
}
