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

	protected WidgetEnergyBar energyBar = new WidgetEnergyBar(((TileInfuserBase)tile).getStorage(), this.guiLeft + 7, this.guiTop + 9);
	protected WidgetFluidTank fluidTankWater = new WidgetFluidTank(((TileInfuserBase)tile).getTankWater(), this.guiLeft + 22, this.guiTop + 15);
	protected WidgetFluidTank fluidTank = new WidgetFluidTank(((TileInfuserBase)tile).getTank(), this.guiLeft + 151, this.guiTop + 15);
	
	public GuiInfuser(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
		ySize = 181;
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
        if(energyBar != null)
        	energyBar.draw(this);
        if (energyBar != null)
            if (func_146978_c(energyBar.posX, energyBar.posY, 12, 69, x, y)) {
            	energyBar.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
        if(fluidTank != null)
        	fluidTank.draw(fluidTank.posX, fluidTank.posY, x, y);
        if (fluidTank != null)
            if (func_146978_c(fluidTank.posX, fluidTank.posY, 18, 73, x, y)) {
            	fluidTank.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
        if(fluidTankWater != null)
        	fluidTankWater.draw(fluidTankWater.posX, fluidTankWater.posY, x, y);
        if (fluidTankWater != null)
            if (func_146978_c(fluidTankWater.posX, fluidTankWater.posY, 18, 73, x, y)) {
            	fluidTankWater.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
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
