package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.material.IColoredMaterial;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.client.core.ModelManager;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.IColoredItem;
import de.nedelosk.modularmachines.common.utils.Translator;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemModule extends Item implements IColoredItem, IItemModelRegister {

	public ItemModule() {
		setUnlocalizedName("modules");
		setCreativeTab(TabModularMachines.tabModules);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		ModelResourceLocation[] locs = new ModelResourceLocation[]{
				ModelManager.getInstance().getModelLocation("module_small"),
				ModelManager.getInstance().getModelLocation("module_middle"),
				ModelManager.getInstance().getModelLocation("module_large")
		};
		manager.registerItemModel(item, new ModuleItemMeshDefinition(locs));
		ModelBakery.registerItemVariants(item, locs[0]);
		ModelBakery.registerItemVariants(item, locs[1]);
		ModelBakery.registerItemVariants(item, locs[2]);
	}

	private static class ModuleItemMeshDefinition implements ItemMeshDefinition{

		private ModelResourceLocation[] locs;

		public ModuleItemMeshDefinition(ModelResourceLocation... locs) {
			this.locs = locs;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			if(stack.hasTagCompound()){
				NBTTagCompound nbtTag = stack.getTagCompound();
				if(nbtTag.hasKey("Container")){
					IModuleContainer container = ModularMachinesApi.MODULE_CONTAINERS.getValue(new ResourceLocation(nbtTag.getString("Container")));
					return locs[container.getModule().getSize(container).ordinal()-1];
				}
			}
			return ModelManager.getInstance().getModelLocation("module_large");
		}

	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound()){
			NBTTagCompound nbtTag = stack.getTagCompound();
			if(nbtTag.hasKey("Container")){
				IModuleContainer container = ModularMachinesApi.MODULE_CONTAINERS.getValue(new ResourceLocation(nbtTag.getString("Container")));
				if(container != null){
					return container.getDisplayName();
				}
			}
		}
		return Translator.translateToLocal("item.module.name");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for(IModuleContainer container : ModularMachinesApi.getModulesWithDefaultItem()){
			subItems.add(ModularMachinesApi.createDefaultStack(container));
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if(stack.hasTagCompound()){
			NBTTagCompound nbtTag = stack.getTagCompound();
			if(nbtTag.hasKey("Container")){
				IModuleContainer container = ModularMachinesApi.MODULE_CONTAINERS.getValue(new ResourceLocation(nbtTag.getString("Container")));
				if(tintIndex == 0){
					IMaterial material = container.getMaterial();
					if(material instanceof IColoredMaterial){
						return ((IColoredMaterial)material).getColor();
					}
				}else if(tintIndex == 1){
					IModule module = container.getModule();
					if(module instanceof IModuleColored){
						return ((IModuleColored) module).getColor();
					}
				}
			}
		}
		return 16777215;
	}
}
