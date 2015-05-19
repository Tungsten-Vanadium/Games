/**
 * Vincent's Card Game Collection - COMPlayer.java
 */
/**
 * Vincent Tong Dec 8, 2012; Class for computer player
 */
import java.util.*;
public class MahjongCOMPlayer extends MahjongPlayer {
	/**Helps the COMPlayer decide what to do randomly*/
	Random decide = new Random();
	// Will rename as dumb computer player
	public MahjongCOMPlayer(String n){
		name = n;
	}
	/**Computer turn (same as human except no interaction with the user)*/
	protected void mahjongTurn(Tile get, Tile thrown, MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south) {
		if(checkForWin(thrown)){
			won = true;
			return;
		}
		// Checks for a sequence
		ArrayList<Tile[]> check = checkForSequence(thrown);
		if(check.size() != 0){
			// Randomly picks a sequence if more than one
			int index = decide.nextInt(check.size());
			Tile[] add = check.get(index);
			System.out.println(name + " made a sequence.");
			for(Tile lcv : add)
				if(hand.contains(lcv))
					rem(lcv);
			show.add(add);
			discard(west, north, east, south);
			// Ends the turn
			return;
		}
		add(get);
		// Check for flower
		while(true){
			if(get.isFlower()){
				Tile[] a = {get};
				show.add(a);
				rem(get);
				get = MahjongGame.wall.remove(MahjongGame.wall.size() - 1);
				add(get);
				continue;
			}
			break;
		}
		if(checkForWin(hand)){
			won = true;
			return;
		}
		hand = diviForSort();
	}
	/**Randomly discards a Tile*/
	protected Tile discard(MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south) {
		return hand.remove(decide.nextInt(hand.size()));
	}
	/**Interrupts the turn sequence for a pong or gong; CHECK FOR REMOVING TILES FROM HAND*/
	protected Tile interrupt (Tile thrown, MahjongPlayer west, MahjongPlayer north, MahjongPlayer east, MahjongPlayer south){
		if(checkForThreeKind(thrown) == 4){
			System.out.println(name + " made a gong.");
			Tile[] add = {thrown, thrown, thrown, thrown};
			show.add(add);
			for(int lcv = 0; lcv < 3; lcv++)
				hand.remove(thrown);
			MahjongGame.discarded.remove(thrown);
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
		}else{
			System.out.println(name + " made a pong.");
			Tile[] add = {thrown, thrown, thrown};
			show.add(add);
			hand.remove(thrown);
			hand.remove(thrown);
			MahjongGame.discarded.remove(thrown);
		}
		return discard(west, north, east, south);
	}
}
