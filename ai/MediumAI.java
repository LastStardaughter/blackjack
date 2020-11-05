//"Medium" AI uses some strategy.
//Split if you have 8s or better or the dealer doesn't.
//Stand on hard 17 or better or soft 19 or better.

package ai;
import card.blackjack.Hand;
import card.base.Card;
import java.util.Arrays;
import java.util.ArrayList;

public class MediumAI implements AI {
    int startingBalance, balance, min, max, decks, dealer, lead;
    boolean ace, pair;

    public void init(int balance, int min, int max, int decks){
        this.balance=startingBalance=balance;
        this.min=min;
        this.max=max;
        this.decks=decks;
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
        int[][] arrays = {null, null, null};
        this.balance=balance;
        Score.generateArrays(scoreTable, arrays);
        int[] scores=arrays[0], remainingScores=arrays[1], wagers=arrays[2];
        Arrays.sort(scores);
        if(scores.length>0){lead = scores[scores.length-1];}
        int bet=BettingStrategy.bet(startingBalance, min, max, balance, scores, remainingScores, wagers, curRound, finalRound);
        this.balance-=bet;
        return bet;
    }

    public char turn1(Hand hand, String info){
        dealer=valFromChar(info.charAt(7)); //6 if value first, 7 if suit first
        boolean canDouble=balance>=hand.getWager();
        boolean[] values={false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        ace = pair = false;
        for(String card : hand){
            int val=Card.value(card);
            if(values[val]){
                pair = true;
            }
            values[val]=true;
        }
        ace = values[1];
        int score = hand.score();

        if((pair && canDouble) && (dealer<8 || score>15)){return 'p';}

        return turn(hand);
    }
    
    public char turn(Hand hand){
        if(Card.value(hand.get(hand.size()-1))==1){ace=true;}
        int score = hand.score();

        if (ace && score<19){return 'h';}
        if (score<17){return 'h';}
        return 's';
    }

    private static int valFromChar(char c){
        switch(c){
            case 'A':return 11;
            case '9':return 9;
            case '8':return 8;
            case '7':return 7;
            case '6':return 6;
            case '5':return 5;
            case '4':return 4;
            case '3':return 3;
            case '2':return 2;
            default:return 10;
        }
    }

}
