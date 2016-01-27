package nedelosk.modularmachines.common.items;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import akka.japi.Pair;
import nedelosk.forestcore.library.utils.MapUtil;
import nedelosk.modularmachines.api.modular.type.Materials.Material;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.special.IProducerWithItem;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.common.core.TabModularMachines;
import nedelosk.modularmachines.modules.ModuleModular;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemProducers extends Item {

	private static LinkedHashMap<Pair<Material, IModule>, ItemStack> subItems = Maps.newLinkedHashMap();
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

	public static ModuleStack addProducer(ModuleStack moduleStack) {
		ItemStack itemStack = new ItemStack(ModuleModular.ItemManager.Producers.item());
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("Type", moduleStack.getMaterial().getName());
		nbtTag.setString("Name", moduleStack.getModule().getName(moduleStack));
		nbtTag.setString("ModuleName", moduleStack.getModule().getModuleName());
		itemStack.setTagCompound(nbtTag);
		subItems.put(new Pair(moduleStack.getMaterial(), moduleStack.getModule()), itemStack);
		subItems = (LinkedHashMap<Pair<Material, IModule>, ItemStack>) MapUtil.sort(subItems, new ModuleComparator());
		moduleStack.setItemStack(itemStack);
		return moduleStack;
	}

	private static class ModuleComparator implements Comparator<Map.Entry<Pair<Material, IModule>, ItemStack>> {

		@Override
		public int compare(Entry<Pair<Material, IModule>, ItemStack> o1, Entry<Pair<Material, IModule>, ItemStack> o2) {
			if (o1.getKey().first().getTier() > o2.getKey().first().getTier()) {
				return 1;
			} else if (o1.getKey().first().getTier() < o2.getKey().first().getTier()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound()) {
			return StatCollector.translateToLocal("item.module.name");
		}
		return StatCollector.translateToLocal("item.module.name") + " " + StatCollector.translateToLocal(stack.getTagCompound().getString("Name") + ".name");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		subItems.addAll(ItemProducers.subItems.values());
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if (pass == 1 && stack.hasTagCompound()) {
			IProducerWithItem producer = (IProducerWithItem) ModuleRegistry.producerFactory.createProducer(stack.getTagCompound().getString("Name"));
			return producer.getColor();
		} else {
			return 16777215;
		}
	}

	public static HashMap<Pair<Material, IModule>, ItemStack> getItems() {
		return subItems;
	}

	public static ItemStack getItem(Pair<Material, IModule> pair) {
		return subItems.get(pair);
	}

	public static ItemStack getItem(Material type, IModule module) {
		return subItems.get(new Pair<Material, IModule>(type, module));
	}
}
