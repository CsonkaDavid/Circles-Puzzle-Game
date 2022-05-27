package application;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import save.GameState;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Objects;

public class BaseGameController
{
    private final Game game = new Game("");

    private boolean controllable = true;

    private final double rotationTime = 0.3;
    private final double rotationAngle = 60;

    @FXML
    private Label RotationLabel, PlayerName;

    @FXML
    private StackPane WinPane;

    @FXML
    private Label Left0Label, Left1Label, Left2Label, Left3Label, Left4Label, Left5Label;

    @FXML
    private Label Right0Label, Right1Label, Right2Label, Right3Label, Right4Label, Right5Label;

    @FXML
    private Label Bottom0Label, Bottom1Label, Bottom2Label, Bottom3Label, Bottom4Label, Bottom5Label;

    @FXML
    private StackPane LeftCircle, RightCircle, BottomCircle;

    @FXML
    private StackPane LeftElement0, LeftElement1, LeftElement2, LeftElement3, LeftElement4, LeftElement5;

    @FXML
    private StackPane RightElement0, RightElement1, RightElement2, RightElement3, RightElement4, RightElement5;

    @FXML
    private StackPane BottomElement0, BottomElement1, BottomElement2, BottomElement3, BottomElement4, BottomElement5;

    @FXML
    private Polygon LeftRotaterLeft, LeftRotaterRight, RightRotaterLeft, RightRotaterRight, BottomRotaterLeft, BottomRotaterRight;

    private RotateTransition rotateLeftToLeftTransition, rotateLeftToRightTransition;
    private RotateTransition rotateRightToLeftTransition, rotateRightToRightTransition;
    private RotateTransition rotateBottomToLeftTransition, rotateBottomToRightTransition;

    private RotateTransition counterRotationForLeftToLeft, counterRotationForLeftToRight;
    private RotateTransition counterRotationForRightToLeft, counterRotationForRightToRight;
    private RotateTransition counterRotationForBottomToLeft, counterRotationForBottomToRight;

    private ParallelTransition parallelLeftToLeftRotation, parallelLeftToRightRotation;
    private ParallelTransition parallelRightToLeftRotation, parallelRightToRightRotation;
    private ParallelTransition parallelBottomToLeftRotation, parallelBottomToRightRotation;

    private final IntegerProperty[] LeftCircleNumbers = new IntegerProperty[6];
    private final IntegerProperty[] RightCircleNumbers = new IntegerProperty[6];
    private final IntegerProperty[] BottomCircleNumbers = new IntegerProperty[6];

    private final IntegerProperty rotationProperty = new SimpleIntegerProperty(0);

    public void setPlayerName(String name) {
        PlayerName.setText(name);
        game.setPlayerName(name);
    }

    public GameState[] getSaves() { return game.getSaves(); }

    public void loadInSave(GameState state) {
        game.loadInGame(state);
        rotationProperty.setValue(game.getRotations());

        for(int i = 0; i < 6; i++)
        {
            LeftCircleNumbers[i].set(game.getFlower().getLeftCircle()[i]);
            RightCircleNumbers[i].set(game.getFlower().getRightCircle()[i]);
            BottomCircleNumbers[i].set(game.getFlower().getBottomCircle()[i]);
        }
    }

    @FXML
    private void initialize() {
        hideWinPane();
        fillIntegerProperties();
        bindLabelTexts();
        bindLabelRotations();

        createCounterRotationsOfLeftCircle();
        createCounterRotationsOfRightCircle();
        createCounterRotationsOfBottomCircle();

        createTransitionsOfLeftCircle();
        createTransitionsOfRightCircle();
        createTransitionsOfBottomCircle();

        connectTransitionsIntoParallels();

        setFunctionality();

        Logger.info("Loaded Game view");
    }

    @FXML
    private void rotateLeftToLeft(MouseEvent event) {
        if(controllable)
        {
            hideElementsForLeftRotation();
            revealElementsForLeftRotation();

            parallelLeftToLeftRotation.play();
        }
        Logger.info("Clicked Left rotater at the Left circle");
    }

