package nedelosk.forestday.common.plugins.railcraft;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.api.crafting.BurningMode;
import nedelosk.forestday.api.crafting.ForestdayCrafting;
import nedelosk.forestday.api.crafting.IBurnRecipe;
import nedelosk.forestday.common.registrys.FItems;
import nedelosk.nedeloskcore.plugins.basic.Plugin;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginRailcraft extends Plugin {

	private Item itemCoke = GameRegistry.findItem(this.getRequiredMod(), "fuel.coke");
	
	private Block blockCoke = GameRegistry.findBlock(this.getRequiredMod(), "cube");
	
	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
		IBurnRecipe burnRecipe = ForestdayCrafting.burningRecipe;
		
		burnRecipe.addRecipe(new ItemStack(itemCoke, 1, 0), new ItemStack(FItems.nature.item(), 1, 7), 3200, BurningMode.Coke);
		burnRecipe.addRecipe(new ItemStack(blockCoke, 1, 0), new ItemStack(FItems.nature.item(), 1, 7), 28800, BurningMode.Coke);
		
	}

	@Override
	public void postInit() {
		
	}

	@Override
	public void registerRecipes() {
		
	}

	@Override
	public String getRequiredMod() {
		return "Railcraft";
	}

}
