package nedelosk.modularmachines.common.items;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.parts.IMachineComponent;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.TabModularMachines;
import nedelosk.modularmachines.common.modular.utils.MaterialManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemMachineComponent extends Item implements IMachineComponent {

	public String componentName;
    public MaterialType[] type;
	
	public ItemMachineComponent(String name, MaterialType... type) {
		this.setUnlocalizedName("component." + name);
		this.setCreativeTab(TabModularMachines.components);
		this.componentName = name;
		this.type = type;
	}

    @Override
    public String getItemStackDisplayName (ItemStack stack)
    {
        Material materialM = MaterialManager.getMaterial(stack);
        if(materialM == null)
        	return super.getItemStackDisplayName(stack);

        if (StatCollector.canTranslate("component." + componentName) && StatCollector.canTranslate("material." + materialM.identifier))
        {
            return materialM.localizedName() + " " + StatCollector.translateToLocal("component." + componentName);
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
    	Material materialM = MaterialManager.getMaterial(stack);
        if(materialM == null)
            return getUnlocalizedName();

        String material = "unknown";
        material = materialM.materialName;

        return "component." + componentName + "." + material;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {

        // material id == metadata
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(item, 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(type[0] == MaterialType.ALL || mat.type == type[0] || (type[0] == MaterialType.METAL && mat.type == MaterialType.METAL_Custom)){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null)
               		list.add(stack);
            }else if(type.length > 1){
            	if(mat.type == type[1])
                	MaterialManager.setMaterial(stack, mat);
                	if (MaterialManager.getMaterial(stack) != null)
                   		list.add(stack);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("modularmachines:components/" + componentName);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderpass) {
        Material material = MaterialManager.getMaterial(stack);

        if(material != null)
        {
        	return material.primaryColor();
        }
        return super.getColorFromItemStack(stack, renderpass);
    }
	
	@Override
	public MaterialType getMaterialType() {
		return type[0];
	}
	
	@Override
	public Material getMaterial(ItemStack stack) {
		return MaterialManager.getMaterial(stack);
	}
    
}
