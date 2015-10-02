package nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleManager;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleWithItem;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.producer.producer.IModuleProducer;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import nedelosk.modularmachines.common.core.tabs.TabModularMachinesModules;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModuleItems extends ModularItem {

	public ModuleItems() {
		super("module");
		setHasSubtypes(true);
		setCreativeTab(TabModularMachinesModules.instance);
	}
	
	public static void registerModules(String moduleName)
	{
		if(moduleName.equals("Manager"))
		{
			for(IModule module : ModularManager.getModules().values())
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
			for(IModule module : ModularManager.getModules().values())
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
			for(IModule module : ModularManager.getModules().values())
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
	public IIcon[] itemIcons = new IIcon[4];
	
	
	public HashMap<String, IIcon[]> icons = new HashMap<String, IIcon[]>();
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
    	itemIcons[0] = iconRegister.registerIcon("modularmachines:modules/moduleBase");
    	itemIcons[1] = iconRegister.registerIcon("modularmachines:modules/moduleImproved");
    	itemIcons[2] = iconRegister.registerIcon("modularmachines:modules/moduleAdvanced");
    	itemIcons[3] = iconRegister.registerIcon("modularmachines:modules/moduleHolder");
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
    	list.add(new ItemStack(id, 1, 3));
    	for(String s : names)
    	{
    		for(int i = 0;i < 3;i++)
    		{
        		ItemStack stack = new ItemStack(id);
        		stack.setTagCompound(new NBTTagCompound());
        		if(modules.get(s) instanceof IModuleWithItem)
        		{
        			NBTTagCompound nbt = new NBTTagCompound();
        			((IModuleWithItem)modules.get(s)).writeToItemNBT(nbt, i);
        			modules.get(s).readFromNBT(nbt);
        		}
        		stack.getTagCompound().setString("Name", s);
        		stack.getTagCompound().setInteger("Tier", i);
    			list.add(stack);
    			ModularManager.addModuleStack(stack, modules.get(s), i + 1, true);
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
    		else if(stack.getItemDamage() == 3)
    			return itemIcons[3];
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
    		else if(stack.getItemDamage() == 3)
    			return itemIcons[3];
    	NBTTagCompound nbt = stack.getTagCompound();
    	return icons.get(nbt.getString("Name"))[nbt.getInteger("Tier")];
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
    	if(!itemstack.hasTagCompound())
    		return super.getItemStackDisplayName(itemstack);
    	return StatCollector.translateToLocal("module." + itemstack.getTagCompound().getInteger("Tier")) + " " + StatCollector.translateToLocal("module." + itemstack.getTagCompound().getString("Name")) + " " + StatCollector.translateToLocal("module");
    }
    
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
    	if(!itemstack.hasTagCompound())
    		return NRegistry.setUnlocalizedItemName("module." + itemstack.getItemDamage(), "mm");
        return NRegistry.setUnlocalizedItemName("module." + itemstack.getItemDamage() , "mm");
    }

}
