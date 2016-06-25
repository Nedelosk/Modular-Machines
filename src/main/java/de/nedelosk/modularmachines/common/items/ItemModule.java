package de.nedelosk.modularmachines.common.items;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.material.MaterialRegistry;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.common.core.ItemManager;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.IColoredItem;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemModule extends Item implements IColoredItem {

	private static List<ItemStack> subItems = Lists.newArrayList();

	public ItemModule() {
		setUnlocalizedName("producers");
		setCreativeTab(TabModularMachines.tabModules);
		setHasSubtypes(true);
	}

	public static <M extends IModule> ItemStack registerAndCreateItem(IModuleContainer moduleContainer) {
		ItemStack itemStack = new ItemStack(ItemManager.itemModules);
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("UID", moduleContainer.getRegistryName().toString());
		nbtTag.setString("Material", moduleContainer.getMaterial().getName());
		itemStack.setTagCompound(nbtTag);
		subItems.add(itemStack);
		return itemStack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			return Translator.translateToLocal("item.module.name");
		}
		return Translator.translateToLocal("item.module.name") + " "
		+ Translator.translateToLocal(stack.getTagCompound().getString("Material") + ".name");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		subItems.addAll(ItemModule.subItems);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		IModule module = ModuleManager.getContainerFromItem(stack).getModule();
		if (module instanceof IModuleColored && stack.hasTagCompound() && tintIndex == 1) {
			IModuleColored moduleColered = (IModuleColored) module;
			return moduleColered.getColor();
		} else {
			return 16777215;
		}
	}

	public static ItemStack getItem(ResourceLocation location, IMaterial material) {
		for(ItemStack stack : subItems) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("UID")) {
				String itemUID = stack.getTagCompound().getString("UID");
				IMaterial m = MaterialRegistry.getMaterial(stack.getTagCompound().getString("Material"));
				if (itemUID.equals(location.toString()) && m.equals(material)) {
					return stack;
				}
			}
		}
		return null;
	}
}
