package javafxapp.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainControllerFx implements Initializable {

	private static MainControllerFx INSTANCE = null;
	public static HomeControllerFx homeControllerFx = HomeControllerFx.getInstance();
	public static MercatoControllerFx mercatoControllerFx = MercatoControllerFx.getInstance();
	public static MyTeamControllerFx myTeamControllerFx = MyTeamControllerFx.getInstance();

	Parent root;
	Stage primaryStage;
	@FXML
	Tab home;
	@FXML
	Tab myteam;
	@FXML
	Tab mercato;
	Boolean mustInitHome = false;
	Boolean mustInitMyTeam = false;
	Boolean mustInitMercato = false;

	@FXML
	public void logout() {
		try {
			primaryStage = Main.getStage();
			root = FXMLLoader.load(getClass().getResource("/javafxapp/control/index.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/javafxapp/view/application.css").toExternalForm());
			primaryStage.setTitle("FantaLEC");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle rs) {
		root = Main.getRoot();
		primaryStage = Main.getStage();
		try {
			AnchorPane anchHome = FXMLLoader.load(getClass().getResource("home.fxml"));
			home.setContent(anchHome);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			AnchorPane anchMyTeam = FXMLLoader.load(getClass().getResource("myteam.fxml"));
			myteam.setContent(anchMyTeam);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			AnchorPane anchMercato = FXMLLoader.load(getClass().getResource("mercato.fxml"));
			mercato.setContent(anchMercato);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static MainControllerFx getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MainControllerFx();
		}
		return INSTANCE;
	}

}