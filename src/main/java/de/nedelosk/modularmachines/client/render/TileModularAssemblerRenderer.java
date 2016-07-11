package de.nedelosk.modularmachines.client.render;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.client.model.ModelModularAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileModularAssemblerRenderer extends TileEntitySpecialRenderer {

	public static final ResourceLocation textureModelOFF = new ResourceLocation("modularmachines", "textures/models/modular_assembler_off.png");
	public static final ResourceLocation textureModelON = new ResourceLocation("modularmachines", "textures/models/modular_assembler_on.png");
	private final EntityItem dummyEntityItem = new EntityItem(null);
	private long lastTick;
	private ModelModularAssembler model;

	public TileModularAssemblerRenderer() {
		this.model = new ModelModularAssembler();
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f, int destroyStage) {
		if(entity != null){
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glPushMatrix();
			RenderUtil.bindTexture(textureModelOFF);
			model.render();
			GL11.glPopMatrix();
			GL11.glPopMatrix();

			World world = entity.getWorld();
			ItemStack stack = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(1);

			if (stack != null && world != null && world.isAirBlock(entity.getPos().up())) {
				dummyEntityItem.worldObj = world;

				float renderScale = 1.5f;

				GlStateManager.pushMatrix();
				{
					GlStateManager.translate((float) x + 0.5f, (float) y + 0.65f, (float) z + 0.5f);
					GlStateManager.scale(renderScale, renderScale, renderScale);
					dummyEntityItem.setEntityItemStack(stack);

					if (world.getTotalWorldTime() != lastTick) {
						lastTick = world.getTotalWorldTime();
						dummyEntityItem.onUpdate();
					}

					RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
					rendermanager.doRenderEntity(dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
				}
				GlStateManager.popMatrix();
			}
		}else{
			GL11.glPushMatrix();
			GL11.glTranslated(0.5F, 1.5F, 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			RenderUtil.bindTexture(textureModelOFF);
			model.render();
			GL11.glPopMatrix();
		}
	}
}