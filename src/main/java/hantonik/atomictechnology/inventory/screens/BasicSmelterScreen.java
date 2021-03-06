package hantonik.atomictechnology.inventory.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.inventory.containers.BasicSmelterContainer;
import hantonik.atomictechnology.inventory.screens.basics.BasicContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class BasicSmelterScreen extends BasicContainerScreen<BasicSmelterContainer> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(AtomicTechnology.MOD_ID, "textures/gui/smelter.png");

    public BasicSmelterScreen(BasicSmelterContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title, BACKGROUND, 176, 194);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        BasicSmelterContainer container = this.getContainer();

        super.render(stack, mouseX, mouseY, partialTicks);

        if (mouseX > left + 7 && mouseX < left + 20 && mouseY > top + 17 && mouseY < top + 94) {
            StringTextComponent text = new StringTextComponent(container.getEnergyStored() + " FE");
            this.renderTooltip(stack, text, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack stack, int mouseX, int mouseY) {
        String title = this.getTitle().getString();
        this.font.drawString(stack, title, 7, 6, 4210752);
        String inventory = this.playerInventory.getDisplayName().getString();
        this.font.drawString(stack, inventory, 8, this.ySize - 94, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);

        int x = this.getGuiLeft();
        int y = this.getGuiTop();
        BasicSmelterContainer container = this.getContainer();

        int i1 = container.getEnergyBarScaled(78);
        this.blit(stack, x + 7, y + 95 - i1, 178, 78 - i1, 15, i1 + 1);

        if (container.hasRecipe()) {
            if (container.getProgress() > 0 && container.getEnergyRequired() > 0) {
                int i2 = container.getProgressBarScaled(80);
                this.blit(stack, x + 52, y + 22, 177, 79, i2 + 1, 69);
            }
        }
    }
}
