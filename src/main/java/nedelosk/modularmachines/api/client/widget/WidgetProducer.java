package nedelosk.modularmachines.api.client.widget;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.api.guis.GuiBase;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.api.utils.RenderUtils;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketTankManager;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class WidgetProducer<T extends TileEntity & IModularTileEntity> extends Widget<T> {

	protected ResourceLocation widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
	public int producer;
	public int ID;

	public WidgetProducer(int posX, int posY, int producer, int ID) {
		super(posX, posY, 18, 18);
		this.producer = producer;
		this.ID = ID;
	}

	@Override
	public void draw(IGuiBase<T> gui) {
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 18, 0, 18, 18);
		
		if(gui.getTile().getModular().getFluidProducers().isEmpty())
			return;
		
		ModuleStack<IModule, IProducer> stack = gui.getTile().getModular().getFluidProducers().get(producer);
		if(stack.getItem() != null)
			GuiBase.getItemRenderer().renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, stack.getItem(), gui.getGuiLeft() + pos.x - 1, gui.getGuiTop() + pos.y - 1);
		GL11.glEnable(GL11.GL_LIGHTING);

	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<T> gui) {
		if (gui.getTile() != null && gui.getTile().getModular() != null && gui.getTile().getModular().getFluidProducers() != null) {
			List<ModuleStack> stacks = gui.getTile().getModular().getFluidProducers();
			if(stacks.isEmpty())
				return;
			ModuleStack stack = stacks.get(producer);
			if(!stacks.isEmpty()){
				if (stacks.indexOf(stack) != stacks.size() - 1){
					producer = stacks.indexOf(stack) + 1;
				}
				else{
					producer = 0;
				}
				gui.getTile().getModular().getTankManeger().getProducer().getData(ID).setProducer(producer);
				PacketHandler.INSTANCE.sendToServer(new PacketTankManager(gui.getTile(), producer, ID));
			}
		}
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase<T> gui) {
		ArrayList<String> list = new ArrayList<String>();
		
		List<ModuleStack> stacks = gui.getTile().getModular().getFluidProducers();
		if(stacks.isEmpty())
			return list;
		ModuleStack stack = stacks.get(producer);
		list.add(StatCollector.translateToLocal(stack.getProducer().getName(stack) + ".name"));
		return list;
	}

}
