package de.nedelosk.techtree.common.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import de.nedelosk.techtree.client.gui.GuiTechTree;
import de.nedelosk.techtree.client.gui.GuiTechTreeEditor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

	public void init() {
	}

	public void postInit() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case 0:
				return new GuiTechTreeEditor();
			case 1:
				return new GuiTechTree();
			default:
				return null;
		}
	}
}
