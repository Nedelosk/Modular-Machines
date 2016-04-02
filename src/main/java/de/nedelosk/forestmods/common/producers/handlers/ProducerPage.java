package de.nedelosk.forestmods.common.producers.handlers;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestcore.gui.WidgetProgressBar;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.gui.FontRenderer;

public abstract class ProducerPage<M extends IModule> implements IModulePage {

	protected int pageID;
	protected IModular modular;
	protected ModuleStack<M> moduleStack;
	protected M module;

	public ProducerPage(int pageID, IModular modular, ModuleStack<M> moduleStack) {
		this.pageID = pageID;
		this.modular = modular;
		this.moduleStack = moduleStack;
		this.module = moduleStack.getModule();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y) {
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for ( Widget widget : widgets ) {
			if (widget instanceof WidgetProgressBar) {
				IModuleEngine engine = ModularUtils.getEngine(modular).getModule();
				if (engine != null) {
					int burnTime = engine.getBurnTime();
					int burnTimeTotal = engine.getBurnTimeTotal();
					((WidgetProgressBar) widget).burntime = burnTime;
					((WidgetProgressBar) widget).burntimeTotal = burnTimeTotal;
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(Widget widget, int mouseX, int mouseY, int mouseButton) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderStrings(FontRenderer fontRenderer, int x, int y, int xM, int yM) {
		if (module.getGui().renderInventoryName()) {
			fontRenderer.drawString(module.getInventory().getInventoryName(), 90 - (fontRenderer.getStringWidth(module.getInventory().getInventoryName()) / 2),
					6, 4210752);
		}
	}

	@Override
	public int getPageID() {
		return pageID;
	}

	@Override
	public IModular getModular() {
		return modular;
	}

	@Override
	public ModuleStack<M> getModuleStack() {
		return moduleStack;
	}
}
