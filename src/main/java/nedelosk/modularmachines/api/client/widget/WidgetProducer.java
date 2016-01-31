package nedelosk.modularmachines.api.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import nedelosk.forestcore.library.gui.GuiBase;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.utils.RenderUtil;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.fluids.IModuleWithFluid;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketTankManager;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class WidgetProducer<T extends TileEntity & IModularTileEntity> extends Widget<T> {

	protected ResourceLocation widget = RenderUtil.getResourceLocation("modularmachines", "widgets", "gui");
	public String module;
	public int ID;

	public WidgetProducer(int posX, int posY, String module, int ID) {
		super(posX, posY, 18, 18);
		this.module = module;
		this.ID = ID;
	}

	@Override
	public void draw(IGuiBase<T> gui) {
		if (module == null) {
			return;
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(widget);
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 18, 0, 18, 18);
		if (ModularUtils.getFluidProducers(gui.getTile().getModular()).isEmpty()) {
			return;
		}
		ModuleStack<IModuleWithFluid, IModuleSaver> stack = ModularUtils.getFluidProducers(gui.getTile().getModular()).get(module);
		if (stack.getItemStack() != null) {
			GuiBase.getItemRenderer().renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, stack.getItemStack(), gui.getGuiLeft() + pos.x + 1,
					gui.getGuiTop() + pos.y + 1);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<T> gui) {
		if (gui.getTile() != null && gui.getTile().getModular() != null && ModularUtils.getFluidProducers(gui.getTile().getModular()) != null
				&& module != null) {
			HashMap<String, ModuleStack<IModuleWithFluid, IModuleSaver>> stacks = ModularUtils.getFluidProducers(gui.getTile().getModular());
			List<ModuleStack<IModuleWithFluid, IModuleSaver>> stacksList = Lists.newArrayList();
			stacksList.addAll(stacks.values());
			if (stacks.isEmpty()) {
				return;
			}
			ModuleStack stack = stacks.get(module);
			if (!stacks.isEmpty()) {
				ModuleStack m;
				if (stacksList.indexOf(stack) != stacks.size() - 1) {
					m = stacksList.get(stacksList.indexOf(stack) + 1);
				} else {
					m = stacksList.get(0);
				}
				module = m.getModule().getUID();
				ModularUtils.getTankManager(gui.getTile().getModular()).getSaver().getData(ID).setModule(module);
				PacketHandler.INSTANCE.sendToServer(new PacketTankManager(gui.getTile(), module, ID));
			}
		}
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase<T> gui) {
		if (module == null) {
			return null;
		}
		ArrayList<String> list = new ArrayList<String>();
		HashMap<String, ModuleStack<IModuleWithFluid, IModuleSaver>> stacks = ModularUtils.getFluidProducers(gui.getTile().getModular());
		if (stacks.isEmpty()) {
			return list;
		}
		ModuleStack stack = stacks.get(module);
		list.add(StatCollector.translateToLocal(stack.getModule().getUID() + ".name"));
		return list;
	}
}
