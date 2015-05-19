/**
 * Vincent's Card Game Collection - MahjongGame.java
 */
/**
 * Vincent Tong 
 * Dec 8, 2012; Runs the game of Mahjong (Chinese style)
 */
import java.util.*;
public class MahjongGame {
	/**Wall of Tiles to draw from*/
	public static ArrayList<Tile> wall;
	/**Tiles that have been discarded*/
	public static ArrayList<Tile> discarded = new ArrayList<Tile>();
	// Main game
	public void mahjongGame() {
		// The wall from which players will draw their tiles
		boolean playAgain = true, won = false;
		int startingPlayer = 0;
		while (playAgain) {
			// Setup
			// Fills the wall
			wall = fill(new ArrayList<Tile>());
			Scanner input = new Scanner(System.in);
			Random gen = new Random();
	// Edit later so that multiple humans can play
			MahjongHumanPlayer south = new MahjongHumanPlayer("South");
			MahjongCOMPlayer west = new MahjongCOMPlayer("West"), north = new MahjongCOMPlayer("North"), east = new MahjongCOMPlayer("East");
			System.out.println("You are south.");
			int roll = 0;
			// Rolls 3 die to see where to start drawing
			for (int lcv = 0; lcv < 3; lcv++)
				roll += gen.nextInt(6) + 1;
			int index = (roll % 4) * 18 + roll - 1;
			while (index > 0) {
				wall.add(wall.get(0));
				wall.add(wall.get(1));
				wall.remove(0);
				wall.remove(0);
				index--;
			}
			// Draw tiles in groups of 4 for each player; starting player gets one extra
			// Very important array to allow for less hard-coding of turn sequence
			MahjongPlayer[] players = { east, south, west, north };
			// Rearranges if incorrect
			switch (startingPlayer) {
			case 0:
				break;
			case 1:
				for (int lcv = 0; lcv < 3; lcv++)
					players[lcv] = players[lcv + 1];
				players[3] = east;
				break;
			case 2:
				for (int lcv = 0; lcv < 2; lcv++)
					players[lcv] = players[lcv + 2];
				players[2] = east;
				players[3] = south;
				break;
			case 3:
				for (int lcv = 3; lcv > 0; lcv--)
					players[lcv] = players[lcv - 1];
				players[0] = north;
			}
			// Adds tiles from the wall to the players' hands
			for (MahjongPlayer lcv : players)
				for (int times = 0; times < 3; times++)
					for (int get = 0; get < 4; get++)
						lcv.add(wall.remove(0));
			for (MahjongPlayer lcv : players)
				lcv.add(wall.remove(0));
			players[0].add(wall.remove(0));
			// Checks hands for flowers
			boolean hasFlower1 = true, hasFlower2 = true, hasFlower3 = true, hasFlower4 = true;
			while (hasFlower1 || hasFlower2 || hasFlower3 || hasFlower4) {
				boolean change = false;
				if (hasFlower1) {
					for (int lcv = 0; lcv < players[0].hand.size(); lcv++) {
						if (players[0].hand.get(lcv).isFlower()) {
							Tile[] a = new Tile[1];
							a[0] = players[0].hand.get(lcv);
							players[0].hand.remove(lcv);
							players[0].hand.add(lcv, wall.remove(wall.size() - 1));
							change = true;
						}
					}
					if (!change)
						hasFlower1 = false;
				}
				change = false;
				if (hasFlower2) {
					for (int lcv = 0; lcv < players[1].hand.size(); lcv++) {
						if (players[1].hand.get(lcv).isFlower()) {
							Tile[] a = new Tile[1];
							a[0] = players[1].hand.get(lcv);
							players[1].hand.remove(lcv);
							players[1].hand.add(lcv, wall.remove(wall.size() - 1));
							change = true;
						}
					}
					if (!change)
						hasFlower2 = false;
				}
				change = false;
				if (hasFlower3) {
					for (int lcv = 0; lcv < players[2].hand.size(); lcv++) {
						if (players[2].hand.get(lcv).isFlower()) {
							Tile[] a = new Tile[1];
							a[0] = players[2].hand.get(lcv);
							players[2].hand.remove(lcv);
							players[2].hand.add(lcv, wall.remove(wall.size() - 1));
							change = true;
						}
					}
					if (!change)
						hasFlower3 = false;
				}
				change = false;
				if (hasFlower4) {
					for (int lcv = 0; lcv < players[3].hand.size(); lcv++) {
						if (players[3].hand.get(lcv).isFlower()) {
							Tile[] a = new Tile[1];
							a[0] = players[3].hand.get(lcv);
							players[3].hand.remove(lcv);
							players[3].hand.add(lcv, wall.remove(wall.size() - 1));
							change = true;
						}
					}
					if (!change)
						hasFlower4 = false;
				}
			}
			// Checks for a very rare starting win
			if (players[0].checkForWin(players[0].hand)) {
				System.out.println(players[0].name + " has won with his original hand!");
				won = true;
			}
			int person = 1;
			if(!won){
				// First person discards to start
				discarded.add(players[0].discard(west, north, east, south));
				System.out.println(players[0].name + " discarded a " + discarded.get(discarded.size() - 1).toString() + ".");
			}else{
				person = 0;
			}
			// Keeps game going until somebody wins or 8 tiles are left in the wall
			while (!won && wall.size() > 8) {
				// Checks each player in order for a win
				for (int lcv = person; lcv < person + 4; lcv++) {
					if (players[lcv % 4].checkForWin(discarded.get(discarded.size() - 1)))
						won = true;
				}
				// Checks each player for an interrupt
				boolean interrupt = false;
				for (int lcv = person; lcv < person + 3; lcv++) {
					if (players[lcv % 4].checkForThreeKind(discarded.get(discarded.size() - 1)) > 2){
						Tile discard = players[lcv % 4].interrupt(discarded.get(discarded.size() - 1), west, north, east, south);
						if (discard.getNumber() != 0) {
							// Changes the turn sequence
							discarded.add(discard);
							System.out.println(players[lcv % 4].name + " discarded a " + discarded.get(discarded.size() - 1).toString() + ".");
							person = lcv % 4 + 1;
							interrupt = true;
							break;
						}else{
							if(players[person % 4].won){
								won = true;
								interrupt = true;
								break;
							}
						}
					}
				}
				if (interrupt)
					continue;
				// Normal turn
				players[person % 4].mahjongTurn(wall.remove(0), discarded.get(discarded.size() - 1), west, north, east, south);
				if(players[person % 4].won){
					won = true;
					break;
				}
				discarded.add(players[person % 4].discard(west, north, east, south));
				System.out.println(players[person % 4].name + " discarded a " + discarded.get(discarded.size() - 1).toString() + ".");
				person++;
			}
			if (!won){
				System.out.println("Since there are only 8 tiles left, the game ends in a draw.");
			}else{
				System.out.println(players[person % 4].name + " has won!");
				players[person % 4].display();
			}
			System.out.println("Play again? Y/N");
			String playA = input.next();
			if (!playA.equals("Y")) {
				playAgain = false;
			} else {
				wall.clear();
				startingPlayer = (startingPlayer + 1) % 4;
			}
		}
	}

	/**Fills the wall so it is random*/
	public ArrayList<Tile> fill(ArrayList<Tile> wall) {
		ArrayList<Tile> temp = new ArrayList<Tile>();
		for (int howMany = 1; howMany <= 4; howMany++) {
			for (int suit = 1; suit <= 4; suit++) {
				for (int number = 1; number <= 9; number++) {
					temp.add(new Tile(suit, number));
				}
			}
		}
		for (int lcv = 0; lcv < 144; lcv++) {
			int select = new Random().nextInt(temp.size());
			wall.add(temp.remove(select));
		}
		return wall;
	}
	/**Removes a tile from the wall*/
	public ArrayList<Tile> remove(ArrayList<Tile> wall, int index) {
		wall.remove(index);
		return wall;
	}
}
