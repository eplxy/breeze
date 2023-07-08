package breezeApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    //test start using ColorRecognitionController
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/colorRecognitionMenu.fxml"));
        ImageAdjustMenuController crc = new ImageAdjustMenuController(stage);
        loader.setController(crc);
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Color Recognition Test");
        stage.sizeToScene();
        stage.show();

        crc.initialize();

    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
//        MainMenuController mmc = new MainMenuController();
//        loader.setController(mmc);
//        BorderPane root = loader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("Breeze");
//        stage.sizeToScene();
//        stage.show();
//    }
    public static void main(String[] args) {
        launch(args);
    }
}
