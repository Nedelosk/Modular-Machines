package modularmachines.api.modules.components;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiFactory {
	@SideOnly(Side.CLIENT)
	GuiContainer createGui(InventoryPlayer inventory);
	
	Container createContainer(InventoryPlayer inventory);
	
	//Server Side Only
	default void onOpenGui(EntityPlayer player) {
	
	}
}
