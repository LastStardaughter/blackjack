//"Easy" AI hits until 17 or higher, then stands. It also has a 50% chance to get excited and bet high regardless of circumstance.

package ai;
import card.blackjack.Hand;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

public class EasyAI implements AI {
    int startingBalance, balance, min, max, lead;

    public void init(int balance, int min, int max, int decks){
        this.balance=startingBalance=balance;
        this.min=min;
        this.max=max;
    }

    public void message(String msg){}

    public boolean keepPlaying(int balance){
        return balance <= lead+Math.min(lead, max);
    }

    public void updateLimits(int min, int max){
        this.min=min;
        this.max=max;
    }

    public int wager(int balance, ArrayList<Score> scoreTable, int curRound, int finalRound){
        //int[] scores=null, remainingScores=null, wagers=null;
        int[][] arrays = {null, null, null};
        this.balance=balance;
        Score.generateArrays(scoreTable, arrays);
        int[] scores=arrays[0], remainingScores=arrays[1], wagers=arrays[2];
        Arrays.sort(scores);
        lead = scores[scores.length-1];
        if(new Random().nextBoolean()){
            int bet=(startingBalance/finalRound) * 4, remainder=balance-bet;
            if(remainder<=(bet/7) || remainder<min){
                bet=balance; //Go all in
            }
            if(bet<min){bet=min;}
            if(bet>max){bet=max;}
            balance-=bet;
            return bet;
        }
        int bet=BettingStrategy.bet(startingBalance, min, max, balance, scores, remainingScores, wagers, curRound, finalRound);
        this.balance-=bet;
        return bet;
    }

    public char turn1(Hand hand, String info){
        return turn(hand);
    }
    
    public char turn(Hand hand){
        if(hand.score() < 17){return 'h';}
        return 's';
    }

}
