package nedelosk.modularmachines.common.items;

import java.util.HashMap;
import java.util.List;
import com.google.common.collect.Maps;

import akka.japi.Pair;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerWithItem;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.core.TabModularMachines;
import nedelosk.modularmachines.common.core.manager.MMItemManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemProducers extends Item {

	private static HashMap<Pair<Type, IModule>, ItemStack> subItems = Maps.newHashMap();
	private IIcon[] icons;

	public ItemProducers() {
		setUnlocalizedName("producers");
		setCreativeTab(TabModularMachines.components);
		setHasSubtypes(true);
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public void registerIcons(IIconRegister IIconRegister) {
		icons = new IIcon[2];
		icons[0] = IIconRegister.registerIcon("modularmachines:producers/producer.0");
		icons[1] = IIconRegister.registerIcon("modularmachines:producers/producer.1");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return icons[pass];
	}

	public static ModuleStack addModuleItem(ModuleStack moduleStack) {
		ItemStack itemStack = new ItemStack(MMItemManager.Producers.item());
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("Type", moduleStack.getType().getName());
		nbtTag.setString("Name", moduleStack.getProducer().getName(moduleStack));
		nbtTag.setString("ModuleName", moduleStack.getModule().getModuleName());
		itemStack.setTagCompound(nbtTag);
		subItems.put(new Pair(moduleStack.getType(), moduleStack.getModule()), itemStack);
		moduleStack.setItemStack(itemStack);
		return moduleStack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound())
			return StatCollector.translateToLocal("item.module.name");
		return StatCollector.translateToLocal("item.module.name") + " "
				+ StatCollector.translateToLocal(stack.getTagCompound().getString("Name") + ".name");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		subItems.addAll(ItemProducers.subItems.values());
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if (!stack.hasTagCompound())
			return 16777215;
		if (pass == 1) {
			IProducerWithItem producer = (IProducerWithItem) ModuleRegistry.moduleFactory.createProducer(stack.getTagCompound().getString("Name"));
			return producer.getColor();
		} else {
			return 16777215;
		}
	}
	
	public static HashMap<Pair<Type, IModule>, ItemStack> getItems(){
		return subItems;
	}
	
	public static ItemStack getItem(Pair<Type, IModule> pair){
		return subItems.get(pair);
	}
	
	public static ItemStack getItem(Type type, IModule module){
		return subItems.get(new Pair<Type, IModule>(type, module));
	}

}
