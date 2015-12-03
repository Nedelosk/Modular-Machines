package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.api.guis.GuiBase;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.api.utils.RenderUtils;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketSelectTankManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import net.minecraft.util.ResourceLocation;

public class WidgetProducer extends Widget<TileModular> {

	protected ResourceLocation widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
	public String producerName;
	public int ID;
	public ModuleStack stack;

	public WidgetProducer(int posX, int posY, String producerName, ModuleStack stack, int ID) {
		super(posX, posY, 18, 18);
		this.producerName = producerName;
		this.stack = stack;
		this.ID = ID;
	}

	@Override
	public void draw(IGuiBase<TileModular> gui) {
		if(stack == null)
			return;
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 18, 0, 18, 18);
		if(stack.getItem() != null)
		GuiBase.getItemRenderer().renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, stack.getItem(), gui.getGuiLeft() + pos.x - 1, gui.getGuiTop() + pos.y - 1);
		GL11.glEnable(GL11.GL_LIGHTING);

	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<TileModular> gui) {
		if (gui.getTile() != null && gui.getTile().getModular() != null && gui.getTile().getModular().getFluidProducers() != null) {
			List<ModuleStack> stacks = gui.getTile().getModular().getFluidProducers();
			if(!stacks.isEmpty()){
				if (stacks.indexOf(stacks) != stacks.size() - 1){
					stack = stacks.get(stacks.indexOf(stacks) + 1);
				}
				else{
					stack = stacks.get(0);
				}
				producerName = stack.getProducer().getName(stack);
				gui.getTile().getModular().getTankManeger().getProducer().getManager().setProducer(ID, producerName);
				PacketHandler.INSTANCE.sendToServer(new PacketSelectTankManager(gui.getTile(), producerName, ID));
			}
		}
	}

	@Override
	public ArrayList<String> getTooltip() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(stack.getProducer().getName(stack));
		return list;
	}

}
