package de.nedelosk.forestmods.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import de.nedelosk.forestcore.gui.Button;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.GuiModular;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ButtonModuleTab extends Button<IModularTileEntity> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("forestmods", "modular_machine", "gui");
	public final ModuleStack stack;
	public final GuiModular gui;
	public final boolean right;

	public ButtonModuleTab(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, ModuleStack stack, GuiModular gui, boolean right) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 28, 21, null);
		this.stack = stack;
		this.right = right;
		this.gui = gui;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		IModularTileEntity tile = gui.getTile();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTextureOverlay);
		RenderUtil.drawTexturedModalRect(xPosition, yPosition, 1, (stack.getUID().equals(tile.getModular().getCurrentStack().getUID())) ? 0 : 28,
				right ? 214 : 235, 28, 21);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		ItemStack item = tile.getModular().getItemStack(stack.getUID());
		drawItemStack(item, xPosition + 6, yPosition + 3);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void onButtonClick(IGuiBase<IModularTileEntity> gui) {
		IModular modular = gui.getTile().getModular();
		ModuleStack currentStack = modular.getCurrentStack();
		if (!currentStack.getUID().equals(stack.getUID())) {
			modular.setCurrentStack(stack);
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularTileEntity> gui) {
		IModule module = stack.getModule();
		if (!module.isHandlerDisabled(ModuleManager.inventoryType) && module.getInventory() != null && module.getInventory().hasCustomInventoryName()) {
			return Arrays.asList(module.getInventory().getInventoryName());
		}
		return Arrays.asList(module.getUnlocalizedName(stack));
	}
}
