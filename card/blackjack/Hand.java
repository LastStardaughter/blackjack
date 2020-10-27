package card.blackjack;

import java.util.ArrayList;
import java.util.Collection;

public class Hand extends ArrayList<Card> {
    private static final long serialVersionUID = 0L;
    
    public Hand(){
        super();
    }

    public Hand(Card card){
        super();
        this.add(card);
    }

    public Hand(Collection<? extends Card> cards){
        super(cards);
    }
}
