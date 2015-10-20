package nedelosk.modularmachines.client.renderers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Modular Machines - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelModularMachines extends ModelBase {
    public ModelRenderer Base;
    public ModelRenderer Front;
    public ModelRenderer Back;
    public ModelRenderer Base_1;
    public ModelRenderer Disc;
    public ModelRenderer Base_2;
    public ModelRenderer Casing;
    public ModelRenderer Casing_1;
    public ModelRenderer Casing_2;
    public ModelRenderer Casing_3;

    public ModelModularMachines() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Casing_3 = new ModelRenderer(this, 0, 0);
        this.Casing_3.setRotationPoint(0.0F, 0.0F, 7.0F);
        this.Casing_3.addBox(0.0F, 0.0F, 0.0F, 3, 6, 1, 0.0F);
        this.Casing_2 = new ModelRenderer(this, 0, 0);
        this.Casing_2.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.Casing_2.addBox(0.0F, 0.0F, 0.0F, 3, 6, 1, 0.0F);
        this.Base_2 = new ModelRenderer(this, 0, 0);
        this.Base_2.setRotationPoint(-5.0F, 13.0F, -3.0F);
        this.Base_2.addBox(0.0F, 0.0F, 0.0F, 2, 6, 6, 0.0F);
        this.Back = new ModelRenderer(this, 0, 0);
        this.Back.setRotationPoint(0.0F, 0.0F, 13.0F);
        this.Back.addBox(0.0F, 0.0F, 0.0F, 16, 16, 3, 0.0F);
        this.Disc = new ModelRenderer(this, 0, 0);
        this.Disc.setRotationPoint(-1.0F, -1.0F, 0.0F);
        this.Disc.addBox(0.0F, 0.0F, 0.0F, 5, 5, 3, 0.0F);
        this.Casing = new ModelRenderer(this, 0, 0);
        this.Casing.setRotationPoint(-1.0F, -1.0F, -1.0F);
        this.Casing.addBox(0.0F, 0.0F, 0.0F, 3, 1, 8, 0.0F);
        this.Base = new ModelRenderer(this, 0, 0);
        this.Base.setRotationPoint(-3.0F, 8.0F, -5.0F);
        this.Base.addBox(0.0F, 0.0F, 0.0F, 6, 16, 10, 0.0F);
        this.Front = new ModelRenderer(this, 0, 0);
        this.Front.setRotationPoint(-5.0F, 0.0F, -3.0F);
        this.Front.addBox(0.0F, 0.0F, 0.0F, 16, 16, 3, 0.0F);
        this.Casing_1 = new ModelRenderer(this, 0, 0);
        this.Casing_1.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Casing_1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 8, 0.0F);
        this.Base_1 = new ModelRenderer(this, 0, 0);
        this.Base_1.setRotationPoint(4.0F, 14.0F, -5.0F);
        this.Base_1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
        this.Casing_2.addChild(this.Casing_3);
        this.Casing_1.addChild(this.Casing_2);
        this.Front.addChild(this.Back);
        this.Base_1.addChild(this.Disc);
        this.Base_2.addChild(this.Casing);
        this.Base.addChild(this.Front);
        this.Casing.addChild(this.Casing_1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Base_2.render(f5);
        this.Base.render(f5);
        this.Base_1.render(f5);
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
