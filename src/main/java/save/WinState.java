package save;

/**
 * The {@code WinState} class is a data class that represents any given state of the game
 * characterized by the player's name and rotations made the player.
 * <p></p>
 * WinState is used when a player actually wins the game. There should be no other reasons to use this class.
 */
public class WinState
{
    /**
     * The player's name as a string representation.
     */
    public String playerName;

    /**
     * Number of rotations made by the player.
     */
    public int rotations;

    /**
     * Creates a new WinState instance.
     * @param name the name of the player.
     * @param rotations rotations made by the player when they won.
     */
    public WinState(String name, int rotations) {
        playerName = name;
        this.rotations = rotations;
    }

    /**
     * Default constructor needed for serialization.
     */
    public WinState() {}
}
