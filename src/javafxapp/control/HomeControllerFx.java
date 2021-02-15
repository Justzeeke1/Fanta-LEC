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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafxapp.model.TableClassifica;
import javafxapp.model.TableSquadra;
import webapp.control.AllenatoreController;
import webapp.control.GiocatoreController;
import webapp.control.GiornataController;
import webapp.control.SquadraController;
import webapp.model.Allenatore;
import webapp.model.Giocatore;
import webapp.model.Squadra;

public class HomeControllerFx implements Initializable {
	
	private static HomeControllerFx INSTANCE = null;
	
	public static HomeControllerFx getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HomeControllerFx();
		}
		return INSTANCE;
	}

	MainControllerFx mainControllerFx = MainControllerFx.getInstance();
	LoginControllerFx loginControllerFx = LoginControllerFx.getInstance();
	AllenatoreController allenatoreController = AllenatoreController.getInstance();
	SquadraController squadraController = SquadraController.getInstance();
	GiornataController giornataController = GiornataController.getInstance();
	GiocatoreController giocatoreController = GiocatoreController.getInstance();

	Parent root;
	Stage primaryStage;
	Allenatore allenatore;
	public static Squadra squadra;
	public static Squadra squadraAvversaria;
	public static List<Squadra> classifica;
	List<String> scontri;
	Long idGiornata;

	@FXML
	Label labelErrore;
