package nedelosk.modularmachines.common.items;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.basic.machine.part.MachineMaterial;
import nedelosk.modularmachines.api.basic.machine.part.MaterialType;
import nedelosk.modularmachines.client.renderers.model.ModelModularTable;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.registry.IMachineComponent;
import nedelosk.modularmachines.common.core.tabs.TabModularMachinesModules;
import nedelosk.modularmachines.common.machines.MachineManager;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.CustomMaterial;
import tconstruct.library.tools.DynamicToolPart;
import tconstruct.library.util.TextureHelper;
import tconstruct.util.config.PHConstruct;

public class ItemMachineComponent extends Item implements IMachineComponent {

	public String componentName;
    public MaterialType type;
	
	public ItemMachineComponent(MaterialType type, String name) {
		this.setUnlocalizedName("component" + name);
		this.setCreativeTab(TabModularMachinesModules.instance);
		this.componentName = name;
		this.type = type;
	}

    @Override
    public String getItemStackDisplayName (ItemStack stack)
    {
        String material = "";
        String matName = "";
        MachineMaterial materialM = MachineManager.getMaterial(stack);
        if(materialM == null)
        	return super.getItemStackDisplayName(stack);

        material = materialM.localizedName().toLowerCase(Locale.ENGLISH).startsWith("material.") ? materialM.localizedName().substring(9) : materialM.localizedName(); // :(

        if (StatCollector.canTranslate("component." + componentName + "." + materialM.identifier))
        {
            return StatCollector.translateToLocal("component." + componentName + "." + materialM);
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
    	MachineMaterial materialM = MachineManager.getMaterial(stack);
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
        for(int i = 0;i < MMRegistry.toolMaterials.size();i++) {
            ItemStack stack = new ItemStack(item, 1, i);
            MachineMaterial mat = MMRegistry.toolMaterials.get(i);
            if(type == MaterialType.ALL || mat.type == type){
            	MachineManager.setMaterial(stack, mat);
            	if (MachineManager.getMaterial(stack) != null)
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
        MachineMaterial material = MachineManager.getMaterial(stack);

        if(material != null)
        {
        	return material.primaryColor();
        }
        return super.getColorFromItemStack(stack, renderpass);
    }
	
	@Override
	public MaterialType getMaterialType() {
		return type;
	}
    
}
