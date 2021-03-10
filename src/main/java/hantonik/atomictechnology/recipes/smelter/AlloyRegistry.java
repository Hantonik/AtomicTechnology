package hantonik.atomictechnology.recipes.smelter;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.init.Blocks;
import hantonik.atomictechnology.init.Items;
import net.minecraft.item.crafting.Ingredient;
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

import static net.minecraft.item.Items.*;

public final class AlloyRegistry {
    private static final Logger LOGGER = LogManager.getLogger(AtomicTechnology.NAME);
    private static final AlloyRegistry INSTANCE = new AlloyRegistry();
    private static final Gson JSON = (new GsonBuilder()).setPrettyPrinting().create();

    private final List<Alloy> alloys = new ArrayList<>();

    public void loadAlloys() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        File dir = FMLPaths.CONFIGDIR.get().resolve("atomictechnology/smelter/").toFile();

        if (!dir.exists() && dir.mkdirs()) {
            for (Alloy alloy : defaults()) {
                JsonObject json = AlloyUtils.writeToJson(alloy);
                FileWriter writer = null;

                try {
                    File file = new File(dir, alloy.getId().getPath() + ".json");
                    AtomicTechnology.LOGGER.error(file.getPath());

                    writer = new FileWriter(file);
                    JSON.toJson(json, writer);
                    writer.close();
                } catch (Exception e) {
                    LOGGER.error("An error occurred while generating default alloys", e);
                } finally {
                    IOUtils.closeQuietly(writer);
                }
            }
        }

        if (!dir.mkdirs() && dir.isDirectory())
            this.loadFiles(dir);

        stopwatch.stop();
        LOGGER.info("Loaded {} alloy type(s) in {} ms", this.alloys.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public List<Alloy> getAlloys() {
        return this.alloys;
    }

    private void loadFiles(File dir) {
        File[] files = dir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));

        if (files == null) {
            return;
        }

        for (File file : files) {
            JsonObject json;
            FileReader reader = null;
            Alloy alloy = null;

            try {
                JsonParser parser = new JsonParser();
                reader = new FileReader(file);
                json = parser.parse(reader).getAsJsonObject();
                String name = file.getName().replace(".json", "");
                alloy = AlloyUtils.loadFromJson(new ResourceLocation(AtomicTechnology.MOD_ID, name), json);

                reader.close();
            } catch (Exception e) {
                LOGGER.error("An error occurred while loading alloys", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }

            if (alloy != null) {
                ResourceLocation id = alloy.getId();

                this.alloys.removeIf(s -> id.equals(s.getId()));
                this.alloys.add(alloy);
            }
        }
    }

    public static AlloyRegistry getInstance() {
        return INSTANCE;
    }

    private static List<Alloy> defaults() {
        return Lists.newArrayList(
                new Alloy(new ResourceLocation(AtomicTechnology.MOD_ID, "steel_ingot"), "item.atomictechnology.steel_ingot", new Ingredient[]{ Ingredient.fromItems(IRON_INGOT), Ingredient.fromItems(COAL) }, Ingredient.fromItems(Items.STEEL_INGOT.get()), 2, 0),
                new Alloy(new ResourceLocation(AtomicTechnology.MOD_ID, "steel_block"), "block.atomictechnology.steel_block", new Ingredient[]{ Ingredient.fromItems(IRON_BLOCK), Ingredient.fromItems(COAL_BLOCK) }, Ingredient.fromItems(Blocks.STEEL_BLOCK.get()), 2, 0)
        );
    }
}
