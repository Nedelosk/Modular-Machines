package nedelosk.forestday.client.machines.base.gui;

import nedelosk.forestday.common.machines.base.saw.TileSaw;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widgets.WidgetFluidTank;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiSaw extends GuiMachine {
	
	protected WidgetFluidTank widgetFluidTank;
	
	public GuiSaw(InventoryPlayer inventory, TileSaw tile) {
		super(tile, inventory);
		this.tile = tile;
	}


	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

		fontRenderer.drawString(StatCollector.translateToLocal("container.machine.saw"), 82, ySize - 165 + 2, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 70, ySize - 96 + 2, 4210752);
	}


	@Override
	protected void renderProgressBar() {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k + 82, l + 19, 176, 0, 18, this.tile.getScaledProcess(42));
        this.drawTexturedModalRect(k + 82, l + 19, 176, 42, 18, 42);
        
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
        if(widgetFluidTank != null)
        widgetFluidTank.draw(widgetFluidTank.posX, widgetFluidTank.posY, mouseX, mouseY);
        if (widgetFluidTank != null)
            if (func_146978_c(widgetFluidTank.posX, widgetFluidTank.posY, 18, 73, mouseX, mouseY)) {
                widgetFluidTank.drawTooltip(mouseX - this.guiLeft, mouseY
                        - this.guiTop);
            }
	}


	@Override
	protected String getGuiName() {
		return "saw";
	}
	
    @Override
    public void initGui() {
        super.initGui();
        widgetFluidTank = new WidgetFluidTank(((TileSaw)this.tile).getTank(), 7, 7);
    }

}
