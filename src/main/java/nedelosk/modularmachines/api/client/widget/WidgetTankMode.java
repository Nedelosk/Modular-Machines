package nedelosk.modularmachines.api.client.widget;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.utils.RenderUtil;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.managers.fluids.IModuleTankManager.TankMode;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketTankManager;
import nedelosk.modularmachines.api.utils.ModularUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class WidgetTankMode<T extends TileEntity & IModularTileEntity> extends Widget<T> {

	protected ResourceLocation widget = RenderUtil.getResourceLocation("modularmachines", "widgets", "gui");
	public TankMode mode;
	public int ID;

	public WidgetTankMode(int posX, int posY, TankMode mode, int ID) {
		super(posX, posY, 18, 18);
		this.mode = mode;
		this.ID = ID;
	}

	@Override
	public void draw(IGuiBase<T> gui) {
		if (mode == null) {
			return;
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(widget);
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 18, 0, 18, 18);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void drawStrings(IGuiBase<T> gui) {
		if (mode == null) {
			return;
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		StringBuilder builder = new StringBuilder();
		builder.append(mode.name().charAt(0));
		RenderUtil.glDrawScaledString(Minecraft.getMinecraft().fontRenderer, builder.toString(), gui.getGuiLeft() + pos.x + 6, gui.getGuiTop() + pos.y + 5,
				1.2F, 4210752);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<T> gui) {
		if (mode != null) {
			if (mode.ordinal() != TankMode.values().length - 1) {
				mode = TankMode.values()[mode.ordinal() + 1];
			} else {
				mode = TankMode.values()[0];
			}
			ModularUtils.getTankManager(gui.getTile().getModular()).getSaver().getData(ID).setMode(mode);
			PacketHandler.INSTANCE.sendToServer(new PacketTankManager(gui.getTile(), mode, ID));
		}
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase<T> gui) {
		if (mode == null) {
			return null;
		}
		ArrayList<String> list = new ArrayList<String>();
		list.add(mode.name());
		return list;
	}
}
