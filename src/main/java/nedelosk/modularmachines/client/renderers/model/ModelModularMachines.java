package nedelosk.modularmachines.client.renderers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Modular Machines - Nedelosk
 * Created using Tabula 5.1.0
 */
public class ModelModularMachines extends ModelBase {
    
    //Manager Top
    public ModelRenderer Base_Manager_Top;
    
    //Manager Back
    public ModelRenderer Base_Manager_Back;

    public ModelModularMachines() {
        this.Base_Manager_Back = new ModelRenderer(this, 0, 0);
        this.Base_Manager_Back.setRotationPoint(-5.0F, 11.0F, 7.0F);
        this.Base_Manager_Back.addBox(0.0F, 0.0F, 0.0F, 10, 10, 1, 0.0F);
        this.Base_Manager_Top = new ModelRenderer(this, 0, 0);
        this.Base_Manager_Top.setRotationPoint(-5.0F, 8.0F, -5.0F);
        this.Base_Manager_Top.addBox(0.0F, 0.0F, 0.0F, 10, 1, 10, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Base_Manager_Back.render(f5);
        this.Base_Manager_Top.render(f5);
    }
}
