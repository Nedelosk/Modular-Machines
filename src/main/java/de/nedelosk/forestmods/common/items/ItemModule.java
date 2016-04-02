package de.nedelosk.forestmods.common.items;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.api.modules.special.IModuleWithItem;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.core.ItemManager;
import de.nedelosk.forestmods.common.core.TabModularMachines;
import de.nedelosk.forestmods.common.modules.ModuleRegistry;
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

	public static <M extends IModule> ModuleStack addModule(M module, IModuleType type) {
		ItemStack itemStack = new ItemStack(ItemManager.itemModules);
		ModuleStack moduleStack = new ModuleStack<M, IModuleSaver>(module, type);
		if (ModuleRegistry.getModule(moduleStack.getModule().getUID()) == null) {
			ModuleRegistry.registerModule(module);
		}
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("UID", module.getUID().toString());
		nbtTag.setString("Material", moduleStack.getType().getMaterial().getName());
		itemStack.setTagCompound(nbtTag);
		subItems.add(itemStack);
		moduleStack.setItemStack(itemStack);
		ModuleRegistry.registerItemForModule(moduleStack, false);
		return moduleStack;
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
		if (stack.hasTagCompound() && pass == 1) {
			IModuleWithItem module = (IModuleWithItem) ModuleRegistry.getModuleFromItem(stack).moduleStack.getModuleStack();
			return module.getColor();
		} else {
			return 16777215;
		}
	}

	public static ItemStack getItem(String moduleUID, Material material) {
		for ( ItemStack stack : subItems ) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("UID")) {
				String UID = stack.getTagCompound().getString("UID");
				Material m = Materials.getMaterial(stack.getTagCompound().getString("Material"));
				if (UID.equals(moduleUID) && m.equals(material)) {
					return stack;
				}
			}
		}
		return null;
	}
}
