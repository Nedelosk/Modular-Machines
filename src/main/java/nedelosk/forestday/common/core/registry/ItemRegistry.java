package nedelosk.forestday.common.core.registry;

import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.common.core.managers.FBlockManager;
import nedelosk.forestday.common.core.managers.FItemManager;
import nedelosk.forestday.common.items.base.ItemGearWood;
import nedelosk.forestday.common.items.base.ItemWoodBucket;
import nedelosk.forestday.common.items.materials.ItemCampfire;
import nedelosk.forestday.common.items.materials.ItemGem;
import nedelosk.forestday.common.items.materials.ItemIngot;
import nedelosk.forestday.common.items.materials.ItemNature;
import nedelosk.forestday.common.items.materials.ItemNugget;
import nedelosk.forestday.common.items.tools.ItemCutter;
import nedelosk.forestday.common.items.tools.ItemFile;
import nedelosk.forestday.common.items.tools.ItemFlintAxe;
import nedelosk.forestday.common.items.tools.ItemToolCrafting;
import nedelosk.forestday.common.items.tools.ItemToolForestday.Material;
import nedelosk.forestday.common.items.tools.ItemToolParts;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class ItemRegistry {

	public static BlockRegistry coreBlocks;
	
	public static void preInit()
	{
		FItemManager.Crop_Corn.registerItem(new ItemSeedFood(2, 0.4F, FBlockManager.Crop_Corn.block(), Blocks.farmland){
			@Override
			public void registerIcons(net.minecraft.client.renderer.texture.IIconRegister IIconRegister) {
				itemIcon = IIconRegister.registerIcon("forestday:corn");
			};
		}.setUnlocalizedName("corn"));
		MinecraftForge.addGrassSeed(new ItemStack(FItemManager.Crop_Corn.item()), 15);
		FItemManager.Nature.registerItem(new ItemNature());
		
		FItemManager.Curb.registerItem(new ItemCampfire(ForestDayConfig.campfireCurbs, "curb"));
		FItemManager.Pot.registerItem(new ItemCampfire(ForestDayConfig.campfirePots, "pot"));
		FItemManager.Pot_Holder.registerItem(new ItemCampfire(ForestDayConfig.campfirePotHolders, "pot_holder"));
		
		FItemManager.File_Stone.registerItem(new ItemFile(50, 5, "file.stone", "file_stone", 1, Material.Stone));
		FItemManager.File_Iron.registerItem(new ItemFile(100, 2, "file.iron", "file", 2, Material.Iron));
		FItemManager.File_Diamond.registerItem(new ItemFile(150, 1, "file.diamond", "file_diamond", 3, Material.Diamond));
		FItemManager.Hammer.registerItem(new ItemToolCrafting("hammer", 300, 0, Material.Iron, "hammer", 15));
		FItemManager.Knife_Stone.registerItem(new ItemToolCrafting("knife", 200, 1, Material.Iron, "knife", 5));
		FItemManager.Cutter.registerItem(new ItemCutter(250, 10, "cutter", "cutter", 1, Material.Iron));
		FItemManager.Adze.registerItem(new ItemToolCrafting("adze", 175, 1, Material.Stone, "adze", 3));
		FItemManager.Adze_Long.registerItem(new ItemToolCrafting("adze.long", 175, 1, Material.Stone, "adze.long", 3));
		FItemManager.Axe_Flint.registerItem(new ItemFlintAxe(EnumHelper.addToolMaterial("flint", 1, 50, 1, 0, 1)));
		
		FItemManager.Tool_Parts.registerItem(new ItemToolParts());
		
		FItemManager.Bucket_Wood.registerItem(new ItemWoodBucket(Blocks.air, "bucket.wood").setMaxStackSize(16));
		FItemManager.Bucket_Wood_Water.registerItem(new ItemWoodBucket(Blocks.water, "bucket.wood.water"));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(FItemManager.Bucket_Wood_Water.item()), new ItemStack(FItemManager.Bucket_Wood.item()));
		
		FItemManager.Ingots.registerItem(new ItemIngot(ItemIngot.ingots, "forestday"));
		FItemManager.Nuggets.registerItem(new ItemNugget(ItemNugget.nuggets, "forestday"));
		FItemManager.Gems.registerItem(new ItemGem());
		FItemManager.Gears_Wood.registerItem(new ItemGearWood());
		
		}
	
}