//	@FXML Label labelIstruzioni; CTRL+7 per mettere/rimuovere commento dopo aver selezionato 
//	@FXML Label labelInfo1;
//	@FXML Label labelInfo2;
//	@FXML Label labelInfo3;
	@FXML
	Label labelLaTuaSquadra;
	@FXML
	Label labelSquadraAvversaria;
	@FXML
	Label labelVS;
	@FXML
	Label labelClassifica;
	@FXML
	Button buttonGioca;
	@FXML
	Button buttonContinua;
	@FXML
	Label labelIstruzioniT;
	@FXML
	Label labelIstruzioni;
	@FXML
	TableView<TableSquadra> tableViewSquadra;
	@FXML
	private TableColumn<TableSquadra, String> columnOverall;
	@FXML
	private TableColumn<TableSquadra, String> columnNickname;
	@FXML
	private TableColumn<TableSquadra, String> columnRuolo;
	@FXML
	TableView<TableSquadra> tableViewSquadraAvversaria;
	@FXML
	private TableColumn<TableSquadra, String> columnOverallSA;
	@FXML
	private TableColumn<TableSquadra, String> columnNicknameSA;
	@FXML
	private TableColumn<TableSquadra, String> columnRuoloSA;
	@FXML
	TableView<TableClassifica> tableViewClassifica;
	@FXML
	private TableColumn<TableClassifica, String> columnPosizione;
	@FXML
	private TableColumn<TableClassifica, String> columnNomeSquadra;
	@FXML
	private TableColumn<TableClassifica, String> columnPunteggio;
	@FXML
	private TableColumn<TableClassifica, String> columnVittorie;
	@FXML
	private TableColumn<TableClassifica, String> columnSconfitte;
	@FXML
	private ImageView imgVittoria;
	@FXML
	private ImageView imgSconfitta;
	@FXML
	Button buttonAggiorna;
	public static URL url;
	public static ResourceBundle resBundle;

    @Override
	public void initialize(URL location, ResourceBundle rs) {
    	url = location;
    	resBundle = rs;
    	System.out.println(url);
    	System.out.println(resBundle);
		allenatore = Main.getAllenatore();
		squadra = Main.getSquadra();
		List<Giocatore> giocatoriTotali = new ArrayList<Giocatore>();
		List<Giocatore> giocatoriTitolari = new ArrayList<Giocatore>();
		List<Giocatore> giocatoriTotaliAvversario = new ArrayList<Giocatore>();
		List<Giocatore> giocatoriTitolariAvversario = new ArrayList<Giocatore>();
		labelIstruzioni.setText("- Compra i giocatori all'interno della pagina \"Mercato\".\r\n"
				+ "\r\n"
				+ "- Modifica i giocatori all'interno della formazione nella pagina \"My Team\".\r\n"
				+ "\r\n"
				+ "- Clicca il pulsante gioca per iniziare la partita.\r\n"
				+ "\r\n"
				+ "GOOD LUCK :)");

		classifica = squadraController.findAllOrderByPunteggio();

		initGiornate();
		giocatoriTotali = initSquadre();
		initGiocatori(giocatoriTotali, giocatoriTitolari, giocatoriTotaliAvversario, giocatoriTitolariAvversario);
		giocatoriTitolari = giocatoreController.orderTeamView(giocatoriTitolari);
		giocatoriTitolariAvversario = giocatoreController.orderTeamView(giocatoriTitolariAvversario);
		setTables(giocatoriTitolari, giocatoriTitolariAvversario);
		initErrors(giocatoriTitolari, giocatoriTitolariAvversario);
	}
    
    @FXML
    public void aggiorna() {
    	initialize(url, resBundle);
    }
    
	@FXML
	public void gioca() {
		squadraController.gioca(scontri, idGiornata);
		Squadra oldSquadra = squadra;
		Squadra newSquadra = squadraController.findById(oldSquadra.getId());
		if (oldSquadra.getVittorie() != newSquadra.getVittorie()) {
			imgVittoria.setVisible(true);
			imgSconfitta.setVisible(false);
		} else {
			imgVittoria.setVisible(false);
			imgSconfitta.setVisible(true);
		}
		Main.setSquadra(newSquadra);
		buttonContinua.setVisible(true);
	}

	@FXML
	public void continua() {
		initialize(url, resBundle);
		buttonContinua.setVisible(false);
		imgVittoria.setVisible(false);
		imgSconfitta.setVisible(false);
	}

	private void initErrors(List<Giocatore> giocatoriTitolari, List<Giocatore> giocatoriTitolariAvversario) {
		if (giocatoriTitolari.isEmpty()) {
			labelErrore.setText(
					"NON HAI GIOCATORI!\nClicca il tasto \"MERCATO\" in alto per comprare dei giocatori e iniziare la tua carriera.");
			setVisibility(false);
		} else if (giocatoriTitolari.size() < 5) {
			labelErrore.setText("La tua squadra non è al completo. Devi avere 5 giocatori per avviare lo scontro!");
			labelErrore.setVisible(true);
		} else if (giocatoriTitolariAvversario.size() < 5) {
			labelErrore.setText("Attendi che l'avversario sistemi la formazione per giocare!");
			labelErrore.setVisible(true);
		} else {
			setVisibility(true);
		}
	}

	private List<Giocatore> initSquadre() {
		List<Giocatore> giocatoriTotali = new ArrayList<Giocatore>();
		if (squadra != null) {
			Long idMiaSquadra = squadra.getId();
			squadraAvversaria = squadraController.getSquadraAvversaria(scontri, idMiaSquadra);
			giocatoriTotali = giocatoreController.findAllMyTeam(squadra);
		}
		return giocatoriTotali;
	}

	private void initGiocatori(List<Giocatore> giocatoriTotali, List<Giocatore> giocatoriTitolari,
			List<Giocatore> giocatoriTotaliAvversario, List<Giocatore> giocatoriTitolariAvversario) {
		for (Giocatore giocatore : giocatoriTotali) {
			if (!giocatore.getRiserva()) {
				giocatoriTitolari.add(giocatore);
			}
		}
		if (squadraAvversaria != null) {
			giocatoriTotaliAvversario = giocatoreController.findAllMyTeam(squadraAvversaria);
		}
		for (Giocatore giocatore : giocatoriTotaliAvversario) {
			if (!giocatore.getRiserva()) {
				giocatoriTitolariAvversario.add(giocatore);
			}
		}
	}

	private void initGiornate() {
		if (classifica.size() == 4) {
			giornataController.createScontri();
		}
		idGiornata = giornataController.getGiornataDaGiocare();
		scontri = giornataController.getScontriByIdGiornataDaGiocare(idGiornata);
	}

	private void setTables(List<Giocatore> giocatoriTitolari, List<Giocatore> giocatoriTitolariAvversario) {
		List<TableSquadra> tableSquadra = mapSquadra(giocatoriTitolari);
		List<TableSquadra> tableSquadraAvversaria = mapSquadra(giocatoriTitolariAvversario);
		columnOverall.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("overall"));
		columnNickname.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("nickname"));
		columnRuolo.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("ruolo"));
		columnOverallSA.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("overall"));
		columnNicknameSA.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("nickname"));
		columnRuoloSA.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("ruolo"));
		ObservableList<TableSquadra> dataSquadra = FXCollections.observableList(tableSquadra);
		tableViewSquadra.setItems(dataSquadra);
		ObservableList<TableSquadra> dataSquadraAvversaria = FXCollections.observableList(tableSquadraAvversaria);
		tableViewSquadraAvversaria.setItems(dataSquadraAvversaria);
		List<TableClassifica> tableClassifica = mapClassifica();
		columnPosizione.setCellValueFactory(new PropertyValueFactory<TableClassifica, String>("posizione"));
		columnNomeSquadra.setCellValueFactory(new PropertyValueFactory<TableClassifica, String>("nomeSquadra"));
		columnPunteggio.setCellValueFactory(new PropertyValueFactory<TableClassifica, String>("punteggio"));
		columnVittorie.setCellValueFactory(new PropertyValueFactory<TableClassifica, String>("vittorie"));
		columnSconfitte.setCellValueFactory(new PropertyValueFactory<TableClassifica, String>("sconfitte"));
		ObservableList<TableClassifica> dataClassfica = FXCollections.observableList(tableClassifica);
		tableViewClassifica.setItems(dataClassfica);
	}

	private void setVisibility(Boolean isVisible) {
		labelLaTuaSquadra.setVisible(isVisible);
		labelSquadraAvversaria.setVisible(isVisible);
		buttonGioca.setVisible(isVisible);
		labelVS.setVisible(isVisible);
		labelClassifica.setVisible(isVisible);
		tableViewSquadra.setVisible(isVisible);
		tableViewSquadraAvversaria.setVisible(isVisible);
		tableViewClassifica.setVisible(isVisible);
		labelErrore.setVisible(!isVisible);
		labelIstruzioniT.setVisible(isVisible);
		labelIstruzioni.setVisible(isVisible);

	}

	private List<TableSquadra> mapSquadra(List<Giocatore> giocatori) {
		List<TableSquadra> tableSquadra = new ArrayList<TableSquadra>();
		for (Giocatore giocatore : giocatori) {
			tableSquadra
					.add(new TableSquadra(giocatore.getOverall() + "", giocatore.getNickname(), giocatore.getRuolo()));
		}
		return tableSquadra;
	}

	private List<TableClassifica> mapClassifica() {
		List<TableClassifica> tableClassifica = new ArrayList<TableClassifica>();
		int i = 1;
		for (Squadra squadra : classifica) {
			tableClassifica.add(new TableClassifica(i + "", squadra.getNome(), squadra.getPunteggio() + "",
					squadra.getVittorie() + "", squadra.getSconfitte() + ""));
			i++;
		}
		return tableClassifica;
	}

}