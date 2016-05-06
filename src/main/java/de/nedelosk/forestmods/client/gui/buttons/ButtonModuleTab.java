package de.nedelosk.forestmods.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import de.nedelosk.forestmods.library.gui.Button;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ButtonModuleTab extends Button<IModularTileEntity> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("forestmods", "modular_machine", "gui");
	public final IModule module;
	public final IModularTileEntity tile;
	public final boolean right;

	public ButtonModuleTab(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, IModule module, IModularTileEntity tile, boolean right) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 28, 21, null);
		this.module = module;
		this.right = right;
		this.tile = tile;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTextureOverlay);
		RenderUtil.drawTexturedModalRect(xPosition, yPosition, 1, (module.getModuleContainer().getUID().equals(tile.getModular().getCurrentModule().getModuleContainer().getUID())) ? 0 : 28,
				right ? 214 : 235, 28, 21);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		ItemStack item = module.getModuleContainer().getItemStack();
		drawItemStack(item, xPosition + 6, yPosition + 3);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void onButtonClick(IGuiBase<IModularTileEntity> gui) {
		IModular modular = gui.getHandler().getModular();
		IModule currentModule = modular.getCurrentModule();
		if (!currentModule.getModuleContainer().getUID().equals(module.getModuleContainer().getUID())) {
			modular.setCurrentModule(module);
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularTileEntity> gui) {
		if (!module.isHandlerDisabled(ModuleManager.inventoryType) && module.getInventory() != null && module.getInventory().hasCustomInventoryName()) {
			return Arrays.asList(module.getInventory().getInventoryName());
		}
		return Arrays.asList(module.getUnlocalizedName());
	}
}
