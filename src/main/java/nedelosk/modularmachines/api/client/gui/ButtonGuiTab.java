package nedelosk.modularmachines.api.client.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import nedelosk.forestcore.library.gui.Button;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.utils.RenderUtil;
import nedelosk.modularmachines.api.modular.basic.IModularDefault;
import nedelosk.modularmachines.api.modular.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.managers.IModularInventoryManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketSelectInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class ButtonGuiTab extends Button<IModularTileEntity<IModularDefault>> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("modularmachines", "modular_machine", "gui");
	public final ModuleStack stack;
	public final IModuleGui moduleGui;
	public final boolean right;

	public ButtonGuiTab(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, ModuleStack stack, IModuleGui gui, boolean right) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 28, 21, null);
		this.stack = stack;
		this.right = right;
		this.moduleGui = gui;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GuiModular machine = (GuiModular) mc.currentScreen;
		IModularGuiManager guiManager = ((IModularTileEntity<IModularDefault>) machine.getTile()).getModular().getGuiManager();
		IModuleGui currentGui = guiManager.getCurrentGui();
		RenderUtil.bindTexture(guiTextureOverlay);
		RenderUtil.drawTexturedModalRect(xPosition, yPosition, 1, (moduleGui.getUID().equals(currentGui.getUID())) ? 0 : 28, right ? 214 : 235, 28, 21);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		ItemStack item = stack.getItemStack();
		drawItemStack(item, xPosition + 6, yPosition + 3);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void onButtonClick(IGuiBase<IModularTileEntity<IModularDefault>> gui) {
		IModularGuiManager guiManager = gui.getTile().getModular().getGuiManager();
		IModularInventoryManager inventoryManager = gui.getTile().getModular().getInventoryManager();
		IModuleGui currentGui = guiManager.getCurrentGui();
		IModuleInventory currentInventory = inventoryManager.getCurrentInventory();
		IModuleInventory moduleInventory = inventoryManager.getInventory(currentGui.getUID());
		if (!currentGui.getUID().equals(moduleGui.getUID())) {
			guiManager.setCurrentGui(moduleGui);
			inventoryManager.setCurrentInventory(moduleInventory);
			PacketHandler.INSTANCE.sendToServer(new PacketSelectInventory(((TileEntity) gui.getTile()), moduleInventory));
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularTileEntity<IModularDefault>> gui) {
		return Arrays
				.asList(StatCollector.translateToLocal("mm.modularmachine.bookmark." + moduleGui.getCategoryUID() + "." + moduleGui.getModuleUID() + ".name"));
	}
}
