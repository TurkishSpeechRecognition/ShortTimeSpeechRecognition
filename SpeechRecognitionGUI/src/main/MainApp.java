package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.model.Execution;
import main.model.enumeration.CommandType;
import main.view.MainScreenController;

public class MainApp extends Application {

	private ObservableList<Execution> executionList;
	private Scene scene;
	private Stage stage;

	public MainApp() {
		executionList = FXCollections.observableArrayList(
				new Execution(CommandType.Track_Filter),
				new Execution(CommandType.Track_Info), 
				new Execution(CommandType.Coverage_Area),
				new Execution(CommandType.Track_Filter), 
				new Execution(CommandType.Track_Info),
				new Execution(CommandType.Coverage_Area), 
				new Execution(CommandType.Track_Filter),
				new Execution(CommandType.Track_Info), 
				new Execution(CommandType.Coverage_Area));
	}

	public ObservableList<Execution> getExecutionList() {
		return executionList;
	}

//	public void setOtherScene() {
//		System.out.println("In");
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(MainApp.class.getResource("view/Counter.fxml"));
//			StackPane counter = (StackPane) loader.load();
//			scene = new Scene(counter);
//			stage.setScene(scene);
//			stage.setTitle("asd");
//			stage.show();
//			stage.toFront();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("Out");
//	}

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainScreen.fxml"));
			HBox root = (HBox) loader.load();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
			stage.setScene(scene);

			stage.setTitle("Speech Recognition");
			stage.setResizable(false);
			stage.show();
			MainScreenController mainScreenController = loader.getController();
			mainScreenController.setMainApp(this);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Finally!");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}