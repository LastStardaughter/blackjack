//The base Hand is just an ArrayList<String> with an extra constructor for starting a hand with a single card.
//Mostly it just saves you from repeatedly typing ArrayList<String> in card game code.
//But it might make code written for one card game a little more reusable with subclasses also named Hand.
//Originally I also included additional constructors that took one or more cards, but this messes up subclasses.
//Display isn't a for in because I wrote card.blackjack.Hand.display first and just copied the applicable part here.
package card.base;

import java.util.ArrayList;

public class Hand extends ArrayList<String>{
    private static final long serialVersionUID = 0L;

    public Hand(){
        super();
    }

    public String display(){
        StringBuilder disp=new StringBuilder();
        int pos=0;
        while(pos<this.size()){
            disp.append(Card.display(get(pos++)));
            disp.append(" ");
        }
        return disp.toString();
    }
}
