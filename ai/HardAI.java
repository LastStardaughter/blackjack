//"Hard" AI uses actual strategy. Source: http://wizardofodds.com/games/blackjack/strategy/4-decks/

package ai;
import card.blackjack.Hand;
import card.base.Card;
import java.util.Arrays;
import java.util.ArrayList;

public class HardAI implements AI {
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
        //int[] scores=null, remainingScores=null, wagers=null;
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

        //Surrender
        if(score==16 && !pair && !ace){
            switch(dealer){
                case 9:
                case 10:
                case 11:
                return 'u';
                default:
            }
        }
        if(score==15 && dealer==10){return 'u';}

        //Split
        if(pair){
            if(ace || score==16){return canDouble? 'p' : 'h';}
            switch(score){
                case 4:
                case 6:if(dealer >=4 && dealer <= 7){return canDouble? 'p' : 'h';}
                    break;
                case 12:if(dealer >=3 && dealer <= 6){return canDouble? 'p' : 'h';}
                    break;
                case 14:if(dealer <= 7){return canDouble? 'p' : 'h';}
                    break;
                case 18:if(dealer < 10 && dealer != 7){return canDouble? 'p' : 'h';}
                default:
            }
        }

        //Double
        if(!ace){
            if(score==9 && dealer <=6 && dealer != 2){return canDouble? 'd' : 'h';}
            if(score==10 && dealer < 10){return canDouble? 'd' : 'h';}
        }
        if(score==11 && !ace && dealer != 11){return canDouble? 'd' : 'h';}
        if(ace){
            switch(score){
                case 13:
                case 14:if(dealer == 5 || dealer == 6){return canDouble? 'd' : 'h';}
                    break;
                case 15:
                case 16:if(dealer <= 6 && dealer >= 4){return canDouble? 'd' : 'h';}
                    break;
                case 17:
                case 18:if(dealer<=6 && dealer != 2){return canDouble? 'd' : 's';}
                    break;
                default:
            }
        }

        //End of special first turn logic, if none of these were met use normal turn logic
        return turn(hand);
    }
    
    public char turn(Hand hand){
        if(Card.value(hand.get(hand.size()-1))==1){ace=true;}
        int score = hand.score();
        
        if(!ace){
            if(score<=11){return 'h';}
            switch(score){
                case 12:if(dealer <= 6 && dealer >= 4){return 's';}
                    return 'h';
                case 13:
                case 14:
                case 15:
                case 16:if(dealer <= 6){return 's';}
                    return 'h';
                case 17:
                case 18:
                case 19:
                case 20:return 's';
                default:
            }
        }
        if(score<=17){return 'h';}
        if(score==18){
            if(dealer >= 9){return 'h';}
            return 's';
        }
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
