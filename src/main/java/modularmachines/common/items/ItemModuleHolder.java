package modularmachines.common.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.material.IColoredMaterial;
import modularmachines.api.material.IMaterial;
import modularmachines.api.material.IMetalMaterial;
import modularmachines.api.material.MaterialRegistry;
import modularmachines.api.modules.ModuleManager;
import modularmachines.client.model.ModelManager;
import modularmachines.common.core.Registry;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.utils.content.IColoredItem;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemModuleHolder extends Item implements IColoredItem, IItemModelRegister {

	public ItemModuleHolder() {
		setUnlocalizedName("module_holders");
		setCreativeTab(TabModularMachines.tabModules);
		setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbtTag = stack.getTagCompound();
			if (nbtTag.hasKey("Material", 8)) {
				IMaterial mat = MaterialRegistry.getMaterial(nbtTag.getString("Material"));
				return String.format(super.getItemStackDisplayName(stack), mat.getLocalizedName());
			}
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerItemModels(Item item, ModelManager manager) {
		manager.registerItemModel(item, 0, "module_large_holder");
		manager.registerItemModel(item, 1, "module_medium_holder");
		manager.registerItemModel(item, 2, "module_small_holder");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName(super.getUnlocalizedName(stack).replace("item.", "") + "." + stack.getItemDamage());
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (IMetalMaterial material : ModuleManager.getMaterialsWithHolder()) {
			for (int i = 0; i < 3; i++) {
				subItems.add(ModuleManager.getHolder(material, i));
			}
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbtTag = stack.getTagCompound();
			if (nbtTag.hasKey("Material", 8)) {
				IMaterial mat = MaterialRegistry.getMaterial(nbtTag.getString("Material"));
				return ((IColoredMaterial) mat).getColor();
			}
		}
		return 16777215;
	}
}
