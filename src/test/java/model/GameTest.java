package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import save.GameState;
import save.StateSaver;
import save.WinState;

import static org.junit.jupiter.api.Assertions.*;

class GameTest
{
    @ParameterizedTest
    @ValueSource(strings = {"player", "marika", "dávid", "apu", "anyu"})
    void setPlayerNameToPlayer(String name)
    {
        Game game = new Game("player");
        game.setPlayerName(name);
        assertEquals(name, game.getPlayerName());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void rotateLeftCircle(boolean direction)
    {
        int[] someArray = {1,2,3,4,5,6};
        ArrayShifter shifter = new ArrayShifter();

        int[] shiftedArray;

        if(direction)
            shiftedArray = shifter.shiftArrayRight(someArray);
        else
            shiftedArray = shifter.shiftArrayLeft(someArray);

        Game game = new Game("player");

        int rotations = 0;

        game.getFlower().setLeftCircle(someArray);

        game.rotateLeftCircle(direction);

        rotations++;

        assertEquals(rotations, game.getRotations());
        assertArrayEquals(shiftedArray ,game.getFlower().getLeftCircle());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void rotateRightCircle(boolean direction)
    {
        int[] someArray = {1,2,3,4,5,6};
        ArrayShifter shifter = new ArrayShifter();

        int[] shiftedArray;

        if(direction)
            shiftedArray = shifter.shiftArrayRight(someArray);
        else
            shiftedArray = shifter.shiftArrayLeft(someArray);

        Game game = new Game("player");

        int rotations = 0;

        game.getFlower().setRightCircle(someArray);

        game.rotateRightCircle(direction);

        rotations++;

        assertEquals(rotations, game.getRotations());
        assertArrayEquals(shiftedArray ,game.getFlower().getRightCircle());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void rotateBottomCircle(boolean direction)
    {
        int[] someArray = {1,2,3,4,5,6};
        ArrayShifter shifter = new ArrayShifter();

        int[] shiftedArray;

        if(direction)
            shiftedArray = shifter.shiftArrayRight(someArray);
        else
            shiftedArray = shifter.shiftArrayLeft(someArray);

        Game game = new Game("player");

        int rotations = 0;

        game.getFlower().setBottomCircle(someArray);

        game.rotateBottomCircle(direction);

        rotations++;

        assertEquals(rotations, game.getRotations());
        assertArrayEquals(shiftedArray ,game.getFlower().getBottomCircle());
    }

    @Test
    void loadInGame()
    {
        Game game1 = new Game("player1");

        game1.rotateRightCircle(true);

        Game game2 = new Game("player2");

        game2.rotateBottomCircle(false);

        //Rotated circles for saving

        game2.loadInGame(game1.getCurrentGameState());

        assertEquals(game1.getPlayerName(), game2.getPlayerName());
        assertEquals(game1.getRotations(), game2.getRotations());
        assertArrayEquals(game1.getFlower().getLeftCircle(), game2.getFlower().getLeftCircle());
        assertArrayEquals(game1.getFlower().getRightCircle(), game2.getFlower().getRightCircle());
        assertArrayEquals(game1.getFlower().getBottomCircle(), game2.getFlower().getBottomCircle());
    }

    @Test
    void checkWinTest() {
        Game game = new Game("player");
        StateSaver saver = new StateSaver();

        //A configuration where you can rotate the left circle to the left side and win
        int[] winningLeft = {4, 1, 2, 6, 9, 8};
        int[] winningRight = {1, 3, 7, 10, 6, 5};
        int[] winningBottom = {5, 2, 10, 12, 11, 9};

        game.getFlower().setLeftCircle(winningLeft);
        game.getFlower().setRightCircle(winningRight);
        game.getFlower().setBottomCircle(winningBottom);

        //Rotate left to left side
        game.rotateLeftCircle(true);
        game.rotateLeftCircle(false);
        game.rotateLeftCircle(false);

        assertTrue(game.getWin());
        assertEquals(saver.loadWinStates()[0].playerName, game.getPlayerName());
        assertEquals(saver.loadWinStates()[0].rotations, game.getRotations());
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11})
    void getElementTest(int index) {
        Game game = new Game("player");

        int[] left = {4, 1, 2, 6, 9, 8};
        int[] right = {1, 3, 7, 10, 6, 5};
        int[] bottom = {5, 2, 10, 12, 11, 9};

        //Made a result array manually
        int[] resultFromThese = {4,1,3,8,5,2,7,9,6,10,11,12};

        game.getFlower().setLeftCircle(left);
        game.getFlower().setRightCircle(right);
        game.getFlower().setBottomCircle(bottom);

        //Rotate back and forth to make the game refill its result array
        game.rotateLeftCircle(true);
        game.rotateLeftCircle(false);

        assertEquals(resultFromThese[index], game.getElement(index));
    }
}