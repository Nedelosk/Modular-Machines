package nedelosk.forestday.common.plugins.forestry;

import nedelosk.forestday.api.crafting.BurningMode;
import nedelosk.forestday.api.crafting.ForestdayCrafting;
import nedelosk.forestday.api.crafting.IBurnRecipe;
import nedelosk.forestday.common.registrys.FItems;
import nedelosk.forestday.common.registrys.ItemRegistry;
import nedelosk.nedeloskcore.plugins.Plugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class PluginForestry extends Plugin {

	private Item itemPeat = GameRegistry.findItem(this.getRequiredMod(), "peat");
	
	
	@Override
	public void preInit() {
	}

	@Override
	public void init() {
		
		IBurnRecipe burnRecipe = ForestdayCrafting.burningRecipe;
		
		burnRecipe.addRecipe(new ItemStack(itemPeat), new ItemStack(FItems.nature.item(), 2, 7), 1600, BurningMode.Peat);
		
	}

	@Override
	public void postInit() {
		
	}

	@Override
	public void registerRecipes() {
		
	}

	@Override
	public String getRequiredMod() {
		return "Forestry";
	}

}
