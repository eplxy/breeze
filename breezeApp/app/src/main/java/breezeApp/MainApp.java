package breezeApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);        
        stage.setTitle("This is a JavaFX app template...");
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}