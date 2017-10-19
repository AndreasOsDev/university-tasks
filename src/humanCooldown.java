import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class humanCooldown {
    private final static int NUM_SAMPLES = 100000; //Defining final variables here. Normally I would let users define it
    private final static int NUM_RANGES = 20;      //I think it's bad form to have plain numbers in methods
    private final static int TEMPERATURE = 27;

    public static void main(String[] args) {
        double[] array = cooldownSamples(TEMPERATURE, NUM_SAMPLES); //Generates NUM_SAMPLES amount of possible cooldowns
        double[] counts = countsFromArray(array, NUM_RANGES); //Lists amount of hits in each timeRange
        String[][] array2d = array2dFromCounts(counts); //converts into a 2d array
        printToFile(array2d, minFromArray(array), maxFromArray(array)); //Prints results both into console and into file.
    }

    private static String GenerateOutput(String[][] array2d, double minValue, double maxValue) {
        double timeRange = (maxValue - minValue) / NUM_RANGES;
        double percentValue = ((float) NUM_RANGES / NUM_SAMPLES) * 100;
        return String.format("Time of death probability distribution \n" +
                        "-Each line corresponds to %.2f hours.\n" +
                        "- Each # corresponds to %.2f percentage units.\n" +
                        "===============================================\n" +
                        "The minimum time for the body to cool is %.2f hours\n\n" +
                        "%s\n\n" +
                        "The maximum time for the body to cool is %.2f hours\n" +
                        "===============================================",
                timeRange, percentValue, minValue, printArray2d(array2d), maxValue); //Mimicked example in task for decimal formatting
    }

    private static double[] cooldownSamples(int temperature, int numSamples) {//Creates and array of numSamples amount of cooldowns drawn from the provided Forensic class.
        double tempCooldown[] = new double[numSamples];
        for (int i = 0; i < numSamples; i++) {
            tempCooldown[i] = Forensic.cooldown(temperature);
        }
        return tempCooldown;
    }

    private static double[] countsFromArray(double[] array, int numRanges) { //Turns an array of the cooldowns into an array showing hits in each range.
        double[] counts = new double[numRanges];
        double maxValue = maxFromArray(array);
        double minValue = minFromArray(array);
        double rangeSize = (maxValue - minValue) / numRanges;
        for (int i = 0; i < NUM_SAMPLES; i++) {
            for (int j = 0; j < numRanges; j++) {//Assigning amount of hits in each timeRange.
                if (j * rangeSize <= array[i] - minValue && array[i] - minValue < rangeSize * (j + 1)) {
                    counts[j] += 1.0;
                }
            }
            if (array[i] == maxValue) {//Safety net to catch maxValues, because my main if line doesn't.
                counts[counts.length - 1] += 1;//not included in main if since it made it extremely cluttered.
            }
        }
        return counts;
    }

    private static String[][] array2dFromCounts(double[] counts) { //converts counts[] into a 2d array.
        final int PRINT_WIDTH = 50;
        double maxValue = maxFromArray(counts);
        String[][] array2d = new String[counts.length][PRINT_WIDTH];
        for (int i = 0; i < array2d.length; i++) {
            for (int j = 0; j < array2d[i].length; j++) {
                if (j < ((counts[i] * PRINT_WIDTH) / maxValue)) {
                    array2d[i][j] = "#";
                } else {
                    array2d[i][j] = " ";
                }
            }
        }
        return array2d;
    }

    private static String printArray2d(String[][] array2d) {//Instead of printing it in the method, I instead return an output.
        StringBuilder output = new StringBuilder();         //This improves clarity in my PrintReport method.
        for (String[] line : array2d) {                     //Double for loops, first counting lines, then appending each element of that line.
            for (String lineElement : line) {
                output.append(lineElement);
            }
            output.append("\n");
        }
        return output.toString();
    }

    private static double minFromArray(double[] array) {
        double minvalue = array[0];
        for (int i = 1; i < array.length; i++) { //Simple for loop to find the min value in an array by simple comparison
            if (minvalue > array[i]) {
                minvalue = array[i];
            }
        }
        return minvalue;
    }

    private static double maxFromArray(double[] array) {
        double maxvalue = array[0];
        for (int i = 1; i < array.length; i++) { //Simple for loop to find the max value in an array by simple comparison
            if (maxvalue < array[i]) {
                maxvalue = array[i];
            }
        }
        return maxvalue;
    }

    private static void printToFile(String[][] array2d, double maxValue, double minValue) {
        PrintWriter writer = null;
        System.out.println(GenerateOutput(array2d, maxValue, minValue)); //This prints to console
        try {
            writer = new PrintWriter("Forensic_Results.txt", "UTF-8");
            writer.println(GenerateOutput(array2d, maxValue, minValue)); //This prints to file
        } catch (FileNotFoundException | UnsupportedEncodingException e) {//These errors wont happen, but my compiler demands I have catch methods for them anyways
            e.printStackTrace();
        } finally {
            if (writer != null) {//Writer will always be open at this point, but compiler demands it and it's good form to include anyway.
                writer.close();
            }
        }
    }
}