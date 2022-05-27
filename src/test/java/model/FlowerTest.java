package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FlowerTest
{

    @Test
    void setLeftCircle()
    {
        Flower flower = new Flower();

        int[] newLeft = {1,2,3,4};

        flower.setLeftCircle(newLeft);

        assertArrayEquals(newLeft, flower.getLeftCircle());
    }

    @Test
    void setRightCircle()
    {
        Flower flower = new Flower();

        int[] newLeft = {1,2,3,4};

        flower.setRightCircle(newLeft);

        assertArrayEquals(newLeft, flower.getRightCircle());
    }

    @Test
    void setBottomCircle()
    {
        Flower flower = new Flower();

        int[] newLeft = {1,2,3,4};

        flower.setBottomCircle(newLeft);

        assertArrayEquals(newLeft, flower.getBottomCircle());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void rotateLeft(boolean direction)
    {
        Flower flower = new Flower();
        ArrayShifter shifter = new ArrayShifter();

        int[] left = flower.getLeftCircle();

        int[] overlapsLeft = {
                flower.getLeftOverlap(0),
                flower.getLeftOverlap(1),
                flower.getLeftOverlap(2),
                flower.getLeftOverlap(3)
        };

        if(direction)
            left = shifter.shiftArrayRight(left);
        else
            left = shifter.shiftArrayLeft(left);

        shifter.shiftOverlaps(overlapsLeft, direction);
        flower.rotateLeft(direction);

        int[] nextOverlapsOfLeft = {
                flower.getLeftOverlap(0),
                flower.getLeftOverlap(1),
                flower.getLeftOverlap(2),
                flower.getLeftOverlap(3)
        };

        assertArrayEquals(left, flower.getLeftCircle());
        assertArrayEquals(overlapsLeft, nextOverlapsOfLeft);
        assertEquals(left[4], flower.getBottomCircle()[5]);
        assertEquals(left[2], flower.getBottomCircle()[1]);
        assertEquals(left[1], flower.getRightCircle()[0]);
        assertEquals(left[3], flower.getRightCircle()[4]);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void rotateRight(boolean direction)
    {
        Flower flower = new Flower();
        ArrayShifter shifter = new ArrayShifter();

        int[] right = flower.getRightCircle();

        int[] overlapsRight = {
                flower.getRightOverlap(0),
                flower.getRightOverlap(1),
                flower.getRightOverlap(2),
                flower.getRightOverlap(3)
        };

        if(direction)
            right = shifter.shiftArrayRight(right);
        else
            right = shifter.shiftArrayLeft(right);

        shifter.shiftOverlaps(overlapsRight, direction);
        flower.rotateRight(direction);

        int[] nextOverlapsOfRight = {
                flower.getRightOverlap(0),
                flower.getRightOverlap(1),
                flower.getRightOverlap(2),
                flower.getRightOverlap(3)
        };

        assertArrayEquals(right, flower.getRightCircle());
        assertArrayEquals(overlapsRight, nextOverlapsOfRight);
        assertEquals(right[5], flower.getBottomCircle()[0]);
        assertEquals(right[3], flower.getBottomCircle()[2]);
        assertEquals(right[0], flower.getLeftCircle()[1]);
        assertEquals(right[4], flower.getLeftCircle()[3]);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void rotateBottom(boolean direction)
    {
        Flower flower = new Flower();
        ArrayShifter shifter = new ArrayShifter();

        int[] bottom = flower.getBottomCircle();

        int[] overlapsBottom = {
                flower.getBottomOverlap(0),
                flower.getBottomOverlap(1),
                flower.getBottomOverlap(2),
                flower.getBottomOverlap(3)
        };

        if(direction)
            bottom = shifter.shiftArrayRight(bottom);
        else
            bottom = shifter.shiftArrayLeft(bottom);

        shifter.shiftOverlaps(overlapsBottom, direction);
        flower.rotateBottom(direction);

        int[] nextOverlapsOfBottom = {
                flower.getBottomOverlap(0),
                flower.getBottomOverlap(1),
                flower.getBottomOverlap(2),
                flower.getBottomOverlap(3)
        };

        assertArrayEquals(bottom, flower.getBottomCircle());
        assertArrayEquals(overlapsBottom, nextOverlapsOfBottom);
        assertEquals(bottom[1], flower.getLeftCircle()[2]);
        assertEquals(bottom[5], flower.getLeftCircle()[4]);
        assertEquals(bottom[2], flower.getRightCircle()[3]);
        assertEquals(bottom[0], flower.getRightCircle()[5]);
    }
}