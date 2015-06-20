package nedelosk.forestday.common.registrys;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.items.materials.ItemCampfire;
import nedelosk.forestday.common.items.materials.ItemDust;
import nedelosk.forestday.common.items.materials.ItemGearWood;
import nedelosk.forestday.common.items.materials.ItemGears;
import nedelosk.forestday.common.items.materials.ItemIngot;
import nedelosk.forestday.common.items.materials.ItemNature;
import nedelosk.forestday.common.items.materials.ItemNugget;
import nedelosk.forestday.common.items.materials.ItemPowder;
import nedelosk.forestday.common.items.tools.ItemAdze;
import nedelosk.forestday.common.items.tools.ItemBowAndStick;
import nedelosk.forestday.common.items.tools.ItemCutter;
import nedelosk.forestday.common.items.tools.ItemFile;
import nedelosk.forestday.common.items.tools.ItemFlintAxe;
import nedelosk.forestday.common.items.tools.ItemKnife;
import nedelosk.forestday.common.items.tools.ItemToolForestday.Material;
import nedelosk.forestday.common.items.tools.ItemToolParts;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.ItemCharcoalKiln;
import net.minecraftforge.common.util.EnumHelper;

public class ItemRegistry {

	public static BlockRegistry coreBlocks;
	
	public static void preInit()
	{
		
		FItems.ingots.registerItem(new ItemIngot());
		FItems.nuggets.registerItem(new ItemNugget());
		FItems.dust.registerItem(new ItemDust());
		FItems.powder.registerItem(new ItemPowder());
		FItems.gears.registerItem(new ItemGears());
		FItems.gears_wood.registerItem(new ItemGearWood());
		FItems.nature.registerItem(new ItemNature());
		FItems.charcoal_kiln.registerItem(new ItemCharcoalKiln());
		
		FItems.curb.registerItem(new ItemCampfire(ForestdayConfig.campfireCurbs, "curb"));
		FItems.pot.registerItem(new ItemCampfire(ForestdayConfig.campfirePots, "pot"));
		FItems.pot_holder.registerItem(new ItemCampfire(ForestdayConfig.campfirePotHolders, "pot_holder"));
		
		FItems.file_stone.registerItem(new ItemFile(50, 5, "file.stone", "file_stone", 1, Material.Stone));
		FItems.file_iron.registerItem(new ItemFile(100, 2, "file.iron", "file", 2, Material.Iron));
		FItems.file_diamond.registerItem(new ItemFile(150, 1, "file.diamond", "file_diamond", 3, Material.Diamond));
		FItems.knife_stone.registerItem(new ItemKnife("knife", 200, 1, Material.Iron));
		FItems.cutter.registerItem(new ItemCutter(250, 10, "cutter", "cutter", 1, Material.Iron));
		FItems.adze.registerItem(new ItemAdze("adze", 175, 1, Material.Stone, 3));
		FItems.adze_long.registerItem(new ItemAdze("adze.long", 175, 1, Material.Stone, 3));
		FItems.axe_flint.registerItem(new ItemFlintAxe(EnumHelper.addToolMaterial("flint", 1, 50, 1, 0, 1)));
		FItems.bow_and_stick.registerItem(new ItemBowAndStick("bowandstick", 150, "bow_and_stick", 1, Material.Wood));
		
		FItems.tool_parts.registerItem(new ItemToolParts());
		
		}
	
}
