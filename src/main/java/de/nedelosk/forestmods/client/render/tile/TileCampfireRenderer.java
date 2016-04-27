package de.nedelosk.forestmods.client.render.tile;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestmods.client.render.models.ModelCampfire;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileCampfireRenderer extends TileEntitySpecialRenderer {

	public static final ResourceLocation textureModelWood = new ResourceLocation("forestmods", "textures/models/campfire_wood.png");
	public static final ResourceLocation textureModelItem = new ResourceLocation("forestmods", "textures/models/campfire_item.png");
	private ModelCampfire model;

	public TileCampfireRenderer() {
		this.model = new ModelCampfire();
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		GL11.glPushMatrix();
		if (((TileCampfire) entity).fuelStorage > 0) {
			RenderUtil.bindTexture(textureModelWood);
			model.renderWood();
		}
		if (((TileCampfire) entity).getStackInSlot(4) != null) {
			RenderUtil.bindTexture(new ResourceLocation("forestmods",
					"textures/models/campfire_" + "curbs_" + Config.campfireCurbs[((TileCampfire) entity).getStackInSlot(4).getItemDamage()] + ".png"));
			model.renderCubs();
		}
		if (((TileCampfire) entity).getStackInSlot(5) != null) {
			RenderUtil.bindTexture(new ResourceLocation("forestmods", "textures/models/campfire_" + "pot_holders_"
					+ Config.campfirePotHolders[((TileCampfire) entity).getStackInSlot(5).getItemDamage()] + ".png"));
			model.renderPotHolder();
		}
		if (((TileCampfire) entity).getStackInSlot(6) != null) {
			RenderUtil.bindTexture(new ResourceLocation("forestmods",
					"textures/models/campfire_" + "pots_" + Config.campfirePots[((TileCampfire) entity).getStackInSlot(6).getItemDamage()] + ".png"));
			model.renderPot();
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public void renderItem(int meta, String texture) {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		if (texture == "curb") {
			RenderUtil.bindTexture(new ResourceLocation("forestmods", "textures/models/campfire_" + "curbs_" + Config.campfireCurbs[meta] + ".png"));
			model.renderCubs();
		} else if (texture == "pot_holder") {
			GL11.glTranslated(0, 0.2, 0);
			RenderUtil.bindTexture(new ResourceLocation("forestmods", "textures/models/campfire_" + "pot_holders_" + Config.campfirePotHolders[meta] + ".png"));
			model.renderPotHolder();
		} else if (texture == "pot") {
			GL11.glTranslated(0, 0.7, 0);
			RenderUtil.bindTexture(new ResourceLocation("forestmods", "textures/models/campfire_" + "pots_" + Config.campfirePots[meta] + ".png"));
			model.renderPot();
		}
		GL11.glPopMatrix();
	}

	public void renderItem(int meta, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		this.bindTexture(textureModelItem);
		GL11.glPushMatrix();
		model.renderItem();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
