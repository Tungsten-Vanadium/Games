/**
 * Vincent's Card Game Collection - TexasHoldem.java
 */
/**
 * Vincent Tong
 * Mar 27, 2013; Runs a game of Texas Hold'em (fix from Project Euler Problem 54)
 */
import java.util.*;
public class TexasHoldem {
	// Global vars
	ArrayList<Card> deckOne = new ArrayList<Card>();
	boolean ante, blinds;
	int totalPlayers = 0, numHuman = 0, totalChips = 0, anteAmount, smallBlindAmount;
	// Constructor
	public TexasHoldem(){
		deckOne = fill(deckOne);
		int choice = 0;
		while(choice <= 1 || choice >= 4){
			System.out.println("Type in the number choice.\n1. No antes or blinds\n2. Antes\n3. Blinds\n4. Both antes and blinds");
			try{
				choice = new Scanner(System.in).nextInt();
				if(choice <= 1 || choice >= 4)
					throw new Exception();
			}catch (Exception e){
				System.out.println("Type in a number choice between 1 and 4.");
				continue;
			}
			switch(choice){
			case 1:
				ante = false;
				blinds = false;
				break;
			case 2:
				ante = true;
				blinds = false;
				break;
			case 3:
				ante = false;
				blinds = true;
				break;
			case 4:
				ante = true;
				blinds = true;
			}
		}
		fill(deckOne);
	}
	public ArrayList<Card> fill(ArrayList<Card> deck) {
		ArrayList<Card> temp = new ArrayList<Card>();
		for (int suit = 1; suit <= 4; suit++) {
			for (int number = 1; number <= 13; number++) {
				temp.add(new Card(suit, number));
			}
		}
		for (int lcv = 0; lcv < 52; lcv++) {
			int select = new Random().nextInt(temp.size());
			deck.add(temp.remove(select));
		}
		return deck;
	}
	public void texasHoldemGame(){
		// Main game
		// Set up blinds and antes if applicable
		Scanner scanner = new Scanner(System.in);
		while(totalPlayers <= 1){
			System.out.println("How many total players do you want?");
			try{
				totalPlayers = scanner.nextInt();
			}catch (Exception e){
				System.out.println("Type in an integer greater than 1.");
				continue;
			}
			break;
		}
		while(numHuman <= 0){
			System.out.println("How many human players do you want?");
			try{
				numHuman = scanner.nextInt();
			}catch (Exception e){
				System.out.println("Type in an integer greater than 0.");
				continue;
			}
			break;
		}
		while(totalChips <= 0){
			System.out.println("How many chips should each player have?");
			try{
				totalChips = scanner.nextInt();
			}catch (Exception e){
				System.out.println("Type in an integer greater than 0.");
				continue;
			}
			break;
		}
		while(true){
			try{
				if(ante){
					System.out.println("What do you want the ante to be?");
					anteAmount = scanner.nextInt();
					if(anteAmount == 0 || anteAmount >= totalChips){
						throw new NumberFormatException();
					}
					if(blinds){
						System.out.println("What should the small blind amount be?");
						smallBlindAmount = scanner.nextInt();
						if(smallBlindAmount == 0 || 2 * smallBlindAmount + anteAmount >= totalChips){
							// Consider making new Exception class to handle exceptions
						}
					}else{
						
					}
				}else{
					
				}
			}catch(InputMismatchException e){
				System.out.println("Invalid input.");
				continue;
			}catch(NumberFormatException e){
				System.out.println("Type in an integer greater than 0 and smaller than the total chips.");
				continue;
			}
			break;
		}


	}
	public void handCheck(String[] hand1, String[] hand2){
		// Checks what a player has

		boolean flush1 = false, flush2 = false, straight1 = false, straight2 = false;
		String playerHand1 = "High Card", playerHand2 = "High Card";
		for(int lcv = 0; lcv < 5; lcv++){
			if(Character.isLetter(hand1[lcv].charAt(0))){
				switch(hand1[lcv].charAt(0)){
				case 'A':
					// Ace
					hand1[lcv] = "E" + hand1[lcv].charAt(1);
					break;
				case 'K':
					// King
					hand1[lcv] = "D" + hand1[lcv].charAt(1);
					break;
				case 'Q':
					// Queen
					hand1[lcv] = "C" + hand1[lcv].charAt(1);
					break;
				case 'J':
					// Jack
					hand1[lcv] = "B" + hand1[lcv].charAt(1);
					break;
				case 'T':
					// Ten
					hand1[lcv] = "A" + hand1[lcv].charAt(1);
				}
			}
		}
		Arrays.sort(hand1);
		String[] changedHand1 = new String[5];
		System.arraycopy(hand1, 0, changedHand1, 0, 5);
		// Check for flushes
		char suit1 = hand1[0].charAt(1);
		int lcv;
		for(lcv = 1; lcv < 5; lcv++){
			if(hand1[lcv].charAt(1) != suit1)
				break;
		}
		if(lcv == 5)
			flush1 = true;
		// Check for straights
		int start1 = hand1[0].charAt(0) - 48;
		for(lcv = 1; lcv < 5; lcv++){
			int comp;
			if(Character.isLetter(hand1[lcv].charAt(0)))
				comp = hand1[lcv].charAt(0) - 55;
			else
				comp = hand1[lcv].charAt(0) - 48;
			if(start1 != comp - lcv)
				break;
		}
		if(lcv == 5)
			straight1 = true;
		int[] match1 = new int[2];
		char[] matched1 = new char[2];
		matched1[0] = 'P';
		if(!(straight1 || flush1)){
			// Checks for pairs, two pairs, three of a kinds, four of a kinds and full houses
			for(lcv = 0; lcv < 4; lcv++){
				if(matched1[0] == hand1[lcv].charAt(0) || matched1[1] == hand1[lcv].charAt(0))
					continue;
				for(int check = lcv + 1; check < 5; check++){
					if(hand1[lcv].charAt(0) == hand1[check].charAt(0)){
						if(matched1[0] == 'P'){
							matched1[0] = hand1[lcv].charAt(0);
							match1[0] += 2;
						}else if(matched1[0] == hand1[lcv].charAt(0)){
							match1[0]++;
						}else{
							matched1[1] = hand1[lcv].charAt(0);
							if(match1[1] == 0)
								match1[1]++;
							match1[1]++;
						}
					}
				}
			}
		}
		int max1 = Math.max(match1[0], match1[1]);
		if(match1[0] == match1[1] && match1[0] == 2)
			max1 = 5;
		if(Math.max(match1[0], match1[1]) == 3 && Math.min(match1[0], match1[1]) == 2)
			max1 = 6;
		if(straight1){
			// Straights
			if(flush1){
				playerHand1 = "Straight Flush";
			}else{
				playerHand1 = "Straight";
			}
		}else{
			if(flush1){
				playerHand1 = "Flush";
			}else{
				switch(max1){
				case 6:
					playerHand1 = "Full House";
					if(hand1[1].charAt(0) == hand1[2].charAt(0)){
						System.arraycopy(hand1, 0, changedHand1, 2, 3);
						changedHand1[0] = hand1[3];
						changedHand1[1] = hand1[4];
					}
					break;
				case 5:
					playerHand1 = "Two Pair";
					if(hand1[3].charAt(0) != hand1[4].charAt(0)){
						System.arraycopy(hand1, 0, changedHand1, 1, 4);
						changedHand1[0] = hand1[4];
					}else{
						if(hand1[2].charAt(0) != hand1[1].charAt(0)){
							System.arraycopy(hand1, 3, changedHand1, 3, 2);
							System.arraycopy(hand1, 0, changedHand1, 1, 2);
							changedHand1[0] = hand1[2];
						}
					}
					break;
				case 4:
					playerHand1 = "Four of a kind";
					if(hand1[0].charAt(0) == hand1[1].charAt(0)){
						System.arraycopy(hand1, 0, changedHand1, 1, 4);
						changedHand1[0] = hand1[4];
					}
					break;
				case 3:
					playerHand1 = "Three of a kind";
					if(hand1[1].charAt(0) != hand1[2].charAt(0)){

					}else if(hand1[1].charAt(0) == hand1[0].charAt(0)){
						System.arraycopy(hand1, 0, changedHand1, 2, 3);
						changedHand1[0] = hand1[0];
						changedHand1[1] = hand1[1];
					}else{
						System.arraycopy(hand1, 1, changedHand1, 2, 3);
						changedHand1[0] = hand1[0];
						changedHand1[1] = hand1[4];
					}
					break;
				case 2:
					playerHand1 = "Pair";
					if(hand1[0].charAt(0) == hand1[1].charAt(0)){
						changedHand1[3] = hand1[0];
						changedHand1[4] = hand1[1];
						changedHand1[0] = hand1[2];
						changedHand1[1] = hand1[3];
						changedHand1[2] = hand1[4];
					}else if(hand1[1].charAt(0) == hand1[2].charAt(0)){
						changedHand1[3] = hand1[1];
						changedHand1[4] = hand1[2];
						changedHand1[0] = hand1[0];
						changedHand1[1] = hand1[3];
						changedHand1[2] = hand1[4];
					}else if(hand1[2].charAt(0) == hand1[3].charAt(0)){
						changedHand1[3] = hand1[2];
						changedHand1[4] = hand1[3];
						changedHand1[0] = hand1[0];
						changedHand1[1] = hand1[1];
						changedHand1[2] = hand1[4];
					}
				}
			}
		}
	}
	
