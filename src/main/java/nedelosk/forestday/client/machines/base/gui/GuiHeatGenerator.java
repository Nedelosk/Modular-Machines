package nedelosk.forestday.client.machines.base.gui;

import nedelosk.forestday.common.machines.base.heater.generator.TileHeatGenerator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiHeatGenerator extends GuiMachine {
	
	public GuiHeatGenerator(InventoryPlayer inventory, TileHeatGenerator tile) {
		super(tile, inventory);
		this.tile = tile;
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		fontRenderer.drawString(StatCollector.translateToLocal("container.machine.generator.burning"), 8, ySize - 166 + 2, 4210752);
	}

	@Override
	protected void renderProgressBar() {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		
        if (((TileHeatGenerator)this.tile).isActive0())
        {
            int i1 = ((TileHeatGenerator)this.tile).getBurnTimeRemainingScaled0(13);
            this.drawTexturedModalRect(k + 63, l + 35 + 12 - i1, 186, 12 - i1, 14, i1 + 1);
        }
        
        if (((TileHeatGenerator)this.tile).isActive1())
        {
            int i1 = ((TileHeatGenerator)this.tile).getBurnTimeRemainingScaled1(13);
            this.drawTexturedModalRect(k + 81, l + 35 + 12 - i1, 186, 12 - i1, 14, i1 + 1);
        }
        
        if (((TileHeatGenerator)this.tile).isActive2())
        {
            int i1 = ((TileHeatGenerator)this.tile).getBurnTimeRemainingScaled2(13);
            this.drawTexturedModalRect(k + 99, l + 35 + 12 - i1, 186, 12 - i1, 14, i1 + 1);
        }
	}

	@Override
	protected String getGuiName() {
		return "generator_coal";
	}

}
