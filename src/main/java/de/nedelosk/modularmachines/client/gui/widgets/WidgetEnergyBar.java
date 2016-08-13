package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import cofh.api.energy.IEnergyStorage;
import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetEnergyBar extends Widget {

	public IEnergyStorage storage;
	public int posX, posY;

	public WidgetEnergyBar(IEnergyStorage storage, int posX, int posY) {
		super(posX, posY, 12, 69);
		this.storage = storage;
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiProvider gui) {
		ArrayList<String> description = new ArrayList<String>();
		description.add(storage.getEnergyStored() + " RF / " + storage.getMaxEnergyStored() + " RF");
		return description;
	}

	@Override
	public void draw(IGuiProvider gui) {
		if (storage == null) {
			return;
		}
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 180, 187, 12, 69);
		int energy = (this.storage.getEnergyStored() * pos.height) / this.storage.getMaxEnergyStored();
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 69 - energy, 192, 256 - energy, pos.width, energy);
		GlStateManager.disableAlpha();
	}
}