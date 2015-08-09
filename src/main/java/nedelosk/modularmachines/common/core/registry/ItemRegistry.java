package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.IModuleSpecial;
import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.items.ModuleItems;
import nedelosk.modularmachines.common.items.materials.ItemAlloyIngot;
import nedelosk.modularmachines.common.items.materials.ItemAlloyNugget;
import nedelosk.modularmachines.common.items.materials.ItemDust;
import nedelosk.modularmachines.common.items.materials.ItemGears;
import nedelosk.nedeloskcore.common.items.ItemIngot;
import nedelosk.nedeloskcore.common.items.ItemNugget;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemRegistry {
	
	public static String[] ingotsOther = new String[]{ "Niobium", "Tantalum" };
	
	public static void preInit()
	{
		
		//Blocks
		
		MMItems.Dusts.registerItem(new ItemDust(ItemDust.dusts, ""));
		MMItems.Dusts_Others.registerItem(new ItemDust(ItemDust.dustsOtherOres, ".other"));
		MMItems.Alloy_Ingots.registerItem(new ItemAlloyIngot());
		MMItems.Ingots_Others.registerItem(new ItemIngot(ingotsOther, "modularmachines"));
		MMItems.Alloy_Nuggets.registerItem(new ItemAlloyNugget());
		MMItems.Nuggets_Others.registerItem(new ItemNugget(ingotsOther, "modularmachines"));
		MMItems.Gears.registerItem(new ItemGears());
		
		MMItems.Module_Items.registerItem(new ModuleItems());
    	for(String s : ModuleItems.names)
    	{
    		for(int i = 0;i < 3;i++)
    		{
        		ItemStack stack = new ItemStack(MMItems.Module_Items.item());
        		stack.setTagCompound(new NBTTagCompound());
        		if(ModuleItems.modules.get(s) instanceof IModuleSpecial)
        		{
        			NBTTagCompound nbt = new NBTTagCompound();
        			((IModuleSpecial)ModuleItems.modules.get(s)).writeToItemNBT(nbt, i);
        			ModuleItems.modules.get(s).readFromNBT(nbt);
        		}
        		stack.getTagCompound().setString("Name", s);
        		stack.getTagCompound().setInteger("Tier", i);
    			ModularMachinesApi.addModuleItem(stack, ModuleItems.modules.get(s), i + 1, true);
    		}
    	}
		
	}
	
}