    @FXML
    private void rotateLeftToRight(MouseEvent event) {
        if(controllable)
        {
            hideElementsForLeftRotation();
            revealElementsForLeftRotation();

            parallelLeftToRightRotation.play();
        }
        Logger.info("Clicked Right rotater at the Left circle");
    }

    @FXML
    private void rotateRightToLeft(MouseEvent event) {
        if(controllable)
        {
            hideElementsForRightRotation();
            revealElementsForRightRotation();

            parallelRightToLeftRotation.play();
        }
        Logger.info("Clicked Left rotater at the Right circle");
    }

    @FXML
    private void rotateRightToRight(MouseEvent event) {
        if(controllable)
        {
            hideElementsForRightRotation();
            revealElementsForRightRotation();

            parallelRightToRightRotation.play();
        }
        Logger.info("Clicked Right rotater at the Right circle");
    }

    @FXML
    private void rotateBottomToLeft(MouseEvent event) {
        if(controllable)
        {
            hideElementsForBottomRotation();
            revealElementsForBottomRotation();

            parallelBottomToLeftRotation.play();
        }
        Logger.info("Clicked Right rotater at the Bottom circle");
    }

    @FXML
    private void rotateBottomToRight(MouseEvent event) {
        if(controllable)
        {
            hideElementsForBottomRotation();
            revealElementsForBottomRotation();

            parallelBottomToRightRotation.play();
        }
        Logger.info("Clicked Left rotater at the Bottom circle");
    }

