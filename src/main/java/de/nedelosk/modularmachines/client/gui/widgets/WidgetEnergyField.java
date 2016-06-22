package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cofh.api.energy.IEnergyStorage;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.client.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class WidgetEnergyField extends Widget {

	private final ResourceLocation widget = new ResourceLocation("forestmods", "textures/gui/widgets.png");
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
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(widget);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 0, 190, 66, 66);
		int eS = this.storage.getEnergyStored() / 100 * 66;
		int energy = eS / (this.storage.getMaxEnergyStored() / 100);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 66 - energy, 66, 190 + 66 - energy, 66, energy);
	}
}