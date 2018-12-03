public class Game {
	int[] grid;
	boolean turn;
	
	Game(boolean turn){
		this.grid = new int[9];
		this.turn = turn;
		}
	
	void playX(int k) throws PlayException {
		if (this.grid[k] == 0) {
			this.grid[k] = 1;
			this.turn = false;
		}
		else {
			throw new PlayException(k);
		}
	}
	
	void playO(int k) throws PlayException {
		if (this.grid[k] == 0) {
			this.grid[k] = -1;
			this.turn = true;
		}
		else {
			throw new PlayException(k);
		}
	}
	
	void play(int k) throws PlayException {
		if (this.turn) {
			playX(k);
		}
		else {
			playO(k);
		}
	}
	
	int wins() {
		if (this.Xwins()) {return 1;}
		else if (this.Owins()) {return -1;}
		else return 0;
	}
	
	boolean Xwins() {
		for (int k = 0; k<9; k+=3) {
			if (this.grid[k] + this.grid[k+1] + this.grid[k+2] == 3) {return true;}
		}
		for (int k = 0; k<3; k++) {
			if (this.grid[k] + this.grid[k+3] + this.grid[k+6] == 3) {return true;}
		}
		if (this.grid[0] + this.grid[4] + this.grid[8] == 3 || this.grid[2] + this.grid[4] + this.grid[6] == 3) {
			return true;
		}
		return false;
	}
	
	boolean Owins() {
		for (int k = 0; k<9; k+=3) {
			if (this.grid[k] + this.grid[k+1] + this.grid[k+2] == -3) {return true;}
		}
		for (int k = 0; k<3; k++) {
			if (this.grid[k] + this.grid[k+3] + this.grid[k+6] == -3) {return true;}
		}
		if (this.grid[0] + this.grid[4] + this.grid[8] == -3 || this.grid[2] + this.grid[4] + this.grid[6] == -3) {
			return true;
		}
		return false;
	}
	
	boolean over() {
		if (this.wins() != 0) { return true;}
		for (int i = 0; i<9; i++) {
			if (this.grid[i] == 0) { return false;}
		}
		return (true);
	}
	
	int state() {
		if (this.over()) {
			return this.wins();
		}
		else {
			return 2;
		}
	}
	
	void reset() {
		this.grid = new int[9];
		this.turn = GGame.X;
	}
}