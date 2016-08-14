package de.nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.tuple.Pair;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.material.IColoredMaterial;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.material.MaterialRegistry;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.common.core.ItemManager;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.IColoredItem;
import de.nedelosk.modularmachines.common.utils.Translator;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemModule extends Item implements IColoredItem, IItemModelRegister {

	private static List<Pair<ResourceLocation, IMaterial>> modules = new ArrayList<>();

	public ItemModule() {
		setUnlocalizedName("modules");
		setCreativeTab(TabModularMachines.tabModules);
		setHasSubtypes(true);
	}

	public static <M extends IModule> ItemStack registerAndCreateStack(IModule module, IMaterial material) {
		if(module == null || material == null){
			return null;
		}
		register(module, material);
		return createStack(module, material);
	}

	public static <M extends IModule> void register(IModule module, IMaterial material){
		if(module == null || material == null){
			return;
		}
		modules.add(Pair.of(module.getRegistryName(), material));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "module");
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		IModuleContainer container = ModularMachinesApi.getContainerFromItem(stack);
		if(container != null){
			return container.getDisplayName();
		}
		return Translator.translateToLocal("item.module.name");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for(Pair<ResourceLocation, IMaterial> moduleEntry : modules){
			subItems.add(createStack(moduleEntry.getKey(), moduleEntry.getValue()));
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if(stack.hasTagCompound()){
			NBTTagCompound nbtTag = stack.getTagCompound();
			if(tintIndex == 0){
				if(nbtTag.hasKey("Material")){
					IMaterial material = MaterialRegistry.getMaterial(nbtTag.getString("Material"));
					if(material instanceof IColoredMaterial){
						return ((IColoredMaterial)material).getColor();
					}
				}
			}else if(tintIndex == 1){
				if(nbtTag.hasKey("Module")){
					IModule module = ModularMachinesApi.MODULES.getValue(new ResourceLocation(nbtTag.getString("Module")));
					if(module instanceof IModuleColored){
						return ((IModuleColored) module).getColor();
					}
				}
			}
		}
		return 16777215;
	}

	public static ItemStack createStack(IModuleContainer container) {
		if(container == null){
			return null;
		}
		return createStack(container.getModule(), container.getMaterial());
	}

	public static ItemStack createStack(IModule module, IMaterial material) {
		if(module == null || material == null){
			return null;
		}
		return createStack(module.getRegistryName(), material);
	}

	public static ItemStack createStack(ResourceLocation location, IMaterial material) {
		if(location == null || material == null){
			return null;
		}
		for(Pair<ResourceLocation, IMaterial> module : modules){
			if(module.getKey().equals(location) && module.getValue().equals(material)){
				ItemStack itemStack = new ItemStack(ItemManager.itemModules);
				NBTTagCompound nbtTag = new NBTTagCompound();
				nbtTag.setString("Module", location.toString());
				nbtTag.setString("Material", material.getName().toLowerCase(Locale.ENGLISH));
				itemStack.setTagCompound(nbtTag);
				return itemStack;
			}
		}
		return null;
	}
}
