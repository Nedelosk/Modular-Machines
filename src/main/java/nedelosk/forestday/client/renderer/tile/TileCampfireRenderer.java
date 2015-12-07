package nedelosk.forestday.client.renderer.tile;

import nedelosk.forestday.api.utils.RenderUtils;
import nedelosk.forestday.client.renderer.model.ModelCampfire;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.configs.ForestDayConfig;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class TileCampfireRenderer extends TileEntitySpecialRenderer {

	public static final ResourceLocation textureModelWood = new ResourceLocation("forestday",
			"textures/models/campfire_wood.png");
	public static final ResourceLocation textureModelItem = new ResourceLocation("forestday",
			"textures/models/campfire_item.png");

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
			RenderUtils.bindTexture(textureModelWood);
			model.renderWood();
		}
		if (((TileCampfire) entity).getStackInSlot(4) != null) {
			RenderUtils.bindTexture(new ResourceLocation("forestday",
					"textures/models/campfire_" + "curbs_"
							+ ForestDayConfig.campfireCurbs[((TileCampfire) entity).getStackInSlot(4).getItemDamage()]
							+ ".png"));
			model.renderCubs();
		}
		if (((TileCampfire) entity).getStackInSlot(5) != null) {
			RenderUtils.bindTexture(new ResourceLocation("forestday", "textures/models/campfire_" + "pot_holders_"
					+ ForestDayConfig.campfirePotHolders[((TileCampfire) entity).getStackInSlot(5).getItemDamage()]
					+ ".png"));
			model.renderPotHolder();
		}
		if (((TileCampfire) entity).getStackInSlot(6) != null) {

			RenderUtils.bindTexture(new ResourceLocation("forestday",
					"textures/models/campfire_" + "pots_"
							+ ForestDayConfig.campfirePots[((TileCampfire) entity).getStackInSlot(6).getItemDamage()]
							+ ".png"));
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
			RenderUtils.bindTexture(new ResourceLocation("forestday",
					"textures/models/campfire_" + "curbs_" + ForestDayConfig.campfireCurbs[meta] + ".png"));
			model.renderCubs();
		} else if (texture == "pot_holder") {
			GL11.glTranslated(0, 0.2, 0);
			RenderUtils.bindTexture(new ResourceLocation("forestday",
					"textures/models/campfire_" + "pot_holders_" + ForestDayConfig.campfirePotHolders[meta] + ".png"));
			model.renderPotHolder();
		} else if (texture == "pot") {
			GL11.glTranslated(0, 0.7, 0);
			RenderUtils.bindTexture(new ResourceLocation("forestday",
					"textures/models/campfire_" + "pots_" + ForestDayConfig.campfirePots[meta] + ".png"));
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
