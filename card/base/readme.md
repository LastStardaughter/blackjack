**package card.base**  
Classes for card games.  
Cards are internally represented as strings. This allows the Deck class to work with other games with a little modification.  
For 52-card decks, cards are represented as value and suit, ie "10H" is the 10 of Hearts, "AC" is the Ace of Clubs and "4D" is the 4 of Diamonds.  
**Card** Static class with methods that return various information about the passed string; its face value, suit, etc.  
**Deck** A deck of cards. Can also represent multiple decks shuffled together. A shuffled array of strings that tracks position.  
**Hand** A hand of cards. Extends ArrayList<String> to save on typing and provides a compact display method.  