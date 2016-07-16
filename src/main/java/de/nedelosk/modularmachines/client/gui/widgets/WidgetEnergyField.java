package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetEnergyField extends Widget {

	private final ResourceLocation widget = new ResourceLocation("modularmachines", "textures/gui/widgets.png");
	public IEnergyInterface energyInterface;
	public IEnergyType type;

	public WidgetEnergyField(IEnergyInterface energyInterface, IEnergyType type, int posX, int posY) {
		super(posX, posY, 66, 66);
		this.energyInterface = energyInterface;
		this.type = type;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase gui) {
		ArrayList<String> description = new ArrayList<String>();
		description.add(energyInterface.getEnergyStored(type) + " RF / " + energyInterface.getCapacity(type) + " RF");
		return description;
	}

	@Override
	public void draw(IGuiBase gui) {
		if (energyInterface == null) {
			return;
		}
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(widget);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 0, 190, 66, 66);
		if(this.energyInterface.getEnergyStored(type) > 0){
			int eS = (int) (this.energyInterface.getEnergyStored(type) / 100 * 66);
			int energy = (int) (eS / (this.energyInterface.getCapacity(type) / 100));
			gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 66 - energy, 66, 190 + 66 - energy, 66, energy);
		}
	}
}