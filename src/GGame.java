import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

@SuppressWarnings("restriction")
public class GGame extends Application {
	static Game game;
	Label winner = new Label();
	Label warning = new Label();
	VBox fenetre = new VBox();
	VBox titre = new VBox();
	static Button[] b = new Button[9];
	Scene Jeu;
	Scene setup;
	boolean isAI;
	Stage stageGlobal;
	int diff;
	static boolean X;

	@Override
	public void start(Stage stage) {
		stage.setTitle("XO");
		stage.setHeight(615);
		stage.setWidth(422);
		stage.show();

		warning.setFont(new Font("Arial", 30));
		winner.setFont(new Font("Arial", 40));
		HBox affichage = new HBox();
		HBox affbas = new HBox();
		affichage.getChildren().add(winner);
		affbas.getChildren().add(warning);

		for (int k = 0; k < 9; k++) {
			b[k] = b(k);
		}
		
		diff = 9;
		X = true;
		Button J1 = bTitre();
		J1.setFont(new Font("Helvetica", 30));
		J1.setText("Un Joueur");
		J1.setOnAction(value -> {
			game = new AIGame(X, diff, false);
			isAI = true;
			stage.setScene(Jeu);
		});
		Button J2 = bTitre();
		J2.setFont(new Font("Helvetica", 30));
		J2.setText("Deux Joueurs");
		J2.setOnAction(value -> {
			game = new Game(X);
			isAI = false;
			stage.setScene(Jeu);
		});
		Label esp = new Label("              Bienvenue !");
		esp.setFont(new Font("Arial", 30));
		Button facile = new Button("Facile");
		facile.setFont(new Font("Helvetica", 30));
		facile.setMaxSize(200, 120);
		facile.setMinSize(200, 120);
		facile.setOnAction(value -> {diff = 5;});
		Button impo = new Button("Impossible");
		impo.setFont(new Font("Helvetica", 30));
		impo.setMaxSize(200, 120);
		impo.setMinSize(200, 120);
		impo.setOnAction(value -> {diff = 9;});
		HBox choixDiff = new HBox();
		choixDiff.getChildren().add(facile);
		choixDiff.getChildren().add(impo);
		Label tour = new Label("Qui Commence ?");
		tour.setFont(new Font("Arial", 30));
		Button bX = new Button("X");
		bX.setFont(new Font("Helvetica", 55));
		bX.setMaxSize(200, 120);
		bX.setMinSize(200, 120);
		bX.setOnAction(value -> {X = true;});
		Button bO = new Button("O");
		bO.setFont(new Font("Helvetica", 55));
		bO.setMaxSize(200, 120);
		bO.setMinSize(200, 120);
		bO.setOnAction(value -> {X = false;});
		HBox choiX = new HBox();
		choiX.getChildren().add(bX);
		choiX.getChildren().add(bO);
		titre.getChildren().add(esp);
		titre.getChildren().add(J1);
		titre.getChildren().add(J2);
		titre.getChildren().add(choixDiff);
		titre.getChildren().add(tour);
		titre.getChildren().add(choiX);
		setup = new Scene(titre);
		stage.setScene(setup);

		HBox ligne1 = new HBox();
		ligne1.getChildren().add(b[0]);
		ligne1.getChildren().add(b[1]);
		ligne1.getChildren().add(b[2]);

		HBox ligne2 = new HBox();
		ligne2.getChildren().add(b[3]);
		ligne2.getChildren().add(b[4]);
		ligne2.getChildren().add(b[5]);

		HBox ligne3 = new HBox();
		ligne3.getChildren().add(b[6]);
		ligne3.getChildren().add(b[7]);
		ligne3.getChildren().add(b[8]);

		/*
		 * HBox ligne4 = new HBox(); ligne4.getChildren().add(b('0'));
		 * ligne4.getChildren().add(b('C')); ligne4.getChildren().add(b('/'));
		 * 
		 * HBox ligne5 = new HBox(); ligne5.getChildren().add(b('('));
		 * ligne5.getChildren().add(b('$')); ligne5.getChildren().add(b('='));
		 */

		fenetre.getChildren().add(affichage);
		fenetre.getChildren().add(ligne1);
		fenetre.getChildren().add(ligne2);
		fenetre.getChildren().add(ligne3);
		fenetre.getChildren().add(affbas);
		// fenetre.getChildren().add(ligne5);

		Jeu = new Scene(fenetre);
		stageGlobal = stage;
		/*
		 * scene.setOnKeyTyped(e -> { try { handlekey(e); } catch (PlayException e1) {
		 * // TODO Auto-generated catch block e1.printStackTrace(); } });
		 */
		//stage.setScene(Jeu);
	}

	Button b(int k) {
		Button bouton = new Button(" ");
		bouton.setMinSize(133, 133);
		bouton.setMaxSize(133, 133);
		bouton.setText("");
		bouton.setOnAction(value -> {
			try {
				if (!GGame.game.over()) {
					GGame.game.play(k);
				} else {
					reset();
				}
				refreshGame();
			} catch (PlayException e) {
				// winner.setFont(new Font("Arial", 30));
				this.warning.setText(e.toString());
			}
		});
		bouton.setFont(new Font("Helvetica", 55));
		return (bouton);
	}

	public static void main(String[] args) {
		launch(args);
	}

	void refreshGame() {
		for (int i = 0; i < 9; i++) {
			int but = GGame.game.grid[i];
			switch (but) {
			case 0:
				GGame.b[i].setText("");
				break;
			case 1:
				GGame.b[i].setText("X");
				break;
			case -1:
				GGame.b[i].setText("O");
				break;
			}
		}
		String display = "";
		if (X) {
			switch (GGame.game.state()) {
			case -1:
				display = "Vous avez perdu.";
				break;
			case 0:
				display = "Match nul.";
				break;
			case 1:
				display = "Vous avez gagné !";
				break;
			}
		} else {
			switch (GGame.game.state()) {
			case 1:
				display = "Vous avez perdu.";
				break;
			case 0:
				display = "Match nul.";
				break;
			case -1:
				display = "Vous avez gagné !";
				break;
			}
		}
		warning.setText("");
		this.winner.setText(display);
	}

	void reset() {
		GGame.game.reset();
		refreshGame();
		stageGlobal.setScene(setup);;
	}

	Button bTitre() {
		Button bouton = new Button(" ");
		bouton.setMinSize(400, 120);
		bouton.setMaxSize(400, 120);
		return bouton;
	}

}