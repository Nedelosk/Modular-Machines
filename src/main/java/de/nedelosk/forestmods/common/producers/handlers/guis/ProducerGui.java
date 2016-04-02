package de.nedelosk.forestmods.common.producers.handlers.guis;

import java.util.Collections;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.Button;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGui;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ProducerGui implements IModuleGui {

	private final IModular modular;
	private final ModuleStack moduleStack;
	private final int guiTop;
	private final ResourceLocation customGui;
	private final boolean renderInventoryName;
	private final List<Widget> widgets;
	private final List<Button> buttons;

	public ProducerGui(IModular modular, ModuleStack moduleStack, int guiTop, ResourceLocation customGui, boolean renderInventoryName, List<Widget> widgets,
			List<Button> buttons) {
		this.modular = modular;
		this.moduleStack = moduleStack;
		this.guiTop = guiTop;
		this.customGui = customGui;
		this.renderInventoryName = renderInventoryName;
		this.widgets = Collections.unmodifiableList(widgets);
		this.buttons = Collections.unmodifiableList(buttons);
	}

	@Override
	public IModular getModular() {
		return modular;
	}

	@Override
	public ModuleStack getModuleStack() {
		return moduleStack;
	}

	@Override
	public int getGuiTop() {
		return guiTop;
	}

	@Override
	public ResourceLocation getCustomGui() {
		return customGui;
	}

	@Override
	public boolean renderInventoryName() {
		return renderInventoryName;
	}

	@Override
	public List<Button> getButtons() {
		return buttons;
	}

	@Override
	public List<Widget> getWidgets() {
		return widgets;
	}
}
