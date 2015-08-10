package nedelosk.modularmachines.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.common.blocks.tile.TileModularWorkbench;
import nedelosk.nedeloskcore.client.gui.GuiBase;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

@SideOnly(Side.CLIENT)
public class GuiModularWorkbench
  extends GuiBase
{
  private TileModularWorkbench workbench;
  private InventoryPlayer ip;
  
  public GuiModularWorkbench(InventoryPlayer inventory, TileModularWorkbench workbench)
  {
    super(workbench, inventory);
    this.workbench = workbench;
    this.ip = inventory;
    ySize = 191;
  }

  @Override
  protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	  
  }

  @Override
	protected void renderProgressBar() {
	
  }

  @Override
  protected String getGuiName() {
	  return "gui_modularworkbench";
  }

  @Override
  protected String getModName() {
	  return "modularmachines";
  }
}
