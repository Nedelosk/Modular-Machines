package de.nedelosk.forestmods.api.producers.handlers.gui;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.Button;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.producers.handlers.IModuleHandler;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public interface IModuleGui extends IModuleHandler {

	int getGuiTop();

	ResourceLocation getCustomGui();

	boolean renderInventoryName();

	List<Button> getButtons();

	List<Widget> getWidgets();
}
