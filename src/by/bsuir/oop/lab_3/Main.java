package by.bsuir.oop.lab_3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    private static Stage modalStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Scene mainScene = new Scene(FXMLLoader.load(getClass().getResource("sample/sample.fxml")), 654, 600);
        stage.setScene(mainScene);
        primaryStage.setTitle("OOP labs");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void showModalWindow(String path, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("sample/" + path));
        AnchorPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        modalStage = dialogStage;
        dialogStage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void closeModalWindow(){
        modalStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
