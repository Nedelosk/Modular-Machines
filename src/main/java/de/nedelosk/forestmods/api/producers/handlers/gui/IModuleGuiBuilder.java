package de.nedelosk.forestmods.api.producers.handlers.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.Button;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.producers.handlers.IModuleBuilder;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public interface IModuleGuiBuilder extends IModuleBuilder {

	void setGuiTop(int top);

	void setCustomGui(ResourceLocation customGui);

	void serRenderInventoryName(boolean renderInventoryName);

	void addButton(Button button);

	void addWidget(Widget widget);

	@Override
	IModuleGui build();
}
