package hantonik.atomictechnology.configs;

import hantonik.atomiccore.utils.helpers.ConfigHelper;
import hantonik.atomictechnology.AtomicTechnology;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Configs {
    private static final ForgeConfigSpec GENERAL;

    public static final ForgeConfigSpec.BooleanValue ENABLE_DEFAULT_RECIPES;
    public static final ForgeConfigSpec.IntValue DEFAULT_ITEM_MELTING_TEMPERATURE;

    public static final ForgeConfigSpec.IntValue BASIC_SMELTER_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue BASIC_SMELTER_RF_RATE;

    public static final ForgeConfigSpec.IntValue ELITE_SMELTER_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue ELITE_SMELTER_RF_RATE;

    public static final ForgeConfigSpec.IntValue ADVANCED_SMELTER_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue ADVANCED_SMELTER_RF_RATE;

    public static final ForgeConfigSpec.IntValue ATOMIC_SMELTER_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue ATOMIC_SMELTER_RF_RATE;

    public static final ForgeConfigSpec.IntValue BASIC_COMPRESSOR_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue BASIC_COMPRESSOR_RF_RATE;

    public static final ForgeConfigSpec.IntValue ELITE_COMPRESSOR_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue ELITE_COMPRESSOR_RF_RATE;

    public static final ForgeConfigSpec.IntValue ADVANCED_COMPRESSOR_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue ADVANCED_COMPRESSOR_RF_RATE;

    public static final ForgeConfigSpec.IntValue ATOMIC_COMPRESSOR_RF_CAPACITY;
    public static final ForgeConfigSpec.IntValue ATOMIC_COMPRESSOR_RF_RATE;

    public static final ForgeConfigSpec.IntValue BASIC_ENERGY_BANK_CAPACITY;

    static {
        final ForgeConfigSpec.Builder general = new ForgeConfigSpec.Builder();

        general.push("General");
        general.pop();

        general.push("Settings");
        ENABLE_DEFAULT_RECIPES = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".enable_default_recipes")
                .define("EnableDefaultRecipes", true);
        DEFAULT_ITEM_MELTING_TEMPERATURE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".default_item_melting_temperature")
                .defineInRange("DefaultItemMeltingTemperature", 1000, Integer.MIN_VALUE, Integer.MAX_VALUE);
        general.pop();

        general.push("Smelter");
        BASIC_SMELTER_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".basic_smelter_rf_capacity")
                .defineInRange("BasicSmelterRFCapacity", 50_000, 0, Integer.MAX_VALUE);
        BASIC_SMELTER_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".basic_smelter_rf_rate")
                .defineInRange("BasicSmelterRFRate", 500, 0, Integer.MAX_VALUE);

        ELITE_SMELTER_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".elite_smelter_rf_capacity")
                .defineInRange("EliteSmelterRFCapacity", 500_000, 0, Integer.MAX_VALUE);
        ELITE_SMELTER_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".elite_smelter_rf_rate")
                .defineInRange("EliteSmelterRFRate", 1_000, 0, Integer.MAX_VALUE);

        ADVANCED_SMELTER_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".advanced_smelter_rf_capacity")
                .defineInRange("AdvancedSmelterRFCapacity", 5_000_000, 0, Integer.MAX_VALUE);
        ADVANCED_SMELTER_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".advanced_smelter_rf_rate")
                .defineInRange("AdvancedSmelterRFRate", 10_000, 0, Integer.MAX_VALUE);

        ATOMIC_SMELTER_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".atomic_smelter_rf_capacity")
                .defineInRange("AtomicSmelterRFCapacity", 500_000_000, 0, Integer.MAX_VALUE);
        ATOMIC_SMELTER_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".atomic_smelter_rf_rate")
                .defineInRange("AtomicSmelterRFRate", 1_000_000, 0, Integer.MAX_VALUE);
        general.pop();

        general.push("Compressor");
        BASIC_COMPRESSOR_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".basic_compressor_rf_capacity")
                .defineInRange("BasicCompressorRFCapacity", 50_000, 0, Integer.MAX_VALUE);
        BASIC_COMPRESSOR_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".basic_compressor_rf_rate")
                .defineInRange("BasicCompressorRFRate", 500, 0, Integer.MAX_VALUE);

        ELITE_COMPRESSOR_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".elite_compressor_rf_capacity")
                .defineInRange("EliteCompressorRFCapacity", 500_000, 0, Integer.MAX_VALUE);
        ELITE_COMPRESSOR_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".elite_compressor_rf_rate")
                .defineInRange("EliteCompressorRFRate", 1_000, 0, Integer.MAX_VALUE);

        ADVANCED_COMPRESSOR_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".advanced_compressor_rf_capacity")
                .defineInRange("AdvancedCompressorRFCapacity", 5_000_000, 0, Integer.MAX_VALUE);
        ADVANCED_COMPRESSOR_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + "advanced_compressor_rf_rate")
                .defineInRange("AdvancedCompressorRFRate", 10_000, 0, Integer.MAX_VALUE);

        ATOMIC_COMPRESSOR_RF_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".atomic_compressor_rf_capacity")
                .defineInRange("AtomicCompressorRFCapacity", 500_000_000, 0, Integer.MAX_VALUE);
        ATOMIC_COMPRESSOR_RF_RATE = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".atomic_compressor_rf_rate")
                .defineInRange("AtomicCompressorRFRate", 1_000_000, 0, Integer.MAX_VALUE);
        general.pop();

        general.push("EnergyBank");
        BASIC_ENERGY_BANK_CAPACITY = general
                .translation("configGui." + AtomicTechnology.MOD_ID + ".basic_energy_bank_rf_capacity")
                .defineInRange("BasicEnergyBankRFCapacity", 2_500_000, Integer.MIN_VALUE, Integer.MAX_VALUE);
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

        ConfigHelper.load(GENERAL, AtomicTechnology.MOD_ID + "/config.toml");
    }
}
