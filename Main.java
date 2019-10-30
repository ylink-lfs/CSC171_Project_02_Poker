package pokeGame;

public class Main {
	private static void testPhase1() {
		System.out.println("Generating new deck...");
		Deck d = new Deck();
		System.out.println("Print inorder deck:");
		d.printDeck();
		System.out.println("-----------------------------");
		System.out.println("Shuffling deck...");
		d.shuffle();
		System.out.println("After shuffle:");
		d.printDeck();
	}

	private static void testPhase2() {
		Hand h1 = new Hand();
		Hand h2 = new Hand();
		Deck d = new Deck();
		d.shuffle();
		System.out.println("Before getting cards:");
		d.printDeck();
		System.out.println("Getting cards...");
		h1.handGetCards(d);
		h2.handGetCards(d);
		System.out.println("After getting cards:");
		System.out.println("Remaining cards:");
		d.printDeck();
		System.out.println("Hand 1:");
		h1.printHoldCards();
		System.out.println("Hand 2:");
		h2.printHoldCards();
	}

	private static void testPhase3And4() {
		String straightFlushTest = new String("8h9hThJhQh");
		String fourKindTest = new String("3hKcKhKsKd");
		String fullHouseTest = new String("3h3c3s6s6d");
		String flushTest = new String("4d6s7htcKc");
		String straightTest = new String("3h4h5c6c7d");
		String threeKindTest = new String("2d2h2c6hKc");
		String twoPairTest = new String("4d4s9hJhJs");
		String onePairTest = new String("4h4s5hTdKd");
		String highCardTest = new String("4s7d8cJhKh");

		Hand h = new Hand();

		System.out.println("Test classification of hand cards:");
		h.parseStrToCards(straightFlushTest);
		System.out.println(h.getStringClassification());
		h.parseStrToCards(fourKindTest);
		System.out.println(h.getStringClassification());
		h.parseStrToCards(fullHouseTest);
		System.out.println(h.getStringClassification());
		h.parseStrToCards(flushTest);
		System.out.println(h.getStringClassification());
		h.parseStrToCards(straightTest);
		System.out.println(h.getStringClassification());
		h.parseStrToCards(threeKindTest);
		System.out.println(h.getStringClassification());
		h.parseStrToCards(twoPairTest);
		System.out.println(h.getStringClassification());
		h.parseStrToCards(onePairTest);
		System.out.println(h.getStringClassification());
		h.parseStrToCards(highCardTest);
		System.out.println(h.getStringClassification());
		System.out.println("-----------------------------");

		Hand otherH = new Hand();
		h.parseStrToCards(straightFlushTest);
		otherH.parseStrToCards(flushTest);
		int handCmpRes;
		handCmpRes = h.lessThan(otherH);
		if(handCmpRes == -1) {
			System.out.println("Other hand win");
		} else if(handCmpRes == 0) {
			System.out.println("Draw");
		} else {
			//The expected output, this hand hold the highest rank of cards
			System.out.println("This hand win");
		}

		//Poke game test adding guide:
		//1. Use specific strings to initialize 2 hands
		//2. Use lessThan member func to test two hands
		//3. Test the return value to determine comparison result
	}

	public static void main(String[] args) {
		testPhase1();
		testPhase2();
		testPhase3And4();
	}
}
