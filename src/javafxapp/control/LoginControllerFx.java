package javafxapp.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import webapp.control.AllenatoreController;
import webapp.control.SquadraController;
import webapp.model.Allenatore;
import webapp.model.Squadra;

public class LoginControllerFx implements Initializable {
	
	private static LoginControllerFx INSTANCE = null;

	public static LoginControllerFx getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LoginControllerFx();
		}
		return INSTANCE;
	}
	
	MainControllerFx mainControllerFx = MainControllerFx.getInstance();
	AllenatoreController allenatoreController = AllenatoreController.getInstance();
	SquadraController squadraController = SquadraController.getInstance();
	Boolean isLogin;
	Parent root;
	Stage primaryStage;
	
	@FXML
	Label labelGioca;
	@FXML
	Label labelInserisciDati;
	@FXML
	Label labelContinua;
	@FXML
	Label o;
	@FXML
	Button buttonGioca;
	@FXML
	Button buttonContinua;
	@FXML
	TextField tfUsername;
	@FXML
	TextField tfPassword;
	@FXML
	Button buttonLogin;
	@FXML
	Button buttonRegistrati;
	@FXML
	Label labelBenvenuto;
	@FXML
	Label labelIndex1T;
	@FXML
	Label labelIndex1;
	@FXML
	Label labelIndex2T;
	@FXML
	Label labelIndex2;
	@FXML
	Label labelIndex3T;
	@FXML
	Label labelIndex3;
	@FXML
	Label labelIndex4T;
	@FXML
	Label labelIndex4;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		labelBenvenuto.setStyle("-fx-font-weight: bold;");
		labelContinua.setText("Oppure, \"Continua\" per effettuare il login.");
		labelGioca.setText("Clicca \"Gioca\" per iniziare una nuova partita.");
		labelIndex1T.setStyle("-fx-font-weight: bold;");
		labelIndex1.setText("La parte grafica è stata fatta interamente con librerie Bootstrap, mentre la parte logica con jsp, aggiungendo anche un database realizzato con MySQL.");
		labelIndex2T.setStyle("-fx-font-weight: bold;");
		labelIndex2.setText("Mi sono ispirato alla parte competitiva e professionistica del gioco \"League of Legends\", prenderne spunto è stato abbastanza facile, ed ho realizzato questo progetto perché fino ad ora una versione di \"Fanta Calcio\" per questo gioco non esisteva.");
    	labelIndex3T.setStyle("-fx-font-weight: bold;");
		labelIndex3.setText("\"LEC\" sta per League of Legends European Championship.");
		labelIndex4T.setStyle("-fx-font-weight: bold;");
		labelIndex4.setText("Questa Web App non è altro che un gioco stile Fanta Calcio dove i giocatori sono i professionisti della scena competitiva europea di League of Legends.\r\n"
				+ "Puoi comprare giocatori, sistemare la tua formazione e simulare le partite.");
		isLogin = null;
	}
	
	@FXML
	public void login(ActionEvent event) {
		String usernameString = tfUsername.getText();
		String passwordString = tfPassword.getText();
		Allenatore allenatore = allenatoreController.validate(usernameString, passwordString);
		if (allenatore != null) {
			Main.setAllenatore(allenatore);
			Squadra squadra = squadraController.checkSquadraPresente(allenatore);
			Main.setSquadra(squadra);
			goToHome();
		}
	}

	@FXML
	public void registrati(ActionEvent event) {
		String usernameString = tfUsername.getText();
		String passwordString = tfPassword.getText();
		Allenatore allenatore = allenatoreController.register(usernameString, passwordString);
		if (allenatore != null) {
			Main.setAllenatore(allenatore);
			Main.setSquadra(null);
			goToHome();
		}
	}
	
	public void goToHome() {
		root = Main.getRoot();
		primaryStage = Main.getStage();
		try {
			primaryStage = Main.getStage();
			root = FXMLLoader.load(getClass().getResource("/javafxapp/control/Container.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/javafxapp/view/home.css").toExternalForm());
			primaryStage.setTitle("FantaLEC");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	@FXML
	public void gioca(ActionEvent event) {
		resetForm();
		setLoginToken(false);
		setFormVisibility(true);
	}


	@FXML
	public void continua(ActionEvent event) {
		resetForm();
		setLoginToken(true);
		setFormVisibility(true);
	}
	
	private void resetForm() {
		tfUsername.clear();
		tfPassword.clear();
	}
	
	private void setLoginToken(Boolean token) {
		isLogin = token;
	}
	
	private void setFormVisibility(Boolean isVisible) {
		labelInserisciDati.setVisible(isVisible);
		tfUsername.setVisible(isVisible);
		tfPassword.setVisible(isVisible);
		if (isLogin) {
			buttonLogin.setVisible(isVisible);
			buttonRegistrati.setVisible(!isVisible);
		} else {
			buttonLogin.setVisible(!isVisible);
			buttonRegistrati.setVisible(isVisible);
		}
	}
}