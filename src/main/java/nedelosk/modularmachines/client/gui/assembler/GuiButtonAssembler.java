package nedelosk.modularmachines.client.gui.assembler;

import nedelosk.modularmachines.api.modular.machines.basic.AssemblerMachineInfo;
import nedelosk.modularmachines.client.gui.GuiButtonItem;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

public class GuiButtonAssembler extends GuiButtonItem {

  public static final AssemblerMachineInfo info;

  public GuiButtonAssembler(int buttonId, int x, int y) {
    super(buttonId, x, y, StatCollector.translateToLocal("gui.machine"), info);
  }

  @Override
  protected void drawIcon(Minecraft mc) {
    mc.getTextureManager().bindTexture(GuiButtonItem.locBackground);
  }


  static {
    int x = 7 + 82/2 - 14;
    int y = 18 + 36/2 - 8;

    info = new AssemblerMachineInfo();
    info.machine = MMBlockManager.Modular_Machine.getItemStack();

    info.addSlotPosition(x, y - 23);
    info.addSlotPosition(x - 22, y - 5);
    info.addSlotPosition(x, y);
    info.addSlotPosition(x + 22, y - 5);

    info.addSlotPosition(x - 18, y + 20);
    info.addSlotPosition(x + 18, y + 20);
    info.addSlotPosition(x - 14, y + 43);
    info.addSlotPosition(x + 14, y + 43);
  }
}