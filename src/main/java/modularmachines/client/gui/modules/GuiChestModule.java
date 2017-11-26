package modularmachines.client.gui.modules;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

import modularmachines.api.IGuiProvider;
import modularmachines.api.modules.IModule;
import modularmachines.client.gui.GuiBase;

public class GuiChestModule extends GuiBase {
	
	private final IModule module;
	
	public GuiChestModule(IModule module, InventoryPlayer inventory) {
		super(module.getComponent(IGuiProvider.class), inventory);
		this.module = module;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiTexture);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, 3 * 18 + 17);
		this.drawTexturedModalRect(guiLeft, guiTop + 3 * 18 + 17, 0, 126, this.xSize, 96);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		this.fontRenderer.drawString(module.getData().getDisplayName(), 8, 6, 4210752);
		this.fontRenderer.drawString(player.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 4210752);
	}
	
	@Override
	protected String getTextureModID() {
		return "minecraft";
	}
	
	@Override
	protected String getGuiTexture() {
		return "container/generic_54";
	}
}
