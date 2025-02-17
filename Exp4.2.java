Experiment 4.2: Card Collection System

Objective:
Develop a Java program that collects and stores playing cards to help users find all the cards of a given symbol (suit).
The program should utilize the Collection interface (such as ArrayList, HashSet, or HashMap) to manage the card data efficiently.

Understanding the Problem Statement

1. Card Structure:
Each card consists of a symbol (suit) and a value (rank).

Example card representations:
Ace of Spades
King of Hearts
10 of Diamonds
5 of Clubs

2. Operations Required:
Add Cards → Store card details in a collection.
Find Cards by Symbol (Suit) → Retrieve all cards belonging to a specific suit (e.g., all "Hearts").
Display All Cards → Show all stored cards.

3. Collections Usage:
ArrayList: To store cards in an ordered manner.
HashSet: To prevent duplicate cards.
HashMap<String, List<Card>>: To organize cards based on suits for faster lookup.
----------------------------------------------------------------------------------------------------------------
  Code
----------------------------------------------------------------------------------------------------------------
 ====================================================================================================================

  import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

class Card {
    String value;
    String suit;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return value.equals(card.value) && suit.equals(card.suit);
    }

    @Override
    public int hashCode() {
        return value.hashCode() + suit.hashCode();
    }
}

public class CardCollectionSystem {
    
    private static HashSet<Card> cardSet = new HashSet<>();
    private static HashMap<String, List<Card>> suitMap = new HashMap<>();

    public static void main(String[] args) {
        addCard(new Card("Ace", "Spades"));
        addCard(new Card("King", "Hearts"));
        addCard(new Card("10", "Diamonds"));
        addCard(new Card("5", "Clubs"));

        displayAllCards();
        findCardsBySuit("Hearts");
        findCardsBySuit("Diamonds");

        removeCard(new Card("10", "Diamonds"));
        displayAllCards();
        addCard(new Card("King", "Hearts"));
    }

    public static void addCard(Card card) {
        if (cardSet.contains(card)) {
            System.out.println("Error: Card \"" + card + "\" already exists.");
        } else {
            cardSet.add(card);
            suitMap.computeIfAbsent(card.suit, k -> new ArrayList<>()).add(card);
            System.out.println("Card added: " + card);
        }
    }

    public static void findCardsBySuit(String suit) {
        List<Card> cards = suitMap.get(suit);
        if (cards == null || cards.isEmpty()) {
            System.out.println("No cards found for " + suit + ".");
        } else {
            System.out.println("Cards of " + suit + ":");
            for (Card card : cards) {
                System.out.println(card);
            }
        }
    }

    public static void removeCard(Card card) {
        if (cardSet.contains(card)) {
            cardSet.remove(card);
            suitMap.get(card.suit).remove(card);

            if (suitMap.get(card.suit).isEmpty()) {
                suitMap.remove(card.suit);
            }

            System.out.println("Card removed: " + card);
        } else {
            System.out.println("Card \"" + card + "\" not found.");
        }
    }

    public static void displayAllCards() {
        if (cardSet.isEmpty()) {
            System.out.println("No cards found.");
        } else {
            System.out.println("All Cards:");
            for (Card card : cardSet) {
                System.out.println(card);
            }
        }
    }
}

  
  ===================================================================================================================
  --------------------------------------------------------------------------------------------------------------------
  -------------------------------------------------------------------------------------------------------------------

Test Cases

Test Case 1: No Cards Initially
Input:
Display All Cards
Expected Output:
No cards found.

Test Case 2: Adding Cards
Input:
Add Card: Ace of Spades
Add Card: King of Hearts
Add Card: 10 of Diamonds
Add Card: 5 of Clubs
Expected Output:
Card added: Ace of Spades
Card added: King of Hearts
Card added: 10 of Diamonds
Card added: 5 of Clubs

Test Case 3: Finding Cards by Suit
Input:
Find All Cards of Suit: Hearts
Expected Output:
King of Hearts

Test Case 4: Searching Suit with No Cards
Input:
Find All Cards of Suit: Diamonds
(If no Diamonds were added)
Expected Output:
No cards found for Diamonds.

Test Case 5: Displaying All Cards
Input:
Display All Cards
Expected Output:
Ace of Spades
King of Hearts
10 of Diamonds
5 of Clubs

Test Case 6: Preventing Duplicate Cards
Input:
Add Card: King of Hearts
Expected Output:
Error: Card "King of Hearts" already exists.

Test Case 7: Removing a Card
Input:
Remove Card: 10 of Diamonds
Expected Output:
Card removed: 10 of Diamonds
