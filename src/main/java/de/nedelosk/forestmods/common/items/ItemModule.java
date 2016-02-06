package de.nedelosk.forestmods.common.items;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.api.modular.material.Materials.Material;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.special.IModuleWithItem;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.core.TabForestMods;
import de.nedelosk.forestmods.common.core.modules.ModuleModularMachine;
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
		setCreativeTab(TabForestMods.tabModules);
		setHasSubtypes(true);
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public void registerIcons(IIconRegister IIconRegister) {
		icons = new IIcon[2];
		icons[0] = IIconRegister.registerIcon("modularmachines:modules/module.0");
		icons[1] = IIconRegister.registerIcon("modularmachines:modules/module.1");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return icons[pass];
	}

	public static <M extends IModule> ModuleStack addModule(M module, Material material) {
		ItemStack itemStack = new ItemStack(ModuleModularMachine.ItemManager.Producers.item());
		ModuleStack moduleStack = new ModuleStack<M, IModuleSaver>(module, material);
		if (ModuleRegistry.getModule(moduleStack.getModule().getModuleUID()) == null) {
			ModuleRegistry.registerModule(module);
		}
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("UID", module.getUID());
		itemStack.setTagCompound(nbtTag);
		subItems.add(itemStack);
		moduleStack.setItemStack(itemStack);
		ModuleRegistry.addModuleToItem(moduleStack, false);
		return moduleStack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			return StatCollector.translateToLocal("item.module.name");
		}
		return StatCollector.translateToLocal("item.module.name") + " " + StatCollector.translateToLocal(stack.getTagCompound().getString("Name") + ".name");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		subItems.addAll(ItemModule.subItems);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if (stack.hasTagCompound() && pass == 1) {
			IModuleWithItem module = (IModuleWithItem) ModuleRegistry.getModuleFromItem(stack).moduleStack.getModule();
			return module.getColor();
		} else {
			return 16777215;
		}
	}

	public static ItemStack getItem(String moduleUID) {
		for ( ItemStack stack : subItems ) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("UID")) {
				String UID = stack.getTagCompound().getString("UID");
				if (UID.equals(moduleUID)) {
					return stack;
				}
			}
		}
		return null;
	}
}