package nedelosk.forestday.client.gui;

import nedelosk.forestday.client.gui.button.ButtonWorkbenchMode;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench.Mode;
import nedelosk.forestday.common.network.packets.PacketHandler;
import nedelosk.forestday.common.network.packets.machines.PacketSwitchMode;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
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
	public void initGui() {
		super.initGui();

		buttonList.add(new ButtonWorkbenchMode(0, guiLeft + 130, guiTop + 10, tile.getMode(),
				new ResourceLocation("forestday", "textures/gui/button/workbanch_mode.png")));
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

	}

	@Override
	protected void renderProgressBar() {
	}

	@Override
	protected String getGuiName() {
		return "machines/workbanch";
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button instanceof ButtonWorkbenchMode) {
			tile.setMode(tile.getMode() == Mode.further_processing ? Mode.stop_processing : Mode.further_processing);
			((ButtonWorkbenchMode) buttonList.get(0)).setMode(tile.getMode());
			switchMode();
		}
	}

	private void switchMode() {
		PacketHandler.INSTANCE.sendToServer(new PacketSwitchMode(tile));
	}

}
