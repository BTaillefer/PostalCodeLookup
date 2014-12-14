package jxlauncher;

import java.awt.Event;
import java.io.File;

import postalcode.PostalCode;
import postalcode.PostalCodeIndex;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * FXLauncher creates the primaryStage for PostalCode objects to be displayed on, particularly the ObservableList's after they have been sorted by our comparable methods.
 * Allows a visual representation of our Data
 * @author Brodie
 *
 */
public class FXLauncher extends Application {
	PostalCodeIndex postalCodeIndex;

	public static void main(String[] args) { 
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		postalCodeIndex = new PostalCodeIndex();
		Group root = new Group();
		primaryStage.setScene(new Scene(root));
		BorderPane borderPane = new BorderPane();
		primaryStage.setTitle("Postal Code Analyser");
		postalCodeIndex.load(new File("postal_codes.csv")); //Load in data file
		final VBox vbox1 = new VBox(); //Vbox for tab1
		final VBox vbox2 = new VBox(); //Vbox for tab2
		final VBox vbox3 = new VBox(); //Vbox for tab3
		final VBox vbox4 = new VBox(); //Vbox for tab4
		final VBox vbox5 = new VBox(); //Vbox for tab5
		final TabPane tabPane = new TabPane(); //Create tabpane to hold tab objects
		tabPane.setPrefSize(1200, 1200); 
		tabPane.setSide(Side.TOP);
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		final Tab tab1 = new Tab(); tab1.setText("Original View");
		final Tab tab2 = new Tab();	tab2.setText("By City");
		final Tab tab3 = new Tab();	tab3.setText("By Province");
		final Tab tab4 = new Tab();	tab4.setText("By Latitude");
		final Tab tab5 = new Tab();	tab5.setText("By Longitude");
		tabPane.getTabs().addAll(tab1,tab2,tab3,tab4,tab5);
		borderPane.setCenter(tabPane);
		root.getChildren().add(borderPane);
		primaryStage.setHeight(480);
		tab1.setContent(vbox1);
		tab2.setContent(vbox2);
		tab3.setContent(vbox3);
		tab4.setContent(vbox4);
		tab5.setContent(vbox5);
		//System.out.println(postalCodeIndex.findClosestCoordinate(47.5165, -52.8229));
		postalCodeIndex.findLatitudeRange(47.5165, -52.8229);
		
		//Load ObservableLists of each sorted ArrayList
		vbox1.getChildren().add(new ListView<PostalCode>(postalCodeIndex.getCodeOrder()));
		vbox2.getChildren().add(new ListView<PostalCode>(postalCodeIndex.getCityOrder()));
		vbox3.getChildren().add(new ListView<PostalCode>(postalCodeIndex.getProvinceOrder()));
		vbox4.getChildren().add(new ListView<PostalCode>(postalCodeIndex.getLatitudeOrder()));
		//vbox5.getChildren().add(new ListView<PostalCode>(postalCodeIndex.getLongitudeOrder()));
		primaryStage.show();
	}

}
