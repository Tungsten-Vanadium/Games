/**
 * Vincent's Card Game Collection - MahjongPlayer.java
 */
/**
 * Vincent Tong
 * Dec 8, 2012; Class for general mahjong player
 */
import java.util.ArrayList;
import java.util.Arrays;
public abstract class MahjongPlayer {
	// Used by subclasses
	/**The MahjongPlayer's hand*/
	protected ArrayList<Tile> hand = new ArrayList<Tile>();
	/**The MahjongPlayer's Tiles that are showing*/
	protected ArrayList<Tile[]> show = new ArrayList<Tile[]>();
	/**The name of the MahjongPlayer*/
	protected String name;
	/**Checks to see if the MahjongPlayer has a winning hand*/
	protected boolean won = false;
	
	// Abstract methods
	protected abstract Tile discard(MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south);
	protected abstract void mahjongTurn(Tile get, Tile thrown, MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south);
	protected abstract Tile interrupt(Tile thrown, MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south);
	/**Adds a tile to the hand*/
	protected void add(Tile newTile){
		hand.add(newTile);
	}
	/**Throws a tile out*/
	protected Tile rem(Tile throwTile){
		for (int lcv = 0; lcv < hand.size(); lcv++) 
			if (hand.get(lcv).toString().equals(throwTile.toString())) {
				return hand.remove(lcv);
			}
		return new Tile(0, 0);
	}
	/**Displays hand and showing*/
	protected void display(){
		for(int lcv = 0; lcv < hand.size(); lcv++)
			System.out.print(hand.get(lcv).toString() + " ");
		for(int lcv = 0; lcv < show.size(); lcv++)
			for(int lcv2 = 0; lcv2 < show.get(lcv).length; lcv2++)
				System.out.print(show.get(lcv)[lcv2].toString() + " ");
		System.out.println();
	}
	/**Divides the hand by suit for sorting the hand*/
	protected ArrayList<Tile> diviForSort(){
		ArrayList<Tile> special = new ArrayList<Tile>(), character = new ArrayList<Tile>(), dot = new ArrayList<Tile>(), bamboo = new ArrayList<Tile>();
		for(int lcv = 0; lcv < hand.size(); lcv++){
			switch(hand.get(lcv).getType()){
			case 1:
				special.add(hand.get(lcv));
				break;
			case 2:
				character.add(hand.get(lcv));
				break;
			case 3:
				dot.add(hand.get(lcv));
				break;
			case 4:
				bamboo.add(hand.get(lcv));
				break;
			}
		}
		special = sort(special);
		character = sort(character);
		dot = sort(dot);
		bamboo = sort(bamboo);
		for(int lcv = 0; lcv < character.size(); lcv++)
			special.add(character.get(lcv));
		for(int lcv = 0; lcv < dot.size(); lcv++)
			special.add(dot.get(lcv));
		for(int lcv = 0; lcv < bamboo.size(); lcv++)
			special.add(bamboo.get(lcv));
		return special;
	}
	/**Sorts the hand*/
	protected ArrayList<Tile> sort(ArrayList<Tile> type) {
		ArrayList<Tile> tempHold = new ArrayList<Tile>();
		int lcv, index;
		// Sorts each mini-arraylist by number, then combines all of them to return
		if(type.size() > 0){
			tempHold.add(type.get(0));
			for(lcv = 1; lcv < type.size(); lcv++){
				for(index = 0; index < tempHold.size(); index++){
					if(type.get(lcv).getNumber() <= tempHold.get(index).getNumber()){
						tempHold.add(index, type.get(lcv));
						break;
					}
				}
				if(index == tempHold.size())
					tempHold.add(type.get(lcv));
			}
		}
		return tempHold;
	}
	/** Checks for a win when a Tile is thrown out*/
	protected boolean checkForWin(Tile check) {
		ArrayList<Tile> trans = new ArrayList<Tile>();
		trans.add(check);
		for(int lcv = 0; lcv < hand.size(); lcv++)
			trans.add(hand.get(lcv));
		return checkForWin(trans);
	}
	/**Checks for a win in one's hand*/
	protected boolean checkForWin(ArrayList<Tile> h) {
		System.out.println(name + " " + h);
		h = diviForSort();
		//System.out.println(h);
		// Makes all the ArrayList<Tile> that are needed for this method
		ArrayList<Tile> thirteen = new ArrayList<Tile>(), temp = new ArrayList<Tile>(), special = new ArrayList<Tile>(), character = new ArrayList<Tile>(), dot = new ArrayList<Tile>(), bamboo = new ArrayList<Tile>();
		// Checks for a very special way to win
		for(int lcv = 3; lcv <= 9; lcv++)
			thirteen.add(new Tile(1, lcv));
		for(int lcv = 2; lcv <= 4; lcv++){
			thirteen.add(new Tile(lcv, 1));
			thirteen.add(new Tile(lcv, 9));
		}
		boolean thirteenPair = false;
		int lcv, jumpThirteen = 0;
		for(lcv = 0; lcv < 13; lcv++){
			if(h.get(lcv).toString().equals(thirteen.get(lcv - jumpThirteen).toString())){
				continue;
			}
			if(lcv == 0)
				break;
			if(!thirteenPair && h.get(lcv - 1).toString().equals(thirteen.get(lcv - 1).toString())){
				thirteenPair = true;
				jumpThirteen = 1;
				continue;
			}
			break;
		}
		if(lcv == 13 && thirteenPair){
			won = true;
			System.out.println("13" + h);
			return true;
		}
		// Dumps flowers since they are not needed for calculation
		ArrayList<Tile[]> tempHold = new ArrayList<Tile[]>();
		for (lcv = 0; lcv < show.size(); lcv++) {
			if (show.get(lcv)[0].isFlower())
				continue;
			tempHold.add(show.get(lcv));
		}
		// For checking the tiles in one's hand
		// Sorts the tiles by type to make it easier to check without type challenges
		for(lcv = 0; lcv < h.size(); lcv++)
			temp.add(h.get(lcv));
		for(lcv = 0; lcv < temp.size(); lcv++){
			switch(temp.get(lcv).getType()){
			case 1:
				special.add(temp.get(lcv));
				break;
			case 2:
				character.add(temp.get(lcv));
				break;
			case 3:
				dot.add(temp.get(lcv));
				break;
			case 4:
				bamboo.add(temp.get(lcv));
				break;
			}
		}
		// Too few tiles in a suit makes it impossible to win
		if(special.size() == 1 || character.size() == 1 || dot.size() == 1 || bamboo.size() == 1)
			return false;
		// If one suit fails, then there is no win
		if(!(assistCheck(special) && assistCheck(character) && assistCheck(dot) && assistCheck(bamboo)))
			return false;
		won = true;
		System.out.println(tempHold + " " + temp);
		return true;
	}
	/** Helps with checking for a win; SCRUTINIZE THIS AS THIS IS THE MOST BUGGY SECTION!!!*/
	private boolean assistCheck(ArrayList<Tile> type){
		if(type.size() < 2)
			return type.size() == 0;
		//System.out.println("Once " + type.get(0).getType());
		ArrayList<ArrayList<Tile>> check = new ArrayList<ArrayList<Tile>>();
		ArrayList<Tile> temp = new ArrayList<Tile>();
		for(int lcv = 0; lcv < type.size(); lcv++)
			temp.add(type.get(lcv));
		Tile ad = new Tile(0, 0);
		//System.out.println(temp);
		for(int lcv = 1; lcv <= 9; lcv++){
			int times = 0;
			// Checks how many times a number occurs
			for(int lcv2 = 0; lcv2 < 4; lcv2++){
				if(temp.size() == 0)
					break;
				if(temp.get(0).getNumber() == lcv){
					ad = temp.remove(0);
					times++;
					continue;
				}
				break;
			}
			ArrayList<Tile> add = new ArrayList<Tile>();
			for(int lcv2 = 0; lcv2 < times; lcv2++)
				add.add(ad);
			if(times != 0)
				check.add(add);
		}
		// Gets the special suit out of the way
		if(check.size() == 0)
			return true;
		if(check.get(0).get(0).getType() == 1){
			boolean pair = false;
			for(int lcv = 0; lcv < check.size(); lcv++){
				if(check.get(lcv).size() == 1)
					return false;
				if(check.get(lcv).size() == 2){
					if(pair)
						return false;
					pair = true;
				}
			}
			return true;
		}
		//System.out.println(check);
		boolean pair = false;
		for(int lcv = 0; lcv < check.size(); lcv++){
			//System.out.println(check + " hkj");
			if(check.size() < 3)
				break;
			// Ones with only one must be in a sequence for a win
			if(check.get(lcv).size() == 1){
				int num = check.get(lcv).get(0).getNumber();
				Tile[] a = checkWinSequence(check, num, lcv, -1), b = checkWinSequence(check, num, lcv, 0), c = checkWinSequence(check, num, lcv, 1);
				if(a.equals(new Tile[0]) && b.equals(new Tile[0]) && c.equals(new Tile[0]))
					return false;
				ArrayList<Tile> one = new ArrayList<Tile>(), two = new ArrayList<Tile>(), three = new ArrayList<Tile>();
				for(int lcv2 = 0; lcv2 < temp.size(); lcv2++){
					one.add(check.get(0).get(lcv));
					two.add(check.get(0).get(lcv));
					three.add(check.get(0).get(lcv));
				}
				//System.out.println(one + "" + two + "" + three + "" + Arrays.toString(a) + " ahjskdf");
				// Removes the proposed sequence
				for(int lcv2 = 0; lcv2 < a.length; lcv2++)
					one.remove(a[lcv2]);
				for(int lcv2 = 0; lcv2 < b.length; lcv2++)
					two.remove(b[lcv2]);
				for(int lcv2 = 0; lcv2 < c.length; lcv2++)
					three.remove(c[lcv2]);
				// Recursively checks after removing the proposed sequence
				boolean acheck = false, bcheck = false, ccheck = false;
				if(!a.equals(new Tile[0]))
					acheck = assistCheck(one);
				if(!b.equals(new Tile[0]))
					bcheck = assistCheck(two);
				if(!c.equals(new Tile[0]))
					ccheck = assistCheck(three);
				return acheck || bcheck || ccheck;
				/*if(!a.equals(new Tile[0]))
					if(!b.equals(new Tile[0])){
						if(!c.equals(new Tile[0]))
							return assistCheck(one) || assistCheck(two) || assistCheck(three);
						else
							return assistCheck(one) || assistCheck(two);
					}else{
						if(!c.equals(new Tile[0])){
							return assistCheck(one) || assistCheck(three);
						}else
							return assistCheck(one);
					}
				}else{
					if(!b.equals(new Tile[0])){
						if(!c.equals(new Tile[0]))
							return assistCheck(two) || assistCheck(three);
						else
							return assistCheck(two);
					}else{
						if(!c.equals(new Tile[0]))
							return assistCheck(three);
					}
				}*/
		// Incomplete section
			}else if(check.get(lcv).size() == 2){
				// May be the pair or the overlap of sequences
				if(!pair){
					int num = check.get(lcv).get(0).getNumber();
					Tile[] a = checkWinSequence(check, num, lcv, -1), b = checkWinSequence(check, num, lcv, 0), c = checkWinSequence(check, num, lcv, 1);
					if(a.equals(new Tile[0]) && b.equals(new Tile[0]) || a.equals(new Tile[0]) && c.equals(new Tile[0]) || b.equals(new Tile[0]) && c.equals(new Tile[0])){
						pair = true;
					}else{
						
					}
				}else{
					// Copy above except modify "pair = true;" with "return false;"
				}
			}
		}
		for(int lcv = 0; lcv < check.size(); lcv++){
			if(check.get(lcv).size() == 1)
				return false;
			if(check.get(lcv).size() == 2){
				if(pair)
					return false;
				pair = true;
			}
		}
		return true;
	}
	/**Checks to see if something can go in a sequence through the proposed direction (-1 goes x-2 to x; 0 goes x-1 to x+1; 1 goes x to x+2); used in assistCheck()*/
	private Tile[] checkWinSequence(ArrayList<ArrayList<Tile>> check, int num, int where, int direction){
		Tile[] ret = new Tile[3];
		// Location in ArrayList of ArrayList of Tiles
		switch(where){
		case 0:
			// Can only go forward && checks if needed tiles are there
			if(direction != 1 || check.get(1).get(0).getNumber() - 1 != num || check.get(2).get(0).getNumber() - 2 != num)
				return new Tile[0];
			for(int lcv = 0; lcv < 2; lcv++)
				ret[lcv] = new Tile(check.get(0).get(0).getType(), num + lcv);
			break;
		case 1:
			switch(direction){
			case -1:	
				// Can't go backward
				return new Tile[0];
			case 0:
				// Either direction && checks if needed tiles are there
				if(check.get(0).get(0).getNumber() + 1 != num || check.get(2).get(0).getNumber() - 1 != num)
					return new Tile[0];
				for(int lcv = 0; lcv < 2; lcv++)
					ret[lcv] = new Tile(check.get(0).get(0).getType(), num + lcv - 1);
				break;
			// Forward
			case 1:
				// Checks if needed tiles are there
				if(check.get(0).get(0).getNumber() + 1 != num || check.get(2).get(0).getNumber() + 2 != num)
					return new Tile[0];
				for(int lcv = 0; lcv < 2; lcv++)
					ret[lcv] = new Tile(check.get(0).get(0).getType(), num + lcv - 2);
			}
			break;
		default:
			switch(direction){
			// Backward
			case -1:
				// Checks if needed tiles are there
				if(check.get(1).get(0).getNumber() - 1 != num || check.get(2).get(0).getNumber() - 2 != num)
					return new Tile[0];
				for(int lcv = 0; lcv < 2; lcv++)
					ret[lcv] = new Tile(check.get(0).get(0).getType(), num - lcv);
				break;
			// Both ways
			case 0:
				// Checks if where is at the boundary
				if(check.size() - 1 == where)
					return new Tile[0];
				// Checks if needed tiles are there
				if(check.get(0).get(0).getNumber() + 1 != num || check.get(2).get(0).getNumber() - 1 != num)
					return new Tile[0];
				for(int lcv = 0; lcv < 2; lcv++)
					ret[lcv] = new Tile(check.get(0).get(0).getType(), num + lcv - 1);
				break;
			// Forward
			case 1:
				// Checks if where is one away from or at the boundary
				if(check.size() - 2 < where)
					return new Tile[0];
				// Checks if needed tiles are there
				if(check.get(0).get(0).getNumber() + 1 != num || check.get(2).get(0).getNumber() + 2 != num)
					return new Tile[0];
				for(int lcv = 0; lcv < 2; lcv++)
					ret[lcv] = new Tile(check.get(0).get(0).getType(), num + lcv - 2);
			}
		}
		return ret;
	}
	/**Checks to see if a player can interrupt to make a three/four of a kind*/
	protected int checkForThreeKind(Tile thrown){
		int match = 1;
		for(int lcv = 0; lcv < hand.size(); lcv++)
			if(hand.get(lcv).toString().equals(thrown.toString()))
				match++;
		return match;
	}

