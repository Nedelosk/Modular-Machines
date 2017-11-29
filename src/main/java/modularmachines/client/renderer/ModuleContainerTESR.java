package modularmachines.client.renderer;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.IOMode;
import modularmachines.api.IScrewdriver;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.block.IBoundingBoxComponent;
import modularmachines.api.modules.components.handlers.IIOComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.blocks.tile.TileEntityModuleContainer;

public class ModuleContainerTESR extends TileEntitySpecialRenderer<TileEntityModuleContainer> {
	private float animationTime = 0.0F;
	private EnumFacing lastFacing;
	
	@Override
	public void render(TileEntityModuleContainer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		Minecraft minecraft = Minecraft.getMinecraft();
		RayTraceResult hit = minecraft.objectMouseOver;
		EntityPlayer player = minecraft.player;
		ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
		IScrewdriver screwdriver = null;
		if (heldItem.getItem() instanceof IScrewdriver) {
			screwdriver = (IScrewdriver) heldItem.getItem();
			lastFacing = screwdriver.getSelectedFacing(heldItem);
			animationTime = 50.0F;
		}
		if (screwdriver != null || animationTime > 0.0F) {
			IModuleContainer container = te.moduleContainer;
			BlockPos hitPos = hit == null ? null : hit.getBlockPos();
			BlockPos pos = te.getPos();
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.disableTexture2D();
			GlStateManager.glLineWidth(6.0F);
			GlStateManager.disableLighting();
			GlStateManager.depthMask(false);
			GlStateManager.translate(x, y, z);
			IModule hitModule = null;
			if (hit != null) {
				hitModule = container.getModule(hit.subHit);
			}
			if (pos.equals(hitPos) && hitModule != null && hitModule.hasComponent(IIOComponent.class)) {
				drawModule(hitModule, lastFacing);
			} else {
				for (IModule module : container.getModules()) {
					drawModule(module, lastFacing);
				}
			}
			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
			animationTime -= partialTicks;
		}
	}
	
	private void drawModule(IModule module, @Nullable EnumFacing facing) {
		IBoundingBoxComponent component = module.getComponent(IBoundingBoxComponent.class);
		IIOComponent ioComponent = module.getComponent(IIOComponent.class);
		if (component == null || ioComponent == null) {
			return;
		}
		IOMode mode = ioComponent.getMode(facing);
		int color = mode.getColor();
		float colorRed = (float) (color >> 16 & 255) / 255.0F;
		float colorGreen = (float) (color >> 8 & 255) / 255.0F;
		float colorBlue = (float) (color & 255) / 255.0F;
		AxisAlignedBB boundingBox = component.getCollisionBox().grow(0.0020000000949949026D);
		RenderGlobal.drawSelectionBoundingBox(boundingBox, colorRed, colorGreen, colorBlue, 0.85F * (animationTime / 50.0F));
		RenderGlobal.renderFilledBox(boundingBox, colorRed, colorGreen, colorBlue, 0.65F * (animationTime / 50.0F));
		
	}
}
