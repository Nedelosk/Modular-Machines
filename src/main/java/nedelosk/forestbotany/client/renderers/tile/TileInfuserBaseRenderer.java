package nedelosk.forestbotany.client.renderers.tile;

import nedelosk.forestbotany.common.blocks.tile.TileInfuser;
import nedelosk.forestbotany.common.blocks.tile.TileInfuserBase;
import nedelosk.forestbotany.common.blocks.tile.TileInfuserChamber;
import nedelosk.forestbotany.common.items.ItemPlant;
import nedelosk.forestbotany.common.items.ItemSeed;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class TileInfuserBaseRenderer extends TileEntitySpecialRenderer {

    private static RenderBlocks renderBlocks = new RenderBlocks();
	
	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		renderTileEntityAt((TileInfuserBase)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
	}
	
	public void renderTileEntityAt(TileInfuserBase tile, double x, double y, double z, float p_147500_8_) {
		int meta = tile.getBlockMetadata();
		if (meta == 0 || meta == 2) {
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			ResourceLocation metal = new ResourceLocation("forestbotany", "textures/blocks/infuser_metal.png");
			ResourceLocation nutrientInput = new ResourceLocation("forestbotany", "textures/blocks/infuser_metal_input_nutrient.png");
			this.bindTexture(metal);
			GL11.glPushMatrix();
			
			if(tile.waterInput != null && tile.waterInput.ordinal() == 0)
			{
				this.bindTexture(nutrientInput);
			}
			//Down 0
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 1, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 0, 1);
			t.draw();
			this.bindTexture(metal);
			
			if(tile.waterInput != null && tile.waterInput.ordinal() == 5)
			{
				this.bindTexture(nutrientInput);
			}
			//East 5
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 1, 1);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0, 1);
			t.draw();
			this.bindTexture(metal);
			
			if(tile.waterInput != null && tile.waterInput.ordinal() == 4)
			{
				this.bindTexture(nutrientInput);
			}
			//West 4
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 1, 1);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0, 1);
			t.draw();
			this.bindTexture(metal);
			
			if(tile.waterInput != null && tile.waterInput.ordinal() == 2)
			{
				this.bindTexture(nutrientInput);
			}
			//North 2
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 1);
			t.draw();
			this.bindTexture(metal);
			
			if(tile.waterInput != null && tile.waterInput.ordinal() == 3)
			{
				this.bindTexture(nutrientInput);
			}
			//South 3
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0, 0);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0, 1);
			t.draw();
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		else if(meta == 1 || meta == 3)
		{
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			this.bindTexture(new ResourceLocation("forestbotany", "textures/blocks/infuser_metal.png"));
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.3, 0.5, -0.3, 0, 0);
		    t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 0);
		    t.addVertexWithUV(0.3, 0.5, 0.3, 1, 1);
		    t.addVertexWithUV(0.3, 0.5, -0.3, 0, 1);
		    t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.5, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.5, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 0.7, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.5, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 0, 0);
			t.addVertexWithUV(-0.3,0.5, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.5, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 0.7, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 0, 0);
			t.addVertexWithUV(0.3,0.5, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.4, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.4, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.4, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.4, -0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.4, -0.3, 0, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.4, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.4, 0.3, 0, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.4, -0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 1);
			t.draw();
		    
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.3, 1.4, -0.3, 0, 0);
		    t.addVertexWithUV(-0.3, 1.4, 0.3, 1, 0);
		    t.addVertexWithUV(0.3, 1.4, 0.3, 1, 1);
		    t.addVertexWithUV(0.3, 1.4, -0.3, 0, 1);
		    t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 0, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 1, 1);
			t.addVertexWithUV(0.3, 0.7, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 0, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 1, 1);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 0, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 1, 1);
			t.addVertexWithUV(0.3, 0.7, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 0, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 1);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 0, 1);
			t.draw();
			
			this.bindTexture(new ResourceLocation("forestbotany", "textures/blocks/infuser_glass.png"));
			
			//Front
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			//back
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			//Right
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			//left
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			//Plant
			if(tile.getBaseTile() != null)
			{
			if(tile.getBaseTile().getPlant() != null)
			{
				if(tile.getBaseTile().getPlant().getTagCompound().getCompoundTag("Crop").getBoolean("IsCrop"))
				{
					RenderUtils.bindItemTexture();
					
					IIcon iconPlant = ((ItemPlant)tile.getBaseTile().getPlant().getItem()).getPlantIcon(tile.getBaseTile().getPlant(), 0);
					
					if(iconPlant == null)
						iconPlant = Blocks.wheat.getIcon(0, 0);
			
			float plantEnd = 0.7F;
			float plantTop = 1.27F;
			
			float UMax = iconPlant.getMaxU();
			float UMin = iconPlant.getMinU();
			float VMax = iconPlant.getMaxV();
			float VMin = iconPlant.getMinV();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.2, plantTop, -0.28, UMin, VMin);
			t.addVertexWithUV(-0.2, plantEnd, -0.28, UMin, VMax);
			t.addVertexWithUV(-0.2, plantEnd, 0.28, UMax, VMax);
			t.addVertexWithUV(-0.2, plantTop, 0.28, UMax, VMin);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.2, plantTop, -0.28, UMin, VMin);
			t.addVertexWithUV(0.2, plantEnd, -0.28, UMin, VMax);
			t.addVertexWithUV(0.2, plantEnd, 0.28, UMax, VMax);
			t.addVertexWithUV(0.2, plantTop, 0.28, UMax, VMin);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.2, plantTop, 0.28, UMin, VMin);
			t.addVertexWithUV(-0.2, plantEnd, 0.28, UMin, VMax);
			t.addVertexWithUV(-0.2, plantEnd, -0.28, UMax, VMax);
			t.addVertexWithUV(-0.2, plantTop, -0.28, UMax, VMin);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.2, plantTop, 0.28, UMin, VMin);
			t.addVertexWithUV(0.2, plantEnd, 0.28, UMin, VMax);
			t.addVertexWithUV(0.2, plantEnd, -0.28, UMax, VMax);
			t.addVertexWithUV(0.2, plantTop, -0.28, UMax, VMin);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.28, plantTop, -0.2, UMin, VMin);
			t.addVertexWithUV(0.28, plantEnd, -0.2, UMin, VMax);
			t.addVertexWithUV(-0.28, plantEnd, -0.2, UMax, VMax);
			t.addVertexWithUV(-0.28, plantTop, -0.2, UMax, VMin);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.28, plantTop, 0.2, UMin, VMin);
			t.addVertexWithUV(0.28, plantEnd, 0.2, UMin, VMax);
			t.addVertexWithUV(-0.28, plantEnd, 0.2, UMax, VMax);
			t.addVertexWithUV(-0.28, plantTop, 0.2, UMax, VMin);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.28, plantTop, 0.2, UMin, VMin);
			t.addVertexWithUV(-0.28, plantEnd, 0.2, UMin, VMax);
			t.addVertexWithUV(0.28, plantEnd, 0.2, UMax, VMax);
			t.addVertexWithUV(0.28, plantTop, 0.2, UMax, VMin);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.28, plantTop, -0.2, UMin, VMin);
			t.addVertexWithUV(-0.28, plantEnd, -0.2, UMin, VMax);
			t.addVertexWithUV(0.28, plantEnd, -0.2, UMax, VMax);
			t.addVertexWithUV(0.28, plantTop, -0.2, UMax, VMin);
			t.draw();
			RenderUtils.bindBlockTexture();
			}
			}
			}
			else
			{
				tile.onNeighborBlockChange(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, tile.getBlockType());
			}
			if(tile.getBaseTile() != null && tile.getBaseTile().hasSoil() && tile.getBaseTile().getSoil() != null)
			{
			RenderUtils.bindBlockTexture();
			IIcon icon = tile.getBaseTile().getSoil().getSoil().getIcon(tile.getBaseTile().getSoil().getIconSide(), tile.getBaseTile().getSoil().getSoilMeta());
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.3, 0.7, -0.3, icon.getMinU(), icon.getMinV());
		    t.addVertexWithUV(-0.3, 0.7, 0.3, icon.getMaxU(), icon.getMinV());
		    t.addVertexWithUV(0.3, 0.7, 0.3, icon.getMaxU(), icon.getMaxV());
		    t.addVertexWithUV(0.3, 0.7, -0.3, icon.getMinU(), icon.getMaxV());
		    t.draw();
			}
			renderTank(t, tile);

			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	
	}
	
	public void renderTank(Tessellator t, TileInfuserBase tile)
	{
		if(tile.getBaseTile() != null)
		{
		if(tile.getBaseTile().getTank().getFluid() != null)
		{
			
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderUtils.bindBlockTexture();
		IIcon icon = tile.getBaseTile().getTank().getFluid().getFluid().getStillIcon();
		int amount = tile.getBaseTile().getTank().getFluidAmount();
		int capacity = tile.getBaseTile().getTank().getCapacity();
		float fluidAmount = (float)amount / capacity;
	
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.29, 0.5 + fluidAmount - 0.2, 0.29, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(0.29, 0.5, 0.29, icon.getMaxU(), icon.getMinV());
		t.addVertexWithUV(0.29, 0.5, -0.29, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(0.29, 0.5 + fluidAmount - 0.2, -0.29, icon.getMinU(), icon.getMaxV());
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.29, 0.5 + fluidAmount - 0.2, -0.29, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(-0.29, 0.5, -0.29, icon.getMaxU(), icon.getMinV());
		t.addVertexWithUV(-0.29, 0.5, 0.29, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(-0.29, 0.5 + fluidAmount - 0.2, 0.29, icon.getMinU(), icon.getMaxV());
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.29, 0.5 + fluidAmount - 0.2, -0.29, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(0.29, 0.5, -0.29, icon.getMaxU(), icon.getMinV());
		t.addVertexWithUV(-0.29, 0.5, -0.29, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(-0.29, 0.5 + fluidAmount - 0.2, -0.29, icon.getMinU(), icon.getMaxV());
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.29, 0.5 + fluidAmount - 0.2, 0.29, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(-0.29, 0.5, 0.29, icon.getMaxU(), icon.getMinV());
		t.addVertexWithUV(0.29, 0.5, 0.29, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(0.29, 0.5 + fluidAmount - 0.2, 0.29, icon.getMinU(), icon.getMaxV());
		t.draw();
		
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.29, 0.5 + fluidAmount - 0.2, -0.29, icon.getMinU(), icon.getMinV());
	    t.addVertexWithUV(-0.29, 0.5 + fluidAmount - 0.2, 0.29, icon.getMaxU(), icon.getMinV());
	    t.addVertexWithUV(0.29, 0.5 + fluidAmount - 0.2, 0.29, icon.getMaxU(), icon.getMaxV());
	    t.addVertexWithUV(0.29, 0.5 + fluidAmount - 0.2, -0.29, icon.getMinU(), icon.getMaxV());
	    t.draw();
	    
	    GL11.glPopAttrib();
		}
		}
		else
		{
			tile.onNeighborBlockChange(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, tile.getBlockType());
		}
	}
	
	public void renderItem(ItemStack stack)
	{
		int meta = stack.getItemDamage();
		if(meta == 1 || meta == 0 || meta == 2 || meta == 3)
		{
			double x = 0.0D, y = 0.0D, z = 0.0D;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			this.bindTexture(new ResourceLocation("forestbotany", "textures/blocks/infuser_metal.png"));
			GL11.glPushMatrix();
			// Down
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 1, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 1, 1);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 1, 1);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0, 0);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0, 1);
			t.draw();
			
			this.bindTexture(new ResourceLocation("forestbotany", "textures/blocks/infuser_metal.png"));
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.3, 0.5, -0.3, 0, 0);
		    t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 0);
		    t.addVertexWithUV(0.3, 0.5, 0.3, 1, 1);
		    t.addVertexWithUV(0.3, 0.5, -0.3, 0, 1);
		    t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.5, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.5, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 0.7, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.5, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 0, 0);
			t.addVertexWithUV(-0.3,0.5, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.5, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 0.7, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 0, 0);
			t.addVertexWithUV(0.3,0.5, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.4, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.4, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.4, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.4, -0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.4, -0.3, 0, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.4, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.4, 0.3, 0, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.4, -0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 1);
			t.draw();
		    
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.3, 1.4, -0.3, 0, 0);
		    t.addVertexWithUV(-0.3, 1.4, 0.3, 1, 0);
		    t.addVertexWithUV(0.3, 1.4, 0.3, 1, 1);
		    t.addVertexWithUV(0.3, 1.4, -0.3, 0, 1);
		    t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 0, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 1, 1);
			t.addVertexWithUV(0.3, 0.7, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 0, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 1, 1);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 0, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 1, 1);
			t.addVertexWithUV(0.3, 0.7, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 0, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 1);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 0, 1);
			t.draw();
			
			this.bindTexture(new ResourceLocation("forestbotany", "textures/blocks/infuser_glass.png"));
			
			//Front
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			//back
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			//Right
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			//left
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.7, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.7, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.7, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.7, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 1);
			t.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		else if(meta == 4 || meta == 5)
		{
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslated((float) 0.5F, (float) 0.5F, (float) 0.5F);
			Tessellator t = Tessellator.instance;
			this.bindTexture(new ResourceLocation("forestbotany", "textures/blocks/infuser_metal.png"));
			GL11.glPushMatrix();
			// Down
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 1, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 0, 1);
			t.draw();
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
		    t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
		    t.addVertexWithUV(0.5, 0.5, 0.5, 1, 1);
		    t.addVertexWithUV(0.5, 0.5, -0.5, 0, 1);
		    t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 1, 1);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 1, 1);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0, 0);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0, 1);
			t.draw();
			
			this.bindTexture(new ResourceLocation("forestbotany", "textures/blocks/infuser_metal.png"));
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.4, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.4, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.4, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.4, -0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.4, -0.3, 0, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.4, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.4, 0.3, 0, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.4, -0.3, 0, 1);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 1);
			t.draw();
		    
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.3, 1.4, -0.3, 0, 0);
		    t.addVertexWithUV(-0.3, 1.4, 0.3, 1, 0);
		    t.addVertexWithUV(0.3, 1.4, 0.3, 1, 1);
		    t.addVertexWithUV(0.3, 1.4, -0.3, 0, 1);
		    t.draw();
			
			this.bindTexture(new ResourceLocation("forestbotany", "textures/blocks/infuser_glass.png"));
			
			//Front
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.5, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.5, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.5, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			//back
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.5, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.5, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.5, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			//Right
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.5, 0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.5, -0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.5, -0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 1);
			t.draw();
			
			//left
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(-0.3, 0.5, -0.3, 1, 0);
			t.addVertexWithUV(-0.3, 0.5, 0.3, 1, 1);
			t.addVertexWithUV(-0.3, 1.35, 0.3, 0, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.3, 1.35, -0.3, 0, 0);
			t.addVertexWithUV(0.3, 0.5, -0.3, 1, 0);
			t.addVertexWithUV(0.3, 0.5, 0.3, 1, 1);
			t.addVertexWithUV(0.3, 1.35, 0.3, 0, 1);
			t.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
			
		}
	}

}
