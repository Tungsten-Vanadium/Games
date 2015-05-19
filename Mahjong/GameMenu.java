/**
 * Vincent's Card Game Collection - GameMenu.java
 */

/**
 * Vincent Tong Jan 2, 2013; Main menu to play Vincent's programmed card games
 */
import java.util.*;

public class GameMenu {
	public static int numChoices = 4;
	/**The main menu for starting*/
	public static void main(String[] args) {
		// Main method
		Scanner input = new Scanner(System.in);
		int choice = 0;
		while (choice != numChoices) {
			choice = 0;
			while(choice < 1 || choice > numChoices){
				try{
					System.out .println("Type in the number of the menu choice that you want\n" +
							"1. Mahjong (Chinese Styled)\n2. Rummykub\n3. Texas Hold'em\n4. Quit");
					String check = input.nextLine();
					choice = Integer.parseInt(check);
				}catch (NumberFormatException e) {
					System.out.println("That is not a number choice.");
					continue;
				}
				if(choice < 1 || choice > numChoices)
					System.out.println("Type in an integer between 1 and " + numChoices + ".");
			}
			switch (choice) {
			case 1:
				// Mahjong (Chinese Styled)(incomplete)
				new MahjongGame().mahjongGame();
				break;
			case 2:
				// Rummykub (incomplete)
				new RummykubGame().rummykubGame();
				break;
			case 3:
				// Texas Hold'em (incomplete)
				new TexasHoldem().texasHoldemGame();
				break;
			case 4:
				// Quit
			}
		}
		// Debug for Mahjong
		/*MahjongHumanPlayer a = new MahjongHumanPlayer("");
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		for(int lcv = 1; lcv <= 9; lcv++){
			tiles.add(new Tile(2, lcv));
		}
		for(int lcv = 1; lcv <= 3; lcv++){
			tiles.add(new Tile(3, lcv));
		}
		tiles.add(new Tile(4, 2));
		tiles.add(new Tile(4, 2));
		//System.out.println(tiles);
		a.hand = tiles;
		System.out.println(a.checkForWin(tiles) + " done");*/
	}
}
