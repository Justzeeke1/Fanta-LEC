package javafxapp.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxapp.model.TableSquadra;
import webapp.control.AllenatoreController;
import webapp.control.GiocatoreController;
import webapp.control.GiornataController;
import webapp.control.SquadraController;
import webapp.model.Allenatore;
import webapp.model.Giocatore;
import webapp.model.Squadra;

public class MyTeamControllerFx implements Initializable {

	private static MyTeamControllerFx INSTANCE = null;

	public static MyTeamControllerFx getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MyTeamControllerFx();
		}
		return INSTANCE;
	}

	LoginControllerFx loginControllerFx = LoginControllerFx.getInstance();
	AllenatoreController allenatoreController = AllenatoreController.getInstance();
	SquadraController squadraController = SquadraController.getInstance();
	GiornataController giornataController = GiornataController.getInstance();
	GiocatoreController giocatoreController = GiocatoreController.getInstance();

	Parent root;
	Stage primaryStage;
	Allenatore allenatore;
	Squadra squadra;

	@FXML
	Label labelErrore;
	@FXML
	Label labelErroreRiserve;
	@FXML
	Label labelModificaSquadraT;
	@FXML
	Label labelModificaSquadra;
	@FXML
	Label labelRiserve;
	@FXML
	Label labelTitolari;
	@FXML
	TableView<TableSquadra> tableViewSquadra;
	@FXML
	private TableColumn<TableSquadra, String> columnId;
	@FXML
	private TableColumn<TableSquadra, String> columnOverall;
	@FXML
	private TableColumn<TableSquadra, String> columnNickname;
	@FXML
	private TableColumn<TableSquadra, String> columnRuolo;
	@FXML
	TableView<TableSquadra> tableViewRiserva;
	@FXML
	private TableColumn<TableSquadra, String> columnIdR;
	@FXML
	private TableColumn<TableSquadra, String> columnOverallR;
	@FXML
	private TableColumn<TableSquadra, String> columnNicknameR;
	@FXML
	private TableColumn<TableSquadra, String> columnRuoloR;
	@FXML
	Button buttonAggiorna;
	public static URL url;
	public static ResourceBundle resBundle;

	@Override
	public void initialize(URL location, ResourceBundle rs) {
		url = location;
		resBundle = rs;
		allenatore = Main.getAllenatore();
		squadra = Main.getSquadra();
		List<Giocatore> giocatoriTotali = new ArrayList<Giocatore>();
		List<Giocatore> giocatoriTitolari = new ArrayList<Giocatore>();
		List<Giocatore> giocatoriRiserva = new ArrayList<Giocatore>();

		giocatoriTotali = initSquadra();
		initGiocatori(giocatoriTotali, giocatoriTitolari, giocatoriRiserva);
		giocatoriTitolari = giocatoreController.orderTeamView(giocatoriTitolari);
		setTables(giocatoriTitolari, giocatoriRiserva);
		initErrors(giocatoriTitolari, giocatoriRiserva);
	}

	@FXML
	public void aggiorna() {
		initialize(url, resBundle);
	}

	private void initErrors(List<Giocatore> giocatoriTitolari, List<Giocatore> giocatoriRiserva) {
		if (giocatoriTitolari.isEmpty()) {
			labelErrore.setText(
					"NON HAI GIOCATORI!\nClicca il tasto \"MERCATO\" in alto per comprare dei giocatori e iniziare la tua carriera.");
			setVisibility(false);
		} else {
			setVisibility(true);
		}
		if (giocatoriRiserva.isEmpty()) {
			labelErroreRiserve.setVisible(true);
			setVisibilityRiserva(false);
		} else {
			setVisibilityRiserva(true);
		}
	}

	private void setVisibilityRiserva(Boolean isVisible) {
		labelRiserve.setVisible(isVisible);
		labelErroreRiserve.setVisible(!isVisible);
		tableViewRiserva.setVisible(isVisible);
	}

	private void initGiocatori(List<Giocatore> giocatoriTotali, List<Giocatore> giocatoriTitolari,
			List<Giocatore> giocatoriRiserva) {
		for (Giocatore giocatore : giocatoriTotali) {
			if (!giocatore.getRiserva()) {
				giocatoriTitolari.add(giocatore);
			} else {
				giocatoriRiserva.add(giocatore);
			}
		}
	}

	private List<Giocatore> initSquadra() {
		List<Giocatore> giocatoriTotali = new ArrayList<Giocatore>();
		if (squadra != null) {
			giocatoriTotali = giocatoreController.findAllMyTeam(squadra);
		}
		return giocatoriTotali;
	}

	private void setTables(List<Giocatore> giocatoriTitolari, List<Giocatore> giocatoriRiserva) {
		List<TableSquadra> tableSquadra = mapSquadra(giocatoriTitolari);
		List<TableSquadra> tableRiserva = mapSquadra(giocatoriRiserva);
		columnId.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("id"));
		columnOverall.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("overall"));
		columnNickname.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("nickname"));
		columnRuolo.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("ruolo"));
		columnIdR.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("id"));
		columnOverallR.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("overall"));
		columnNicknameR.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("nickname"));
		columnRuoloR.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("ruolo"));
		ObservableList<TableSquadra> dataSquadra = FXCollections.observableList(tableSquadra);
		tableViewSquadra.setItems(dataSquadra);
		ObservableList<TableSquadra> dataRiserva = FXCollections.observableList(tableRiserva);
		tableViewRiserva.setItems(dataRiserva);
	}

	private void setVisibility(Boolean isVisible) {
		labelErroreRiserve.setVisible(isVisible);
		labelModificaSquadra.setVisible(isVisible);
		labelModificaSquadraT.setVisible(isVisible);
		labelRiserve.setVisible(isVisible);
		tableViewSquadra.setVisible(isVisible);
		tableViewRiserva.setVisible(isVisible);
		labelTitolari.setVisible(isVisible);
		labelErrore.setVisible(!isVisible);
	}

	private List<TableSquadra> mapSquadra(List<Giocatore> giocatori) {
		List<TableSquadra> tableSquadra = new ArrayList<TableSquadra>();
		for (Giocatore giocatore : giocatori) {
			tableSquadra.add(new TableSquadra(giocatore.getId() + "", giocatore.getOverall() + "",
					giocatore.getNickname(), giocatore.getRuolo(), giocatore.getPrezzo() + ""));
		}
		return tableSquadra;
	}

	@FXML
	public void clickItemVendi1(KeyEvent event) {
		String idGiocatoreString = tableViewSquadra.getSelectionModel().getSelectedItem().getId();
		Long idGiocatore = Long.parseLong(idGiocatoreString);
		Giocatore giocatore = giocatoreController.findById(idGiocatore);
		giocatoreController.vendiGiocatore(giocatore, squadra.getId(), allenatore);
		initialize(url, resBundle);
	}
	
	@FXML
	public void clickItemVendi2(KeyEvent event) {
		String idGiocatoreString = tableViewRiserva.getSelectionModel().getSelectedItem().getId();
		Long idGiocatore = Long.parseLong(idGiocatoreString);
		Giocatore giocatore = giocatoreController.findById(idGiocatore);
		giocatoreController.vendiGiocatore(giocatore, squadra.getId(), allenatore);
		initialize(url, resBundle);
	}

	@FXML
	public void clickItem2(MouseEvent event) {
		String idGiocatoreString = tableViewRiserva.getSelectionModel().getSelectedItem().getId();
		Long idGiocatore = Long.parseLong(idGiocatoreString);
		Giocatore giocatore = giocatoreController.findById(idGiocatore);
		if (event.getClickCount() == 2) {
			giocatoreController.cambia(giocatore);
		}
		initialize(url, resBundle);
	}

}