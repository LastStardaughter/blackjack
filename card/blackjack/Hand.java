/*
Blackjack uses facedown cards. I see four ways to do it:
1. Have each hand track which cards are facedown seperately from the list of cards
2. Make cards instantiated objects with a facedown field
3. Add a single field that holds the facedown card (Blackjack hands only have one) or null if there's none
4. Just track whether there is a facedown card and if so assume it's the first one (it always will be in Blackjack)

1, 2, and 4 make operations over the entire hand (like scoring) simpler but complicate checking visible cards
3 makes checking visible cards easy but complicates scoring
Of 1, 2, and 4, 4 seems like the lightest implementation and is probably simpler than 3.

Sidebar: Wager: Public or Private?
In OOP, we're taught to avoid public fields, but a getter() and setter() with no logic seems just the same.
I'm not tracking the state of the hand with regards to which turn it is, that seems like it's taken care of in the game code.
Validating wagers as too small, big, or not numbers, also seems like the game's job rather than a property of the hand.
So by all reasonings I previously used, wager should be public. However...
Some future, specific implementation of blackjack might need to add logic, and then every use of Hand.wager in reused code would have to change.
So for that reason, I'll mark it protected with a getter and setter.
*/
package card.blackjack;
import card.base.Card;

public class Hand extends card.base.Hand {
    private static final long serialVersionUID = 0L;
    private boolean facedown = true; //The first card is always facedown in a new hand, except splits.
    protected int wager;

    public Hand(){
        super();
    }

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
        Hand temp=(Hand) clone();
        if(!facedown){return temp;}
        temp.reveal();
        temp.remove(0);
        return temp;
    }

    //For UI helper or AI-reasoning purposes
    public int visibleScore(){
        if(!facedown){return score();}
        Hand temp = (Hand) clone();
        temp.remove(0);
        return temp.score();
    }

    public String display(){
        StringBuilder disp=new StringBuilder();
        int pos=0;
        if(facedown){
            disp.append("## ");
            pos=1;
        }
        while(pos<this.size()){
            disp.append(Card.display(get(pos++)));
            if(pos<this.size()){disp.append(" ");}
        }
        return disp.toString();
    }

    public int getWager(){
        return wager;
    }

    public void setWager(int wager){
        this.wager = wager;
    }

//these methods return the value difference
    public int doubleWager(){
        wager*=2;
        return wager/2;
    }

    public int surrender(){
        int orig = wager;
        wager/=2;
        return orig-wager;
    }
}
