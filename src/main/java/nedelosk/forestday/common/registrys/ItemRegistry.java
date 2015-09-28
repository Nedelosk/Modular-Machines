package nedelosk.forestday.common.registrys;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.items.materials.ItemCampfire;
import nedelosk.forestday.common.items.materials.ItemNature;
import nedelosk.forestday.common.items.tools.ItemBowAndStick;
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

public class ItemRegistry {

	public static BlockRegistry coreBlocks;
	
	public static void preInit()
	{
		FItems.crop_corn.registerItem(new ItemSeedFood(2, 0.4F, FBlocks.Crop_Corn.block(), Blocks.farmland){
			@Override
			public void registerIcons(net.minecraft.client.renderer.texture.IIconRegister IIconRegister) {
				itemIcon = IIconRegister.registerIcon("forestday:corn");
			};
		}.setUnlocalizedName("corn"));
		MinecraftForge.addGrassSeed(new ItemStack(FItems.crop_corn.item()), 15);
		FItems.nature.registerItem(new ItemNature());
		//FItems.charcoal_kiln.registerItem(new ItemCharcoalKiln());
		
		FItems.curb.registerItem(new ItemCampfire(ForestdayConfig.campfireCurbs, "curb"));
		FItems.pot.registerItem(new ItemCampfire(ForestdayConfig.campfirePots, "pot"));
		FItems.pot_holder.registerItem(new ItemCampfire(ForestdayConfig.campfirePotHolders, "pot_holder"));
		
		FItems.file_stone.registerItem(new ItemFile(50, 5, "file.stone", "file_stone", 1, Material.Stone));
		FItems.file_iron.registerItem(new ItemFile(100, 2, "file.iron", "file", 2, Material.Iron));
		FItems.file_diamond.registerItem(new ItemFile(150, 1, "file.diamond", "file_diamond", 3, Material.Diamond));
		FItems.hammer.registerItem(new ItemToolCrafting("hammer", 300, 0, Material.Iron, "hammer", 15));
		FItems.knife_stone.registerItem(new ItemToolCrafting("knife", 200, 1, Material.Iron, "knife", 5));
		FItems.cutter.registerItem(new ItemCutter(250, 10, "cutter", "cutter", 1, Material.Iron));
		FItems.adze.registerItem(new ItemToolCrafting("adze", 175, 1, Material.Stone, "adze", 3));
		FItems.adze_long.registerItem(new ItemToolCrafting("adze.long", 175, 1, Material.Stone, "adze.long", 3));
		FItems.axe_flint.registerItem(new ItemFlintAxe(EnumHelper.addToolMaterial("flint", 1, 50, 1, 0, 1)));
		FItems.bow_and_stick.registerItem(new ItemBowAndStick("bowandstick", 150, "bow_and_stick", 1, Material.Wood));
		
		FItems.tool_parts.registerItem(new ItemToolParts());
		
		}
	
}
