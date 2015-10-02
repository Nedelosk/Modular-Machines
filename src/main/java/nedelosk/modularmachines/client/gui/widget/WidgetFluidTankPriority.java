package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.machines.module.fluids.ModuleTankManager;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularNBT;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.api.machines.Widget;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WidgetFluidTankPriority extends Widget<TileModular> {

	protected ResourceLocation widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
	public int priority;
	public int ID;
	
	public WidgetFluidTankPriority(int posX, int posY, int ID, int priority) {
		super(posX, posY, 18, 18);
		this.ID = ID;
		this.priority = priority;
		if(priority == 0)
			this.priority = 1;
	}
	
	@Override
	public void draw(IGuiBase<TileModular> gui) {
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(gui.getGuiLeft() + this.posX, gui.getGuiTop() + this.posY, 0, 0, 18, 18);
		
		drawTexturedModalRect(gui.getGuiLeft() + this.posX, gui.getGuiTop() + this.posY, 0, 18 + ((priority - 1) * 18), 18, 18);

		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<TileModular> gui) {
		if(priority != 3)
			priority++;
		else
			priority = 1;
		((ModuleTankManager)gui.getTile().getModular().getTankManeger().getModule()).manager.prioritys[ID] = priority;
		PacketHandler.INSTANCE.sendToServer(new PacketModularNBT(gui.getTile()));
	}
	
	@Override
	public ArrayList<String> getTooltip() {
		ArrayList<String> list = new ArrayList<String>();
		if(priority != 0)
			list.add(Integer.toString(priority));
		return list;
	}
}