package nedelosk.forestday.client.machines.base.gui;

import nedelosk.forestday.client.machines.base.gui.button.ButtonWorkbenchMode;
import nedelosk.forestday.common.machines.base.wood.workbench.TileWorkbench;
import nedelosk.forestday.common.machines.base.wood.workbench.TileWorkbench.Mode;
import nedelosk.forestday.common.network.packets.PacketHandler;
import nedelosk.forestday.common.network.packets.machines.PacketSwitchMode;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiWorkbench extends GuiMachine {

	public GuiWorkbench(TileWorkbench tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if(tile.getBlockMetadata() == 2){
	    xSize = 234;
		}
	}
    
    @Override
    public void initGui() {
    	super.initGui();
    	
    	buttonList.add(new ButtonWorkbenchMode(0, guiLeft + 130, guiTop + 10, ((TileWorkbench)tile).getMode(), new ResourceLocation("forestday", "textures/gui/button/workbanch_mode.png")));
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
		if(button instanceof ButtonWorkbenchMode)
		{
			((TileWorkbench) tile).setMode(((TileWorkbench) tile).getMode() == Mode.further_processing ? Mode.stop_processing : Mode.further_processing);
			((ButtonWorkbenchMode)buttonList.get(0)).setMode(((TileWorkbench) tile).getMode());
			switchMode();
		}
	}
	
	private void switchMode()
	{
		PacketHandler.INSTANCE.sendToServer(new PacketSwitchMode((TileWorkbench) tile));
	}

}
