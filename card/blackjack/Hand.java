/*
Blackjack uses facedown cards. I see four ways to do it:
1. Have each hand track which cards are facedown seperately from the list of cards
2. Make cards instantiated objects with a facedown field
3. Add a single field that holds the facedown card (Blackjack hands only have one) or null if there's none
4. Just track whether there is a facedown card and if so assume it's the first one (it always will be in Blackjack)

1, 2, and 4 make operations over the entire hand (like scoring) simpler but complicate checking visible cards
3 makes checking visible cards easy but complicates scoring
Of 1, 2, and 4, 4 seems like the lightest implementation and is probably simpler than 3.
*/
package card.blackjack;
import card.base.Card;

public class Hand extends card.base.Hand {
    private static final long serialVersionUID = 0L;
    private boolean facedown = true; //The first card is always facedown in a new hand, except splits.
    public int wager; // At least in this iteration of the code there's no special logic to this -- may as well allow it to be accessed directly.

    public void reveal(){
        facedown = false;
    }

    public boolean facedown(){
        return facedown;
    }

    public int score(){
        int score=0, aces=0;
        for (String card : this){
            int val = Card.value(card);
            if(val==1){
                aces++;
                score+=11;
            } else {
                score+=Math.min(val, 10);
            }
        }
        while(score>21 && aces>0){
            aces--;
            score-=10;
        }
        return score;
    }

    public Hand visibleHand(){
        Hand temp=(Hand) this.clone();
        if(!facedown){return temp;}
        temp.reveal();
        temp.remove(0);
        return temp;
    }

    //For UI helper or AI-reasoning purposes
    public int visibleScore(){
        if(!facedown){return score();}
        Hand temp = (Hand) this.clone();
        temp.remove(0);
        return temp.score();
    }

    // public int getWager(){
    //     return wager;
    // }

}
