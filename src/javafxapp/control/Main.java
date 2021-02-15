package javafxapp.control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import webapp.model.Allenatore;
import webapp.model.Squadra;

public class Main extends Application {

	private static Allenatore allenatore;
	private static Squadra squadra;
	private static Parent root;
	private static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = FXMLLoader.load(getClass().getResource("/javafxapp/control/index.fxml"));
			stage = primaryStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/javafxapp/view/application.css").toExternalForm());
			primaryStage.setTitle("FantaLEC");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Allenatore getAllenatore() {
		return allenatore;
	}

	public static void setAllenatore(Allenatore allenatore) {
		Main.allenatore = allenatore;
	}

	public static Squadra getSquadra() {
		return squadra;
	}

	public static void setSquadra(Squadra squadra) {
		Main.squadra = squadra;
	}

	public static Parent getRoot() {
		return root;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void invalidate() {
		Main.allenatore = null;
		Main.squadra = null;
	}


}