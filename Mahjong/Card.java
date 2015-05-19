/**
 * Vincent's Card Game Collection - Card.java
 */

/**
 * Vincent Tong
 * Jan 16, 2013; Class for cards
 */
public class Card {
	/**The suit of the Card*/
	protected int type;
	/**The number of the Card*/
	protected int number;

	public Card(int t, int n) {
		type = t;
		number = n;
	}

	// Getter methods
	// For calculation purposes
	/**Returns the type (int)*/
	public int getType() {
		return type;
	}
	/**Returns the number (int)*/
	public int getNumber() {
		return number;
	}
	// For display purposes
	/**Returns the String form of the type*/
	public String getStringType() {
		switch (type) {
		case 1:
			return "Spade";
		case 2:
			return "Heart";
		case 3:
			return "Diamond";
		case 4:
			return "Club";
		default:
			return "Error";
		}
	}
	/**Returns the String form of the number*/
	public String getStringNumber() {
		switch(getNumber()){
		case 1:
			return "Ace";
		case 11:
			return "Jack";
		case 12:
			return "Queen";
		case 13:
			return "King";
		}
		return Integer.toString(number);
	}

	// Used to compare cards
	public String toString() {
		return getStringType() + " " + getStringNumber();
		/*String ret = "";
		switch (type) {
		case 1:
			ret = " Spade";
			break;
		case 2:
			ret = " Heart";
			break;
		case 3:
			ret = " Diamond";
			break;
		case 4:
			ret = " Club";
		}
		return Integer.toString(number) + ret;*/
	}
}
