package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.modular.module.manager.ModuleTankManager;
import nedelosk.modularmachines.common.modular.module.manager.TankManager;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularMachineNBT;
import nedelosk.nedeloskcore.api.machines.Widget;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;

public class WidgetFluidTankFilter extends Widget {

	protected ResourceLocation widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
	public Fluid fluid;
	public int ID;
	
	public WidgetFluidTankFilter(int posX, int posY, int ID, Fluid fluid) {
		super(posX, posY, 18, 18);
		this.fluid = fluid;
		this.ID = ID;
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, GuiBase gui) {
		if(GuiScreen.isShiftKeyDown())
		{
			fluid = null;
			((TankManager)((ModuleTankManager)((TileModularMachine)gui.getTile()).machine.getTankManeger()).manager).filters[ID] = fluid;
			PacketHandler.INSTANCE.sendToServer(new PacketModularMachineNBT((TileModularMachine) gui.getTile()));
		}
		else{
		ItemStack stack = mc.thePlayer.inventory.getItemStack();
		if(stack != null && FluidContainerRegistry.isContainer(stack))
		{
			fluid = FluidContainerRegistry.getFluidForFilledItem(stack).getFluid();
			((TankManager)((ModuleTankManager)((TileModularMachine)gui.getTile()).machine.getTankManeger()).manager).filters[ID] = fluid;
			PacketHandler.INSTANCE.sendToServer(new PacketModularMachineNBT((TileModularMachine) gui.getTile()));
		}
		}
	}
	
	@Override
	public void draw(GuiBase gui) {
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(gui.getGuiLeft() + this.posX, gui.getGuiTop() + this.posY, 36, 0, 18, 18);
		
		if(fluid != null)
		{
			IIcon icon = fluid.getStillIcon();
			if(icon != null)
			{
				RenderUtils.bindBlockTexture();
				drawTexturedModelRectFromIcon(gui.getGuiLeft() + this.posX + 1, gui.getGuiTop() + this.posY + 1, icon, 16, 16);
			}
		}

		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public Fluid getFluid() {
		return fluid;
	}
	
	@Override
	public ArrayList<String> getTooltip() {
		ArrayList<String> description = new ArrayList<String>();
		if(fluid != null)
			description.add(fluid.getLocalizedName(null));
		else
			description.add(StatCollector.translateToLocal("nc.tooltip.nonefluid"));
		return description;
	}

}