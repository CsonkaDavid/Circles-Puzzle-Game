package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayShifterTest
{

    @Test
    void shiftArrayLeftOnSmall()
    {
        ArrayShifter shifter = new ArrayShifter();

        int[] someArray = {1,2,3};
        int[] shiftedArray = {2,3,1};
        int[] newArray = shifter.shiftArrayLeft(someArray);

        assertArrayEquals(shiftedArray, newArray);
    }

    @Test
    void shiftArrayRightOnSmall()
    {
        ArrayShifter shifter = new ArrayShifter();

        int[] someArray = {1,2,3};
        int[] shiftedArray = {3,1,2};
        int[] newArray = shifter.shiftArrayRight(someArray);

        assertArrayEquals(shiftedArray, newArray);
    }

    @Test
    void shiftArrayLeftOnLarge()
    {
        ArrayShifter shifter = new ArrayShifter();

        int[] someArray = {3,7,1,3,5,8,2,9,12,23,53,1,2,76};
        int[] shiftedArray = {7,1,3,5,8,2,9,12,23,53,1,2,76,3};
        int[] newArray = shifter.shiftArrayLeft(someArray);

        assertArrayEquals(shiftedArray, newArray);
    }

    @Test
    void shiftArrayRightOnLarge()
    {
        ArrayShifter shifter = new ArrayShifter();

        int[] someArray = {3,7,1,3,5,8,2,9,12,23,53,1,2,76};
        int[] shiftedArray = {76,3,7,1,3,5,8,2,9,12,23,53,1,2};
        int[] newArray = shifter.shiftArrayRight(someArray);

        assertArrayEquals(shiftedArray, newArray);
    }

    @Test
    void shiftOverlapsLeft()
    {
        ArrayShifter shifter = new ArrayShifter();
        int[] someArray = {1,2,3};
        int[] shiftedArray = {2,3,4};
        shifter.shiftOverlaps(someArray,false);

        assertArrayEquals(shiftedArray, someArray);
    }

    @Test
    void shiftOverlapsRight()
    {
        ArrayShifter shifter = new ArrayShifter();
        int[] someArray = {1,2,3};
        int[] shiftedArray = {0,1,2};
        shifter.shiftOverlaps(someArray,true);

        assertArrayEquals(shiftedArray, someArray);
    }

    @Test
    void shiftOverlapsWithZeroChange()
    {
        ArrayShifter shifter = new ArrayShifter();
        int[] someArray = {0,2,3};
        int[] shiftedArray = {5,1,2};
        shifter.shiftOverlaps(someArray,true);

        assertArrayEquals(shiftedArray, someArray);
    }

    @Test
    void shiftOverlapsWithFiveChange()
    {
        ArrayShifter shifter = new ArrayShifter();
        int[] someArray = {5,2,3};
        int[] shiftedArray = {0,3,4};
        shifter.shiftOverlaps(someArray,false);

        assertArrayEquals(shiftedArray, someArray);
    }
}