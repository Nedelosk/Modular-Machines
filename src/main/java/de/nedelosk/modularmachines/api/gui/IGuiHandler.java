package de.nedelosk.modularmachines.api.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiHandler {

	@SideOnly(Side.CLIENT)
	GuiContainer createGui(InventoryPlayer inventory);

	Container createContainer(InventoryPlayer inventory);
}
