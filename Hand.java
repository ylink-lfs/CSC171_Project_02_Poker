package pokeGame;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

class Hand {
	Card[] oneHand;

	static int cardsPerHand = 5;

	Hand() {
		//Currently oneHand has no cards(null in array)
		oneHand = new Card[cardsPerHand];
	}

	//Remove top 5 cards from deck, then put back the remaining cards
	void handGetCards(Deck d) {
		//Convenient way of removing element from raw array
		//Search for sample code of conversion
		ArrayList<Card> modifyList = new ArrayList<Card>(Arrays.asList(d.getCurrentCards()));
		for(int i = 0; i < cardsPerHand; i++) {
			oneHand[i] = modifyList.get(0);
			modifyList.remove(0);
		}
		d.setCurrentCards(modifyList.toArray());
	}

	void printHoldCards() {
		for(int i = 0; i < cardsPerHand; i++) {
			System.out.print("Card " + String.valueOf(i + 1) + ": ");
			System.out.print("| Rank: " + oneHand[i].getRank() + " | Suit: " + oneHand[i].getSuit() + " |\n");
		}
	}

	private boolean isStraightFlush() {
		int suitKinds = 4;
		int[] suitCount = new int[suitKinds];

		for(int i = 0; i < cardsPerHand; i++) {
			suitCount[oneHand[i].suitIndex()]++;
		}

		Arrays.sort(oneHand);
		Boolean hasFive = false;
		for(int i = 0; i < suitKinds; i++) {
			//One suit repeats 5 times
			if(suitCount[i] == 5) {
				hasFive = true;
				break;
			}
		}
		if(hasFive) {
			for(int i = 1; i < cardsPerHand; i++) {
				//Check increment order of rand
				if(oneHand[i].rankIndex() != oneHand[i - 1].rankIndex() + 1) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isFourKind() {
		Arrays.sort(oneHand);
		int rankLen = 13;
		int[] rankCount = new int[rankLen];

		for(int i = 0; i < cardsPerHand; i++) {
			rankCount[oneHand[i].rankIndex()]++;
		}
		
		for(int i = 0; i < rankCount.length; i++) {
			//One rank repeat 4 or more times, then check suit
			if(rankCount[i] >= 4) {
				int suitLen = 4;
				int[] suitCount = new int[suitLen];
				for(int j = 0; j < cardsPerHand; j++) {
					suitCount[oneHand[j].suitIndex()]++;
				}
				for(int j = 0; j < suitLen; j++) {
					//One suit does not occur
					if(suitCount[j] == 0) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	private boolean isFullHouse() {
		Arrays.sort(oneHand);
		int rankLen = 13;
		int[] rankCount = new int[rankLen];

		for(int i = 0; i < cardsPerHand; i++) {
			rankCount[oneHand[i].rankIndex()]++;
		}

		Boolean hasThree = false, hasTwo = false;
		for(int i = 0; i < rankCount.length; i++) {
			if(rankCount[i] == 3) {
				hasThree = true;
			} else if(rankCount[i] == 2) {
				hasTwo = true;
			}
		}
		
		return hasThree && hasTwo;
	}

	private boolean isFlush() {
		Arrays.sort(oneHand);
		int suitKinds = 4;
		int[] suitCount = new int[suitKinds];

		for(int i = 0; i < cardsPerHand; i++) {
			suitCount[oneHand[i].suitIndex()]++;
		}
		for(int i = 0; i < suitKinds; i++) {
			if(suitCount[i] == 5) {
				return true;
			}
		}
		
		return false;
	}

	private boolean isStraight() {
		Arrays.sort(oneHand);

		for(int i = 1; i < cardsPerHand; i++) {
			//Check increment order of rand
			if(oneHand[i].getRank() != oneHand[i - 1].getRank() + 1) {
				return false;
			}
		}
		return true;
	}

	private boolean isThreeKind() {
		Arrays.sort(oneHand);
		int rankLen = 13;
		int[] rankCount = new int[rankLen];
		for(int i = 0; i < cardsPerHand; i++) {
			rankCount[oneHand[i].rankIndex()]++;
		}
		Boolean hasThree = false, hasTwo = false;
		for(int i = 0; i < rankCount.length; i++) {
			if(rankCount[i] == 3) {
				hasThree = true;
			} else if(rankCount[i] == 2) {
				hasTwo = true;
			}
		}

		return hasThree && !hasTwo;
	}

	private boolean isTwoPair() {
		Arrays.sort(oneHand);
		int rankLen = 13;
		int[] rankCount = new int[rankLen];
		for(int i = 0; i < cardsPerHand; i++) {
			rankCount[oneHand[i].rankIndex()]++;
		}
		int twoCnt = 0;
		for(int i = 0; i < rankCount.length; i++) {
			if(rankCount[i] == 2) {
				twoCnt++;
			}
		}

		return twoCnt == 2;
	}


	private boolean isOnePair() {
		Arrays.sort(oneHand);
		int rankLen = 13;
		int[] rankCount = new int[rankLen];
		for(int i = 0; i < cardsPerHand; i++) {
			rankCount[oneHand[i].rankIndex()]++;
		}
		int twoCnt = 0;
		for(int i = 0; i < rankCount.length; i++) {
			if(rankCount[i] == 2) {
				twoCnt++;
			}
		}
		return twoCnt == 1;
	}

	private int getLevel() {
		if(isStraightFlush()) {
			return 9;
		} else if(isFourKind()) {
			return 8;
		} else if(isFullHouse()){
			return 7;
		} else if(isFlush()) {
			return 6;
		} else if(isStraight()){
			return 5;
		} else if(isThreeKind()){
			return 4;
		} else if(isTwoPair()){
			return 3;
		} else if(isOnePair()){
			return 2;
		} else {
			//Need to compare high card;
			return 1;
		}	
	}

	private Card getHighestCard() {
		Arrays.sort(oneHand);
		return oneHand[oneHand.length - 1];
	}

	String getStringClassification() {
		int level = getLevel();
		switch (level) {
			case 1:
				return new String("NoPattern");
			case 2:
				return new String("OnePair");
			case 3:
				return new String("TwoPair");
			case 4:
				return new String("ThreeOfAKind");
			case 5:
				return new String("Straight");
			case 6:
				return new String("Flush");
			case 7:
				return new String("FullHouse");
			case 8:
				return new String("FourOfAKind");
			case 9:
				return new String("StraightFlush");
		}
		return new String("Error pattern");
	}

	//Assume that length of s is 10
	void parseStrToCards(String s) {
		int handIndex = 0;
		for(int i = 0; i < s.length(); i += 2) {
			char c1 = s.charAt(i);
			char c2 = s.charAt(i + 1);
			String rank = new String();
			String suit = new String();
			switch (c1) {
				case '2':
					rank = new String("2");
					break;
				case '3':
					rank = new String("3");
					break;
				case '4':
					rank = new String("4");
					break;
				case '5':
					rank = new String("5");
					break;
				case '6':
					rank = new String("6");
					break;
				case '7':
					rank = new String("7");
					break;
				case '8':
					rank = new String("8");
					break;
				case '9':
					rank = new String("9");
					break;
				case 'T':
				case 't':
					rank = new String("10");
					break;
				case 'J':
				case 'j':
					rank = new String("J");
					break;
				case 'Q':
				case 'q':
					rank = new String("Q");
					break;
				case 'K':
				case 'k':
					rank = new String("K");
					break;
				case 'A':
				case 'a':
					rank = new String("A");
					break;
			}
			switch (c2) {
				case 'C':
				case 'c':
					suit = new String("club");
					break;
				case 'H':
				case 'h':
					suit = new String("heart");
					break;
				case 'D':
				case 'd':
					suit = new String("diamond");
					break;
				case 'S':
				case 's':
					suit = new String("spade");
					break;
			}
			oneHand[handIndex++] = new Card(suit, rank);
		}
	}

	//Three state: win, lose, draw. So use int to represent 3 status.
	//If this is less than h, then return -1
	//Else if this is bigger than h, return 1
	//Else, return 0
	int lessThan(Hand h) {
		int level = getLevel();
		int otherLevel = h.getLevel();
		if(level < otherLevel) {
			return -1;
		} else if(level > otherLevel) {
			return 1;
		} else {
			//Both have no pattern. Compare the highest card.
			if (level == 1) {
				int res = getHighestCard().compareTo(h.getHighestCard());
				return res == 0 ? res : -res;
			} else if(level == 2) {
				return onePairCmp(h);
			} else if(level == 3) {
				return twoPairCmp(h);
			} else if(level == 4) {
				return threeKindCmp(h);
			} else if(level == 5) {
				return straightCmp(h);
			} else if(level == 6) {
				return flushCmp(h);
			} else if(level == 7) {
				return fullHouseCmp(h);
			} else if(level == 8) {
				return fourKindCmp(h);
			} else if(level == 9) {
				return straightFlushCmp(h);
			}
		}
		return 0;
	}

	private int onePairCmp(Hand h) {
		int twoIndex = 0;
		int rankLen = 13;
		int[] rankCount = new int[rankLen];
		for(int i = 0; i < rankCount.length; i++) {
			rankCount[oneHand[i].rankIndex()]++;
		}
		for(int i = 0; i < cardsPerHand; i++) {
			if(rankCount[oneHand[i].rankIndex()] == 2) {
				twoIndex = oneHand[i].rankIndex();
			}
		}

		int otherTwoIndex = 0;
		for(int i = 0; i < rankCount.length; i++) {
			rankCount[h.oneHand[i].rankIndex()]++;
		}
		for(int i = 0; i < cardsPerHand; i++) {
			if(rankCount[h.oneHand[i].rankIndex()] == 2) {
				otherTwoIndex = h.oneHand[i].rankIndex();
			}
		}

		if(twoIndex < otherTwoIndex) {
			return -1;
		} else if(twoIndex > otherTwoIndex) {
			return 1;
		} else {
			int[] restRanks = new int[3];
			int restIndex = 0;
			for(int i = 0; i < cardsPerHand; i++) {
				if(oneHand[i].rankIndex() != twoIndex) {
					restRanks[restIndex++] = oneHand[i].rankIndex();
				}
			}
			int[] otherRestRanks = new int[3];
			restIndex = 0;
			for(int i = 0; i < cardsPerHand; i++) {
				if(h.oneHand[i].rankIndex() != twoIndex) {
					restRanks[restIndex++] = h.oneHand[i].rankIndex();
				}
			}
			for(int i = 3; i >= 0; i--) {
				if(restRanks[i] < otherRestRanks[i]) {
					return -1;
				} else if(restRanks[i] > otherRestRanks[i]) {
					return 1;
				}
			}
			return 0;
		}
	}

	private int twoPairCmp(Hand h) {
		int lowerTwoIndex = -1;
		int upperTwoIndex = -1;
		int distinctNum = 0;
		for(int i = 0; i < cardsPerHand; ) {
			if(oneHand[i] == oneHand[i + 1]) {
				if(lowerTwoIndex == -1) {
					lowerTwoIndex = oneHand[i].rankIndex();
				} else {
					upperTwoIndex = oneHand[i].rankIndex();
				}
			} else {
				distinctNum = oneHand[i].rankIndex();
			}
		}

		int otherLowerTwoIndex = -1;
		int otherUpperTwoIndex = -1;
		int otherDistinctNum = 0;
		for(int i = 0; i < cardsPerHand; ) {
			if(h.oneHand[i] == h.oneHand[i + 1]) {
				if(otherLowerTwoIndex == -1) {
					otherLowerTwoIndex = h.oneHand[i].rankIndex();
				} else {
					otherUpperTwoIndex = h.oneHand[i].rankIndex();
				}
			} else {
				otherDistinctNum = h.oneHand[i].rankIndex();
			}
		}

		if(upperTwoIndex < otherUpperTwoIndex) {
			return -1;
		} else if(upperTwoIndex > otherUpperTwoIndex) {
			return 1;
		} else if(lowerTwoIndex < otherLowerTwoIndex) {
			return -1;
		} else if(lowerTwoIndex > otherLowerTwoIndex) {
			return 1;
		} else if(distinctNum < otherDistinctNum) {
			return -1;
		} else if(distinctNum > otherDistinctNum) {
			return 1;
		} else {
			return 0;
		}
	}

	private int threeKindCmp(Hand h) {
		int threeIndex = 0;
		int rankLen = 13;
		int[] rankCount = new int[rankLen];
		for(int i = 0; i < rankCount.length; i++) {
			rankCount[oneHand[i].rankIndex()]++;
		}
		for(int i = 0; i < cardsPerHand; i++) {
			if(rankCount[oneHand[i].rankIndex()] == 3) {
				threeIndex = oneHand[i].rankIndex();
			}
		}

		int otherThreeIndex = 0;
		for(int i = 0; i < rankCount.length; i++) {
			rankCount[h.oneHand[i].rankIndex()]++;
		}
		for(int i = 0; i < cardsPerHand; i++) {
			if(rankCount[h.oneHand[i].rankIndex()] == 3) {
				otherThreeIndex = h.oneHand[i].rankIndex();
			}
		}

		if(threeIndex < otherThreeIndex) {
			return -1;
		} else if(threeIndex > otherThreeIndex) {
			return 1;
		} else {
			int[] restRanks = new int[2];
			int restIndex = 0;
			for(int i = 0; i < cardsPerHand; i++) {
				if(oneHand[i].rankIndex() != threeIndex) {
					restRanks[restIndex++] = oneHand[i].rankIndex();
				}
			}
			int[] otherRestRanks = new int[2];
			restIndex = 0;
			for(int i = 0; i < cardsPerHand; i++) {
				if(h.oneHand[i].rankIndex() != threeIndex) {
					restRanks[restIndex++] = h.oneHand[i].rankIndex();
				}
			}
			for(int i = 2; i >= 0; i--) {
				if(restRanks[i] < otherRestRanks[i]) {
					return -1;
				} else if(restRanks[i] > otherRestRanks[i]) {
					return 1;
				}
			}
			return 0;
		}
	}

	private int straightCmp(Hand h) {
		int rank = oneHand[0].rankIndex();
		int otherRank = h.oneHand[0].rankIndex();
		if(rank < otherRank) {
			return -1;
		} else if(rank > otherRank) {
			return 1;
		} else {
			return 0;
		}
	}

	private int flushCmp(Hand h) {
		int[] rankStore = new int[cardsPerHand];
		int[] otherRandStore = new int[cardsPerHand];
		for(int i = cardsPerHand - 1; i >= 0; i--) {
			rankStore[i] = oneHand[i].rankIndex();
			otherRandStore[i] = h.oneHand[i].rankIndex();
			if(rankStore[i] < otherRandStore[i]) {
				return -1;
			} else if(rankStore[i] > otherRandStore[i]) {
				return 1;
			}
		}
		int res = getHighestCard().compareTo(h.getHighestCard());
		return res == 0 ? res : -res;
	}

	private int fullHouseCmp(Hand h) {
		int threeIndex = 0;
		int twoIndex = 0;
		int rankLen = 13;
		int[] rankCount = new int[rankLen];
		for(int i = 0; i < rankCount.length; i++) {
			rankCount[oneHand[i].rankIndex()]++;
		}
		for(int i = 0; i < cardsPerHand; i++) {
			if(rankCount[oneHand[i].rankIndex()] == 3) {
				threeIndex = oneHand[i].rankIndex();
			} else if(rankCount[oneHand[i].rankIndex()] == 2) {
				twoIndex = oneHand[i].rankIndex();
			}
		}

		int otherThreeIndex = 0;
		int otherTwoIndex = 0;
		for(int i = 0; i < rankCount.length; i++) {
			rankCount[h.oneHand[i].rankIndex()]++;
		}
		for(int i = 0; i < cardsPerHand; i++) {
			if(rankCount[h.oneHand[i].rankIndex()] == 3) {
				otherThreeIndex = h.oneHand[i].rankIndex();
			} else if(rankCount[h.oneHand[i].rankIndex()] == 2) {
				otherTwoIndex = h.oneHand[i].rankIndex();
			}
		}
		if(threeIndex < otherThreeIndex) {
			return -1;
		} else if (threeIndex > otherThreeIndex) {
			return 1;
		} else if (twoIndex < otherTwoIndex) {
			return -1;
		} else if(twoIndex > otherTwoIndex) {
			return 1;
		} else {
			int res = getHighestCard().compareTo(h.getHighestCard());
			return res == 0 ? res : -res;
		}
	}


	private int fourKindCmp(Hand h) {
		int mostRankIndex = 0;
		int rankLen = 13;
		int[] rankCount = new int[rankLen];
		for(int i = 0; i < rankCount.length; i++) {
			rankCount[oneHand[i].rankIndex()]++;
		}
		for(int i = 0; i < cardsPerHand; i++) {
			if(rankCount[oneHand[i].rankIndex()] == 4) {
				mostRankIndex = oneHand[i].rankIndex();
			}
		}

		int otherMostRankIndex = 0;
		for(int i = 0; i < rankCount.length; i++) {
			rankCount[h.oneHand[i].rankIndex()]++;
		}
		for(int i = 0; i < cardsPerHand; i++) {
			if(rankCount[h.oneHand[i].rankIndex()] == 4) {
				otherMostRankIndex = h.oneHand[i].rankIndex();
			}
		}

		if(mostRankIndex < otherMostRankIndex) {
			return -1;
		} else if(mostRankIndex > otherMostRankIndex) {
			return 1;
		} else {
			int res = getHighestCard().compareTo(h.getHighestCard());
			return res == 0 ? res : -res;
		}
	}

	//Compare the hightest card is enough because of ascending order
	private int straightFlushCmp(Hand h) {
		int res = getHighestCard().compareTo(h.getHighestCard());
		return res == 0 ? res : -res;
	}
}
