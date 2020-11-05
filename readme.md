**Blackjack OOP Example** by Scottbert  
This is an object-oriented implementation of a simple blackjack game.  
  
**Features:**  
Extensible interface that potentially allows for more AI player types or a GUI in the future  
AI players (Easy, Medium, and Hard)  
AI implements betting strategy, "Hard" AI chooses actions based on dealer card and current hand  
Variable number of decks  
Tournament-style gameplay: Everyone starts with the same number of chips and there are eliminations on certain rounds  
Prompts user for number of players and rounds, decks, starting chips, min and max bet, has hopefully sensible defaults  
  
**Game rules:**  
Dealer stays on soft 17  
Double, Split, and Surrender are only allowed with your initial two cards  
Player with lowest chip total is eliminated after rounds 8, 16, 25, and 30 (if played)  
Blackjack pays 3:2 and split aces, tens, or face cards can become blackjacks.  
Players keep one card facedown until their turn.  
In the event of a tie for last place after an elimination round, no one is eliminated.  

**Things I'd do differently now:**  
Write the version of the game logic that interfaces with AI before the AI so I have a clearer picture of what data I have access to and how I can send it to the interface at various points. I'd probably end up with something like a ScoreTable class that's some kind of collection of the Score class.  
Maybe break each section of game code into a function. This would necessitate some convenient way to pass all required data between functions.  
