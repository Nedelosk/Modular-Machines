package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.utils.RenderUtils;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularSelectTankManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class WidgetFluidTankDirection extends Widget<TileModular> {

	protected ResourceLocation widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
	public ForgeDirection direction;
	public int ID;

	public WidgetFluidTankDirection(int posX, int posY, int ID, ForgeDirection direction) {
		super(posX, posY, 18, 18);
		this.ID = ID;
		this.direction = direction;
		if (direction == null)
			this.direction = ForgeDirection.UNKNOWN;
	}

	@Override
	public void draw(IGuiBase<TileModular> gui) {

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(gui.getGuiLeft() + this.posX, gui.getGuiTop() + this.posY, 18, 0, 18, 18);
		StringBuilder builder = new StringBuilder();
		builder.append(direction.name().charAt(0));
		RenderUtils.glDrawScaledString(Minecraft.getMinecraft().fontRenderer, builder.toString(),
				gui.getGuiLeft() + this.posX + 6, gui.getGuiTop() + this.posY + 5, 1.2F, 14737632);

		GL11.glEnable(GL11.GL_LIGHTING);

	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<TileModular> gui) {
		if (direction != null) {
			if (direction.ordinal() != ForgeDirection.values().length - 1)
				direction = ForgeDirection.values()[direction.ordinal() + 1];
			else
				direction = ForgeDirection.values()[0];
			gui.getTile().getModular().getTankManeger().getProducer().getManager().setDirection(direction, ID);
			PacketHandler.INSTANCE.sendToServer(new PacketModularSelectTankManager(gui.getTile(), direction, ID));
		}
	}

	@Override
	public ArrayList<String> getTooltip() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(direction.name());
		return list;
	}

}
