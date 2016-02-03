package de.nedelosk.forestmods.client.render.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCampfire extends ModelBase {

	ModelRenderer Stone0;
	ModelRenderer Stone1;
	ModelRenderer Stone2;
	ModelRenderer Stone3;
	ModelRenderer Stone4;
	ModelRenderer Stone5;
	ModelRenderer Stone6;
	ModelRenderer Stone7;
	ModelRenderer Stone8;
	ModelRenderer Stone9;
	ModelRenderer Stone10;
	ModelRenderer Stone11;
	ModelRenderer Wood0;
	ModelRenderer Wood1;
	ModelRenderer Wood2;
	ModelRenderer Wood3;
	ModelRenderer Iron0;
	ModelRenderer Iron1;
	ModelRenderer Iron2;
	ModelRenderer Iron3;
	ModelRenderer Pot0;
	ModelRenderer Pot1;
	ModelRenderer Pot2;
	ModelRenderer Pot3;
	ModelRenderer Pot4;

	public ModelCampfire() {
		textureWidth = 32;
		textureHeight = 32;
		textureWidth = 32;
		textureHeight = 32;
		Stone0 = new ModelRenderer(this, 0, 4);
		Stone0.addBox(0F, 0F, 0F, 6, 2, 2);
		Stone0.setRotationPoint(-3F, 22F, 6F);
		Stone0.setTextureSize(32, 32);
		Stone0.mirror = true;
		setRotation(Stone0, 0F, 0F, 0F);
		Stone1 = new ModelRenderer(this, 0, 4);
		Stone1.addBox(0F, 0F, 0F, 6, 2, 2);
		Stone1.setRotationPoint(-3F, 22F, -8F);
		Stone1.setTextureSize(32, 32);
		Stone1.mirror = true;
		setRotation(Stone1, 0F, 0F, 0F);
		Stone2 = new ModelRenderer(this, 0, 4);
		Stone2.addBox(0F, 0F, 0F, 6, 2, 2);
		Stone2.setRotationPoint(-8F, 22F, 3F);
		Stone2.setTextureSize(32, 32);
		Stone2.mirror = true;
		setRotation(Stone2, 0F, 1.570796F, 0F);
		Stone3 = new ModelRenderer(this, 0, 4);
		Stone3.addBox(0F, 0F, 0F, 6, 2, 2);
		Stone3.setRotationPoint(6F, 22F, 3F);
		Stone3.setTextureSize(32, 32);
		Stone3.mirror = true;
		setRotation(Stone3, 0F, 1.570796F, 0F);
		Stone4 = new ModelRenderer(this, 0, 0);
		Stone4.addBox(0F, 0F, 0F, 2, 2, 2);
		Stone4.setRotationPoint(3F, 22F, 5F);
		Stone4.setTextureSize(32, 32);
		Stone4.mirror = true;
		setRotation(Stone4, 0F, 0F, 0F);
		Stone5 = new ModelRenderer(this, 0, 0);
		Stone5.addBox(0F, 0F, 0F, 2, 2, 2);
		Stone5.setRotationPoint(5F, 22F, 3F);
		Stone5.setTextureSize(32, 32);
		Stone5.mirror = true;
		setRotation(Stone5, 0F, 0F, 0F);
		Stone6 = new ModelRenderer(this, 0, 0);
		Stone6.addBox(0F, 0F, 0F, 2, 2, 2);
		Stone6.setRotationPoint(-5F, 22F, 5F);
		Stone6.setTextureSize(32, 32);
		Stone6.mirror = true;
		setRotation(Stone6, 0F, 0F, 0F);
		Stone7 = new ModelRenderer(this, 0, 0);
		Stone7.addBox(0F, 0F, 0F, 2, 2, 2);
		Stone7.setRotationPoint(-7F, 22F, 3F);
		Stone7.setTextureSize(32, 32);
		Stone7.mirror = true;
		setRotation(Stone7, 0F, 0F, 0F);
		Stone8 = new ModelRenderer(this, 0, 0);
		Stone8.addBox(0F, 0F, 0F, 2, 2, 2);
		Stone8.setRotationPoint(5F, 22F, -5F);
		Stone8.setTextureSize(32, 32);
		Stone8.mirror = true;
		setRotation(Stone8, 0F, 0F, 0F);
		Stone9 = new ModelRenderer(this, 0, 0);
		Stone9.addBox(0F, 0F, 0F, 2, 2, 2);
		Stone9.setRotationPoint(3F, 22F, -7F);
		Stone9.setTextureSize(32, 32);
		Stone9.mirror = true;
		setRotation(Stone9, 0F, 0F, 0F);
		Stone10 = new ModelRenderer(this, 0, 0);
		Stone10.addBox(0F, 0F, 0F, 2, 2, 2);
		Stone10.setRotationPoint(-7F, 22F, -5F);
		Stone10.setTextureSize(32, 32);
		Stone10.mirror = true;
		setRotation(Stone10, 0F, 0F, 0F);
		Stone11 = new ModelRenderer(this, 0, 0);
		Stone11.addBox(0F, 0F, 0F, 2, 2, 2);
		Stone11.setRotationPoint(-5F, 22F, -7F);
		Stone11.setTextureSize(32, 32);
		Stone11.mirror = true;
		setRotation(Stone11, 0F, 0F, 0F);
		Wood0 = new ModelRenderer(this, 0, 8);
		Wood0.addBox(0F, 0F, 0F, 2, 7, 2);
		Wood0.setRotationPoint(-1F, 18F, -1F);
		Wood0.setTextureSize(32, 32);
		Wood0.mirror = true;
		setRotation(Wood0, 0.4542062F, -0.6434587F, 0F);
		Wood1 = new ModelRenderer(this, 0, 8);
		Wood1.addBox(0F, 0F, 0F, 2, 7, 2);
		Wood1.setRotationPoint(0F, 18F, 1F);
		Wood1.setTextureSize(32, 32);
		Wood1.mirror = true;
		setRotation(Wood1, 0.4542062F, 2.498134F, 0F);
		Wood2 = new ModelRenderer(this, 0, 8);
		Wood2.addBox(0F, 0F, 0F, 2, 7, 2);
		Wood2.setRotationPoint(-1F, 18F, 2F);
		Wood2.setTextureSize(32, 32);
		Wood2.mirror = true;
		setRotation(Wood2, 0.4542062F, 1.249067F, 0F);
		Wood3 = new ModelRenderer(this, 0, 8);
		Wood3.addBox(0F, 0F, 0F, 2, 7, 2);
		Wood3.setRotationPoint(0F, 18F, -1F);
		Wood3.setTextureSize(32, 32);
		Wood3.mirror = true;
		setRotation(Wood3, 0.4542062F, -2.119629F, 0F);
		Iron0 = new ModelRenderer(this, 16, 0);
		Iron0.addBox(0F, 0F, 0F, 2, 12, 2);
		Iron0.setRotationPoint(-1F, 10F, -8F);
		Iron0.setTextureSize(32, 32);
		Iron0.mirror = true;
		setRotation(Iron0, 0F, 0F, 0F);
		Iron1 = new ModelRenderer(this, 16, 0);
		Iron1.addBox(0F, 0F, 0F, 2, 12, 2);
		Iron1.setRotationPoint(-1F, 10F, 6F);
		Iron1.setTextureSize(32, 32);
		Iron1.mirror = true;
		setRotation(Iron1, 0F, 0F, 0F);
		Iron2 = new ModelRenderer(this, 0, 17);
		Iron2.addBox(0F, 0F, 0F, 2, 2, 3);
		Iron2.setRotationPoint(-1F, 10F, 3F);
		Iron2.setTextureSize(32, 32);
		Iron2.mirror = true;
		setRotation(Iron2, 0F, 0F, 0F);
		Iron3 = new ModelRenderer(this, 0, 17);
		Iron3.addBox(0F, 0F, 0F, 2, 2, 3);
		Iron3.setRotationPoint(-1F, 10F, -6F);
		Iron3.setTextureSize(32, 32);
		Iron3.mirror = true;
		setRotation(Iron3, 0F, 0F, 0F);
		Pot0 = new ModelRenderer(this, 0, 22);
		Pot0.addBox(0F, 0F, 0F, 6, 5, 1);
		Pot0.setRotationPoint(-3F, 10F, -3F);
		Pot0.setTextureSize(32, 32);
		Pot0.mirror = true;
		setRotation(Pot0, 0F, 0F, 0F);
		Pot1 = new ModelRenderer(this, 0, 22);
		Pot1.addBox(0F, 0F, 0F, 6, 5, 1);
		Pot1.setRotationPoint(-3F, 10F, 2F);
		Pot1.setTextureSize(32, 32);
		Pot1.mirror = true;
		setRotation(Pot1, 0F, 0F, 0F);
		Pot2 = new ModelRenderer(this, 14, 21);
		Pot2.addBox(0F, 0F, 0F, 4, 5, 1);
		Pot2.setRotationPoint(-3F, 10F, 2F);
		Pot2.setTextureSize(32, 32);
		Pot2.mirror = true;
		setRotation(Pot2, 0F, 1.570796F, 0F);
		Pot3 = new ModelRenderer(this, 14, 21);
		Pot3.addBox(0F, 0F, 0F, 4, 5, 1);
		Pot3.setRotationPoint(2F, 10F, 2F);
		Pot3.setTextureSize(32, 32);
		Pot3.mirror = true;
		setRotation(Pot3, 0F, 1.570796F, 0F);
		Pot4 = new ModelRenderer(this, 10, 16);
		Pot4.addBox(0F, 0F, 0F, 4, 1, 4);
		Pot4.setRotationPoint(-2F, 14F, -2F);
		Pot4.setTextureSize(32, 32);
		Pot4.mirror = true;
		setRotation(Pot4, 0F, 0F, 0F);
	}

	public void renderWood() {
		float pixel = 0.0625F;
		Wood0.render(pixel);
		Wood1.render(pixel);
		Wood2.render(pixel);
		Wood3.render(pixel);
	}

	public void renderItem() {
		renderWood();
		renderCubs();
	}

	public void renderCubs() {
		float pixel = 0.0625F;
		Stone0.render(pixel);
		Stone1.render(pixel);
		Stone2.render(pixel);
		Stone3.render(pixel);
		Stone4.render(pixel);
		Stone5.render(pixel);
		Stone6.render(pixel);
		Stone7.render(pixel);
		Stone8.render(pixel);
		Stone9.render(pixel);
		Stone10.render(pixel);
		Stone11.render(pixel);
	}

	public void renderPot() {
		float pixel = 0.0625F;
		Pot0.render(pixel);
		Pot1.render(pixel);
		Pot2.render(pixel);
		Pot3.render(pixel);
		Pot4.render(pixel);
	}

	public void renderPotHolder() {
		float pixel = 0.0625F;
		Iron0.render(pixel);
		Iron1.render(pixel);
		Iron2.render(pixel);
		Iron3.render(pixel);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
