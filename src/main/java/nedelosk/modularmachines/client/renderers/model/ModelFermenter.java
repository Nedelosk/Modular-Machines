package nedelosk.modularmachines.client.renderers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Fermenter - Nedelosk
 * Created using Tabula 5.1.0
 */
public class ModelFermenter extends ModelBase {
    public ModelRenderer monitor;
    public ModelRenderer monitor_underground;
    public ModelRenderer monitor_holder_0;
    public ModelRenderer monitor_holder_2;
    public ModelRenderer tank;
    public ModelRenderer tank_holder_0;
    public ModelRenderer tank_undergrund;
    public ModelRenderer tank_holder_1;
    public ModelRenderer input_underground;
    public ModelRenderer input_holder_0_0;
    public ModelRenderer input_holder_1_0;
    public ModelRenderer input_0;
    public ModelRenderer input_holder_0_1;
    public ModelRenderer input_holder_1_1;
    public ModelRenderer input_1;
    public ModelRenderer input_2;

    public ModelFermenter() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.monitor_holder_0 = new ModelRenderer(this, 40, 0);
        this.monitor_holder_0.setRotationPoint(2.0F, 12.0F, 1.0F);
        this.monitor_holder_0.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.tank_holder_1 = new ModelRenderer(this, 30, 0);
        this.tank_holder_1.setRotationPoint(10.0F, 0.0F, 0.0F);
        this.tank_holder_1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
        this.input_holder_1_1 = new ModelRenderer(this, 30, 0);
        this.input_holder_1_1.setRotationPoint(10.0F, 0.0F, 0.0F);
        this.input_holder_1_1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
        this.monitor = new ModelRenderer(this, 0, 0);
        this.monitor.setRotationPoint(19.0F, -6.0F, 2.0F);
        this.monitor.addBox(0.0F, 0.0F, -9.0F, 6, 12, 12, 0.0F);
        this.setRotateAngle(monitor, 0.0F, 1.5707963267948966F, 0.0F);
        this.input_holder_0_0 = new ModelRenderer(this, 30, 0);
        this.input_holder_0_0.setRotationPoint(-22.0F, 10.0F, 4.0F);
        this.input_holder_0_0.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
        this.tank = new ModelRenderer(this, 0, 50);
        this.tank.setRotationPoint(-6.0F, -8.0F, -6.0F);
        this.tank.addBox(0.0F, 0.0F, 0.0F, 12, 18, 12, 0.0F);
        this.tank_holder_0 = new ModelRenderer(this, 30, 0);
        this.tank_holder_0.setRotationPoint(-6.0F, 10.0F, -1.0F);
        this.tank_holder_0.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
        this.input_1 = new ModelRenderer(this, 80, 82);
        this.input_1.setRotationPoint(4.0F, 0.0F, 4.0F);
        this.input_1.addBox(0.0F, 0.0F, 0.0F, 4, 16, 12, 0.0F);
        this.setRotateAngle(input_1, 0.0F, 1.5707963267948966F, 0.0F);
        this.input_holder_0_1 = new ModelRenderer(this, 30, 0);
        this.input_holder_0_1.setRotationPoint(10.0F, 0.0F, 0.0F);
        this.input_holder_0_1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
        this.input_holder_1_0 = new ModelRenderer(this, 30, 0);
        this.input_holder_1_0.setRotationPoint(-22.0F, 10.0F, -6.0F);
        this.input_holder_1_0.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
        this.input_underground = new ModelRenderer(this, 0, 24);
        this.input_underground.setRotationPoint(-24.0F, 16.0F, -8.0F);
        this.input_underground.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16, 0.0F);
        this.monitor_holder_2 = new ModelRenderer(this, 40, 0);
        this.monitor_holder_2.setRotationPoint(0.0F, 0.0F, -10.0F);
        this.monitor_holder_2.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.monitor_underground = new ModelRenderer(this, 0, 24);
        this.monitor_underground.setRotationPoint(8.0F, 16.0F, -8.0F);
        this.monitor_underground.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16, 0.0F);
        this.tank_undergrund = new ModelRenderer(this, 0, 24);
        this.tank_undergrund.setRotationPoint(-8.0F, 16.0F, -8.0F);
        this.tank_undergrund.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16, 0.0F);
        this.input_0 = new ModelRenderer(this, 0, 82);
        this.input_0.setRotationPoint(-24.0F, -6.0F, -8.0F);
        this.input_0.addBox(0.0F, 0.0F, 0.0F, 4, 16, 16, 0.0F);
        this.input_2 = new ModelRenderer(this, 42, 82);
        this.input_2.setRotationPoint(-12.0F, 0.0F, 0.0F);
        this.input_2.addBox(0.0F, 0.0F, 0.0F, 4, 16, 12, 0.0F);
        this.monitor.addChild(this.monitor_holder_0);
        this.tank_holder_0.addChild(this.tank_holder_1);
        this.input_holder_1_0.addChild(this.input_holder_1_1);
        this.input_0.addChild(this.input_1);
        this.input_holder_0_0.addChild(this.input_holder_0_1);
        this.monitor_holder_0.addChild(this.monitor_holder_2);
        this.input_1.addChild(this.input_2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.monitor.render(f5);
        this.input_holder_0_0.render(f5);
        this.tank.render(f5);
        this.tank_holder_0.render(f5);
        this.input_holder_1_0.render(f5);
        this.input_underground.render(f5);
        this.monitor_underground.render(f5);
        this.tank_undergrund.render(f5);
        this.input_0.render(f5);
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
