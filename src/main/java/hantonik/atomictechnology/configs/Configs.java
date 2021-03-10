package hantonik.atomictechnology.configs;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.utils.helpers.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Configs {
    private static final ForgeConfigSpec GENERAL;

    public static final ForgeConfigSpec.IntValue BASIC_SMELTER_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue BASIC_SMELTER_RF_RATE;

    public static final ForgeConfigSpec.IntValue ELITE_SMELTER_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue ELITE_SMELTER_RF_RATE;

    public static final ForgeConfigSpec.IntValue ADVANCED_SMELTER_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue ADVANCED_SMELTER_RF_RATE;

    public static final ForgeConfigSpec.IntValue ATOMIC_SMELTER_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue ATOMIC_SMELTER_RF_RATE;

    static {
        final ForgeConfigSpec.Builder general = new ForgeConfigSpec.Builder();

        general.push("General");

        general.push("BasicSmelter");
        BASIC_SMELTER_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + "basic_smelter_rf_capacity")
                .defineInRange("BasicSmelterRFCapacity", 50_000, 0, Integer.MAX_VALUE);
        BASIC_SMELTER_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + "basic_smelter_rf_rate")
                .defineInRange("BasicSmelterRFRate", 500, 0, Integer.MAX_VALUE);
        general.pop();

        general.push("EliteSmelter");
        ELITE_SMELTER_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + "elite_smelter_rf_capacity")
                .defineInRange("EliteSmelterRFCapacity", 500_000, 0, Integer.MAX_VALUE);
        ELITE_SMELTER_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + "elite_smelter_rf_rate")
                .defineInRange("EliteSmelterRFRate", 1_000, 0, Integer.MAX_VALUE);
        general.pop();

        general.push("AdvancedSmelter");
        ADVANCED_SMELTER_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + "advanced_smelter_rf_capacity")
                .defineInRange("AdvancedSmelterRFCapacity", 5_000_000, 0, Integer.MAX_VALUE);
        ADVANCED_SMELTER_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + "advanced_smelter_rf_rate")
                .defineInRange("AdvancedSmelterRFRate", 10_000, 0, Integer.MAX_VALUE);
        general.pop();

        general.push("AtomicSmelter");
        ATOMIC_SMELTER_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + "atomic_smelter_rf_capacity")
                .defineInRange("AtomicSmelterRFCapacity", 500_000_000, 0, Integer.MAX_VALUE);
        ATOMIC_SMELTER_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + "atomic_smelter_rf_rate")
                .defineInRange("AtomicSmelterRFRate", 1_000_000, 0, Integer.MAX_VALUE);
        general.pop();

        GENERAL = general.build();
    }

    public static void setup() {
        try {
            Files.createDirectory(Paths.get(FMLPaths.CONFIGDIR.get().toAbsolutePath().toString(), AtomicTechnology.MOD_ID));
        } catch (FileAlreadyExistsException e) {
            AtomicTechnology.LOGGER.warn(new MarkerManager.Log4jMarker("CONFIG"), AtomicTechnology.NAME + " config directory already exist.");
        } catch (IOException e) {
            AtomicTechnology.LOGGER.error(new MarkerManager.Log4jMarker("CONFIG"), "Failed to create " + AtomicTechnology.NAME + " config directory.", e);
        }

        ConfigHelper.load(GENERAL, AtomicTechnology.MOD_ID + "/general.toml");
    }
}
