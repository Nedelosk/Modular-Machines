package nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.api.modular.module.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.IModuleProducer;
import nedelosk.modularmachines.api.modular.module.IModuleSpecial;
import nedelosk.modularmachines.api.modular.module.manager.IModuleManager;
import nedelosk.modularmachines.common.core.tabs.TabModularMachinesModules;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModuleItems extends ModularItem {

	public ModuleItems() {
		super("module");
		setHasSubtypes(true);
		registerModules("Manager");
		registerModules("Producer");
		registerModules("Engine");
		setCreativeTab(TabModularMachinesModules.instance);
	}
	
	public void registerModules(String moduleName)
	{
		if(moduleName.equals("Manager"))
		{
			for(IModule module : ModularMachinesApi.getModules().values())
			{
				if(module instanceof IModuleManager)
				{
					ModuleItems.names.add(module.getName());
					ModuleItems.modules.put(module.getName(), module);
				}
			}
		}
		else if(moduleName.equals("Producer"))
		{
			for(IModule module : ModularMachinesApi.getModules().values())
			{
				if(module instanceof IModuleProducer)
				{
					ModuleItems.names.add(module.getName());
					ModuleItems.modules.put(module.getName(), module);
				}
			}
		}
		else if(moduleName.equals("Engine"))
		{
			for(IModule module : ModularMachinesApi.getModules().values())
			{
				if(module instanceof IModuleEngine)
				{
					ModuleItems.names.add(module.getName());
					ModuleItems.modules.put(module.getName(), module);
				}
			}
		}
	}
	
	public static ArrayList<String> names = new ArrayList<String>();
	public static HashMap<String, IModule> modules = new HashMap<String, IModule>();
	public IIcon[] itemIcons = new IIcon[3];
	
	
	public HashMap<String, IIcon[]> icons = new HashMap<String, IIcon[]>();
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
    	itemIcons[0] = iconRegister.registerIcon("modularmachines:modules/moduleBase");
    	itemIcons[1] = iconRegister.registerIcon("modularmachines:modules/moduleImproved");
    	itemIcons[2] = iconRegister.registerIcon("modularmachines:modules/moduleAdvanced");
    	for(String s : names)
    	{
			IIcon[] icons = new IIcon[3];
    		for(int t = 0; t < 3;t++)
    		{
        
    			icons[t] = iconRegister.registerIcon("modularmachines:modules/" + s + "_" + t);
    		}
			this.icons.put(s, icons);
    	}
    }
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
    	list.add(new ItemStack(id));
    	list.add(new ItemStack(id, 1, 1));
    	list.add(new ItemStack(id, 1, 2));
    	for(String s : names)
    	{
    		for(int i = 0;i < 3;i++)
    		{
        		ItemStack stack = new ItemStack(id);
        		stack.setTagCompound(new NBTTagCompound());
        		if(modules.get(s) instanceof IModuleSpecial)
        		{
        			NBTTagCompound nbt = new NBTTagCompound();
        			((IModuleSpecial)modules.get(s)).writeToItemNBT(nbt, i);
        			modules.get(s).readFromNBT(nbt);
        		}
        		stack.getTagCompound().setString("Name", s);
        		stack.getTagCompound().setInteger("Tier", i);
    			list.add(stack);
    			ModularMachinesApi.addModuleItem(stack, modules.get(s), i + 1, true);
    		}
    	}
    }
    
    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
    	if(!stack.hasTagCompound())
    		if(stack.getItemDamage() == 0)
    			return itemIcons[0];
    		else if(stack.getItemDamage() == 1)
    			return itemIcons[1];
    		else if(stack.getItemDamage() == 2)
    			return itemIcons[2];
    	NBTTagCompound nbt = stack.getTagCompound();
    	return icons.get(nbt.getString("Name"))[nbt.getInteger("Tier")];
    }
    
    @Override
    public IIcon getIconIndex(ItemStack stack) {
    	if(!stack.hasTagCompound())
    		if(stack.getItemDamage() == 0)
    			return itemIcons[0];
    		else if(stack.getItemDamage() == 1)
    			return itemIcons[1];
    		else if(stack.getItemDamage() == 2)
    			return itemIcons[2];
    	NBTTagCompound nbt = stack.getTagCompound();
    	return icons.get(nbt.getString("Name"))[nbt.getInteger("Tier")];
    }
    
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
    	if(!itemstack.hasTagCompound())
    		return NRegistry.setUnlocalizedItemName("module", "mm");
        return NRegistry.setUnlocalizedItemName("module." + itemstack.getTagCompound().getString("Name") + "." + itemstack.getTagCompound().getInteger("Tier"), "mm");
    }

}
