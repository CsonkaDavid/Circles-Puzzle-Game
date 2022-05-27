package save;

/**
 * The {@code GameState} class is a data class that represents any given state of the game
 * characterized by the player's name, rotations made the player and the state of the formation
 * inside the model.
 */
public class GameState
{
    /**
     * The player's name as a string representation.
     */
    public String playerName;

    /**
     * Array to represent one circle from the flower formation.
     */
    public int[] leftCircle, rightCircle, bottomCircle;

    /**
     * Number of rotations made by the player.
     */
    public int rotations;

    /**
     * Creates a new GameState instance.
     * @param name the name of the player.
     * @param rotations rotations made by the player when they won.
     * @param left the left circle's state in the flower formation.
     * @param right the right circle's state in the flower formation.
     * @param bottom the bottom circle's state in the flower formation.
     */
    public GameState(String name, int rotations, int[] left, int[] right, int[] bottom) {
        playerName = name;
        leftCircle = left;
        rightCircle = right;
        bottomCircle = bottom;
        this.rotations = rotations;
    }

    /**
     * Default constructor needed for serialization.
     */
    public GameState() {}
}
