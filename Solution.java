public class Solution {
	public static boolean canWin(int leap, int[] game) {
		// Return true if you can win the game; otherwise, return false.*
		int n = game.length;
		boolean[] vu = new boolean[n + leap];
		return explore(0, game, vu, leap, n);
	}
	
	static boolean explore(int pos, int[] game, boolean[] vu, int leap, int n) {
		vu[pos] = true;
		if (pos >= n - 1) {
			return true;
		} else if (game[pos] != 0) {
			return false;
		} else {
			boolean dev = false;
			boolean der = false;
			boolean saut = false;
			if (!vu[pos + 1]) {
				dev = explore(pos + 1, game, vu, leap, n);
			}
			if (dev) {
				return true;
			}
			if (!vu[pos - 1]) {
				der = explore(pos - 1, game, vu, leap, n);
			}
			if (der) {
				return true;
			}
			if (!vu[pos + leap]) {
				saut = explore(pos + leap, game, vu, leap, n);
			}
			return saut;
		}
	}
}

public static boolean canWin(int leap, int[] game) {
    // Return true if you can win the game; otherwise, return false.*
    int pos = 0;
    int n = game.length;
    int surPlace = 0;
    int lastLeap = 0;
    Stack<Integer> sauts = new Stack<Integer>();
    while (pos + leap < n) {
        //System.out.println(pos);
        if (game[pos + leap] == 0) {
            //System.out.println("a");
            pos += leap;
            sauts.push(pos);
        }
        else {
            //System.out.println(pos);
            pos ++;
            if (pos == surPlace){
                if (sauts.isEmpty()){
                    return false;
                }
                else{
                    lastLeap = sauts.pop();
                    game[lastLeap] = 1;
                    pos = lastLeap - leap;
                }
            }
            //System.out.println(pos);
            //System.out.println(Arrays.toString(field));
            if (game[pos] != 0){
                //System.out.println(sauts);
                if (pos - 2 >= 0 && game[pos - 2] == 0){
                    //System.out.println("b");
                    //System.out.println(pos);
                    pos = pos - 2;
                    surPlace = pos + 1;
                }
                else{
                    if (sauts.isEmpty()){
                    return false;
                }
                else{
                    lastLeap = sauts.pop();
                    game[lastLeap] = 1;
                    pos = lastLeap - leap;
                }
                }
            }
        }
    }
    return true;
}