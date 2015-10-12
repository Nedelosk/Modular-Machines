package nedelosk.nedeloskcore.client.gui.widget;

import java.util.ArrayList;

import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.api.machines.Widget;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cofh.api.energy.IEnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetEnergyBar extends Widget {

	private final ResourceLocation widget = new ResourceLocation("nedeloskcore", "textures/gui/widgets/widget_energy_bar.png");
	public IEnergyStorage storage;
	public int posX, posY;

	public WidgetEnergyBar(IEnergyStorage storage, int posX, int posY) {
		super(posX, posY, 12, 69);
		this.storage = storage;
		this.posX = posX;
		this.posY = posY;
	}
	
	@Override
	public ArrayList<String> getTooltip() {
		ArrayList<String> description = new ArrayList<String>();

		description.add(storage.getEnergyStored() + " RF / " + storage.getMaxEnergyStored() + " RF");
		return description;
	}
	
	@Override
	public void draw(IGuiBase gui) {
		if(storage == null)return;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(gui.getGuiLeft() + this.posX, gui.getGuiTop() + this.posY, 0, 0, 12, 69);
		
		int energy = (this.storage.getEnergyStored() * 69) / this.storage.getMaxEnergyStored() ;
		
        this.drawTexturedModalRect(gui.getGuiLeft() + this.posX, gui.getGuiTop() + this.posY + 69 - energy, 12, 0 + 69 - energy, 12, energy);

		GL11.glEnable(GL11.GL_LIGHTING);
	}
}