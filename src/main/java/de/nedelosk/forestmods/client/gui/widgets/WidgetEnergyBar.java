package de.nedelosk.forestmods.client.gui.widgets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cofh.api.energy.IEnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestcore.utils.RenderUtil;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class WidgetEnergyBar extends Widget {

	private final ResourceLocation widget = new ResourceLocation("forestmods", "textures/gui/widgets/widget_energy_bar.png");
	public IEnergyStorage storage;
	public int posX, posY;

	public WidgetEnergyBar(IEnergyStorage storage, int posX, int posY) {
		super(posX, posY, 12, 69);
		this.storage = storage;
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase gui) {
		ArrayList<String> description = new ArrayList<String>();
		description.add(storage.getEnergyStored() + " RF / " + storage.getMaxEnergyStored() + " RF");
		return description;
	}

	@Override
	public void draw(IGuiBase gui) {
		if (storage == null) {
			return;
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(widget);
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 0, 0, 12, 69);
		int energy = (this.storage.getEnergyStored() * 69) / this.storage.getMaxEnergyStored();
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 69 - energy, 12, 0 + 69 - energy, 12, energy);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}