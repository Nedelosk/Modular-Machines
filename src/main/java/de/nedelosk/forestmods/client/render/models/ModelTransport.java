package de.nedelosk.forestmods.client.render.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTransport extends ModelBase {

	public ModelRenderer base;
	public ModelRenderer stand_1;
	public ModelRenderer stand_base;
	public ModelRenderer stand_0;
	public ModelRenderer side_base_left;
	public ModelRenderer side_base_down;
	public ModelRenderer side_base_top;
	public ModelRenderer side_base;
	public ModelRenderer side_base_right;
	public ModelRenderer base_right_top;
	public ModelRenderer base_right_front;
	public ModelRenderer base_right_back;
	public ModelRenderer base_right_down;
	public ModelRenderer base_left_down;
	public ModelRenderer base_left_top;
	public ModelRenderer base_left_front;
	public ModelRenderer base_left_back;
	public ModelRenderer base_front_down;
	public ModelRenderer base_front_top;
	public ModelRenderer base_back_top;
	public ModelRenderer base_back_back;
	public ModelRenderer base_down;
	public ModelRenderer top_base;
	public ModelRenderer top_base_left;
	public ModelRenderer top_base_right;
	public ModelRenderer top_base_top;
	public ModelRenderer top_base_down;

	public ModelTransport() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.base_back_top = new ModelRenderer(this, 22, 17);
		this.base_back_top.setRotationPoint(-3.0F, 11.0F, 3.0F);
		this.base_back_top.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.side_base_right = new ModelRenderer(this, 42, 19);
		this.side_base_right.setRotationPoint(1.0F, 14.0F, -6.0F);
		this.side_base_right.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.stand_0 = new ModelRenderer(this, 52, 14);
		this.stand_0.setRotationPoint(-1.5F, 20.0F, -1.5F);
		this.stand_0.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
		this.base_down = new ModelRenderer(this, 0, 0);
		this.base_down.setRotationPoint(-3.0F, 18.0F, -3.0F);
		this.base_down.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6, 0.0F);
		this.side_base_down = new ModelRenderer(this, 41, 12);
		this.side_base_down.setRotationPoint(-2.0F, 16.0F, -6.0F);
		this.side_base_down.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.side_base_top = new ModelRenderer(this, 41, 14);
		this.side_base_top.setRotationPoint(-2.0F, 13.0F, -6.0F);
		this.side_base_top.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.base_front_down = new ModelRenderer(this, 25, 0);
		this.base_front_down.setRotationPoint(-3.0F, 18.0F, -4.0F);
		this.base_front_down.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.side_base = new ModelRenderer(this, 28, 25);
		this.side_base.setRotationPoint(-3.0F, 12.0F, -5.0F);
		this.side_base.addBox(0.0F, 0.0F, 0.0F, 6, 6, 1, 0.0F);
		this.base_left_front = new ModelRenderer(this, 0, 8);
		this.base_left_front.setRotationPoint(-4.0F, 12.0F, -4.0F);
		this.base_left_front.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
		this.base_back_back = new ModelRenderer(this, 22, 19);
		this.base_back_back.setRotationPoint(-3.0F, 18.0F, 3.0F);
		this.base_back_back.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.side_base_left = new ModelRenderer(this, 42, 22);
		this.side_base_left.setRotationPoint(-2.0F, 14.0F, -6.0F);
		this.side_base_left.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.base_left_top = new ModelRenderer(this, 11, 17);
		this.base_left_top.setRotationPoint(-4.0F, 11.0F, -4.0F);
		this.base_left_top.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
		this.base_right_top = new ModelRenderer(this, 0, 23);
		this.base_right_top.setRotationPoint(3.0F, 11.0F, -4.0F);
		this.base_right_top.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
		this.base_front_top = new ModelRenderer(this, 25, 2);
		this.base_front_top.setRotationPoint(-3.0F, 11.0F, -4.0F);
		this.base_front_top.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.stand_base = new ModelRenderer(this, 44, 21);
		this.stand_base.setRotationPoint(-2.5F, 19.0F, -2.5F);
		this.stand_base.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5, 0.0F);
		this.base_right_down = new ModelRenderer(this, 0, 8);
		this.base_right_down.setRotationPoint(3.0F, 18.0F, -4.0F);
		this.base_right_down.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
		this.base_left_down = new ModelRenderer(this, 18, 8);
		this.base_left_down.setRotationPoint(-4.0F, 18.0F, -4.0F);
		this.base_left_down.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
		this.base_right_front = new ModelRenderer(this, 0, 24);
		this.base_right_front.setRotationPoint(3.0F, 12.0F, -4.0F);
		this.base_right_front.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
		this.base = new ModelRenderer(this, 40, 0);
		this.base.setRotationPoint(-3.0F, 12.0F, -3.0F);
		this.base.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
		this.base_left_back = new ModelRenderer(this, 12, 8);
		this.base_left_back.setRotationPoint(-4.0F, 12.0F, 3.0F);
		this.base_left_back.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
		this.stand_1 = new ModelRenderer(this, 48, 27);
		this.stand_1.setRotationPoint(-2.0F, 23.0F, -2.0F);
		this.stand_1.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, 0.0F);
		this.base_right_back = new ModelRenderer(this, 0, 17);
		this.base_right_back.setRotationPoint(3.0F, 12.0F, 3.0F);
		this.base_right_back.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
		this.top_base_left = new ModelRenderer(this, 0, 3);
		this.top_base_left.setRotationPoint(-2.0F, 9.0F, -1.0F);
		this.top_base_left.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
		this.top_base_down = new ModelRenderer(this, 16, 13);
		this.top_base_down.setRotationPoint(-2.0F, 9.0F, -2.0F);
		this.top_base_down.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.top_base = new ModelRenderer(this, 0, 0);
		this.top_base.setRotationPoint(-3.0F, 10.0F, -3.0F);
		this.top_base.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6, 0.0F);
		this.top_base_top = new ModelRenderer(this, 16, 9);
		this.top_base_top.setRotationPoint(-2.0F, 9.0F, 1.0F);
		this.top_base_top.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.top_base_right = new ModelRenderer(this, 0, 0);
		this.top_base_right.setRotationPoint(1.0F, 9.0F, -1.0F);
		this.top_base_right.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
	}

	public void render() {
		float pixel = 0.0625F;
		this.base_back_top.render(pixel);
		this.base_front_down.render(pixel);
		this.base_left_front.render(pixel);
		this.base_back_back.render(pixel);
		this.base_left_top.render(pixel);
		this.base_right_top.render(pixel);
		this.base_front_top.render(pixel);
		this.base_right_down.render(pixel);
		this.base_left_down.render(pixel);
		this.base_right_front.render(pixel);
		this.base.render(pixel);
		this.base_left_back.render(pixel);
		this.base_right_back.render(pixel);
	}

	public void renderStand() {
		float pixel = 0.0625F;
		this.base_down.render(pixel);
		this.stand_0.render(pixel);
		this.stand_base.render(pixel);
		this.stand_1.render(pixel);
	}

	public void renderLaser() {
		float pixel = 0.0625F;
		this.side_base_right.render(pixel);
		this.side_base_down.render(pixel);
		this.side_base_top.render(pixel);
		this.side_base.render(pixel);
		this.side_base_left.render(pixel);
	}

	public void renderLaserTop() {
		float pixel = 0.0625F;
		this.top_base.render(pixel);
		this.top_base_down.render(pixel);
		this.top_base_top.render(pixel);
		this.top_base_left.render(pixel);
		this.top_base_right.render(pixel);
	}
}
