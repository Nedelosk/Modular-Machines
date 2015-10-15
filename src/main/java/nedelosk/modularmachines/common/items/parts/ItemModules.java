package nedelosk.modularmachines.common.items.parts;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleWithItem;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.core.TabModularMachines;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemModules extends Item{
	
	public ArrayList<ItemStack> subItems = Lists.newArrayList();
	
	public ItemModules() {
		setUnlocalizedName("modules");
		setCreativeTab(TabModularMachines.components);
		setHasSubtypes(true);
	}
	
	public void addModuleItem(ModuleStack module){
		ItemStack stack = new ItemStack(this);
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("Tier", module.getTier().getName());
		nbtTag.setString("Name", module.getModule().getName(module));
		nbtTag.setString("ModuleName", module.getModule().getModuleName());
		stack.setTagCompound(nbtTag);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound())
			return StatCollector.translateToLocal("item.module.name");
		return StatCollector.translateToLocal("item.module.name") + " " + StatCollector.translateToLocal(stack.getTagCompound().getString("Name") + ".name");
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		subItems.addAll(this.subItems);
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(stack.hasTagCompound())
			return 16777215;
		if(pass == 1){
			IModuleWithItem module = ModuleRegistry.moduleFactory.createModule(stack.getTagCompound().getString("Name"));
			return module.getColor();
		}
		else{
			return 16777215;
		}
	}

}
