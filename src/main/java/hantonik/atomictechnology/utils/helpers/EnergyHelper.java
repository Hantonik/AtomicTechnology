package hantonik.atomictechnology.utils.helpers;

public final class EnergyHelper {
    public static float calculateEnergy(long value) {
        String sNum = String.valueOf(value);

        float tempFloat;

        if (sNum.length() > 0 && sNum.length() < 4)
            return value;

        else if (sNum.length() > 3 && sNum.length() < 7) {
            tempFloat = value;

            tempFloat /= 1_000;

            tempFloat *= 100;
            tempFloat = Math.round(tempFloat);
            tempFloat /= 100;

            return tempFloat;
        } else if (sNum.length() > 6 && sNum.length() < 10) {
            tempFloat = value;

            tempFloat /= 1_000_000;

            tempFloat *= 100;
            tempFloat = Math.round(tempFloat);
            tempFloat /= 100;

            return tempFloat;
        } else if (sNum.length() > 9 && sNum.length() < 13) {
            tempFloat = value;

            tempFloat /= 1_000_000_000;

            tempFloat *= 100;
            tempFloat = Math.round(tempFloat);
            tempFloat /= 100;

            return tempFloat;
        } else if (sNum.length() > 12) {
            tempFloat = value;

            tempFloat /= 1_000_000_000_000L;

            tempFloat *= 100;
            tempFloat = Math.round(tempFloat);
            tempFloat /= 100;

            return tempFloat;
        }

        return 0.0F;
    }
}
