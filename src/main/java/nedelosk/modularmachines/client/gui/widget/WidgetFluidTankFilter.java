package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.utils.RenderUtils;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularSelectTankManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class WidgetFluidTankFilter extends Widget<TileModular> {

	protected ResourceLocation widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
	public Fluid fluid;
	public int ID;

	public WidgetFluidTankFilter(int posX, int posY, int ID, Fluid fluid) {
		super(posX, posY, 18, 18);
		this.fluid = fluid;
		this.ID = ID;
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<TileModular> gui) {
		if (GuiScreen.isShiftKeyDown()) {
			fluid = null;
			gui.getTile().getModular().getTankManeger().getProducer().getManager().setFilter(fluid, ID);
			PacketHandler.INSTANCE.sendToServer(new PacketModularSelectTankManager(gui.getTile(), fluid, ID));
		} else {
			ItemStack stack = mc.thePlayer.inventory.getItemStack();
			if (stack != null && FluidContainerRegistry.isContainer(stack)) {
				fluid = FluidContainerRegistry.getFluidForFilledItem(stack).getFluid();
				gui.getTile().getModular().getTankManeger().getProducer().getManager().setFilter(fluid, ID);
				PacketHandler.INSTANCE.sendToServer(new PacketModularSelectTankManager(gui.getTile(), fluid, ID));
			}
		}
	}

	@Override
	public void draw(IGuiBase<TileModular> gui) {

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(gui.getGuiLeft() + this.posX, gui.getGuiTop() + this.posY, 36, 0, 18, 18);

		if (fluid != null) {
			IIcon icon = fluid.getStillIcon();
			if (icon != null) {
				RenderUtils.bindBlockTexture();
				drawTexturedModelRectFromIcon(gui.getGuiLeft() + this.posX + 1, gui.getGuiTop() + this.posY + 1, icon,
						16, 16);
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
		if (fluid != null)
			description.add(fluid.getLocalizedName(null));
		else
			description.add(StatCollector.translateToLocal("nc.tooltip.nonefluid"));
		return description;
	}

}
