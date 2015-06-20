package nedelosk.forestday.client.machines.base.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKiln extends ModelBase
{
  //fields
    ModelRenderer Dirt;
    ModelRenderer Output;
    ModelRenderer Output1;
    ModelRenderer Output2;
    ModelRenderer Output3;
    ModelRenderer Iron_Rod;
    ModelRenderer Iron_Rod1;
    ModelRenderer Iron_Rod2;
    ModelRenderer Iron_Rod3;
    ModelRenderer Iron_Rod4;
    ModelRenderer Iron_Rod5;
    ModelRenderer Iron_Rod6;
    ModelRenderer Iron_Rod7;
  
  public ModelKiln()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Dirt = new ModelRenderer(this, 0, 0);
      Dirt.addBox(0F, 0F, 0F, 12, 16, 12);
      Dirt.setRotationPoint(-6F, 8F, -6F);
      Dirt.setTextureSize(64, 64);
      Dirt.mirror = true;
      setRotation(Dirt, 0F, 0F, 0F);
      Output = new ModelRenderer(this, 0, 28);
      Output.addBox(0F, 0F, 0F, 8, 8, 2);
      Output.setRotationPoint(-4F, 12F, -8F);
      Output.setTextureSize(64, 64);
      Output.mirror = true;
      setRotation(Output, 0F, 0F, 0F);
      Output1 = new ModelRenderer(this, 0, 28);
      Output1.addBox(0F, 0F, 0F, 8, 8, 2);
      Output1.setRotationPoint(-6F, 12F, -4F);
      Output1.setTextureSize(64, 64);
      Output1.mirror = true;
      setRotation(Output1, 0F, -1.570796F, 0F);
      Output2 = new ModelRenderer(this, 0, 28);
      Output2.addBox(0F, 0F, 0F, 8, 8, 2);
      Output2.setRotationPoint(4F, 12F, 8F);
      Output2.setTextureSize(64, 64);
      Output2.mirror = true;
      setRotation(Output2, 0F, -3.141593F, 0F);
      Output3 = new ModelRenderer(this, 0, 28);
      Output3.addBox(0F, 0F, 0F, 8, 8, 2);
      Output3.setRotationPoint(6F, 12F, 4F);
      Output3.setTextureSize(64, 64);
      Output3.mirror = true;
      setRotation(Output3, 0F, 1.570796F, 0F);
      Iron_Rod = new ModelRenderer(this, 48, 0);
      Iron_Rod.addBox(0F, 0F, 0F, 2, 16, 2);
      Iron_Rod.setRotationPoint(4F, 8F, -8F);
      Iron_Rod.setTextureSize(64, 64);
      Iron_Rod.mirror = true;
      setRotation(Iron_Rod, 0F, 0F, 0F);
      Iron_Rod1 = new ModelRenderer(this, 48, 0);
      Iron_Rod1.addBox(0F, 0F, 0F, 2, 16, 2);
      Iron_Rod1.setRotationPoint(6F, 8F, -6F);
      Iron_Rod1.setTextureSize(64, 64);
      Iron_Rod1.mirror = true;
      setRotation(Iron_Rod1, 0F, 0F, 0F);
      Iron_Rod2 = new ModelRenderer(this, 48, 0);
      Iron_Rod2.addBox(0F, 0F, 0F, 2, 16, 2);
      Iron_Rod2.setRotationPoint(6F, 8F, 4F);
      Iron_Rod2.setTextureSize(64, 64);
      Iron_Rod2.mirror = true;
      setRotation(Iron_Rod2, 0F, 0F, 0F);
      Iron_Rod3 = new ModelRenderer(this, 48, 0);
      Iron_Rod3.addBox(0F, 0F, 0F, 2, 16, 2);
      Iron_Rod3.setRotationPoint(4F, 8F, 6F);
      Iron_Rod3.setTextureSize(64, 64);
      Iron_Rod3.mirror = true;
      setRotation(Iron_Rod3, 0F, 0F, 0F);
      Iron_Rod4 = new ModelRenderer(this, 48, 0);
      Iron_Rod4.addBox(0F, 0F, 0F, 2, 16, 2);
      Iron_Rod4.setRotationPoint(-6F, 8F, 6F);
      Iron_Rod4.setTextureSize(64, 64);
      Iron_Rod4.mirror = true;
      setRotation(Iron_Rod4, 0F, 0F, 0F);
      Iron_Rod5 = new ModelRenderer(this, 48, 0);
      Iron_Rod5.addBox(0F, 0F, 0F, 2, 16, 2);
      Iron_Rod5.setRotationPoint(-8F, 8F, 4F);
      Iron_Rod5.setTextureSize(64, 64);
      Iron_Rod5.mirror = true;
      setRotation(Iron_Rod5, 0F, 0F, 0F);
      Iron_Rod6 = new ModelRenderer(this, 48, 0);
      Iron_Rod6.addBox(0F, 0F, 0F, 2, 16, 2);
      Iron_Rod6.setRotationPoint(-8F, 8F, -6F);
      Iron_Rod6.setTextureSize(64, 64);
      Iron_Rod6.mirror = true;
      setRotation(Iron_Rod6, 0F, 0F, 0F);
      Iron_Rod7 = new ModelRenderer(this, 48, 0);
      Iron_Rod7.addBox(0F, 0F, 0F, 2, 16, 2);
      Iron_Rod7.setRotationPoint(-6F, 8F, -8F);
      Iron_Rod7.setTextureSize(64, 64);
      Iron_Rod7.mirror = true;
      setRotation(Iron_Rod7, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Dirt.render(f5);
    Output.render(f5);
    Output1.render(f5);
    Output2.render(f5);
    Output3.render(f5);
    Iron_Rod.render(f5);
    Iron_Rod1.render(f5);
    Iron_Rod2.render(f5);
    Iron_Rod3.render(f5);
    Iron_Rod4.render(f5);
    Iron_Rod5.render(f5);
    Iron_Rod6.render(f5);
    Iron_Rod7.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void renderModel(float f5)
  {
	    Dirt.render(f5);
	    Output.render(f5);
	    Output1.render(f5);
	    Output2.render(f5);
	    Output3.render(f5);
	    Iron_Rod.render(f5);
	    Iron_Rod1.render(f5);
	    Iron_Rod2.render(f5);
	    Iron_Rod3.render(f5);
	    Iron_Rod4.render(f5);
	    Iron_Rod5.render(f5);
	    Iron_Rod6.render(f5);
	    Iron_Rod7.render(f5);
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
