package hantonik.atomictechnology.registry.meltable;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.init.AtomicBlocks;
import hantonik.atomictechnology.init.AtomicItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MeltableItemsRegistry {
    private static final Logger LOGGER = LogManager.getLogger(AtomicTechnology.NAME);
    private static final MeltableItemsRegistry INSTANCE = new MeltableItemsRegistry();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    private final List<MeltableItem> meltableItems = new ArrayList<>();

    public void loadItems() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        File dir = FMLPaths.CONFIGDIR.get().resolve("atomictechnology/meltingTemperature/").toFile();

        if (!dir.exists() && dir.mkdirs()) {
            for (MeltableItem meltableItem : defaults()) {
                JsonObject json = MeltableItemUtils.writeToJson(meltableItem);
                FileWriter writer = null;

                try {
                    File file = new File(dir, meltableItem.getId().getPath() + ".json");
                    writer = new FileWriter(file);
                    GSON.toJson(json, writer);
                    writer.close();
                } catch (Exception e) {
                    LOGGER.error("An error occurred while generating default meltable items", e);
                } finally {
                    IOUtils.closeQuietly(writer);
                }
            }
        }

        if (!dir.mkdirs() && dir.isDirectory())
            this.loadFiles(dir);

        stopwatch.stop();
        LOGGER.info("Loaded {} meltable item type(s) in {} ms", this.meltableItems.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public List<MeltableItem> getMeltableItems() {
        return this.meltableItems;
    }

    public MeltableItem getMeltableItemById(ResourceLocation id) {
        return this.meltableItems.stream().filter(s -> id.equals(s.getId())).findFirst().orElse(null);
    }

    private void loadFiles(File dir) {
        File[] files = dir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));

        if (files == null)
            return;

        for (File file : files) {
            JsonObject json;
            FileReader reader = null;
            MeltableItem meltableItem = null;

            try {
                JsonParser parser = new JsonParser();
                reader = new FileReader(file);
                json = parser.parse(reader).getAsJsonObject();
                String name = file.getName().replace(".json", "");
                meltableItem = MeltableItemUtils.loadFromJson(new ResourceLocation(AtomicTechnology.MOD_ID, name), json);

                reader.close();
            } catch (Exception e) {
                LOGGER.error("An error occurred while generating default meltable items", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }

            if (meltableItem != null) {
                ResourceLocation id = meltableItem.getId();

                this.meltableItems.removeIf(s -> id.equals(s.getId()));
                this.meltableItems.add(meltableItem);
            }
        }
    }

    public static MeltableItemsRegistry getInstance() {
        return INSTANCE;
    }

    private static List<MeltableItem> defaults() {
        return Lists.newArrayList(
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "iron_ingot"), Items.IRON_INGOT, 1_538),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "gold_ingot"), Items.GOLD_INGOT, 1_064),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "redstone"), Items.REDSTONE, 2_200),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "netherite_ingot"), Items.NETHERITE_INGOT, 2_500),

                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "steel_ingot"), AtomicItems.STEEL_INGOT.get(), 2_750),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "silver_ingot"), AtomicItems.SILVER_INGOT.get(), 962),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "copper_ingot"), AtomicItems.COPPER_INGOT.get(), 1_085),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "lead_ingot"), AtomicItems.LEAD_INGOT.get(), 328),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "titanium_ingot"), AtomicItems.TITANIUM_INGOT.get(), 1_668),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "aluminium_ingot"), AtomicItems.ALUMINIUM_INGOT.get(), 660),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "silicon_ingot"), AtomicItems.SILICON.get(), 1214),

                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "iron_block"), Items.IRON_BLOCK, 1_538 * 3),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "gold_block"), Items.GOLD_BLOCK, 1_064 * 3),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "redstone_block"), Items.IRON_INGOT, 2_200 * 3),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "netherite_block"), Blocks.NETHERITE_BLOCK, 2_500 * 3),

                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "steel_block"), AtomicBlocks.STEEL_BLOCK.get(), 2_750 * 3),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "silver_block"), AtomicBlocks.SILVER_BLOCK.get(), 962 * 3),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "copper_block"), AtomicBlocks.COPPER_BLOCK.get(), 1_085 * 3),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "lead_block"), AtomicBlocks.LEAD_BLOCK.get(), 328 * 3),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "titanium_block"), AtomicBlocks.TITANIUM_BLOCK.get(), 1_668 * 3),
                new MeltableItem(new ResourceLocation(AtomicTechnology.MOD_ID, "aluminium_block"), AtomicBlocks.ALUMINIUM_BLOCK.get(), 660 * 3)
        );
    }
}
