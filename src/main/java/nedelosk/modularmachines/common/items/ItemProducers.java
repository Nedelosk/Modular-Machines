package nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerWithItem;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.core.TabModularMachines;
import nedelosk.modularmachines.common.core.manager.MMItemManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemProducers extends Item{
	
	private static ArrayList<ItemStack> subItems = Lists.newArrayList();
	
	public ItemProducers() {
		setUnlocalizedName("producers");
		setCreativeTab(TabModularMachines.components);
		setHasSubtypes(true);
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	public static ModuleStack addModuleItem(ModuleStack moduleStack){
		ItemStack itemStack = new ItemStack(MMItemManager.Producers.item());
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("Type", moduleStack.getType().getName());
		nbtTag.setString("Name", moduleStack.getProducer().getName(moduleStack));
		nbtTag.setString("ModuleName", moduleStack.getModule().getModuleName());
		itemStack.setTagCompound(nbtTag);
		subItems.add(itemStack);
		moduleStack.setItemStack(itemStack);
		return moduleStack;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound())
			return StatCollector.translateToLocal("item.module.name");
		return StatCollector.translateToLocal("item.module.name") + " " + StatCollector.translateToLocal(stack.getTagCompound().getString("Name") + ".name");
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		subItems.addAll(ItemProducers.subItems);
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(stack.hasTagCompound())
			return 16777215;
		if(pass == 1){
			IProducerWithItem producer = (IProducerWithItem) ModuleRegistry.moduleFactory.createProducer(stack.getTagCompound().getString("Name"));
			return producer.getColor();
		}
		else{
			return 16777215;
		}
	}

}
