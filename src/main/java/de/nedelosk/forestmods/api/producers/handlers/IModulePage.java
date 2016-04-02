package de.nedelosk.forestmods.api.producers.handlers;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGuiBuilder;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.api.producers.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.api.producers.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.gui.FontRenderer;

public interface IModulePage {

	void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder);

	void createSlots(IContainerBase container, List<SlotModule> modularSlots);

	@SideOnly(Side.CLIENT)
	void createGui(IModuleGuiBuilder guiBuilder);

	@SideOnly(Side.CLIENT)
	void updateGui(IGuiBase base, int x, int y);

	@SideOnly(Side.CLIENT)
	void handleMouseClicked(Widget widget, int mouseX, int mouseY, int mouseButton);

	@SideOnly(Side.CLIENT)
	void renderStrings(FontRenderer fontRenderer, int x, int y, int xM, int yM);

	int getPageID();

	IModular getModular();

	ModuleStack getModuleStack();
}
