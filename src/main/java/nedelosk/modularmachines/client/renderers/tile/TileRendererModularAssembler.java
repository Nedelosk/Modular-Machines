package nedelosk.modularmachines.client.renderers.tile;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.client.renderers.model.ModelModularAssembler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileRendererModularAssembler extends TileEntitySpecialRenderer{

	private ModelModularAssembler assembler;
	
	public TileRendererModularAssembler()
	{
		this.assembler = new ModelModularAssembler();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		int damage = entity.blockMetadata;
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		bindTexture(new ResourceLocation("modularmachines", "textures/models/modular_assembler_" + damage + ".png"));
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
		bindTexture(new ResourceLocation("modularmachines", "textures/models/modular_assembler_" + damage + ".png"));
		GL11.glPushMatrix();
		assembler.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
