/**
 * Vincent's Card Game Collection - Tile.java
 */
/**
 * Vincent Tong Dec 8, 2012; Class for tiles
 */
public class Tile extends Card{
	public Tile(int t, int n) {
		super(t, n);
	}
	/**Checks if tile is flower*/
	public boolean isFlower() {
		return type == 1 && number < 3;
	}
	/**Checks if the suit is the one that cannot be in a sequence*/
	public boolean isSpecial() {
		return type == 1;
	}
	// For display purposes
	/**Returns the String form of the type*/
	public String getStringType() {
		switch (type) {
		case 1:
			return "Special";
		case 2:
			return "Character";
		case 3:
			return "Dot";
		case 4:
			return "Bamboo";
		default:
			return "Error";
		}
	}
	/**Returns the String form of the number*/
	public String getStringNumber() {
		String ret = "Empty";
		if(type == 0)
			return ret;
		if(type == 1){
			switch (number){
			case 1:
			case 2:
				ret = "Flower";
				break;
			case 3:
				ret = "East";
				break;
			case 4:
				ret = "South";
				break;
			case 5:
				ret = "West";
				break;
			case 6:
				ret = "North";
				break;
			case 7:
				ret = "Red dragon";
				break;
			case 8:
				ret = "Green dragon";
				break;
			case 9:
				ret = "White dragon";
			}
		} else {
			ret = Integer.toString(number);
		}
		return ret;
	}

	/**Returns the String form of the Tile*/
	public String toString() {
		if(type == 1)
			return getStringNumber();
		return getStringType() + " " + getStringNumber();
		/*String ret = "Empty";
		switch (type) {
		case 1:
			switch (number) {
			case 1:
			case 2:
				ret = "Flower";
				break;
			case 3:
				ret = "East";
				break;
			case 4:
				ret = "South";
				break;
			case 5:
				ret = "West";
				break;
			case 6:
				ret = "North";
				break;
			case 7:
				ret = "Red dragon";
				break;
			case 8:
				ret = "Green dragon";
				break;
			case 9:
				ret = "White dragon";
			}
			break;
		case 2:
			ret = "Character " + Integer.toString(number);
			break;
		case 3:
			ret = "Dot " + Integer.toString(number);
			break;
		case 4:
			ret = "Bamboo " + Integer.toString(number);
		}
		return ret;*/
	}
}