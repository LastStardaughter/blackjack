//This is pretty simple, but it's a seperate class so that dealer behavior can be easily customized.
package ai;
import card.blackjack.Hand;

public class Dealer {
    public static char turn(Hand hand){
        if(hand.score() < 17){return 'h';}
        for(String card : hand){
            if(card.charAt(0) == 'A'){return 's';} //Whether to hit or stay on soft 17
        }
        return 's';
    }
}
