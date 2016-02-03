package de.nedelosk.forestmods.client.gui.widgets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cofh.api.energy.IEnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.library.gui.IGuiBase;
import de.nedelosk.forestcore.library.gui.Widget;
import de.nedelosk.forestcore.utils.RenderUtil;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class WidgetEnergyField extends Widget {

	private final ResourceLocation widget = new ResourceLocation("modularmachines", "textures/gui/widgets.png");
	public IEnergyStorage storage;

	public WidgetEnergyField(IEnergyStorage storage, int posX, int posY) {
		super(posX, posY, 66, 66);
		this.storage = storage;
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
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 0, 190, 66, 66);
		int energy = (this.storage.getEnergyStored() * 66) / this.storage.getMaxEnergyStored();
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 66 - energy, 66, 190 + 66 - energy, 66, energy);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}