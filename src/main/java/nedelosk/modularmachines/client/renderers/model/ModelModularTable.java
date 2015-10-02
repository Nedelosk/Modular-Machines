package nedelosk.modularmachines.client.renderers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelModularTable extends ModelBase
{
	
    ModelRenderer Top;
    ModelRenderer Leg;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer post;
    ModelRenderer post1;
    ModelRenderer post2;
    ModelRenderer post3;
  
  public ModelModularTable()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Top = new ModelRenderer(this, 0, 0);
      Top.addBox(0F, 0F, 0F, 16, 3, 16);
      Top.setRotationPoint(-8F, 9F, -8F);
      Top.setTextureSize(64, 64);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Leg = new ModelRenderer(this, 40, 48);
      Leg.addBox(0F, 0F, 0F, 4, 12, 4);
      Leg.setRotationPoint(-2F, 12F, -2F);
      Leg.setTextureSize(64, 64);
      Leg.mirror = true;
      setRotation(Leg, 0F, 0F, 0F);
      Leg1 = new ModelRenderer(this, 0, 52);
      Leg1.addBox(0F, 0F, 0F, 10, 2, 10);
      Leg1.setRotationPoint(-5F, 22F, -5F);
      Leg1.setTextureSize(64, 64);
      Leg1.mirror = true;
      setRotation(Leg1, 0F, 0F, 0F);
      Leg2 = new ModelRenderer(this, 0, 52);
      Leg2.addBox(0F, 0F, 0F, 10, 2, 10);
      Leg2.setRotationPoint(-5F, 12F, -5F);
      Leg2.setTextureSize(64, 64);
      Leg2.mirror = true;
      setRotation(Leg2, 0F, 0F, 0F);
      Leg3 = new ModelRenderer(this, 0, 43);
      Leg3.addBox(0F, 0F, 0F, 8, 1, 8);
      Leg3.setRotationPoint(-4F, 21F, -4F);
      Leg3.setTextureSize(64, 64);
      Leg3.mirror = true;
      setRotation(Leg3, 0F, 0F, 0F);
      Leg4 = new ModelRenderer(this, 0, 43);
      Leg4.addBox(0F, 0F, 0F, 8, 1, 8);
      Leg4.setRotationPoint(-4F, 14F, -4F);
      Leg4.setTextureSize(64, 64);
      Leg4.mirror = true;
      setRotation(Leg4, 0F, 0F, 0F);
      post = new ModelRenderer(this, 32, 44);
      post.addBox(0F, 0F, 0F, 2, 6, 2);
      post.setRotationPoint(2F, 15F, 2F);
      post.setTextureSize(64, 64);
      post.mirror = true;
      setRotation(post, 0F, 0F, 0F);
      post1 = new ModelRenderer(this, 32, 44);
      post1.addBox(0F, 0F, 0F, 2, 6, 2);
      post1.setRotationPoint(2F, 15F, -4F);
      post1.setTextureSize(64, 64);
      post1.mirror = true;
      setRotation(post1, 0F, 0F, 0F);
      post2 = new ModelRenderer(this, 32, 44);
      post2.addBox(0F, 0F, 0F, 2, 6, 2);
      post2.setRotationPoint(-4F, 15F, 2F);
      post2.setTextureSize(64, 64);
      post2.mirror = true;
      setRotation(post2, 0F, 0F, 0F);
      post3 = new ModelRenderer(this, 32, 44);
      post3.addBox(0F, 0F, 0F, 2, 6, 2);
      post3.setRotationPoint(-4F, 15F, -4F);
      post3.setTextureSize(64, 64);
      post3.mirror = true;
      setRotation(post3, 0F, 0F, 0F);
  }
  
  public void render()
  {
	  float pixel = 0.0625F;
	  Top.render(pixel);
	  Leg.render(pixel);
	  Leg1.render(pixel);
	  Leg2.render(pixel);
	  Leg3.render(pixel);
	  Leg4.render(pixel);
	  post.render(pixel);
	  post1.render(pixel);
	  post2.render(pixel);
	  post3.render(pixel);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}