    @FXML
    private void loadMainMenu(ActionEvent event) throws IOException {
        Logger.info("Clicked on Back to menu button");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Circles Puzzle Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setFunctionality() {

            LeftRotaterLeft.setOnMouseClicked(this::rotateLeftToLeft);
            LeftRotaterRight.setOnMouseClicked(this::rotateLeftToRight);

            RightRotaterLeft.setOnMouseClicked(this::rotateRightToLeft);
            RightRotaterRight.setOnMouseClicked(this::rotateRightToRight);

            BottomRotaterLeft.setOnMouseClicked(this::rotateBottomToRight);
            BottomRotaterRight.setOnMouseClicked(this::rotateBottomToLeft);
    }

    private void bindLabelTexts() {
        RotationLabel.textProperty().bind(rotationProperty.asString());

        Left0Label.textProperty().bind(LeftCircleNumbers[0].asString());
        Left1Label.textProperty().bind(LeftCircleNumbers[1].asString());
        Left2Label.textProperty().bind(LeftCircleNumbers[2].asString());
        Left3Label.textProperty().bind(LeftCircleNumbers[3].asString());
        Left4Label.textProperty().bind(LeftCircleNumbers[4].asString());
        Left5Label.textProperty().bind(LeftCircleNumbers[5].asString());

        Right0Label.textProperty().bind(RightCircleNumbers[0].asString());
        Right1Label.textProperty().bind(RightCircleNumbers[1].asString());
        Right2Label.textProperty().bind(RightCircleNumbers[2].asString());
        Right3Label.textProperty().bind(RightCircleNumbers[3].asString());
        Right4Label.textProperty().bind(RightCircleNumbers[4].asString());
        Right5Label.textProperty().bind(RightCircleNumbers[5].asString());

        Bottom0Label.textProperty().bind(BottomCircleNumbers[0].asString());
        Bottom1Label.textProperty().bind(BottomCircleNumbers[1].asString());
        Bottom2Label.textProperty().bind(BottomCircleNumbers[2].asString());
        Bottom3Label.textProperty().bind(BottomCircleNumbers[3].asString());
        Bottom4Label.textProperty().bind(BottomCircleNumbers[4].asString());
        Bottom5Label.textProperty().bind(BottomCircleNumbers[5].asString());
    }

    private void bindLabelRotations() {
        Left1Label.rotateProperty().bind(Left0Label.rotateProperty());
        Left2Label.rotateProperty().bind(Left0Label.rotateProperty());
        Left3Label.rotateProperty().bind(Left0Label.rotateProperty());
        Left4Label.rotateProperty().bind(Left0Label.rotateProperty());
        Left5Label.rotateProperty().bind(Left0Label.rotateProperty());

        Right1Label.rotateProperty().bind(Right0Label.rotateProperty());
        Right2Label.rotateProperty().bind(Right0Label.rotateProperty());
        Right3Label.rotateProperty().bind(Right0Label.rotateProperty());
        Right4Label.rotateProperty().bind(Right0Label.rotateProperty());
        Right5Label.rotateProperty().bind(Right0Label.rotateProperty());

        Bottom1Label.rotateProperty().bind(Bottom0Label.rotateProperty());
        Bottom2Label.rotateProperty().bind(Bottom0Label.rotateProperty());
        Bottom3Label.rotateProperty().bind(Bottom0Label.rotateProperty());
        Bottom4Label.rotateProperty().bind(Bottom0Label.rotateProperty());
        Bottom5Label.rotateProperty().bind(Bottom0Label.rotateProperty());
    }

    private void fillIntegerProperties() {
        for(int i = 0; i < 6; i++) {
            LeftCircleNumbers[i] = new SimpleIntegerProperty(game.getFlower().getLeftCircle()[i]);
            RightCircleNumbers[i] = new SimpleIntegerProperty(game.getFlower().getRightCircle()[i]);
            BottomCircleNumbers[i] = new SimpleIntegerProperty(game.getFlower().getBottomCircle()[i]);
        }
    }

    private ParallelTransition generateDoubleRotations(RotateTransition rotate1, RotateTransition rotate2) {
        ParallelTransition parallel = new ParallelTransition(rotate1, rotate2);
        parallel.setOnFinished(e -> {
            increaseRotationCounter();

            if(game.getWin())
                winGame();
            else
                controllable = true;

            Logger.info("Animation played");
        });

        return parallel;
    }

    private void connectTransitionsIntoParallels() {
        parallelLeftToLeftRotation = generateDoubleRotations(rotateLeftToLeftTransition, counterRotationForLeftToLeft);
        parallelLeftToRightRotation = generateDoubleRotations(rotateLeftToRightTransition, counterRotationForLeftToRight);

        parallelRightToLeftRotation = generateDoubleRotations(rotateRightToLeftTransition, counterRotationForRightToLeft);
        parallelRightToRightRotation = generateDoubleRotations(rotateRightToRightTransition, counterRotationForRightToRight);

        parallelBottomToLeftRotation = generateDoubleRotations(rotateBottomToLeftTransition, counterRotationForBottomToLeft);
        parallelBottomToRightRotation = generateDoubleRotations(rotateBottomToRightTransition, counterRotationForBottomToRight);
    }

    private RotateTransition generateLeftRotation() {
        RotateTransition transition = new RotateTransition();
        transition.setByAngle(-rotationAngle);
        transition.setDuration(Duration.seconds(rotationTime));
        return transition;
    }

    private RotateTransition generateRightRotation() {
        RotateTransition transition = new RotateTransition();
        transition.setByAngle(rotationAngle);
        transition.setDuration(Duration.seconds(rotationTime));
        return transition;
    }

    private void createCounterRotationsOfLeftCircle() {
        counterRotationForLeftToLeft = generateRightRotation();
        counterRotationForLeftToLeft.setNode(Left0Label);

        counterRotationForLeftToRight = generateLeftRotation();
        counterRotationForLeftToRight.setNode(Left0Label);
    }

    private void createCounterRotationsOfRightCircle() {
        counterRotationForRightToLeft = generateRightRotation();
        counterRotationForRightToLeft.setNode(Right0Label);

        counterRotationForRightToRight = generateLeftRotation();
        counterRotationForRightToRight.setNode(Right0Label);
    }

    private void createCounterRotationsOfBottomCircle() {
        counterRotationForBottomToLeft = generateRightRotation();
        counterRotationForBottomToLeft.setNode(Bottom0Label);

        counterRotationForBottomToRight = generateLeftRotation();
        counterRotationForBottomToRight.setNode(Bottom0Label);
    }

    private void createTransitionsOfLeftCircle() {
        rotateLeftToLeftTransition = generateLeftRotation();
        rotateLeftToLeftTransition.setNode(LeftCircle);
        rotateLeftToLeftTransition.setOnFinished(e -> {
            game.rotateLeftCircle(false);
            refillOverlapsOfLeft();
        });

        rotateLeftToRightTransition = generateRightRotation();
        rotateLeftToRightTransition.setNode(LeftCircle);
        rotateLeftToRightTransition.setOnFinished(e -> {
            game.rotateLeftCircle(true);
            refillOverlapsOfLeft();
        });
    }

    private void createTransitionsOfRightCircle() {
        rotateRightToLeftTransition = generateLeftRotation();
        rotateRightToLeftTransition.setNode(RightCircle);
        rotateRightToLeftTransition.setOnFinished(e -> {
            game.rotateRightCircle(false);
            refillOverlapsOfRight();
        });

        rotateRightToRightTransition = generateRightRotation();
        rotateRightToRightTransition.setNode(RightCircle);
        rotateRightToRightTransition.setOnFinished(e -> {
            game.rotateRightCircle(true);
            refillOverlapsOfRight();
        });
    }

    private void createTransitionsOfBottomCircle() {
        rotateBottomToLeftTransition = generateLeftRotation();
        rotateBottomToLeftTransition.setNode(BottomCircle);
        rotateBottomToLeftTransition.setOnFinished(e -> {
            game.rotateBottomCircle(false);
            refillOverlapsOfBottom();
        });

        rotateBottomToRightTransition = generateRightRotation();
        rotateBottomToRightTransition.setNode(BottomCircle);
        rotateBottomToRightTransition.setOnFinished(e -> {
            game.rotateBottomCircle(true);
            refillOverlapsOfBottom();
        });
    }

    private void hideElementsForLeftRotation() {
        setOpacityForOverlapOfBottom(0, 0);
        setOpacityForOverlapOfBottom(2, 0);

        setOpacityForOverlapOfRight(3, 0);
        setOpacityForOverlapOfRight(1, 0);
    }

    private void hideElementsForRightRotation() {
        setOpacityForOverlapOfLeft(0, 0);
        setOpacityForOverlapOfLeft(2, 0);

        setOpacityForOverlapOfBottom(3, 0);
        setOpacityForOverlapOfBottom(1, 0);
    }

    private void hideElementsForBottomRotation() {
        setOpacityForOverlapOfLeft(3, 0);
        setOpacityForOverlapOfLeft(1, 0);

        setOpacityForOverlapOfRight(0, 0);
        setOpacityForOverlapOfRight(2, 0);
    }

    private void revealElementsForLeftRotation() {
        for(int i = 0; i < 4; i++)
            setOpacityForOverlapOfLeft(i, 1);
    }

    private void revealElementsForRightRotation() {
        for(int i = 0; i < 4; i++)
            setOpacityForOverlapOfRight(i, 1);
    }

    private void revealElementsForBottomRotation() {
        for(int i = 0; i < 4; i++)
            setOpacityForOverlapOfBottom(i, 1);
    }

    private void setOpacityForOverlapOfLeft(int index, int opacityValue) {
        switch(game.getFlower().getLeftOverlap(index)) {
            case 0 -> LeftElement0.setOpacity(opacityValue);
            case 1 -> LeftElement1.setOpacity(opacityValue);
            case 2 -> LeftElement2.setOpacity(opacityValue);
            case 3 -> LeftElement3.setOpacity(opacityValue);
            case 4 -> LeftElement4.setOpacity(opacityValue);
            case 5 -> LeftElement5.setOpacity(opacityValue);
        }
    }

    private void setOpacityForOverlapOfRight(int index, int opacityValue) {
        switch(game.getFlower().getRightOverlap(index)) {
            case 0 -> RightElement0.setOpacity(opacityValue);
            case 1 -> RightElement1.setOpacity(opacityValue);
            case 2 -> RightElement2.setOpacity(opacityValue);
            case 3 -> RightElement3.setOpacity(opacityValue);
            case 4 -> RightElement4.setOpacity(opacityValue);
            case 5 -> RightElement5.setOpacity(opacityValue);
        }
    }

    private void setOpacityForOverlapOfBottom(int index, int opacityValue) {
        switch(game.getFlower().getBottomOverlap(index)) {
            case 0 -> BottomElement0.setOpacity(opacityValue);
            case 1 -> BottomElement1.setOpacity(opacityValue);
            case 2 -> BottomElement2.setOpacity(opacityValue);
            case 3 -> BottomElement3.setOpacity(opacityValue);
            case 4 -> BottomElement4.setOpacity(opacityValue);
            case 5 -> BottomElement5.setOpacity(opacityValue);
        }
    }

    private void refillOverlapsOfLeft() {
        int refillValue;
        int index;

        for(int i = 0; i < 4; i++) {
            switch(i) {
                case 0 -> {
                    refillValue = game.getElement(1);
                    index = game.getFlower().getRightOverlap(3);
                    RightCircleNumbers[index].setValue(refillValue);
                }
                case 1 -> {
                    refillValue = game.getElement(5);
                    index = game.getFlower().getBottomOverlap(2);
                    BottomCircleNumbers[index].setValue(refillValue);
                }
                case 2 -> {
                    refillValue = game.getElement(8);
                    index = game.getFlower().getRightOverlap(1);
                    RightCircleNumbers[index].setValue(refillValue);
                }
                case 3 -> {
                    refillValue = game.getElement(7);
                    index = game.getFlower().getBottomOverlap(0);
                    BottomCircleNumbers[index].setValue(refillValue);
                }
            }
        }
    }

    private void refillOverlapsOfRight() {
        int refillValue;
        int index;

        for(int i = 0; i < 4; i++) {
            switch(i) {
                case 0 -> {
                    refillValue = game.getElement(9);
                    index = game.getFlower().getBottomOverlap(3);
                    BottomCircleNumbers[index].setValue(refillValue);
                }
                case 1 -> {
                    refillValue = game.getElement(8);
                    index = game.getFlower().getLeftOverlap(2);
                    LeftCircleNumbers[index].setValue(refillValue);
                }
                case 2 -> {
                    refillValue = game.getElement(4);
                    index = game.getFlower().getBottomOverlap(1);
                    BottomCircleNumbers[index].setValue(refillValue);
                }
                case 3 -> {
                    refillValue = game.getElement(1);
                    index = game.getFlower().getLeftOverlap(0);
                    LeftCircleNumbers[index].setValue(refillValue);
                }
            }
        }
    }

    private void refillOverlapsOfBottom() {
        int refillValue;
        int index;

        for(int i = 0; i < 4; i++) {
            switch(i) {
                case 0 -> {
                    refillValue = game.getElement(7);
                    index = game.getFlower().getLeftOverlap(3);
                    LeftCircleNumbers[index].setValue(refillValue);
                }
                case 1 ->  {
                    refillValue = game.getElement(4);
                    index = game.getFlower().getRightOverlap(2);
                    RightCircleNumbers[index].setValue(refillValue);
                }
                case 2 -> {
                    refillValue = game.getElement(5);
                    index = game.getFlower().getLeftOverlap(1);
                    LeftCircleNumbers[index].setValue(refillValue);
                }
                case 3 -> {
                    refillValue = game.getElement(9);
                    index = game.getFlower().getRightOverlap(0);
                    RightCircleNumbers[index].setValue(refillValue);
                }
            }
        }
    }

    private void increaseRotationCounter() { rotationProperty.setValue(game.getRotations()); }

    private void winGame() {
        revealWinPane();
        Logger.info("Game Won");
    }

    private void hideWinPane() {
        WinPane.setDisable(true);
        WinPane.setOpacity(0);
    }

    private void revealWinPane() {
        WinPane.setDisable(false);
        WinPane.setOpacity(1);
    }
}