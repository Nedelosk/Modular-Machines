package de.nedelosk.forestmods.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import de.nedelosk.forestcore.gui.Button;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.managers.IModularGuiManager;
import de.nedelosk.forestmods.api.modular.managers.IModularInventoryManager;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGui;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.GuiModularMachines;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSelectInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ButtonModuleTab extends Button<IModularTileEntity> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("forestmods", "modular_machine", "gui");
	public final ModuleStack stack;
	public final IModule module;
	public final boolean right;

	public ButtonModuleTab(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, ModuleStack stack, boolean right) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 28, 21, null);
		this.stack = stack;
		this.right = right;
		this.module = stack.getModule();
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GuiModularMachines machine = (GuiModularMachines) mc.currentScreen;
		IModularGuiManager guiManager = ((IModularTileEntity) machine.getTile()).getModular().getManager(IModularGuiManager.class);
		IModuleGui currentGui = guiManager.getCurrentGui();
		RenderUtil.bindTexture(guiTextureOverlay);
		RenderUtil.drawTexturedModalRect(xPosition, yPosition, 1, (stack.getUID().equals(currentGui.getModuleStack().getUID())) ? 0 : 28, right ? 214 : 235, 28,
				21);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		ItemStack item = guiManager.getModular().getManager(IModularModuleManager.class).getItemStack(stack.getUID());
		drawItemStack(item, xPosition + 6, yPosition + 3);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void onButtonClick(IGuiBase<IModularTileEntity> gui) {
		IModularGuiManager guiManager = gui.getTile().getModular().getManager(IModularGuiManager.class);
		IModularInventoryManager inventoryManager = gui.getTile().getModular().getManager(IModularInventoryManager.class);
		IModuleGui currentGui = guiManager.getCurrentGui();
		IModuleInventory currentInventory = inventoryManager.getCurrentInventory();
		IModuleInventory moduleInventory = inventoryManager.getInventory(stack.getUID());
		if (!currentGui.getModuleStack().getUID().equals(stack.getUID())) {
			guiManager.setCurrentGui(module.getGui());
			inventoryManager.setCurrentInventory(moduleInventory);
			PacketHandler.INSTANCE.sendToServer(new PacketSelectInventory(((TileEntity) gui.getTile()), moduleInventory));
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularTileEntity> gui) {
		return Arrays.asList(module.getInventory().getInventoryName());
	}
}
