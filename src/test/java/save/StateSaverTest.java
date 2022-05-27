package save;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateSaverTest
{

    @Test
    void saveAndLoadTestForGameState()
    {
        StateSaver stateSaver = new StateSaver();

        int[] someArray = {1,2,3};

        GameState state = new GameState("player", 2, someArray, someArray, someArray);
        GameState[] states = {state};

        stateSaver.savePlayState(states);

        GameState[] loadedStates = stateSaver.loadGameStates();

        assertEquals(states[0].playerName, loadedStates[0].playerName);
        assertEquals(states[0].rotations, loadedStates[0].rotations);
        assertArrayEquals(states[0].leftCircle, loadedStates[0].leftCircle);
        assertArrayEquals(states[0].rightCircle, loadedStates[0].rightCircle);
        assertArrayEquals(states[0].bottomCircle, loadedStates[0].bottomCircle);
    }

    @Test
    void saveAndLoadTestForWinState()
    {
        StateSaver stateSaver = new StateSaver();

        WinState state = new WinState("player", 35);
        WinState[] states = {state};

        stateSaver.saveWinState(states);

        WinState[] loadedStates = stateSaver.loadWinStates();

        assertEquals(states[0].playerName, loadedStates[0].playerName);
        assertEquals(states[0].rotations, loadedStates[0].rotations);
    }
}