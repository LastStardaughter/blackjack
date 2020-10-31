//"Easy" AI hits until 17 or higher, then stands. It also has a 50% chance to get excited and bet high regardless of circumstance.

package ai;
import card.blackjack.Hand;
import java.util.Random;

public class EasyAI implements AI {
    int startingBalance, balance, min, max;

    public void init(int balance, int min, int max, int decks){
        this.balance=startingBalance=balance;
        this.min=min;
        this.max=max;
    }

    public void message(String msg){}

    public boolean keepPlaying(int balance){
        return true;
    }

    public void updateLimits(int min, int max){
        this.min=min;
        this.max=max;
    }

    public int wager(int balance, int[] scores, int[] remainingScores, int[] wagers, int curRound, int finalRound){
        this.balance=balance;
        if(new Random().nextBoolean()){
            int bet=(startingBalance/finalRound) * 4, remainder=balance-bet;
            if(remainder<=(bet/7) || remainder<min){
                bet=balance; //Go all in
            }
            if(bet<min){bet=min;}
            if(bet>max){bet=max;}
            return bet;
        }
        return BettingStrategy.bet(startingBalance, min, max, balance, scores, remainingScores, wagers, curRound, finalRound);
    }

    public char turn1(Hand hand, String info){
        return turn(hand);
    }
    
    public char turn(Hand hand){
        if(hand.score() < 17){return 'h';}
        return 's';
    }

}
