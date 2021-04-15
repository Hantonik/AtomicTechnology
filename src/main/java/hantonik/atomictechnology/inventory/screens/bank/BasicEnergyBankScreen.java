package hantonik.atomictechnology.inventory.screens.bank;

import com.mojang.blaze3d.matrix.MatrixStack;
import hantonik.atomiccore.inventory.screens.BasicContainerScreen;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.inventory.containers.bank.BasicEnergyBankContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class BasicEnergyBankScreen extends BasicContainerScreen<BasicEnergyBankContainer> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(AtomicTechnology.MOD_ID, "textures/gui/energy_bank.png");

    public BasicEnergyBankScreen(BasicEnergyBankContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title, BACKGROUND, 176, 110);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        BasicEnergyBankContainer container = this.getContainer();

        super.render(stack, mouseX, mouseY, partialTicks);

        if (mouseX > left + 7 && mouseX < left + 168 && mouseY > top + 7 && mouseY < top + 24) {
            StringTextComponent text = new StringTextComponent(container.getEnergyStored() + " FE / " + container.getMaxEnergyStored() + " FE");
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
        BasicEnergyBankContainer container = this.getContainer();

        int i1 = container.getEnergyBarScaled(162);
        this.blit(stack, x + 169 - i1, y + 23, 116 - i1, 78 - i1, 127, i1 + 1);
    }
}
