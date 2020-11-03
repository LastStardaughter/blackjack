**package ai**  
An interface that allows the main game code to communicate with human and computer players alike and supporting classes.  
**AI** interface that returns player decisions  
**BettingStrategy** static helper class that calculates bets in a hopefully-reasonable way when given information about the game  
**ConsolePlayer** gets decisions from the console  
**Dealer** doesn't implement ai, just exists to make dealer logic modular  
**EasyAI** Hits until 17+ then stays. 50% chance to ignore BettingStrategy and just bet high.  
**HardAI** Chooses actions based on the dealer's card and its hand.   
**HardAIDealerHits17** Variant of HardAI that makes slightly different decisions, not maintained  
**Score** Helper struct that, put in an ArrayList, bundles non-card gamestate data for communication between game code and ai interface  