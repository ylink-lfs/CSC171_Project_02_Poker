package pokeGame;

import java.util.Random;

class Deck {
	Card[] deck;

	static int maxDeckSize = 52;
	static int shuffleTime = maxDeckSize * 2;

	Deck() {
		deck = new Card[maxDeckSize];
		String[] rankList = Card.getRankValueList();
		String[] suitList = Card.getSuitValueList();
		for(int i = 0; i < rankList.length; i++) {
			for(int j = 0; j < suitList.length; j++) {
				deck[i * suitList.length + j] = new Card(rankList[i], suitList[j]);
			}
		}
	}

	void shuffle() {
		Random q = new Random();
		for(int i = 0; i < shuffleTime; i++) {
			 int swapa = q.nextInt(maxDeckSize);
			 int swapb = q.nextInt(maxDeckSize);
			 Card t = deck[swapa];
			 deck[swapa] = deck[swapb];
			 deck[swapb] = t;
		}
	}

	void printDeck() {
		for(int i = 0; i < deck.length; i++) {
			System.out.print("Card " + String.valueOf(i + 1) + ": ");
			System.out.print("| Rank: " + deck[i].getRank() + " | Suit: " + deck[i].getSuit() + " |\n");
		}
	}

	public int currentDeckSize() {
		return deck.length;
	}

	Card[] getCurrentCards() {
		return deck;
	}

	void setCurrentCards(Object[] os) {
		deck = new Card[os.length];
		for(int i = 0; i < os.length; i++) {
			deck[i] = (Card)os[i];
		}
	}
}
