package nedelosk.modularmachines.api.client.gui;

import java.util.Arrays;
import java.util.List;
import org.lwjgl.opengl.GL11;
import nedelosk.forestday.api.guis.Button;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.utils.RenderUtils;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.pages.PacketSelectTankManagerTab;
import nedelosk.modularmachines.api.producers.managers.fluids.IProducerTankManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ButtonTabTankManager extends Button<IModularTileEntity<IModular>> {

	protected ResourceLocation guiTextureOverlay = RenderUtils.getResourceLocation("modularmachines", "modular_machine", "gui");

	public ModuleStack<IModule, IProducerTankManager> stack;
	public boolean down;
	public int tabID;

	public ButtonTabTankManager(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, ModuleStack<IModule, IProducerTankManager> stack, boolean down, int tabID) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 28, 21, null);
		this.stack = stack;
		this.down = down;
		this.tabID = tabID;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);

		GuiModularMachine machine = (GuiModularMachine) mc.currentScreen;
		IProducerTankManager manager = stack.getProducer();
		RenderUtils.bindTexture(guiTextureOverlay);
		machine.drawTexturedModalRect(xPosition, yPosition, manager.getTab() == tabID ? 74 : 103, down ? 218 : 237, 29, 19);
		RenderUtils.bindTexture(RenderUtils.getResourceLocation("modularmachines", "widgets", "gui"));
		machine.drawTexturedModalRect(xPosition, yPosition, 0, 18 + id * 18, 18, 18);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();

	}
	
	@Override
	public void onButtonClick(IGuiBase<IModularTileEntity<IModular>> gui) {
		IModularGuiManager guiManager = gui.getTile().getModular().getGuiManager();

		IModularTileEntity<IModular> tile = gui.getTile();
		IProducerTankManager tankManager = stack.getProducer();
		
		if (!(id == tankManager.getTab())) {
			tankManager.setTab(tabID);
			
			PacketHandler.INSTANCE.sendToServer(new PacketSelectTankManagerTab((TileEntity) tile, tabID));
		}
	}
	
	@Override
	public List<String> getTooltip(IGuiBase<IModularTileEntity<IModular>> gui) {
		return Arrays.asList(Integer.toString(stack.getProducer().getTab()));
	}

}