	/**Checks to see if a player can make a sequence; double check this one since it doesn't seem to be working*/
	protected ArrayList<Tile[]> checkForSequence(Tile thrown) {
		// Specials can't be sequenced
		if(thrown.getType() == 1)
			return new ArrayList<Tile[]>();
		ArrayList<Tile[]> a = new ArrayList<Tile[]>();
		// CHECK TO SEE WHY IT DOESN'T WORK
		boolean same = false;
		for(int lcv = 0; lcv < hand.size(); lcv++){
			// Sequence is only same suit
			for(int lcv2 = lcv; lcv2 < hand.size(); lcv2++){
				if(hand.get(lcv).getType() != hand.get(lcv2).getType() || hand.get(lcv).getType() != thrown.getType() || hand.get(lcv2).getType() != thrown.getType())
					continue;
				// Takes the average and sees if it equals one of the tiles; also checks to see if they are adjacent
				double temp = ((hand.get(lcv).getNumber() + hand.get(lcv2).getNumber() + thrown.getNumber()) / 3.0);
				if((temp == hand.get(lcv).getNumber() || temp == hand.get(lcv2).getNumber() || temp == thrown .getNumber()) && Math.abs(hand.get(lcv).getNumber() - thrown.getNumber()) == 1){
					// Will add as an array for display purposes
					Tile[] b = { hand.get(lcv), hand.get(lcv2), thrown };
					for(int check = 0; check < a.size(); check++)
						if(a.get(lcv).equals(b)){
							same = true;
							break;
						}
					if(same)
						a.add(b);
				}
			}
		}
		return a;
	}
}
