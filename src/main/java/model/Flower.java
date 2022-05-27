package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code Flower} class represents the flower formation visualized in the game's main scene.
 * It has 3 arrays representing the 3 circles in the formation.
 * <p>
 *     The circles are rotated with an {@link ArrayShifter} towards both directions.
 * </p>
 */
public class Flower
{
    private int[] leftCircle, rightCircle, bottomCircle;
    private int[] leftOverlaps, rightOverlaps, bottomOverlaps;

    private final ArrayShifter shifter;

    /**
     * Creates a new Flower instance.
     * <p>
     *     This new instance has every value set to a random number between 1 and 12 and
     *     the overlaps set to a default starting state.
     * </p>
     */
    public Flower() {
        leftCircle  = new int[6];
        rightCircle = new int[6];
        bottomCircle = new int[6];

        shifter = new ArrayShifter();

        generateStartingOverlaps();

        fillTheFlower();
    }

    /**
     *
     * @return the array representing the circle on the left side.
     */
    public int[] getLeftCircle() { return leftCircle; }

    /**
     *
     * @return the array representing the circle on the right side.
     */
    public int[] getRightCircle() { return rightCircle; }

    /**
     *
     * @return the array representing the circle at the bottom.
     */
    public int[] getBottomCircle() { return bottomCircle; }

    /**
     * Sets the value of the array representing the circle on the left side.
     * @param array the array to be copied into the array representing the circle on the left side.
     */
    public void setLeftCircle(int[] array) { leftCircle = array; }

    /**
     * Sets the value of the array representing the circle on the right side.
     * @param array the array to be copied into the array representing the circle on the right side.
     */
    public void setRightCircle(int[] array) { rightCircle = array; }

    /**
     * Sets the value of the array representing the circle on the left side.
     * @param array the array to be copied into the array representing the circle on the left side.
     */
    public void setBottomCircle(int[] array) { bottomCircle = array; }

    /**
     * Shifts the array representing the circle on the left side and refills it's overlapping indices with new values.
     * @param right true means the array and the overlaps get shifted to the right or to the left otherwise.
     */
    public void rotateLeft(boolean right) {
        shiftLeftSide(right);

        replaceOverLapsOfLeft();

        shiftOverlapsOfLeft(right);
    }

    /**
     * Shifts the array representing the circle on the right side and refills it's overlapping indices with new values.
     * @param right true means the array and the overlaps get shifted to the right or to the left otherwise.
     */
    public void rotateRight(boolean right) {
        shiftRightSide(right);

        replaceOverlapsOfRight();

        shiftOverlapsOfRight(right);
    }

    /**
     * Shifts the array representing the circle at the bottom and refills it's overlapping indices with new values.
     * @param right true means the array and the overlaps get shifted to the right or to the left otherwise.
     */
    public void rotateBottom(boolean right) {
        shiftBottom(right);

        replaceOverlapsOfBottom();

        shiftOverlapsOfBottom(right);
    }

    /**
     *
     * @param index the index of the desired value
     * @return the array representing the indices of the overlapping elements of the circle on the left side.
     */
    public int getLeftOverlap(int index) { return leftOverlaps[index]; }

    /**
     *
     * @param index the index of the desired value
     * @return the array representing the indices of the overlapping elements of the circle on the right side.
     */
    public int getRightOverlap(int index) { return rightOverlaps[index]; }

    /**
     *
     * @param index the index of the desired value
     * @return the array representing the indices of the overlapping elements of the circle at the bottom.
     */
    public int getBottomOverlap(int index) { return bottomOverlaps[index]; }

    /**
     * Shifts the array representing the indices of the overlapping elements of the circle on the left side.
     * @param right true means the overlaps get shifted to the right or to the left otherwise.
     */
    private void shiftOverlapsOfLeft(boolean right) { shifter.shiftOverlaps(leftOverlaps, right); }

    /**
     * Shifts the array representing the indices of the overlapping elements of the circle on the right side.
     * @param right true means the overlaps get shifted to the right or to the left otherwise.
     */
    private void shiftOverlapsOfRight(boolean right) { shifter.shiftOverlaps(rightOverlaps, right); }

    /**
     * Shifts the array representing the indices of the overlapping elements of the circle at the bottom.
     * @param right true means the overlaps get shifted to the right or to the left otherwise.
     */
    private void shiftOverlapsOfBottom(boolean right) { shifter.shiftOverlaps(bottomOverlaps, right); }

    private void shiftLeftSide(boolean right) {
        if(right)
            leftCircle = shifter.shiftArrayRight(leftCircle);
        else
            leftCircle = shifter.shiftArrayLeft(leftCircle);
    }

    private void shiftRightSide(boolean right) {
        if(right)
            rightCircle = shifter.shiftArrayRight(rightCircle);
        else
            rightCircle = shifter.shiftArrayLeft(rightCircle);
    }

    private void shiftBottom(boolean right) {
        if(right)
            bottomCircle = shifter.shiftArrayRight(bottomCircle);
        else
            bottomCircle = shifter.shiftArrayLeft(bottomCircle);
    }

    private void generateStartingOverlaps() {
        leftOverlaps = new int[]{1, 2, 3, 4};
        rightOverlaps = new int[]{3, 4, 5, 0};
        bottomOverlaps = new int[]{5, 0, 1, 2};
    }

    private void replaceOverLapsOfLeft() {
        bottomCircle[5] = leftCircle[4];
        bottomCircle[1] = leftCircle[2];
        rightCircle[0] = leftCircle[1];
        rightCircle[4] = leftCircle[3];
    }

    private void replaceOverlapsOfRight() {
        bottomCircle[0] = rightCircle[5];
        bottomCircle[2] = rightCircle[3];
        leftCircle[1] = rightCircle[0];
        leftCircle[3] = rightCircle[4];
    }

    private void replaceOverlapsOfBottom() {
        leftCircle[2] = bottomCircle[1];
        leftCircle[4] = bottomCircle[5];
        rightCircle[3] = bottomCircle[2];
        rightCircle[5] = bottomCircle[0];
    }

    private void fillTheFlower() {
        ArrayList<Integer> randoms = new ArrayList<>();
        Random rand = new Random();

        int randomInt = rand.nextInt(1,13);

        for(int i = 0; i < 12; i++) {

            while(randoms.contains(randomInt))
                randomInt = rand.nextInt(1,13);

            switch(i) {
                case 0 -> leftCircle[i] = randomInt;
                case 1 -> {
                    leftCircle[i] = randomInt;
                    rightCircle[0] = randomInt;
                }
                case 2 ->  rightCircle[1] = randomInt;
                case 3 -> leftCircle[5] = randomInt;
                case 4 -> {
                    rightCircle[5] = randomInt;
                    bottomCircle[0] = randomInt;
                }
                case 5 -> {
                    leftCircle[2] = randomInt;
                    bottomCircle[1] = randomInt;
                }
                case 6 ->  rightCircle[2] = randomInt;
                case 7 -> {
                    leftCircle[4] = randomInt;
                    bottomCircle[5] = randomInt;
                }
                case 8 -> {
                    leftCircle[3] = randomInt;
                    rightCircle[4] = randomInt;
                }
                case 9 -> {
                    rightCircle[3] = randomInt;
                    bottomCircle[2] = randomInt;
                }
                case 10 -> bottomCircle[4] = randomInt;
                case 11 -> bottomCircle[3] = randomInt;
            }

            randoms.add(randomInt);
        }
    }
}