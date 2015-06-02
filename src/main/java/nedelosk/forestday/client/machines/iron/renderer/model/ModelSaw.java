package nedelosk.forestday.client.machines.iron.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSaw extends ModelBase {

    ModelRenderer Table1;
    ModelRenderer Table2;
    ModelRenderer Table3;
    ModelRenderer Table4;
    ModelRenderer TableWood1;
    ModelRenderer TableWood2;
    ModelRenderer TableWood3;
    ModelRenderer Iron0;
    ModelRenderer Iron1;
    ModelRenderer File;
  
  public ModelSaw()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Table1 = new ModelRenderer(this, 0, 0);
      Table1.addBox(0F, 0F, 0F, 16, 8, 12);
      Table1.setRotationPoint(-8F, 16F, -6F);
      Table1.setTextureSize(64, 64);
      Table1.mirror = true;
      setRotation(Table1, 0F, 0F, 0F);
      Table2 = new ModelRenderer(this, 0, 20);
      Table2.addBox(0F, 0F, 0F, 16, 1, 1);
      Table2.setRotationPoint(-8F, 16F, -7F);
      Table2.setTextureSize(64, 64);
      Table2.mirror = true;
      setRotation(Table2, 0F, 0F, 0F);
      Table3 = new ModelRenderer(this, 0, 20);
      Table3.addBox(0F, 0F, 0F, 16, 1, 1);
      Table3.setRotationPoint(-8F, 16F, 6F);
      Table3.setTextureSize(64, 64);
      Table3.mirror = true;
      setRotation(Table3, 0F, 0F, 0F);
      Table4 = new ModelRenderer(this, 0, 22);
      Table4.addBox(0F, 0F, 0F, 16, 1, 16);
      Table4.setRotationPoint(-8F, 15F, -8F);
      Table4.setTextureSize(64, 64);
      Table4.mirror = true;
      setRotation(Table4, 0F, 0F, 0F);
      TableWood1 = new ModelRenderer(this, 0, 39);
      TableWood1.addBox(0F, 0F, 0F, 16, 1, 7);
      TableWood1.setRotationPoint(-8F, 14F, -8F);
      TableWood1.setTextureSize(64, 64);
      TableWood1.mirror = true;
      setRotation(TableWood1, 0F, 0F, 0F);
      TableWood2 = new ModelRenderer(this, 0, 39);
      TableWood2.addBox(0F, 0F, 0F, 16, 1, 7);
      TableWood2.setRotationPoint(-8F, 14F, 1F);
      TableWood2.setTextureSize(64, 64);
      TableWood2.mirror = true;
      setRotation(TableWood2, 0F, 0F, 0F);
      TableWood3 = new ModelRenderer(this, 56, 0);
      TableWood3.addBox(0F, 0F, 0F, 1, 1, 2);
      TableWood3.setRotationPoint(7F, 14F, -1F);
      TableWood3.setTextureSize(64, 64);
      TableWood3.mirror = true;
      setRotation(TableWood3, 0F, 0F, 0F);
      Iron0 = new ModelRenderer(this, 0, 47);
      Iron0.addBox(0F, 0F, 0F, 1, 7, 2);
      Iron0.setRotationPoint(-8F, 8F, -1F);
      Iron0.setTextureSize(64, 64);
      Iron0.mirror = true;
      setRotation(Iron0, 0F, 0F, 0F);
      Iron1 = new ModelRenderer(this, 6, 47);
      Iron1.addBox(0F, 0F, 0F, 6, 1, 2);
      Iron1.setRotationPoint(-7F, 8F, -1F);
      Iron1.setTextureSize(64, 64);
      Iron1.mirror = true;
      setRotation(Iron1, 0F, 0F, 0F);
      File = new ModelRenderer(this, 6, 50);
      File.addBox(0F, 0F, 0F, 0, 6, 2);
      File.setRotationPoint(-2F, 9F, -1F);
      File.setTextureSize(64, 64);
      File.mirror = true;
      setRotation(File, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Table1.render(f5);
    Table2.render(f5);
    Table3.render(f5);
    Table4.render(f5);
    TableWood1.render(f5);
    TableWood2.render(f5);
    TableWood3.render(f5);
    Iron0.render(f5);
    Iron1.render(f5);
    File.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
	
	  public void renderModel(float f5)
	  {
		    Table1.render(f5);
		    Table2.render(f5);
		    Table3.render(f5);
		    Table4.render(f5);
		    TableWood1.render(f5);
		    TableWood2.render(f5);
		    TableWood3.render(f5);
		    Iron0.render(f5);
		    Iron1.render(f5);
		    File.render(f5);
	  }
}
