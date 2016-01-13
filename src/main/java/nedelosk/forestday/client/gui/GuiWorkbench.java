package nedelosk.forestday.client.gui;

import nedelosk.forestday.client.gui.button.ButtonWorkbenchMode;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiWorkbench extends GuiMachine<TileWorkbench> {

	public GuiWorkbench(TileWorkbench tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if (tile.getBlockMetadata() == 2) {
			xSize = 234;
		}
	}

	@Override
	public void addButtons() {
		buttonManager.add(new ButtonWorkbenchMode(0, guiLeft + 130, guiTop + 10, tile.getMode(),
				new ResourceLocation("forestday", "textures/gui/button/workbench_mode.png")));
	}

	@Override
	protected void renderProgressBar() {
		if (tile.getBurnTime() > 0 && tile.getBurnTime() < tile.getBurnTimeTotal()) {
			this.drawTexturedModalRect(guiLeft + 49, guiTop + 37, 30, 234, this.tile.getScaledProcess(76), 22);
		}
	}

	@Override
	protected String getGuiName() {
		return "machines/workbench";
	}
}
