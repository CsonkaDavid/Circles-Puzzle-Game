package save;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * {@code StateSaver} is a class that implements and uses parts of the Jackson library to parse and write
 * 2 save files.
 */
public class StateSaver
{
    private final File saveFile = new File(Objects.requireNonNull(getClass().getResource("/plays.json")).getFile());
    private final File winFile = new File(Objects.requireNonNull(getClass().getResource("/wins.json")).getFile());

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Writes a new converted array into the {@code plays.json} file.
     * @param saves the {@link GameState} array to be saved
     */
    public void savePlayState(GameState[] saves) {
        try
        {
            mapper.writeValue(saveFile, saves);
        } catch (IOException e)
        {
            Logger.error("Couldn't write save file!");
        }
    }

    /**
     * Reads {@code plays.json} file into an array.
     * @return a converted array containing every {@link GameState} instance saved into the {@code plays.json} file
     */
    public GameState[] loadGameStates() {
        try
        {
            var ref = new TypeReference<GameState[]>() {};
            if(saveFile.length() > 0)
                return mapper.readValue(saveFile, ref);
            else {
                return null;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Writes a new converted array into the {@code wins.json} file.
     * @param wins the {@link WinState} array to be saved
     */
    public void saveWinState(WinState[] wins) {
        try
        {
            mapper.writeValue(winFile, wins);
        } catch (IOException e)
        {
            Logger.error("Couldn't write save file!");
        }
    }

    /**
     * Reads {@code wins.json} file into an array.
     * @return a converted array containing every {@link WinState} instance saved into the {@code wins.json} file
     */
    public WinState[] loadWinStates() {
        try
        {
            var ref = new TypeReference<WinState[]>() {};
            if(winFile.length() > 0)
                return mapper.readValue(winFile, ref);
            else {
                return null;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
