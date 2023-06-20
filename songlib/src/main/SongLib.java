//Kenneth Lee KL877
//Abriel Hernandez AH1394
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import main.songlibController;
import java.io.IOException;

public class SongLib extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../view/songlibView.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        songlibController listController = loader.getController();
        listController.start(stage);
        
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("Song Library");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}