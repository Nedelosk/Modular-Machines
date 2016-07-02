package de.nedelosk.modularmachines.client.gui;

import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiModularAssembler extends GuiForestBase<TileModularAssembler> {

	public GuiModularAssembler(TileModularAssembler assembler, InventoryPlayer inventory) {
		super(assembler, inventory);

		xSize = 180;
		ySize = 256;
	}

	@Override
	public void drawSlot(Slot slot) {
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		if(slot instanceof SlotAssembler){
			if(((SlotAssembler)slot).isActive){
				RenderUtil.bindTexture(new ResourceLocation(getTextureModID(), "textures/gui/modular_assembler_overlays.png"));
				drawTexturedModalRect(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1, 0, 0, 18, 18);
			}
		}
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		super.drawSlot(slot);
	}

	@Override
	protected void renderToolTip(ItemStack stack, int x, int y) {
		super.renderToolTip(stack, x, y);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}
}
