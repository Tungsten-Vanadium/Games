/**
 * Vincent's Card Game Collection - HumanPlayer.java
 */
/**
 * Vincent Tong
 * Dec 8, 2012; Class for human player
 */
import java.util.*;

public class MahjongHumanPlayer extends MahjongPlayer {
	/**Scanner for input*/
	private Scanner input = new Scanner(System.in);
	public MahjongHumanPlayer(String n) {
		name = n;
	}
	/**Turn for a human*/
	protected void mahjongTurn(Tile get, Tile thrown, MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south) {
		if (checkForWin(thrown)) {
			won = true;
			return;
		}
		// Checks for a sequence
		ArrayList<Tile[]> check = checkForSequence(thrown);
		if (check.size() != 0) {
			display(west, north, east, south);
			System.out.println("Do you want to Chow (make a sequence)?");
			// Displays options
			for (int lcv = 0; lcv < check.size(); lcv++) {
				System.out.print((lcv + 1) + ". ");
				for (int lcv2 = 0; lcv2 < check.get(lcv).length; lcv2++) {
					System.out.print(check.get(lcv)[lcv2].toString() + " ");
				}
				System.out.println();
			}
			int choice = 0;
			while (true) {
				try {
					System.out.println("Type in the number of the choice or any other number (integer only) to decline.");
					String in = input.nextLine();
					choice = Integer.parseInt(in);
				} catch (NumberFormatException e) {
					System.out.println("That is not a valid choice.");
					continue;
				}
				break;
			}
			if (choice > 0 && choice <= check.size()) {
				show.add(check.get(choice - 1));
				MahjongGame.discarded.remove(thrown);
				for(int lcv = 0; lcv < 3; lcv++){
					if(check.get(choice - 1)[lcv].equals(thrown))
						continue;
					rem(check.get(choice - 1)[lcv]);
				}
				hand = diviForSort();
				discard(west, north, east, south);
				// Ends turn
				return;
			}
		}
		System.out.println("\nIt is your turn. You drew a " + get.toString() + ".");
		add(get);
		// Checks for flowers
		while(true){
			if (get.isFlower()) {
				Tile[] a = { get };
				show.add(a);
				rem(get);
				get = MahjongGame.wall.remove(MahjongGame.wall.size() - 1);
				add(get);
				System.out.println("You drew a " + get.toString() + ".");
				continue;
			}
			break;
		}
		hand = diviForSort();
	}

	/**Discard for a human*/
	protected Tile discard(MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south) {
		display(west, north, east, south);
		// Forces player to discard
		boolean notDiscarded, notInHand;
		do {
			notDiscarded = false;
			Tile temp = new Tile(0, 0);
			do {
				notInHand = false;
				System.out.print("Which tile do you want to throw out? Type in the name of the tile as shown above: ");
				String a = input.nextLine();
				if (a.contains("Character") && a.length() == 11) {
					temp = new Tile(2, a.charAt(10) - 48);
				} else if (a.contains("Dot") && a.length() == 5) {
					temp = new Tile(3, a.charAt(4) - 48);
				} else if (a.contains("Bamboo") && a.length() == 8) {
					temp = new Tile(4, a.charAt(7) - 48);
				} else if (a.equals("East")) {
					temp = new Tile(1, 3);
				} else if (a.equals("South")) {
					temp = new Tile(1, 4);
				} else if (a.equals("West")) {
					temp = new Tile(1, 5);
				} else if (a.equals("North")) {
					temp = new Tile(1, 6);
				} else if (a.equals("Red dragon")) {
					temp = new Tile(1, 7);
				} else if (a.equals("Green dragon")) {
					temp = new Tile(1, 8);
				} else if (a.equals("White dragon")) {
					temp = new Tile(1, 9);
				} else {
					System.out.println("Doesn't exist.");
					notInHand = true;
				}
			} while (notInHand);
			int lcv;
			for (lcv = 0; lcv < hand.size(); lcv++) 
				if (hand.get(lcv).toString().equals(temp.toString())) 
					return hand.remove(lcv);
			if(lcv == hand.size()){
				notDiscarded = true;
				System.out.println("That tile doesn't exist in your hand.");
			}
		} while (notDiscarded);
		return new Tile(0, 0);
	}

