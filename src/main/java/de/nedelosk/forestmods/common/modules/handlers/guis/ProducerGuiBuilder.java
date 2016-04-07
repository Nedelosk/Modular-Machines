package de.nedelosk.forestmods.common.modules.handlers.guis;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.Button;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.handlers.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.handlers.gui.IModuleGuiBuilder;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ProducerGuiBuilder implements IModuleGuiBuilder {

	protected ResourceLocation customGui;
	protected IModular modular;
	protected ModuleStack moduleStack;
	protected int guiTop;
	protected boolean renderInventoryName;
	protected List<Widget> widgets = new ArrayList();
	protected List<Button> buttons = new ArrayList();

	public ProducerGuiBuilder() {
		renderInventoryName = true;
		guiTop = 166;
	}

	@Override
	public void setModuleStack(ModuleStack moduleStack) {
		this.moduleStack = moduleStack;
	}

	@Override
	public void setModular(IModular modular) {
		this.modular = modular;
	}

	@Override
	public void setGuiTop(int guiTop) {
		this.guiTop = guiTop;
	}

	@Override
	public void setCustomGui(ResourceLocation customGui) {
		this.customGui = customGui;
	}

	@Override
	public void serRenderInventoryName(boolean renderInventoryName) {
		this.renderInventoryName = renderInventoryName;
	}

	@Override
	public void addButton(Button button) {
		this.buttons.add(button);
	}

	@Override
	public void addWidget(Widget widget) {
		this.widgets.add(widget);
	}

	@Override
	public IModuleGui build() {
		return new ProducerGui(modular, moduleStack, guiTop, customGui, renderInventoryName, widgets, buttons);
	}
}
