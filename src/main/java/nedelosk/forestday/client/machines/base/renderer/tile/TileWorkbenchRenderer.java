package nedelosk.forestday.client.machines.base.renderer.tile;

import nedelosk.forestday.client.machines.base.renderer.model.ModelWorkbench;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class TileWorkbenchRenderer extends TileEntitySpecialRenderer {
	
	public static final ResourceLocation textureModel = new ResourceLocation("forestday", "textures/models/workbench.png");
	
	private ModelWorkbench table;
	
	public TileWorkbenchRenderer()
	{
		this.table = new ModelWorkbench();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		this.bindTexture(textureModel);
		GL11.glPushMatrix();
		table.renderTable();
		if(entity.getBlockMetadata() == 2)
		{
			table.renderChest();
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
	public void renderItem(int meta, double x, double y, double z, float f)
	{
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		this.bindTexture(textureModel);
		GL11.glPushMatrix();
		table.renderTable();
		if(meta == 2)
		{
			table.renderChest();
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
