package hantonik.atomictechnology.lib;

import hantonik.atomiccore.utils.Tooltip;
import hantonik.atomictechnology.AtomicTechnology;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.ModList;

public class AtomicTooltips {
    public static final Tooltip CRAFTING_COMPONENT = new Tooltip("tooltip." + AtomicTechnology.MOD_ID + ".crafting_component");
    public static final Tooltip ADDED_BY = new Tooltip("tooltip." + AtomicTechnology.MOD_ID + ".added_by");
    public static final Tooltip CRAFTING = new Tooltip("tooltip." + AtomicTechnology.MOD_ID + ".crafting");
    public static final Tooltip PATTERN = new Tooltip("tooltip." + AtomicTechnology.MOD_ID + ".pattern");

    public static final Tooltip VOSMENITE_ALLOY = new Tooltip("tooltip." + AtomicTechnology.MOD_ID + ".vosmenite_alloy");
    public static final Tooltip GOLDIUM_ALLOY = new Tooltip("tooltip." + AtomicTechnology.MOD_ID + ".goldium_alloy");

    public static ITextComponent getAddedByTooltip(String modID) {
        String name = ModList.get().getModFileById(modID).getMods().get(0).getDisplayName();
        return ADDED_BY.args(name).build();
    }
}
