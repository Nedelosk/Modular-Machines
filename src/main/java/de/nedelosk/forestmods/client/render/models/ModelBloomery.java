package de.nedelosk.forestmods.client.render.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBloomery extends ModelBase {
	public ModelRenderer base;
	public ModelRenderer side_0;
	public ModelRenderer side_1;
	public ModelRenderer side_2;
	public ModelRenderer tor_0;
	public ModelRenderer tor_1;
	public ModelRenderer tor_2;
	public ModelRenderer tor_3;
	public ModelRenderer top_0;
	public ModelRenderer top_1;
	public ModelRenderer top_2;
	public ModelRenderer top_3;
	public ModelRenderer top_4;
	public ModelRenderer top_5;
	public ModelRenderer top_6;
	public ModelRenderer top_7;

	public ModelBloomery() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.top_4 = new ModelRenderer(this, 92, 0);
		this.top_4.setRotationPoint(-3.0F, 10.0F, 0.0F);
		this.top_4.addBox(0.0F, 0.0F, 0.0F, 1, 7, 4, 0.0F);
		this.tor_3 = new ModelRenderer(this, 12, 38);
		this.tor_3.setRotationPoint(-2.0F, 17.0F, 4.0F);
		this.tor_3.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.tor_2 = new ModelRenderer(this, 12, 34);
		this.tor_2.setRotationPoint(-2.0F, 22.0F, 4.0F);
		this.tor_2.addBox(0.0F, 0.0F, 0.0F, 4, 2, 1, 0.0F);
		this.tor_0 = new ModelRenderer(this, 0, 32);
		this.tor_0.setRotationPoint(2.0F, 17.0F, 4.0F);
		this.tor_0.addBox(0.0F, 0.0F, 0.0F, 2, 7, 1, 0.0F);
		this.top_2 = new ModelRenderer(this, 65, 0);
		this.top_2.setRotationPoint(-3.0F, 10.0F, -1.0F);
		this.top_2.addBox(0.0F, 0.0F, 0.0F, 6, 7, 1, 0.0F);
		this.side_1 = new ModelRenderer(this, 22, 16);
		this.side_1.setRotationPoint(4.0F, 17.0F, -5.0F);
		this.side_1.addBox(0.0F, 0.0F, 0.0F, 2, 7, 9, 0.0F);
		this.top_0 = new ModelRenderer(this, 25, 0);
		this.top_0.setRotationPoint(-5.0F, 16.0F, -5.0F);
		this.top_0.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
		this.side_0 = new ModelRenderer(this, 0, 16);
		this.side_0.setRotationPoint(-6.0F, 17.0F, -5.0F);
		this.side_0.addBox(0.0F, 0.0F, 0.0F, 2, 7, 9, 0.0F);
		this.tor_1 = new ModelRenderer(this, 6, 32);
		this.tor_1.setRotationPoint(-4.0F, 17.0F, 4.0F);
		this.tor_1.addBox(0.0F, 0.0F, 0.0F, 2, 7, 1, 0.0F);
		this.side_2 = new ModelRenderer(this, 44, 23);
		this.side_2.setRotationPoint(-4.0F, 17.0F, -6.0F);
		this.side_2.addBox(0.0F, 0.0F, 0.0F, 8, 7, 2, 0.0F);
		this.top_3 = new ModelRenderer(this, 80, 0);
		this.top_3.setRotationPoint(2.0F, 10.0F, 0.0F);
		this.top_3.addBox(0.0F, 0.0F, 0.0F, 1, 7, 4, 0.0F);
		this.base = new ModelRenderer(this, 0, 55);
		this.base.setRotationPoint(-4.0F, 23.0F, -4.0F);
		this.base.addBox(0.0F, 0.0F, 0.0F, 8, 1, 8, 0.0F);
		this.top_6 = new ModelRenderer(this, 70, 36);
		this.top_6.setRotationPoint(-5.0F, 16.0F, -4.0F);
		this.top_6.addBox(0.0F, 0.0F, 0.0F, 2, 1, 8, 0.0F);
		this.top_5 = new ModelRenderer(this, 70, 25);
		this.top_5.setRotationPoint(3.0F, 16.0F, -4.0F);
		this.top_5.addBox(0.0F, 0.0F, 0.0F, 2, 1, 8, 0.0F);
		this.top_1 = new ModelRenderer(this, 50, 0);
		this.top_1.setRotationPoint(-3.0F, 10.0F, 4.0F);
		this.top_1.addBox(0.0F, 0.0F, 0.0F, 6, 7, 1, 0.0F);
		this.top_7 = new ModelRenderer(this, 0, 4);
		this.top_7.setRotationPoint(-4.0F, 15.0F, -4.0F);
		this.top_7.addBox(0.0F, 0.0F, 0.0F, 8, 1, 3, 0.0F);
	}

	public void render() { 
		float pickel = 0.0625F;
		this.top_4.render(pickel);
		this.tor_3.render(pickel);
		this.tor_2.render(pickel);
		this.tor_0.render(pickel);
		this.top_2.render(pickel);
		this.side_1.render(pickel);
		this.top_0.render(pickel);
		this.side_0.render(pickel);
		this.tor_1.render(pickel);
		this.side_2.render(pickel);
		this.top_3.render(pickel);
		this.base.render(pickel);
		this.top_6.render(pickel);
		this.top_5.render(pickel);
		this.top_1.render(pickel);
		this.top_7.render(pickel);
	}
}
