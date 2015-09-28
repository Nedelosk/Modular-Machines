package nedelosk.modularmachines.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.basic.machine.crafting.IModularCraftingRecipe;
import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.IModule;
import nedelosk.modularmachines.api.basic.machine.module.ModuleStack;
import nedelosk.modularmachines.api.basic.machine.module.farm.IFarm;
import nedelosk.modularmachines.api.basic.techtree.TechPointStack;
import nedelosk.modularmachines.api.basic.techtree.language.ILanguageManager;
import nedelosk.modularmachines.api.multiblocks.IAirHeatingPlantRecipe;
import nedelosk.modularmachines.api.multiblocks.IBlastFurnaceRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;

public class ModularMachinesApi {
	
	public static String currentLanguage;
    
	public final static HashMap<List, TechPointStack[]> techpoinedItems = Maps.newHashMap();
	
	private static ArrayList<IModularCraftingRecipe> recipes = Lists.newArrayList();
	
	public static IBlastFurnaceRecipe blastFurnace;
	
	public static IAirHeatingPlantRecipe airHeatingPlant;
	
	public static final ArrayList<Fluid> airHeatingPlantGas = Lists.newArrayList();
	
	public static final HashMap<Fluid, Integer> fermenterFluid = Maps.newHashMap();
	
	public static ILanguageManager languageManager;
	
	public static void addAirHeatingPlantGas(Fluid fluid)
	{
		airHeatingPlantGas.add(fluid);
	}
	
	public static ArrayList<IModularCraftingRecipe> getModularRecipes() {
		return recipes;
	}
	
	public static void registerRecipe(IModularCraftingRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static void addTechPointsToItem(ItemStack stack, TechPointStack... stacks)
	{
		techpoinedItems.put(Arrays.asList(new Object[] { stack.getItem(), Integer.valueOf(stack.getItemDamage())}), stacks);
	}
	
	public static TechPointStack[] getTechPointsFromItem(ItemStack stack)
	{
		return techpoinedItems.get(Arrays.asList(new Object[] { stack.getItem(), Integer.valueOf(stack.getItemDamage())}));
	}
	
}
