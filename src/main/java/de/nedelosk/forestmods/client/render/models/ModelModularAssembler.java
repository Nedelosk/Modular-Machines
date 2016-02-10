package de.nedelosk.forestmods.client.render.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelModularAssembler extends ModelBase {

	// fields
	ModelRenderer base;
	ModelRenderer top;
	ModelRenderer center;
	ModelRenderer topplate;
	ModelRenderer topplate2;

	public ModelModularAssembler() {
		textureWidth = 128;
		textureHeight = 32;
		base = new ModelRenderer(this, 55, 0);
		base.addBox(0F, 1F, 0F, 14, 4, 14);
		base.setRotationPoint(-7F, 19F, -7F);
		base.setTextureSize(128, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		top = new ModelRenderer(this, 55, 0);
		top.addBox(0F, 0F, 0F, 14, 4, 14);
		top.setRotationPoint(-7F, 14F, -7F);
		top.setTextureSize(128, 32);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		center = new ModelRenderer(this, 0, 17);
		center.addBox(0F, 0F, 0F, 12, 3, 12);
		center.setRotationPoint(-6F, 18F, -6F);
		center.setTextureSize(128, 32);
		center.mirror = true;
		setRotation(center, 0F, 0F, 0F);
		topplate = new ModelRenderer(this, 67, 23);
		topplate.addBox(0F, 0F, 0F, 8, 1, 8);
		topplate.setRotationPoint(-4F, 11F, -4F);
		topplate.setTextureSize(128, 32);
		topplate.mirror = true;
		setRotation(topplate, 0F, 0F, 0F);
		topplate2 = new ModelRenderer(this, 0, 0);
		topplate2.addBox(0F, 0F, 0F, 10, 2, 10);
		topplate2.setRotationPoint(-5F, 12F, -5F);
		topplate2.setTextureSize(128, 32);
		topplate2.mirror = true;
		setRotation(topplate2, 0F, 0F, 0F);
	}

	public void render() {
		base.render(0.0625F);
		top.render(0.0625F);
		center.render(0.0625F);
		topplate.render(0.0625F);
		topplate2.render(0.0625F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
