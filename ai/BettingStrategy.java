package ai;
/*

If others are betting small, bet big; if others are betting big, go small
If we're nearing the last round, bet what we need to to beat the leader in time.
Similar logic with elimination rounds if we have the lowest balance.
(In this implementation, the above two only consider the current round and don't yet try to play ahead)
Todo: Add logic that checks if you're in first place at the end and reasons about that.

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
        if(allOpponents.length==0){return medium;} //If there are no opponents there is no strategy
        String strategy; //Can be output for debugging
        Arrays.sort(allOpponents);
        Arrays.sort(remainingOpponents);
        Arrays.sort(wagers);
        int last = remainingOpponents.length == 0 ? 0 : remainingOpponents[0];
        int lastTarget = Math.max(last+Math.min(last,max),min*3);
        int bet, lead=allOpponents[allOpponents.length-1], catchLead=(lead+Math.min(lead,max))-balance, catchLast=lastTarget-balance;
        int median = wagers.length == 0 ? medium : median(wagers);
        if(current==rounds && balance<lead){
            strategy="TAKE_LEAD";
            bet=catchLead;
        } else if((current==8 || current==16 || current==25 || current==30) && balance<lastTarget){
            strategy="CATCH_LAST";
            bet=catchLast+1;
        } else if(median<medium){
            strategy="LARGE";
            bet=large;
        } else {
            strategy="SMALL";
            bet=small;
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
