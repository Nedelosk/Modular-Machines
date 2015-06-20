package nedelosk.forestday.client.machines.base.gui;

import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiAlloySmelter extends GuiMachine {

	public GuiAlloySmelter(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		
	}

	@Override
	protected void renderProgressBar() {
		
	}

	@Override
	protected String getGuiName() {
		return "alloysmelter";
	}

}
