public class humanCooldown {
    private final static int numSamples = 100000;
    private final static int numRanges = 20;
    private final static int temperature = 27;
    public static void main(String[] args) {
        double[] array = cooldownSamples(temperature, numSamples);
        double[] counts = countsFromArray(array, numRanges);
        String[][] array2d = array2dFromCounts(counts);
        printReport(array2d, minFromArray(array), maxFromArray(array));
    }

    private static void printReport(String[][] array2d, double minValue, double maxValue) {
        double timeRange = (maxValue-minValue)/numRanges;
        double percentValue = ((float) numRanges/numSamples)*100;
        System.out.format("Time of death probability distribution\n" +
                "- Each line corresponds to %.2f hours.\n" +
                "- Each # corresponds to %.2f percentage units.\n " +
                "===============================================\n", timeRange,percentValue);
        System.out.println();
        printArray2d(array2d);
    }

    private static double[] cooldownSamples(int temperature, int numSamples) {
        double tempCooldown[] = new double[numSamples];
        for (int i = 0; i < numSamples; i++) {
            tempCooldown[i] = Forensic.cooldown(temperature);
        }
        return tempCooldown;
    }
    private static double[] countsFromArray(double[] array, int numRanges){
        double[] counts = new double[numRanges];
        double maxValue = maxFromArray(array);
        double minValue = minFromArray(array);
        double rangeSize = (maxValue-minValue)/numRanges;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < numRanges; j++) {
                if (j * rangeSize <= array[i] - minValue && array[i] - minValue < rangeSize * (j + 1)) {
                    counts[j] += 1.0;
                }
            }
            if(array[i]==maxValue){
                counts[counts.length-1] += 1;
            }
        }
        return counts;
    }
    private static String[][] array2dFromCounts(double[] counts){
        final int PRINT_WIDTH = 50;
        int maxValue = (int) maxFromArray(counts);
        String [][] array2d = new String[counts.length][PRINT_WIDTH];
        for (int i = 0; i < array2d.length; i++) {
            for (int j = 0; j < array2d[i].length; j++) {
                if (j<((counts[i]*PRINT_WIDTH)/maxValue)){
                    array2d[i][j] = "#";
                }
                else{
                    array2d[i][j] = " ";
                }
            }
        }
        return array2d;
    }
    private static void printArray2d(String[][] array2d){
        for (String[] anArray2d : array2d) {
            for (String anAnArray2d : anArray2d) {
                System.out.print(anAnArray2d);
            }
            System.out.println();
        }
    }
    private static double minFromArray(double[] array){
        double minvalue = array[0];
        for (int i = 1; i < array.length; i++) {
            if(minvalue > array[i]){
                minvalue = array[i];
            }
        }
        return minvalue;
    }
    private static double maxFromArray(double[] array){
        double maxvalue = array[0];
        for (int i = 1; i < array.length; i++) {
            if(maxvalue < array[i]){
                maxvalue = array[i];
            }
        }
        return maxvalue;
    }
}
