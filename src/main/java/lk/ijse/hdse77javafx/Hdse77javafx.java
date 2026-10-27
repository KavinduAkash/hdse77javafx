package lk.ijse.hdse77javafx;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class Hdse77javafx extends Application {
    
    private static Scene scene;
    
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFxml("Layout"), 1000, 600); // fxml file related Parent Object, scene width, scene hight
        stage.setScene(scene);
        stage.show();
    }
    
    public static Parent loadFxml(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Hdse77javafx.class.getResource("/views/" + fxml + ".fxml")); // /views/Layout.fxml
        return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch();
    }

}
