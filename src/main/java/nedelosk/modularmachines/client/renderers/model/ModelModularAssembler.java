package nedelosk.modularmachines.client.renderers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Modular_Assembler_Wood - Nedelosk Created using Tabula 5.1.0
 */
public class ModelModularAssembler extends ModelBase {

	public ModelRenderer log_leg_0;
	public ModelRenderer log_leg_1;
	public ModelRenderer log_leg_2;
	public ModelRenderer log_leg_3;
	public ModelRenderer log_leg_4;
	public ModelRenderer log_leg_5;
	public ModelRenderer log_leg_6;
	public ModelRenderer top;
	public ModelRenderer stone_leg_1;
	public ModelRenderer stone_leg_2;
	public ModelRenderer stone_leg_3;
	public ModelRenderer stone_leg_0;

	public ModelModularAssembler() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.stone_leg_0 = new ModelRenderer(this, 0, 0);
		this.stone_leg_0.setRotationPoint(2.0F, 15.0F, 2.0F);
		this.stone_leg_0.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
		this.stone_leg_3 = new ModelRenderer(this, 8, 9);
		this.stone_leg_3.setRotationPoint(-3.0F, 15.0F, -3.0F);
		this.stone_leg_3.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
		this.log_leg_6 = new ModelRenderer(this, 55, 5);
		this.log_leg_6.setRotationPoint(-7.0F, 11.0F, -7.0F);
		this.log_leg_6.addBox(0.0F, 0.0F, 0.0F, 14, 1, 14, 0.0F);
		this.log_leg_3 = new ModelRenderer(this, 68, 44);
		this.log_leg_3.setRotationPoint(-4.0F, 14.0F, -4.0F);
		this.log_leg_3.addBox(0.0F, 0.0F, 0.0F, 8, 1, 8, 0.0F);
		this.stone_leg_2 = new ModelRenderer(this, 8, 0);
		this.stone_leg_2.setRotationPoint(-3.0F, 15.0F, 2.0F);
		this.stone_leg_2.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
		this.log_leg_0 = new ModelRenderer(this, 0, 50);
		this.log_leg_0.setRotationPoint(-2.0F, 15.0F, -2.0F);
		this.log_leg_0.addBox(0.0F, 0.0F, 0.0F, 4, 7, 4, 0.0F);
		this.log_leg_4 = new ModelRenderer(this, 64, 33);
		this.log_leg_4.setRotationPoint(-5.0F, 13.0F, -5.0F);
		this.log_leg_4.addBox(0.0F, 0.0F, 0.0F, 10, 1, 10, 0.0F);
		this.stone_leg_1 = new ModelRenderer(this, 0, 9);
		this.stone_leg_1.setRotationPoint(2.0F, 15.0F, -3.0F);
		this.stone_leg_1.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
		this.top = new ModelRenderer(this, 0, 45);
		this.top.setRotationPoint(-8.0F, 8.0F, -8.0F);
		this.top.addBox(0.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
		this.log_leg_2 = new ModelRenderer(this, 68, 44);
		this.log_leg_2.setRotationPoint(-4.0F, 22.0F, -4.0F);
		this.log_leg_2.addBox(0.0F, 0.0F, 0.0F, 8, 1, 8, 0.0F);
		this.log_leg_5 = new ModelRenderer(this, 60, 20);
		this.log_leg_5.setRotationPoint(-6.0F, 12.0F, -6.0F);
		this.log_leg_5.addBox(0.0F, 0.0F, 0.0F, 12, 1, 12, 0.0F);
		this.log_leg_1 = new ModelRenderer(this, 64, 33);
		this.log_leg_1.setRotationPoint(-5.0F, 23.0F, -5.0F);
		this.log_leg_1.addBox(0.0F, 0.0F, 0.0F, 10, 1, 10, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.stone_leg_0.render(f5);
		this.stone_leg_3.render(f5);
		this.log_leg_6.render(f5);
		this.log_leg_3.render(f5);
		this.stone_leg_2.render(f5);
		this.log_leg_0.render(f5);
		this.log_leg_4.render(f5);
		this.stone_leg_1.render(f5);
		this.top.render(f5);
		this.log_leg_2.render(f5);
		this.log_leg_5.render(f5);
		this.log_leg_1.render(f5);
	}

	public void render() {
		float pixel = 0.0625F;
		this.stone_leg_0.render(pixel);
		this.stone_leg_3.render(pixel);
		this.log_leg_6.render(pixel);
		this.log_leg_3.render(pixel);
		this.stone_leg_2.render(pixel);
		this.log_leg_0.render(pixel);
		this.log_leg_4.render(pixel);
		this.stone_leg_1.render(pixel);
		this.top.render(pixel);
		this.log_leg_2.render(pixel);
		this.log_leg_5.render(pixel);
		this.log_leg_1.render(pixel);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
