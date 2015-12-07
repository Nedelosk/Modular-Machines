package nedelosk.forestday.client.gui;

import nedelosk.forestday.common.blocks.tiles.TileKiln;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiKiln extends GuiMachine<TileKiln> {

	public GuiKiln(InventoryPlayer inventory, TileKiln tile) {
		super(tile, inventory);
		this.tile = tile;
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

		fontRenderer.drawString(StatCollector.translateToLocal("container.machine.kiln.resin"), 8, ySize - 163,
				4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);

	}

	@Override
	protected void renderProgressBar() {
		int sx = (width - xSize) / 2;
		int sy = (height - ySize) / 2;
		if (tile.getBurnTime() > 0 && tile.getBurnTime() <= tile.getBurnTimeTotal()) {
			this.drawTexturedModalRect(sx + 77, sy + 36, 176, 4, this.tile.getScaledProcess(24), 15);
		}

	}

	@Override
	protected String getGuiName() {
		return "machines/kiln";
	}

}
