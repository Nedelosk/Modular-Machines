package nedelosk.forestday.common.registrys;

import nedelosk.forestday.common.configs.ForestdayConfig;
import nedelosk.forestday.common.items.materials.ItemCampfire;
import nedelosk.forestday.common.items.materials.ItemNature;
import nedelosk.forestday.common.items.tools.ItemCutter;
import nedelosk.forestday.common.items.tools.ItemFile;
import nedelosk.forestday.common.items.tools.ItemFlintAxe;
import nedelosk.forestday.common.items.tools.ItemToolCrafting;
import nedelosk.forestday.common.items.tools.ItemToolForestday.Material;
import nedelosk.forestday.common.managers.BlockManager;
import nedelosk.forestday.common.managers.FItemManager;
import nedelosk.forestday.common.items.tools.ItemToolParts;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

public class ItemRegistry {

	public static BlockRegistry coreBlocks;
	
	public static void preInit()
	{
		FItemManager.crop_corn.registerItem(new ItemSeedFood(2, 0.4F, BlockManager.Crop_Corn.block(), Blocks.farmland){
			@Override
			public void registerIcons(net.minecraft.client.renderer.texture.IIconRegister IIconRegister) {
				itemIcon = IIconRegister.registerIcon("forestday:corn");
			};
		}.setUnlocalizedName("corn"));
		MinecraftForge.addGrassSeed(new ItemStack(FItemManager.crop_corn.item()), 15);
		FItemManager.nature.registerItem(new ItemNature());
		
		FItemManager.curb.registerItem(new ItemCampfire(ForestdayConfig.campfireCurbs, "curb"));
		FItemManager.pot.registerItem(new ItemCampfire(ForestdayConfig.campfirePots, "pot"));
		FItemManager.pot_holder.registerItem(new ItemCampfire(ForestdayConfig.campfirePotHolders, "pot_holder"));
		
		FItemManager.file_stone.registerItem(new ItemFile(50, 5, "file.stone", "file_stone", 1, Material.Stone));
		FItemManager.file_iron.registerItem(new ItemFile(100, 2, "file.iron", "file", 2, Material.Iron));
		FItemManager.file_diamond.registerItem(new ItemFile(150, 1, "file.diamond", "file_diamond", 3, Material.Diamond));
		FItemManager.hammer.registerItem(new ItemToolCrafting("hammer", 300, 0, Material.Iron, "hammer", 15));
		FItemManager.knife_stone.registerItem(new ItemToolCrafting("knife", 200, 1, Material.Iron, "knife", 5));
		FItemManager.cutter.registerItem(new ItemCutter(250, 10, "cutter", "cutter", 1, Material.Iron));
		FItemManager.adze.registerItem(new ItemToolCrafting("adze", 175, 1, Material.Stone, "adze", 3));
		FItemManager.adze_long.registerItem(new ItemToolCrafting("adze.long", 175, 1, Material.Stone, "adze.long", 3));
		FItemManager.axe_flint.registerItem(new ItemFlintAxe(EnumHelper.addToolMaterial("flint", 1, 50, 1, 0, 1)));
		
		FItemManager.tool_parts.registerItem(new ItemToolParts());
		
		}
	
}
