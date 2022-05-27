package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application
{
    @Override
    public void start(Stage stage) throws IOException, NullPointerException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainMenu.fxml")));
        stage.setTitle("Circles Puzzle Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
