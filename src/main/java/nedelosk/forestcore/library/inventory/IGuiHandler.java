package nedelosk.forestcore.library.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public interface IGuiHandler {

	@SideOnly(Side.CLIENT)
	GuiContainer getGUIContainer(InventoryPlayer inventory);

	Container getContainer(InventoryPlayer inventory);
}
