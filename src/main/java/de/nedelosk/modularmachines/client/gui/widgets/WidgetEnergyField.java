package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetEnergyField extends Widget<IEnergyBuffer> {

	private final ResourceLocation widget = new ResourceLocation("modularmachines", "textures/gui/widgets.png");

	public WidgetEnergyField(int posX, int posY, IEnergyBuffer provider) {
		super(posX, posY, 66, 66, provider);
	}

	@Override
	public List<String> getTooltip(IGuiProvider gui) {
		ArrayList<String> description = new ArrayList<>();
		description.add(provider.getEnergyStored() + " RF / " + provider.getCapacity() + " RF");
		return description;
	}

	@Override
	public void draw(IGuiProvider gui) {
		if (provider == null) {
			return;
		}
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widget);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 0, 190, 66, 66);
		if(this.provider.getEnergyStored() > 0){
			int eS = (int) (this.provider.getEnergyStored() / 100 * 66);
			int energy = (int) (eS / (this.provider.getCapacity() / 100)) ;
			gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 66 - energy, 66, 190 + 66 - energy, pos.width, energy);
		}
		GlStateManager.disableAlpha();
	}
}