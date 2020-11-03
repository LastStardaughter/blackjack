**Blackjack OOP Example** by Scottbert  
This is an object-oriented implementation of a simple blackjack game.  
  
**Features:**  
Extensible interface that potentially allows for more AI player types or a GUI in the future  
AI players (Easy and Hard)  
AI implements betting strategy, Hard AI chooses actions based on dealer card and current hand  
Variable number of decks  
Tournament-style gameplay: Everyone starts with the same number of chips and there are eliminations on certain rounds  
Prompts user for number of players and rounds, decks, starting chips, min and max bet, has hopefully sensible defaults  

**Known bugs:**  
The display of opponent balances and wagers shown to human players doesn't seem to be working right.  
  
**Game rules:**  
Dealer stays on soft 17  
Double, Split, and Surrender are only allowed with your initial two cards  
Player with lowest chip total is eliminated after rounds 8, 16, 25, and 30 (if played)  
Blackjack pays 3:2  
In the event of a tie for last place after an elimination round, no one is eliminated.  

