package de.nedelosk.forestmods.common.items;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.material.MaterialRegistry;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.special.IModuleWithItem;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.core.ItemManager;
import de.nedelosk.forestmods.common.core.TabModularMachines;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemModule extends Item {

	private static List<ItemStack> subItems = Lists.newArrayList();
	private IIcon[] icons;

	public ItemModule() {
		setUnlocalizedName("producers");
		setCreativeTab(TabModularMachines.tabModules);
		setHasSubtypes(true);
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public void registerIcons(IIconRegister IIconRegister) {
		icons = new IIcon[2];
		icons[0] = IIconRegister.registerIcon("forestmods:modules/module.0");
		icons[1] = IIconRegister.registerIcon("forestmods:modules/module.1");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return icons[pass];
	}

	public static <M extends IModule> ItemStack createItem(ModuleStack<M> moduleStack) {
		ItemStack itemStack = new ItemStack(ItemManager.itemModules);
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("UID", moduleStack.getUID().toString());
		nbtTag.setString("Material", moduleStack.getMaterial().getName());
		itemStack.setTagCompound(nbtTag);
		subItems.add(itemStack);
		return itemStack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			return StatCollector.translateToLocal("item.module.name");
		}
		return StatCollector.translateToLocal("item.module.name") + " "
				+ StatCollector.translateToLocal(stack.getTagCompound().getString("Material") + ".name");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		subItems.addAll(ItemModule.subItems);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		IModule module = ModuleManager.moduleRegistry.getModuleFromItem(stack).getModule();
		if (module instanceof IModuleWithItem && stack.hasTagCompound() && pass == 1) {
			IModuleWithItem moduleItem = (IModuleWithItem) ModuleManager.moduleRegistry.getModuleFromItem(stack).getModule();
			return moduleItem.getColor();
		} else {
			return 16777215;
		}
	}

	public static ItemStack getItem(ModuleUID UID, IMaterial material) {
		for(ItemStack stack : subItems) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("UID")) {
				String itemUID = stack.getTagCompound().getString("UID");
				IMaterial m = MaterialRegistry.getMaterial(stack.getTagCompound().getString("Material"));
				if (itemUID.equals(UID.toString()) && m.equals(material)) {
					return stack;
				}
			}
		}
		return null;
	}
}
