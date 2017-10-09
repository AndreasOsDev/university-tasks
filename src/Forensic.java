import java.util.Random;

public class Forensic {

    /**
     * Compute the time in hours required for a body to cool down to temperature
     * degrees. Gaussian noise is added to simulate parameter uncertainty.
     * @param temperature The temperature of the body when found.
     * @return the time in hours required for the body to cool down to
     * temperature degrees.
     */
    public static double cooldown(double temperature) {
        // we need this object to generate Gaussian random variables
        // (remember to import java.util.Random)
        Random random = new Random();

        // the average body temperature of a (living) human
        double bodyTemperature = 37;

        // add noise to simulate that the body temperature of the victim at the
        // time of death is uncertain
        bodyTemperature += random.nextGaussian();

        // compute the time required for the body to cool down from
        // bodyTemperature to temperature using Newton's law of cooling.
        double cooldownTime = Math.log(bodyTemperature / temperature);
        cooldownTime *= 1 / bodyTemperature;

        // normalize this value such that cooling down from 37 to 32 degrees
        // takes 1 hour. we assume that we have measured this for the
        // environment that the body is found in. we add Gaussian noise to
        // simulate measurement uncertainty.
        cooldownTime *= 255 + random.nextGaussian();

        return cooldownTime;
    }

    public static void main(String args[]) {

    }
}
