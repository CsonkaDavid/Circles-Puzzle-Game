package model;

import org.tinylog.Logger;
import save.GameState;
import save.StateSaver;
import save.WinState;

/**
 * The {@link Game} is the class that saves and loads game states and executes methods for the player to control the game.
 */
public class Game
{
    private String playerName;
    private final Flower flower;

    private final int[] result;
    private int[] winResult;

    private boolean win = false;

    private int rotations;

    private final StateSaver stateSaver;
    private GameState[] saves;
    private GameState currentGameState;

    private WinState[] wins;

    /**
     * Creates a new Game instance to control the state of the game.
     * @param player the name of the player controlling the game currently.
     */
    public Game(String player) {
        playerName = player;
        rotations = 0;
        result = new int[12];
        flower = new Flower();

        stateSaver = new StateSaver();
        currentGameState = new GameState(
                playerName,
                rotations,
                flower.getLeftCircle(),
                flower.getRightCircle(),
                flower.getBottomCircle());

        loadInGames();
        reFillResult();
        fillWinResult();
    }

    /**
     *
     * @return the current {@link Flower} instance representing the flower formation on the screen.
     */
    public Flower getFlower() { return flower; }

    /**
     *
     * @return the currently running game's {@link GameState} serializable object.
     */
    public GameState getCurrentGameState() { return currentGameState; }

    /**
     *
     * @return the current player's name.
     */
    public String getPlayerName() { return playerName; }

    /**
     * Sets the current player's name to a new  {@link String} value.
     * @param name new name for the currently playing user.
     * <p></p>
     * This method should only be used when an already existing game state is loaded.
     * The player's name will be set to the current user's name by default.
     */
    public void setPlayerName(String name) { playerName = name; }

    /**
     *
     * @return current number of rotations made by the player.
     */
    public int getRotations() { return rotations; }

    /**
     *
     * @return whether the player already won the game.
     */
    public boolean getWin() { return win; }

    /**
     *
     * @return all the saved {@link GameState} instances.
     */
    public GameState[] getSaves() { return saves; }

    /**
     *
     * @param index the index of the desired value.
     * @return an element from the array representing all the visible values in the formation on the screen.
     */
    public int getElement(int index) { return result[index]; }

    /**
     * Saves a {@link WinState} instance when the player wins the game.
     */
    public void checkWin() {
        if(checkWinCondition())
        {
            win = true;
            saveWin();
        }
    }

    /**
     * Commands the currently used {@link Flower} to rotate its circle on the left side.
     * @param right true means the circle is rotated to the right or to the left otherwise.
     */
    public void rotateLeftCircle(boolean right) {
        flower.rotateLeft(right);

        reFillResult();
        rotations++;
        checkWin();

        if(!checkWinCondition())
            saveGame();
    }

    /**
     * Commands the currently used {@link Flower} to rotate its circle on the right side.
     * @param right true means the circle is rotated to the right or to the left otherwise.
     */
    public void rotateRightCircle(boolean right) {
        flower.rotateRight(right);

        reFillResult();
        rotations++;
        checkWin();

        if(!checkWinCondition())
            saveGame();
    }

    /**
     * Commands the currently used {@link Flower} to rotate its circle at the bottom.
     * @param right true means the circle is rotated to the right or to the left otherwise.
     */
    public void rotateBottomCircle(boolean right) {
        flower.rotateBottom(right);

        reFillResult();
        rotations++;
        checkWin();

        if(!checkWinCondition())
            saveGame();
    }

    /**
     * Loads in a save and changes the currently used {@link Flower}'s values
     * to the ones saved down in the given {@link GameState}.
     * @param save the {@link GameState} to be loaded.
     */
    public void loadInGame(GameState save) {
        GameState currentGame = null;

        for(int i = 0; i < saves.length; i++) {
            if(saves[i].playerName.equals(save.playerName))
            {
                currentGame = saves[i];
                Logger.info("Save Found");
                break;
            }
        }

        currentGameState = currentGame;

        playerName = currentGame.playerName;
        flower.setLeftCircle(currentGameState.leftCircle);
        flower.setRightCircle(currentGameState.rightCircle);
        flower.setBottomCircle(currentGameState.bottomCircle);

        rotations = currentGameState.rotations;
        reFillResult();
    }

    private void fillWinResult() {
        winResult = new int[12];

        for(int i = 0; i < 12; i++)
            winResult[i] = i+1;
    }

    private void reFillResult() {
        result[0] = flower.getLeftCircle()[0];
        result[1] = flower.getLeftCircle()[1];
        result[5] = flower.getLeftCircle()[2];
        result[8] = flower.getLeftCircle()[3];
        result[7] = flower.getBottomCircle()[5];
        result[3] = flower.getLeftCircle()[5];

        result[2] = flower.getRightCircle()[1];
        result[6] = flower.getRightCircle()[2];
        result[9] = flower.getRightCircle()[3];
        result[4] = flower.getRightCircle()[5];

        result[10] = flower.getBottomCircle()[4];
        result[11] = flower.getBottomCircle()[3];
    }

    private boolean checkWinCondition() {
        boolean bool = true;

        for(int i = 0; i < result.length; i++) {
            if(result[i] != winResult[i])
            {
                bool = false;
                break;
            }
        }

        return bool;
    }

    private void saveGame() {
        currentGameState.playerName = playerName;
        currentGameState.leftCircle = flower.getLeftCircle();
        currentGameState.rightCircle = flower.getRightCircle();
        currentGameState.bottomCircle = flower.getBottomCircle();
        currentGameState.rotations = rotations;

        boolean exists = false;

        for(int i = 0; i < saves.length; i++) {
            if(saves[i].playerName.equals(currentGameState.playerName)) {
                saves[i] = currentGameState;
                exists = true;

                break;
            }
        }

        if(!exists) {
            GameState[] tempSaves = saves;

            GameState[] newSaves = new GameState[tempSaves.length+1];

            System.arraycopy(tempSaves, 0, newSaves, 1, tempSaves.length);

            newSaves[0] = currentGameState;

            saves = newSaves;
        }

        stateSaver.savePlayState(saves);

        Logger.info("Saved: " + playerName);
        Logger.info("Saved: " + rotations);
    }

    private void loadInGames() {
        saves = stateSaver.loadGameStates();

        if(saves == null) {
            saves = new GameState[1];
            saves[0] = currentGameState;
            Logger.info("Saves is empty");
        }
    }

    private void saveWin() {
        wins = stateSaver.loadWinStates();

        WinState[] newWins;
        try
        {
            newWins = new WinState[wins.length + 1];

            System.arraycopy(wins, 0, newWins, 1, wins.length);
        } catch(NullPointerException n) {
            Logger.warn("No other wins to append to!");
            newWins = new WinState[1];
        }

        newWins[0] = new WinState(playerName, rotations);

        stateSaver.saveWinState(newWins);

        GameState[] newGameStates = new GameState[saves.length-1];

        System.arraycopy(saves, 1, newGameStates, 0, saves.length - 1);

        stateSaver.savePlayState(newGameStates);
    }
}