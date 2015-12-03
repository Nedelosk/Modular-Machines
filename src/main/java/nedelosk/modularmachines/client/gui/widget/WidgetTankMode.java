package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.api.utils.RenderUtils;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketSelectTankManager;
import nedelosk.modularmachines.api.producers.fluids.ITankManager.TankMode;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class WidgetTankMode extends Widget<TileModular> {

	protected ResourceLocation widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
	public TankMode mode;
	public int ID;

	public WidgetTankMode(int posX, int posY, TankMode mode, int ID) {
		super(posX, posY, 18, 18);
		this.mode = mode;
		this.ID = ID;
	}

	@Override
	public void draw(IGuiBase<TileModular> gui) {
		if(mode == null)
			return;
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 18, 0, 18, 18);
		StringBuilder builder = new StringBuilder();
		builder.append(mode.name().charAt(0));
		RenderUtils.glDrawScaledString(Minecraft.getMinecraft().fontRenderer, builder.toString(), gui.getGuiLeft() + pos.x + 6, gui.getGuiTop() + pos.y + 5, 1.2F, 14737632);
		GL11.glEnable(GL11.GL_LIGHTING);

	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<TileModular> gui) {
		if (mode != null) {
			if (mode.ordinal() != ForgeDirection.values().length - 1)
				mode = TankMode.values()[mode.ordinal() + 1];
			else
				mode = TankMode.values()[0];
			gui.getTile().getModular().getTankManeger().getProducer().getManager().setTankMode(ID, mode);
			PacketHandler.INSTANCE.sendToServer(new PacketSelectTankManager(gui.getTile(), mode, ID));
		}
	}

	@Override
	public ArrayList<String> getTooltip() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(mode.name());
		return list;
	}
}
