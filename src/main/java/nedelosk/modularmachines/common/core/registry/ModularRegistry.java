package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleWithItem;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.items.ModuleItems;
import nedelosk.modularmachines.common.machines.module.ModuleCasing;
import nedelosk.modularmachines.common.machines.module.energy.ModuleBattery;
import nedelosk.modularmachines.common.machines.module.energy.ModuleCapacitor;
import nedelosk.modularmachines.common.machines.module.energy.ModuleEnergyManager;
import nedelosk.modularmachines.common.machines.module.energy.ModuleEngine;
import nedelosk.modularmachines.common.machines.module.fluids.ModuleTankManager;
import nedelosk.modularmachines.common.machines.module.storage.ModuleChest;
import nedelosk.modularmachines.common.machines.module.storage.ModuleStorageManager;
import nedelosk.modularmachines.common.machines.module.tool.producer.alloysmelter.ModuleAlloySmelter;
import nedelosk.modularmachines.common.machines.module.tool.producer.centrifuge.ModuleCentrifuge;
import nedelosk.modularmachines.common.machines.module.tool.producer.furnace.ModuleFurnace;
import nedelosk.modularmachines.common.machines.module.tool.producer.pulverizer.ModulePulverizer;
import nedelosk.modularmachines.common.machines.module.tool.producer.sawmill.ModuleSawMill;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModularRegistry {
	
	public static void preInit()
	{
		ModularManager.addModule(new ModuleEnergyManager());
		ModularManager.addModule(new ModuleTankManager());
		ModularManager.addModule(new ModuleStorageManager());
		ModularManager.addModule(new ModuleFurnace());
		ModularManager.addModule(new ModuleSawMill());
		ModularManager.addModule(new ModuleAlloySmelter());
		ModularManager.addModule(new ModulePulverizer());
		ModularManager.addModule(new ModuleCentrifuge());
		ModularManager.addModule(new ModuleEngine("Normal", 300, 150, 50));
		ModularManager.addModuleStack(new ItemStack(Blocks.iron_block), new ModuleCasing(), 1);
		ModularManager.addModuleStack(new ItemStack(Blocks.gold_block), new ModuleCasing(), 2);
		ModularManager.addModuleStack(new ItemStack(Blocks.diamond_block), new ModuleCasing(), 3);
		ModularManager.addModuleStack(new ItemStack(Items.iron_axe), new ModuleCasing(), 1);
		ModularManager.addModuleStack(new ItemStack(Blocks.chest), new ModuleChest("Normal", 27), 1);
		ModularManager.addModuleStack(new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0), new ModuleCapacitor(10, 20), 1);
		ModularManager.addModuleStack(new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 1), new ModuleCapacitor(20, 30), 2);
		ModularManager.addModuleStack(new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 2), new ModuleCapacitor(25, 40), 2);
		ModularManager.addModuleStack(new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 3), new ModuleCapacitor(40, 60), 1);
		ModularManager.registerModuleClass(ModuleBattery.class, "BatteryModular");
		ModularManager.registerModuleClass(ModuleEngine.class, "EngineModular");
		
    	//if(Loader.isModLoaded("appliedenergistics2"))
    		//ModularMachinesApi.registerBookmark("Storage_AE2");
    	
    	registerModuleItems();
 
	}
	
	public static void registerModuleItems()
	{
		ModuleItems.registerModules("Manager");
		ModuleItems.registerModules("Producer");
		ModuleItems.registerModules("Engine");
    	for(String s : ModuleItems.names)
    	{
    		for(int i = 0;i < 3;i++)
    		{
        		ItemStack stack = new ItemStack(MMItems.Module_Items.item());
        		stack.setTagCompound(new NBTTagCompound());
        		if(ModuleItems.modules.get(s) instanceof IModuleWithItem)
        		{
        			NBTTagCompound nbt = new NBTTagCompound();
        			((IModuleWithItem)ModuleItems.modules.get(s)).writeToItemNBT(nbt, i);
        			ModuleItems.modules.get(s).readFromNBT(nbt);
        		}
        		stack.getTagCompound().setString("Name", s);
        		stack.getTagCompound().setInteger("Tier", i);
        		ModularManager.addModuleStack(stack, ModuleItems.modules.get(s), i + 1, true);
    		}
    	}
	}
	
}
