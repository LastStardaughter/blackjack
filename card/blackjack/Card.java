package card.blackjack;

public class Card extends card.base.Card{
    private boolean facedown;

    public Card(String card){
        super(card);
        this.facedown = false;
    }

    public Card(String card, boolean facedown){
        super(card);
        this.facedown = facedown;
    }

    public boolean facedown(){
        return facedown;
    }

//Why doesn't this work? Gives a nonsensical error about super() being undefined, even if card.base.Card.card is set protected.
/*
    public Card(Card card, boolean facedown){
        this.card = card.card;
        this.facedown = facedown;
    }
*/

}
