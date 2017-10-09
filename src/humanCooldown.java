import java.util.Arrays;

public class humanCooldown {
    public static void main(String[] args) {
        double[] cooldownSamples = cooldownSamples(27, 10);
        System.out.println(Arrays.toString(cooldownSamples));
    }

    public static double[] cooldownSamples(int temperature, int numsamples) {
        double tempCooldown[] = new double[numsamples];
        for (int i = 0; i < numsamples; i++) {
            tempCooldown[i] = Forensic.cooldown(temperature);
        }
        return tempCooldown;
    }
}
