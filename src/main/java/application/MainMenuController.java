package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import save.StateSaver;
import save.WinState;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class MainMenuController
{
    private String playerName;

    private final int minimumCharacterOfName = 3;

    private final StateSaver stateSaver = new StateSaver();

    @FXML
    private Button NewGame, LoadGame;

    @FXML
    private TextField NameField;

    @FXML
    private Label NewGameError;

    @FXML
    private ListView TopList;

    @FXML
    private void initialize() {
        NewGameError.setOpacity(0);
        NewGame.setDisable(true);
        LoadGame.setDisable(true);

        NameField.setOnKeyTyped(this::checkTextFieldContent);

        loadWins();

        Logger.info("Loaded Main Menu");
    }

    @FXML
    private void loadNewGame(ActionEvent event) {
        Logger.info("Clicked on New Game button");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/puzzle.fxml"));

        Parent root = null;
        try
        {
            root = loader.load();
            BaseGameController gameController = loader.getController();
            gameController.setPlayerName(playerName);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Circles Puzzle Game");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadPlayerSave(ActionEvent event) {
        Logger.info("Clicked on Load Game button");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/puzzle.fxml"));

        Parent root = null;
        try
        {
            root = loader.load();

            BaseGameController gameController = loader.getController();
            gameController.setPlayerName(playerName);

            boolean exists = false;

            for(int i = 0; i < gameController.getSaves().length; i++) {
                if(gameController.getSaves()[i].playerName.equals(this.playerName)) {
                    Logger.info(gameController.getSaves()[i]);
                    gameController.loadInSave(gameController.getSaves()[i]);
                    exists = true;
                    break;
                }
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            if(exists)
            {
                stage.setTitle("Circles Puzzle Game");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                NewGameError.setText("No save found for: " + playerName);
                NewGameError.setOpacity(1);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void exitGame(ActionEvent event) {
        Logger.info("Clicked on Exit button");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void checkTextFieldContent(KeyEvent event) {
        checkNameLength();
        checkNameCharacters();

        playerName = NameField.getText();
        NewGameError.setOpacity(0);
    }

    private void checkNameLength() {
        if(NameField.getText().length() >= minimumCharacterOfName) {
            NewGame.setDisable(false);
            LoadGame.setDisable(false);
        } else {
            NewGame.setDisable(true);
            LoadGame.setDisable(true);
        }
    }

    private void checkNameCharacters() {
        if(NameField.getText().contains(" "))
        {
            NewGame.setDisable(true);
            LoadGame.setDisable(true);
        }
    }

    private void loadWins() {
        WinState[] wins = stateSaver.loadWinStates();

        try
        {
            //Sorts array by rotations in ascending order
            Arrays.sort(wins, Comparator.comparingInt((WinState w) -> w.rotations));

            for(int i = 0; i < 5; i++)
            {
                if(i == wins.length)
                    break;

                TopList.getItems().add(i, wins[i].playerName + " - " + wins[i].rotations);
            }

            //Makes list cells unselectable and the list un-scrollable
            TopList.setMouseTransparent(true);
            TopList.setFocusTraversable(false);

            Logger.info("Winning stats added");
        } catch (NullPointerException n) {
            Logger.warn("No winning statistics to show");
        }
    }
}
