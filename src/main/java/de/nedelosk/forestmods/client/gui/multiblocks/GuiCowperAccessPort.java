package de.nedelosk.forestmods.client.gui.multiblocks;

import de.nedelosk.forestcore.gui.GuiBase;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperAccessPort;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCowperAccessPort extends GuiBase<TileCowperAccessPort> {

	public GuiCowperAccessPort(TileCowperAccessPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	@Override
	protected void render() {
	}

	@Override
	protected String getGuiTexture() {
		return "gui_blastfurnace_item";
	}

	@Override
	protected String getTextureModID() {
		return "forestmods";
	}
}
