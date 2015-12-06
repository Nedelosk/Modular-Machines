package nedelosk.modularmachines.api.client.widget;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.api.utils.RenderUtils;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketTankManager;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class WidgetDirection<T extends TileEntity & IModularTileEntity> extends Widget<T> {

	protected ResourceLocation widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
	public ForgeDirection direction;
	public int ID;
	
	public WidgetDirection(int posX, int posY, ForgeDirection direction, int ID) {
		super(posX, posY, 18, 18);
		this.ID = ID;
		this.direction = direction;
		if(direction == null)
			this.direction = ForgeDirection.UNKNOWN;
	}
	
	@Override
	public void draw(IGuiBase<T> gui) {
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 18, 0, 18, 18);

		GL11.glEnable(GL11.GL_LIGHTING);
		
	}
	
	@Override
	public void drawStrings(IGuiBase<T> gui) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		
		StringBuilder builder = new StringBuilder();
		builder.append(direction.name().charAt(0));
		RenderUtils.glDrawScaledString(Minecraft.getMinecraft().fontRenderer, builder.toString(), gui.getGuiLeft() + pos.x + 6, gui.getGuiTop() + pos.y + 5, 1.2F, 4210752);
		
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<T> gui) {
		if(direction != null)
		{
			if(direction.ordinal() != ForgeDirection.values().length - 1)
				direction = ForgeDirection.values()[direction.ordinal() + 1];
			else
				direction = ForgeDirection.values()[0];
			gui.getTile().getModular().getTankManeger().getProducer().getData(ID).setDirection(direction);;
			PacketHandler.INSTANCE.sendToServer(new PacketTankManager(gui.getTile(), direction, ID));
		}
	}
	
	@Override
	public ArrayList<String> getTooltip(IGuiBase<T> gui) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(direction.name());
		return list;
	}

}