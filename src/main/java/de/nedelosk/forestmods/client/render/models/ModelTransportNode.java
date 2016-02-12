package de.nedelosk.forestmods.client.render.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTransportNode extends ModelBase {

	public ModelRenderer base;
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
	public ModelRenderer pipe_top_base_2;
	public ModelRenderer pipe_top_base;
	public ModelRenderer pipe_top_pipe;
	public ModelRenderer top_base_down;
	public ModelRenderer top_base_left;
	public ModelRenderer top_base;
	public ModelRenderer top_base_top;
	public ModelRenderer top_base_right;
	public ModelRenderer side_base_left;
	public ModelRenderer side_base_top;
	public ModelRenderer side_base_down;
	public ModelRenderer side_base;
	public ModelRenderer side_base_right;
	public ModelRenderer pipe_side_base;
	public ModelRenderer pipe_side_pipe;
	public ModelRenderer pipe_side_base_2;

	public ModelTransportNode() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.pipe_side_base_2 = new ModelRenderer(this, 0, 0);
		this.pipe_side_base_2.setRotationPoint(-2.5F, 13.5F, -8.0F);
		this.pipe_side_base_2.addBox(0.0F, 0.0F, 0.0F, 5, 5, 1, 0.0F);
		this.pipe_top_pipe = new ModelRenderer(this, 52, 18);
		this.pipe_top_pipe.setRotationPoint(-1.5F, 9.0F, -1.5F);
		this.pipe_top_pipe.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
		this.base = new ModelRenderer(this, 48, 0);
		this.base.setRotationPoint(-2.0F, 14.0F, -2.0F);
		this.base.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
		this.pipe_top_base_2 = new ModelRenderer(this, 44, 9);
		this.pipe_top_base_2.setRotationPoint(-2.5F, 8.0F, -2.5F);
		this.pipe_top_base_2.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5, 0.0F);
		this.base_right_back = new ModelRenderer(this, 0, 21);
		this.base_right_back.setRotationPoint(2.0F, 14.0F, 2.0F);
		this.base_right_back.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.base_back_top = new ModelRenderer(this, 15, 17);
		this.base_back_top.setRotationPoint(-2.0F, 13.0F, 2.0F);
		this.base_back_top.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.side_base_left = new ModelRenderer(this, 35, 15);
		this.side_base_left.setRotationPoint(-2.0F, 15.0F, -5.0F);
		this.side_base_left.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.base_right_front = new ModelRenderer(this, 0, 26);
		this.base_right_front.setRotationPoint(2.0F, 14.0F, -3.0F);
		this.base_right_front.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.top_base_top = new ModelRenderer(this, 25, 15);
		this.top_base_top.setRotationPoint(-2.0F, 11.0F, 1.0F);
		this.top_base_top.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.base_front_down = new ModelRenderer(this, 25, 17);
		this.base_front_down.setRotationPoint(-2.0F, 18.0F, -3.0F);
		this.base_front_down.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.base_front_top = new ModelRenderer(this, 25, 19);
		this.base_front_top.setRotationPoint(-2.0F, 13.0F, -3.0F);
		this.base_front_top.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.top_base_right = new ModelRenderer(this, 15, 10);
		this.top_base_right.setRotationPoint(1.0F, 11.0F, -1.0F);
		this.top_base_right.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
		this.base_left_back = new ModelRenderer(this, 10, 15);
		this.base_left_back.setRotationPoint(-3.0F, 14.0F, 2.0F);
		this.base_left_back.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.base_left_top = new ModelRenderer(this, 11, 24);
		this.base_left_top.setRotationPoint(-3.0F, 13.0F, -3.0F);
		this.base_left_top.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
		this.top_base = new ModelRenderer(this, 20, 0);
		this.top_base.setRotationPoint(-2.5F, 12.0F, -2.5F);
		this.top_base.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5, 0.0F);
		this.base_back_back = new ModelRenderer(this, 15, 19);
		this.base_back_back.setRotationPoint(-2.0F, 18.0F, 2.0F);
		this.base_back_back.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.side_base_right = new ModelRenderer(this, 35, 18);
		this.side_base_right.setRotationPoint(1.0F, 15.0F, -5.0F);
		this.side_base_right.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.base_left_front = new ModelRenderer(this, 0, 15);
		this.base_left_front.setRotationPoint(-3.0F, 14.0F, -3.0F);
		this.base_left_front.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.base_left_down = new ModelRenderer(this, 34, 0);
		this.base_left_down.setRotationPoint(-3.0F, 18.0F, -3.0F);
		this.base_left_down.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
		this.side_base_top = new ModelRenderer(this, 15, 13);
		this.side_base_top.setRotationPoint(-2.0F, 14.0F, -5.0F);
		this.side_base_top.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.side_base = new ModelRenderer(this, 28, 25);
		this.side_base.setRotationPoint(-2.5F, 13.5F, -4.0F);
		this.side_base.addBox(0.0F, 0.0F, 0.0F, 5, 5, 1, 0.0F);
		this.base_right_top = new ModelRenderer(this, 0, 25);
		this.base_right_top.setRotationPoint(2.0F, 13.0F, -3.0F);
		this.base_right_top.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
		this.pipe_side_base = new ModelRenderer(this, 0, 7);
		this.pipe_side_base.setRotationPoint(-2.5F, 13.5F, -4.0F);
		this.pipe_side_base.addBox(0.0F, 0.0F, 0.0F, 5, 5, 1, 0.0F);
		this.pipe_side_pipe = new ModelRenderer(this, 37, 8);
		this.pipe_side_pipe.setRotationPoint(-1.5F, 14.5F, -7.0F);
		this.pipe_side_pipe.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
		this.pipe_top_base = new ModelRenderer(this, 44, 26);
		this.pipe_top_base.setRotationPoint(-2.5F, 12.0F, -2.5F);
		this.pipe_top_base.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5, 0.0F);
		this.side_base_down = new ModelRenderer(this, 25, 13);
		this.side_base_down.setRotationPoint(-2.0F, 17.0F, -5.0F);
		this.side_base_down.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.top_base_down = new ModelRenderer(this, 15, 15);
		this.top_base_down.setRotationPoint(-2.0F, 11.0F, -2.0F);
		this.top_base_down.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.base_right_down = new ModelRenderer(this, 0, 14);
		this.base_right_down.setRotationPoint(2.0F, 18.0F, -3.0F);
		this.base_right_down.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
		this.top_base_left = new ModelRenderer(this, 21, 10);
		this.top_base_left.setRotationPoint(-2.0F, 11.0F, -1.0F);
		this.top_base_left.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
	}

	public void render() {
		float pixel = 0.0625F;
		this.base_right_down.render(pixel);
		this.base_right_top.render(pixel);
		this.base_left_front.render(pixel);
		this.base_left_down.render(pixel);
		this.base_back_back.render(pixel);
		this.base_front_down.render(pixel);
		this.base_front_top.render(pixel);
		this.base_left_back.render(pixel);
		this.base_left_top.render(pixel);
		this.base_right_front.render(pixel);
		this.base.render(pixel);
		this.base_right_back.render(pixel);
		this.base_back_top.render(pixel);
	}

	public void renderLaser() {
		float pixel = 0.0625F;
		this.side_base_down.render(pixel);
		this.side_base_top.render(pixel);
		this.side_base.render(pixel);
		this.side_base_right.render(pixel);
		this.side_base_left.render(pixel);
	}

	public void renderLaserTop() {
		float pixel = 0.0625F;
		this.top_base_down.render(pixel);
		this.top_base_left.render(pixel);
		this.top_base_top.render(pixel);
		this.top_base_right.render(pixel);
		this.top_base.render(pixel);
	}

	public void renderPipe() {
		float pixel = 0.0625F;
		this.pipe_side_base.render(pixel);
		this.pipe_side_pipe.render(pixel);
		this.pipe_side_base_2.render(pixel);
	}

	public void renderPipeTop() {
		float pixel = 0.0625F;
		this.pipe_top_base.render(pixel);
		this.pipe_top_pipe.render(pixel);
		this.pipe_top_base_2.render(pixel);
	}
}
