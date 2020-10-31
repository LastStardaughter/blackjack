package ai;
/*

If others are betting small, bet big; if others are betting big, go small
If we're nearing the last round, bet what we need to to beat the leader in time.
Similar logic with elimination rounds if we have the lowest balance.
When making such a catchup bet, go all in if remaining money isn't more than MAX(PLANNED_BET/7, SMALL_BET)
Sanity check: Treat MAX_BET as smaller of MAX_BET and LARGE_BET?

The computer makes one of four bets:
TAKE_LEAD: Bet what you 'need to' to take the highest place you can before the game ends.
CATCH_LAST: Bet what you 'need to' to NOT be in last place before an elimination round.
(For my own sanity I'm going to have these only think about the current round)
(Both of the above calculations can get funky if the difference is too big or the max bet is too small)
SMALL: Bet low when others are betting high
LARGE: Bet high when others are betting low
Any of these may throw in extra chips if they're not worth keeping, or be capped by the max bet.

The following numbers are needed for the AI to plan a betting strategy:
init:               Initial chips
min:                Minimum bet
max:                Maximum bet
balance:            Current balance of chips to work with
allOpponents:       Array of all opponents' balances, including those who've quit the game
remainingOpponents: Array of remaining opponents' balances
wagers:             Array of wagers made so far this round
current:            Current round # 
rounds:             Final round # 
*/

import java.util.Arrays;

public class BettingStrategy {

    public static int bet(int init, int min, int max, int balance, int[] allOpponents, int[] remainingOpponents, int[] wagers, int current, int rounds){
        int medium=init/rounds, small=medium/4, large=medium*4;
        String strategy; //Can be output for debugging
        Arrays.sort(allOpponents);
        Arrays.sort(remainingOpponents);
        Arrays.sort(wagers);
        int bet, lead=allOpponents[allOpponents.length-1], last=remainingOpponents[0], catchLead=(lead*2)-balance, catchLast=(Math.max(last*2,min*3)-balance);
        int median = wagers.length == 0 ? medium : median(wagers);
        if(current==rounds && balance<lead){
            strategy="TAKE_LEAD";
            bet=catchLead;
        } else if((current==8 || current==16 || current==25 || current==30) && balance<catchLast){
            strategy="CATCH_LAST";
            bet=catchLast+1;
        } else if(median>=medium){
            strategy="SMALL";
            bet=small;
        } else {
            strategy="LARGE";
            bet=large;
        }
        int remainder=balance-bet;
        if(remainder<=Math.max(bet/7, small-1) || remainder<min){
            bet=balance; //Go all in
        }
        if(bet<min){bet=min;}
        if(bet>max){bet=max;}
        return bet;
    }

    private static int median(int[] arr){
        if(arr.length%2==1){
            return arr[arr.length/2];
        }
        return (arr[arr.length/2] + arr[(arr.length/2)-1])/2;
    }
    
}
