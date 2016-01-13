package nedelosk.forestday.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelWorkbench extends ModelBase {

	ModelRenderer Leg1;
	ModelRenderer Leg2;
	ModelRenderer Leg3;
	ModelRenderer Leg4;
	ModelRenderer Leg5;
	ModelRenderer Leg6;
	ModelRenderer Leg7;
	ModelRenderer Top;
	ModelRenderer chest;

	public ModelWorkbench() {
		textureWidth = 64;
		textureHeight = 64;
		Leg1 = new ModelRenderer(this, 0, 0);
		Leg1.addBox(0F, 0F, 0F, 2, 8, 2);
		Leg1.setRotationPoint(-8F, 16F, 6F);
		Leg1.setTextureSize(64, 32);
		Leg1.mirror = true;
		setRotation(Leg1, 0F, 0F, 0F);
		Leg2 = new ModelRenderer(this, 0, 0);
		Leg2.addBox(0F, 0F, 0F, 2, 8, 2);
		Leg2.setRotationPoint(4F, 16F, 6F);
		Leg2.setTextureSize(64, 32);
		Leg2.mirror = true;
		setRotation(Leg2, 0F, 0F, 0F);
		Leg3 = new ModelRenderer(this, 0, 0);
		Leg3.addBox(0F, 0F, 0F, 2, 8, 2);
		Leg3.setRotationPoint(4F, 16F, -8F);
		Leg3.setTextureSize(64, 32);
		Leg3.mirror = true;
		setRotation(Leg3, 0F, 0F, 0F);
		Leg4 = new ModelRenderer(this, 0, 0);
		Leg4.addBox(0F, 0F, 0F, 2, 8, 2);
		Leg4.setRotationPoint(-8F, 16F, -8F);
		Leg4.setTextureSize(64, 32);
		Leg4.mirror = true;
		setRotation(Leg4, 0F, 0F, 0F);
		Leg5 = new ModelRenderer(this, 0, 0);
		Leg5.addBox(0F, 0F, 0F, 2, 2, 12);
		Leg5.setRotationPoint(-8F, 18F, -6F);
		Leg5.setTextureSize(64, 32);
		Leg5.mirror = true;
		setRotation(Leg5, 0F, 0F, 0F);
		Leg6 = new ModelRenderer(this, 28, 0);
		Leg6.addBox(0F, 0F, 0F, 10, 2, 2);
		Leg6.setRotationPoint(-6F, 18F, -8F);
		Leg6.setTextureSize(64, 32);
		Leg6.mirror = true;
		setRotation(Leg6, 0F, 0F, 0F);
		Leg7 = new ModelRenderer(this, 28, 0);
		Leg7.addBox(0F, 0F, 0F, 10, 2, 2);
		Leg7.setRotationPoint(-6F, 18F, 6F);
		Leg7.setTextureSize(64, 32);
		Leg7.mirror = true;
		setRotation(Leg7, 0F, 0F, 0F);
		Top = new ModelRenderer(this, 0, 46);
		Top.addBox(0F, 0F, 0F, 16, 2, 16);
		Top.setRotationPoint(-8F, 14F, -8F);
		Top.setTextureSize(64, 32);
		Top.mirror = true;
		setRotation(Top, 0F, 0F, 0F);
		chest = new ModelRenderer(this, 0, 28);
		chest.addBox(0F, 0F, 0F, 12, 6, 12);
		chest.setRotationPoint(-6F, 16F, -6F);
		chest.setTextureSize(64, 64);
		chest.mirror = true;
		setRotation(chest, 0F, 0F, 0F);
	}

	public void renderTable() {
		Leg1.render(0.0625F);
		Leg2.render(0.0625F);
		Leg3.render(0.0625F);
		Leg4.render(0.0625F);
		Leg5.render(0.0625F);
		Leg6.render(0.0625F);
		Leg7.render(0.0625F);
		Top.render(0.0625F);
	}

	public void renderChest() {
		chest.render(0.0625F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}