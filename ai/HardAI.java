//"Hard" AI uses actual strategy. Source: http://wizardofodds.com/games/blackjack/strategy/4-decks/

package ai;
import card.blackjack.Hand;
import card.base.Card;

public class HardAI implements AI {
    int startingBalance, balance, min, max, decks, dealer;
    boolean ace, pair;

    public void init(int balance, int min, int max, int decks){
        this.balance=startingBalance=balance;
        this.min=min;
        this.max=max;
        this.decks=decks;
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
        return BettingStrategy.bet(startingBalance, min, max, balance, scores, remainingScores, wagers, curRound, finalRound);
    }

    public char turn1(Hand hand, String info){
        dealer = 0; //Placeholder, fill in with real code after you've set up the info string
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
        if(dealer==11){
            switch(score){
                case 16:if(ace){break;}
                case 15:
                case 17:
                return 'u';
                default:
            }
        }
        if(score==16 && !ace){
            if(pair){return 'p';}; //but split 8s against anything but an ace
            switch(dealer){
                case 9:
                case 10:
                return 'u';
                default:
            }
        }
        if(score==15 && dealer==10){return 'u';}

        //Split
        if(pair){
            if(ace){return 'p';}
            switch(score){
                case 4:
                case 6:if(dealer >=4 && dealer <= 7){return 'p';}
                    break;
                case 12:if(dealer >=3 && dealer <= 6){return 'p';}
                    break;
                case 14:if(dealer <= 7){return 'p';}
                    break;
                case 18:if(dealer < 10 && dealer != 7){return 'p';}
                default:
            }
        }

        //Double
        if(!ace){
            if(score==9 && dealer <=6 && dealer != 2){return 'd';}
            if(score==10 && dealer < 10){return 'd';}
        }
        if(score==11){return 'd';}
        if(ace){
            switch(score){
                case 13:
                case 14:if(dealer == 5 || dealer == 6){return 'd';}
                    break;
                case 15:
                case 16:if(dealer <= 6 && dealer >= 4){return 'd';}
                    break;
                case 18:if(dealer == 2){return 'd';}
                case 17:if(dealer<=6 && dealer != 2){return 'd';}
                    break;
                case 19:if(dealer==6){return 'd';}
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

}
