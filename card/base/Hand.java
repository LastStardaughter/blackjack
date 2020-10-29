//The base Hand is just an ArrayList<String> with an extra constructor for starting a hand with a single card.
//Mostly it just saves you from repeatedly typing ArrayList<String> in card game code.
//But it might make code written for one card game a little more reusable with subclasses also named Hand.
package card.base;

import java.util.ArrayList;
import java.util.Collection;

public class Hand extends ArrayList<String>{
    private static final long serialVersionUID = 0L;

    public Hand(){
        super();
    }

    public Hand(String card){
        super();
        this.add(card);
    }

    public Hand(Collection<String> cards){
        super(cards);
    }
}