	/**Modify to take multiple hands*/
	private int winCheck(String one, String[] hand1, String two, String[] hand2){
		int play1, play2;
		if(one.equals("Straight Flush")){
			play1 = 8;
		}else if(one.equals("Four of a kind")){
			play1 = 7;
		}else if(one.equals("Full House")){
			play1 = 6;
		}else if(one.equals("Flush")){
			play1 = 5;
		}else if(one.equals("Straight")){
			play1 = 4;
		}else if(one.equals("Three of a kind")){
			play1 = 3;
		}else if(one.equals("Two Pair")){
			play1 = 2;
		}else if(one.equals("Pair")){
			play1 = 1;
		}else{
			play1 = 0;
		}
		if(two.equals("Straight Flush")){
			play2 = 8;
		}else if(two.equals("Four of a kind")){
			play2 = 7;
		}else if(two.equals("Full House")){
			play2 = 6;
		}else if(two.equals("Flush")){
			play2 = 5;
		}else if(two.equals("Straight")){
			play2 = 4;
		}else if(two.equals("Three of a kind")){
			play2 = 3;
		}else if(two.equals("Two Pair")){
			play2 = 2;
		}else if(two.equals("Pair")){
			play2 = 1;
		}else{
			play2 = 0;
		}
		if(play1 > play2)
			return 1;
		if(play1 < play2)
			return 2;
		for(int lcv = 4; lcv >= 0; lcv--){
			if(hand1[lcv].charAt(0) > hand2[lcv].charAt(0))
				return 1;
			if(hand1[lcv].charAt(0) < hand2[lcv].charAt(0))
				return 2;
		}
		return 0;
	}
}
