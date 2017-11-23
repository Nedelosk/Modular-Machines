package modularmachines.common.core.managers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

public class OreDictionaryManager {
	
	public static void registerOres() {
		registerOre("blockObsidian", Blocks.OBSIDIAN);
		registerOre("itemCoal", new ItemStack(Items.COAL));
	}
}
