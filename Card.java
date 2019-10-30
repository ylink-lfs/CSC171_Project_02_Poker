package pokeGame;

class Card implements Comparable<Card>{
	String suit;
	String rank;

	static String[] suitValue = {"diamond", "club", "heart", "spade"};
	static String[] rankValue = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	
	Card(String suit,String rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	String getSuit() {
		return suit;
	}

	String getRank() {
		return rank;
	}

	int rankIndex() {
		for(int i = 0; i < rankValue.length; i++) {
			//Must return in if statement
			if(rank.compareTo(rankValue[i]) == 0) {
				return i;
			}
		}
		//Construction error, unexpected rank
		return -1;
	}

	int suitIndex() {
		for(int i = 0; i < suitValue.length; i++) {
			if(suit.compareTo(suitValue[i]) == 0) {
				return i;
			}
		}
		return -1;
	}

	static String[] getSuitValueList() {
		return suitValue;
	}

	static String[] getRankValueList() {
		return rankValue;
	}

	@Override
	public int compareTo(Card o) {
		if(this.rankIndex() < o.rankIndex())
			return 1;
		else if(this.rankIndex() > o.rankIndex())
			return -1;
		else{
			if(this.suitIndex() < o.suitIndex())
				return 1;
			else if(this.suitIndex() < o.suitIndex())
				return -1;
			else
				return 0;
		}
	}
}
