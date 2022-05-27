package model;

/**
 * The {@code ArrayShifter} class can be used to shift arrays in both directions.
 * It's also used by the {@link Flower} class to shift the arrays representing the indices
 * of the overlapping elements in the flower formation.
 */
public class ArrayShifter
{
    /**
     * Shifts given array's elements in the left direction.
     * @param array array to be shifted.
     * @return the given array shifted to the left.
     */
    public int[] shiftArrayLeft(int[] array) {
        int length = array.length;
        int first = array[0];

        int[] newArray = new int[length];
        newArray[length-1] = first;

        System.arraycopy(array, 1, newArray, 0, array.length - 1);

        return newArray;
    }

    /**
     * Shifts given array's elements in the right direction.
     * @param array array to be shifted.
     * @return the given array shifted to the right.
     */
    public int[] shiftArrayRight(int[] array) {
        int length = array.length;
        int last = array[length-1];

        int[] newArray = new int[length];
        newArray[0] = last;

        System.arraycopy(array, 0, newArray, 1, array.length - 1);

        return newArray;
    }

    /**
     * Shifts the {@link Flower}'s overlapping elements on the given circle in either the right or the left direction.
     * @param overlaps an array representing one of the {@link Flower}'s arrays, containing indices of overlapping elements of a circle
     * @param right true means the array has to be shifted to the right or to the left otherwise.
     */
    public void shiftOverlaps(int[] overlaps, boolean right) {
        int i = 0;

        if(right) i--; else i++;

        for(int j = 0; j < overlaps.length; j++)
        {
            overlaps[j] += i;
            if(overlaps[j] < 0) overlaps[j] = 5;
            if(overlaps[j] > 5) overlaps[j] = 0;
        }
    }
}