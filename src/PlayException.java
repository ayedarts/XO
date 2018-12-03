@SuppressWarnings("serial")
public class PlayException extends Exception{
	int cas;
	public PlayException(int k) {
		this.cas = k;
		System.out.println("La case " + k + " est déjà occupée.");
	}
	public String toString() {
		return("La case " + (cas + 1) + " est déjà occupée.");
	}
}