package nedelosk.modularmachines.client.renderers.tile;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.client.renderers.model.ModelModularAssembler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileModularAssemblerRenderer extends TileEntitySpecialRenderer {
	
	public static final ResourceLocation textureModel_0 = new ResourceLocation("modularmachines", "textures/models/modular_assembler_0.png");
	public static final ResourceLocation textureModel_1 = new ResourceLocation("modularmachines", "textures/models/modular_assembler_1.png");
	public static final ResourceLocation textureModel_2 = new ResourceLocation("modularmachines", "textures/models/modular_assembler_2.png");
	public static final ResourceLocation textureModel_3 = new ResourceLocation("modularmachines", "textures/models/modular_assembler_3.png");
	public static final ResourceLocation textureModel_4 = new ResourceLocation("modularmachines", "textures/models/modular_assembler_4.png");
	
	private ModelModularAssembler assembler;
	
	public TileModularAssemblerRenderer()
	{
		this.assembler = new ModelModularAssembler();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		int damage = entity.blockMetadata;
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		switch (damage) {
		case 1:
			bindTexture(textureModel_1);
			break;
		case 2:
			bindTexture(textureModel_2);
			break;
		case 3:
			bindTexture(textureModel_3);
			break;
		case 4:
			bindTexture(textureModel_4);
			break;
		default:
			bindTexture(textureModel_0);
			break;
		}
		GL11.glPushMatrix();
		assembler.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
	public void renderItem(ItemStack stack) {
		int damage = stack.getItemDamage();
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		switch (damage) {
		case 1:
			bindTexture(textureModel_1);
			break;
		case 2:
			bindTexture(textureModel_2);
			break;
		case 3:
			bindTexture(textureModel_3);
			break;
		case 4:
			bindTexture(textureModel_4);
			break;
		default:
			bindTexture(textureModel_0);
			break;
		}
		GL11.glPushMatrix();
		assembler.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
