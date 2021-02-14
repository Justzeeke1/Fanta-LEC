package javafxapp.control;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class MercatoControllerFx {
	
	private static MercatoControllerFx INSTANCE = null;
	
	public static MercatoControllerFx getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MercatoControllerFx();
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
	List<Giocatore> giocatoriMercato = new ArrayList<Giocatore>();

	@FXML
	Label labelCreaSquadra;
	@FXML
	Label labelNomeSquadra;
	@FXML
	Label labelCrediti;
	@FXML
	TextField tfNomeSquadra;
	@FXML
	Button buttonCreaSquadre;
	@FXML
	Button buttonCreaSquadra;
	@FXML
	TableView<TableSquadra> tableViewMercato;
	@FXML
	private TableColumn<TableSquadra, String> columnId;
	@FXML
	private TableColumn<TableSquadra, String> columnOverall;
	@FXML
	private TableColumn<TableSquadra, String> columnNickname;
	@FXML
	private TableColumn<TableSquadra, String> columnRuolo;
	@FXML
	private TableColumn<TableSquadra, String> columnPrezzo;
	@FXML
    private URL location;
    @FXML
    private ResourceBundle resources;
	

	
	public void initialize() {
		try {
			location = new URL("file:/C:/Users/justz/eclipse-workspace-serv/Progetto_Marco/build/classes/javafxapp/control/mercato.fxml");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		allenatore = Main.getAllenatore();
		squadra = Main.getSquadra();
		labelCreaSquadra.setText("CREA LA TUA SQUADRA!\nClicca due volte sul giocatore per acquistarlo");
		labelCrediti.setText("I tuoi crediti: " + allenatore.getCrediti());
		List<Giocatore> giocatoriTitolari = new ArrayList<Giocatore>();
		if (squadra != null) {
			List<Giocatore> giocatoriTotali = giocatoreController.findAllMyTeam(squadra);
			for (Giocatore giocatore : giocatoriTotali) {
				if (!giocatore.getRiserva()) {
					giocatoriTitolari.add(giocatore);
				}
			}
			initCreaSquadre(giocatoriTitolari);
		}
		initMercato();
		setTable();
		checkSquadra();
	}

	private void initCreaSquadre(List<Giocatore> giocatoriTitolari) {
		Boolean areSquadreCreated = squadraController.areSquadreCreated(squadra.getId());
		System.out.println(giocatoriTitolari.size() == 5);
		System.out.println(!areSquadreCreated);
		if (giocatoriTitolari.size() == 5 && !areSquadreCreated) {
			buttonCreaSquadre.setVisible(true);
		} else {
			buttonCreaSquadre.setVisible(false);
		}
	}

	private void checkSquadra() {
		if (squadra != null) {
			setVisibility(true);
		} else {
			setVisibility(false);
		}
	}
	
	@FXML
	public void clickItem(MouseEvent event) {
	    if (event.getClickCount() == 2) {
	        String idGiocatoreString = tableViewMercato.getSelectionModel().getSelectedItem().getId();
	        Long idGiocatore = Long.parseLong(idGiocatoreString);
			Giocatore giocatore = giocatoreController.findById(idGiocatore);
			giocatoreController.compraGiocatore(giocatore, squadra.getId(), allenatore);
			initialize();
	    }
	}

	@FXML
	public void creaSquadre() {
		squadraController.creaSquadre(squadra.getId());
		buttonCreaSquadre.setVisible(false);
	}

	@FXML
	public void saveSquadra() {
		String nomeSquadra = tfNomeSquadra.getText();
		squadra = squadraController.save(nomeSquadra, allenatore);
		Main.setSquadra(squadra);
		initialize();
	}

	private void initMercato() {
		if (squadra != null) {
			giocatoriMercato = giocatoreController.findAllMercatoOrderByOverall();
		}
	}

	private void setTable() {
		List<TableSquadra> tableMercato = mapMercato();
		columnId.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("id"));
		columnOverall.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("overall"));
		columnNickname.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("nickname"));
		columnRuolo.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("ruolo"));
		columnPrezzo.setCellValueFactory(new PropertyValueFactory<TableSquadra, String>("prezzo"));
		ObservableList<TableSquadra> dataMercato = FXCollections.observableList(tableMercato);
		tableViewMercato.setItems(dataMercato);
	}

	private void setVisibility(Boolean isVisible) {
		labelCrediti.setVisible(isVisible);
		labelCreaSquadra.setVisible(isVisible);
		tableViewMercato.setVisible(isVisible);
		tfNomeSquadra.setVisible(!isVisible);
		buttonCreaSquadra.setVisible(!isVisible);
		labelNomeSquadra.setVisible(!isVisible);
	}

	private List<TableSquadra> mapMercato() {
		List<TableSquadra> tableSquadra = new ArrayList<TableSquadra>();
		for (Giocatore giocatore : giocatoriMercato) {
			tableSquadra.add(new TableSquadra(giocatore.getId()+"", giocatore.getOverall() + "", giocatore.getNickname(),
					giocatore.getRuolo(), giocatore.getPrezzo() + ""));
		}
		return tableSquadra;
	}

}