	/**Displays visible tiles*/
	private void display(MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south) {
		// Shows discarded tiles
		System.out.print("\nDiscarded: ");
		for (int lcv = 0; lcv < MahjongGame.discarded.size(); lcv++)
			System.out.print(MahjongGame.discarded.get(lcv).toString() + "  ");
		// Shows West's visible tiles
		System.out.print("\n\n" + west.name + " show: ");
		for (int lcv = 0; lcv < west.show.size(); lcv++)
			for(int lcv2 = 0; lcv2 < west.show.get(lcv).length; lcv2++)
				System.out.print(west.show.get(lcv)[lcv2].toString() + "  ");
		// Shows North's visible tiles
		System.out.print("\n\n" + north.name + " show: ");
		for (int lcv = 0; lcv < north.show.size(); lcv++)
			for(int lcv2 = 0; lcv2 < north.show.get(lcv).length; lcv2++)
				System.out.print(north.show.get(lcv)[lcv2].toString() + "  ");
		// Shows East's visible tiles
		System.out.print("\n\n" + east.name + " show: ");
		for (int lcv = 0; lcv < east.show.size(); lcv++)
			for(int lcv2 = 0; lcv2 < east.show.get(lcv).length; lcv2++)
				System.out.print(east.show.get(lcv)[lcv2].toString() + "  ");
		// Shows South's visible tiles
		System.out.print("\n\n" + south.name + " show: ");
		for (int lcv = 0; lcv < south.show.size(); lcv++)
			for(int lcv2 = 0; lcv2 < south.show.get(lcv).length; lcv2++)
				System.out.print(south.show.get(lcv)[lcv2].toString() + "  ");
		// Shows your own hand
		System.out.print("\n\nYour hand: ");
		for (int lcv = 0; lcv < hand.size(); lcv++){
			System.out.print(hand.get(lcv).toString() + "  ");
			if(lcv % 5 == 4)
				System.out.println();
		}
		System.out.println("\n");
	}

	/**Sees if the human player can interrupt with a pong/gong (three of a kind/four of a kind) or a win; Doesn't work when removing*/
	protected Tile interrupt(Tile thrown, MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south) {
		hand = diviForSort();
		display(west, north, east, south);
		if(checkForThreeKind(thrown) == 4){
			int choice;
			while(true){
				try{
					System.out.println("Do you want to \n1. Pong\n2. Gong\n3. Neither?");
					String check = input.nextLine();
					choice = Integer.parseInt(check);
				}catch(NumberFormatException e){
					System.out.println("That is not a valid input.");
					continue;
				}
				if(choice < 1 || choice > 3){
					System.out.println("That is not a menu choice.");
					continue;
				}
				break;
			}
			switch(choice){
			case 1:
				System.out.println(name + " made a pong.");
				Tile[] add = {thrown, thrown, thrown};
				show.add(add);
				MahjongGame.discarded.remove(thrown);
				hand.remove(thrown);
				hand.remove(thrown);
				break;
			case 2:
				System.out.println(name + " made a gong.");
				Tile[] add2 = {thrown, thrown, thrown, thrown};
				show.add(add2);
				for(int count = 0; count < 3; count++)
					hand.remove(thrown);
				MahjongGame.discarded.remove(thrown);
				for(int lcv = 0; lcv < 3; lcv++)
					hand.remove(thrown);
				Tile get = MahjongGame.wall.remove(MahjongGame.wall.size() - 1);
				hand.add(get);
				// Check for flower
				while(true){
					if (get.isFlower()) {
						Tile[] a = { get };
						show.add(a);
						rem(get);
						get = MahjongGame.wall.remove(MahjongGame.wall.size() - 1);
						add(get);
						continue;
					}
					break;
				}
				if (checkForWin(hand)) {
					won = true;
					return new Tile(-1, 0);
				}
				break;
			case 3:
				return new Tile(0, 0);
			}
		}else{
			int choice;
			while(true){
				try{
					System.out.println("Do you want to Pong?\n1. Yes\n2. No");
					String check = input.nextLine();
					choice = Integer.parseInt(check);
				}catch(NumberFormatException e){
					System.out.println("That is not a valid input.");
					continue;
				}
				if(choice < 1 || choice > 2){
					System.out.println("That is not a menu choice.");
					continue;
				}
				break;
			}
			switch(choice){
			case 1:
				System.out.println(name + " made a pong.");
				Tile[] add = {thrown, thrown, thrown};
				show.add(add);
				System.out.println(hand.remove(thrown));
				hand.remove(thrown);
				MahjongGame.discarded.remove(thrown);
				break;
			case 2:
				return new Tile(0, 0);
			}
		}
		return discard(west, north, east, south);
	}
}
