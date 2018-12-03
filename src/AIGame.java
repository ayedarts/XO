import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AIGame extends Game {

	int difficulty;
	int[] score;
	boolean fake;

	AIGame(boolean turn, int diff, boolean fake) {
		super(turn);
		this.difficulty = diff;
		this.score = new int[9];
		this.fake = fake;
	}

	AIGame(int[] grid, int[] score, boolean turn, int diff, boolean fake) {
		super(turn);
		this.difficulty = diff;
		this.score = new int[9];
		this.fake = fake;
		this.grid = grid;
		this.score = score;
	}

	void playX(int k) throws PlayException {
		if (this.grid[k] == 0) {
			if (!fake) {
				this.grid[k] = 1;
				this.turn = false;
				this.score[k] = -2;
			} else {
				this.grid[k] = 1;
				this.score[k] = -2;
				this.turn = false;
			}
		} else {
			throw new PlayException(k);
		}
	}

	void playO(int k) throws PlayException {
		if (this.grid[k] == 0) {
			if (!fake) {
				this.grid[k] = -1;
				this.turn = true;
				this.score[k] = -2;
			} else {
				this.grid[k] = -1;
				this.turn = true;
				this.score[k] = -2;
			}
		} else {
			throw new PlayException(k);
		}
	}

	void playfx(int k) throws PlayException {
		if (this.turn) {
			this.playX(k);
		} else {
			this.playO(k);
		}
	}

	void play(int k) throws PlayException {
		this.playfx(k);
		if (!this.fake && !this.over()) {
			this.playfx(this.AI());
		}
	}

	void scoringX() throws PlayException {
		for (int k = 0; k < 9; k++) {
			if (grid[k] != 0) {
				score[k] = -2;
			}
		}
		if (this.difficulty > 0) {
			for (int k = 0; k < 9; k++) {
				if (grid[k] == 0) {
					AIGame hypo = new AIGame(this.grid.clone(), this.score.clone(), this.turn, this.difficulty - 1,
							true);
					hypo.playfx(k);
					switch (hypo.state()) {
					case 1:
						this.score[k] = 1;
						break;
					case 2:
						ArrayList<Integer> scores = new ArrayList<Integer>();
						for (int l = 0; l < 9; l++) {
							if (hypo.grid[l] == 0) {
								AIGame hypohypo = new AIGame(hypo.grid.clone(), hypo.score.clone(), hypo.turn,
										hypo.difficulty - 1, true);
								// int[] mem = hypo.score.clone();
								hypohypo.playfx(l);
								switch (hypohypo.state()) {
								case 1:
									scores.add(1);
									break;
								case 2:
									hypohypo.scoringX();
									scores.add(Arrays.stream(hypohypo.score).max().getAsInt());
									break;
								case -1:
									scores.add(-1);
									break;
								case 0:
									scores.add(0);
									break;
								}
								// hypo.revPlay(l);
								// hypo.score = mem;
							}
						}
						//System.out.println(scores);
						this.score[k] = Collections.min(scores);
						break;
					case -1:
						this.score[k] = -1;
						break;
					case 0:
						this.score[k] = 0;
						break;
					}
				}
			}
		}
	}

	void scoringO() throws PlayException {
		for (int k = 0; k < 9; k++) {
			if (grid[k] != 0) {
				score[k] = -2;
			}
		}
		if (this.difficulty > 0) {
			for (int k = 0; k < 9; k++) {
				if (grid[k] == 0) {
					AIGame hypo = new AIGame(this.grid.clone(), this.score.clone(), this.turn, this.difficulty - 1,
							true);
					hypo.playfx(k);
					switch (hypo.state()) {
					case 1:
						this.score[k] = -1;
						break;
					case 2:
						ArrayList<Integer> scores = new ArrayList<Integer>();
						for (int l = 0; l < 9; l++) {
							if (hypo.grid[l] == 0) {
								AIGame hypohypo = new AIGame(hypo.grid.clone(), hypo.score.clone(), hypo.turn,
										hypo.difficulty - 1, true);
								// int[] mem = hypo.score.clone();
								hypohypo.playfx(l);
								switch (hypohypo.state()) {
								case 1:
									scores.add(-1);
									break;
								case 2:
									hypohypo.scoringO();
									scores.add(Arrays.stream(hypohypo.score).max().getAsInt());
									break;
								case -1:
									scores.add(1);
									break;
								case 0:
									scores.add(0);
									break;
								}
								// hypo.revPlay(l);
								// hypo.score = mem;
							}
						}
						this.score[k] = Collections.min(scores);
						break;
					case -1:
						this.score[k] = 1;
						break;
					case 0:
						this.score[k] = 0;
						break;
					}
				}
			}
		}
	}

	void revPlay(int k) {
		this.grid[k] = 0;
		this.turn = !this.turn;
	}

	int AI() throws PlayException {
		if (turn) {
			scoringX();
		} else {
			scoringO();
		}
		int max = score[0];
		int ind = 0;
		for (int k = 0; k < 9; k++) {
			if (score[k] > max) {
				ind = k;
				max = score[k];
			}
		}
		return (ind);
	}
	
	void reset() {
		super.reset();
		this.score = new int[9];
	}
}