package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.modular.module.manager.ModuleTankManager;
import nedelosk.modularmachines.common.modular.module.manager.TankManager;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularMachineNBT;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widget.Widget;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class WidgetFluidTankPriority extends Widget {

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
	public void draw(GuiBase gui) {
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(gui.getGuiLeft() + this.posX, gui.getGuiTop() + this.posY, 0, 0, 18, 18);
		
		drawTexturedModalRect(gui.getGuiLeft() + this.posX, gui.getGuiTop() + this.posY, 0, 18 + ((priority - 1) * 18), 18, 18);

		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, GuiBase gui) {
		if(priority != 3)
			priority++;
		else
			priority = 1;
		((TankManager)((ModuleTankManager)((TileModularMachine)gui.getTile()).machine.getTankManeger()).manager).prioritys[ID] = priority;
		PacketHandler.INSTANCE.sendToServer(new PacketModularMachineNBT((TileModularMachine) gui.getTile()));
	}
	
	@Override
	public ArrayList<String> getTooltip() {
		ArrayList<String> list = new ArrayList<String>();
		if(priority != 0)
			list.add(Integer.toString(priority));
		return list;
	}
}