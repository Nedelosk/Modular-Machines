package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modules.handlers.energy.IModuleEnergyBuffer;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetEnergyField extends Widget {

	private final ResourceLocation widget = new ResourceLocation("modularmachines", "textures/gui/widgets.png");
	public IModuleEnergyBuffer energyBuffer;

	public WidgetEnergyField(IModuleEnergyBuffer energyInterface, int posX, int posY) {
		super(posX, posY, 66, 66);
		this.energyBuffer = energyInterface;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiProvider gui) {
		ArrayList<String> description = new ArrayList<String>();
		description.add(energyBuffer.getEnergyStored() + " RF / " + energyBuffer.getCapacity() + " RF");
		return description;
	}

	@Override
	public void draw(IGuiProvider gui) {
		if (energyBuffer == null) {
			return;
		}
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widget);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 0, 190, 66, 66);
		if(this.energyBuffer.getEnergyStored() > 0){
			int eS = (int) (this.energyBuffer.getEnergyStored() / 100 * 66);
			int energy = (int) (eS / (this.energyBuffer.getCapacity() / 100)) ;
			gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 66 - energy, 66, 190 + 66 - energy, pos.width, energy);
		}
		GlStateManager.disableAlpha();
	}
}