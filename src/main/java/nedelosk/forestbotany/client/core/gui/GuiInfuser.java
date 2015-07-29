package nedelosk.forestbotany.client.core.gui;

import nedelosk.forestbotany.common.blocks.tile.TileInfuserBase;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetEnergyBar;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiInfuser extends GuiBase {
	
	public GuiInfuser(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
		ySize = 181;
		widgetManager.add(new WidgetEnergyBar(((TileInfuserBase)tile).getStorage(), 7, 9));
		widgetManager.add(new WidgetFluidTank(((TileInfuserBase)tile).getTankWater(), 22, 15));
		widgetManager.add(new WidgetFluidTank(((TileInfuserBase)tile).getTank(), 151, 15));
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	@Override
	protected void renderProgressBar() {
		
	}

	@Override
	protected String getGuiName() {
		return "infuser";
	}

	@Override
	protected String getModName() {
		return "forestbotany";
	}

}
