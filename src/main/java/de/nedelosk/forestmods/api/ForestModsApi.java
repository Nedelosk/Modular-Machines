package de.nedelosk.forestmods.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.crafting.ICampfireRecipe;
import de.nedelosk.forestmods.api.internal.FakeInternalMethodHandler;
import de.nedelosk.forestmods.api.internal.IInternalMethodHandler;
import de.nedelosk.forestmods.api.multiblocks.IAirHeatingPlantRecipe;
import de.nedelosk.forestmods.api.multiblocks.IBlastFurnaceRecipe;
import de.nedelosk.forestmods.api.transport.node.INodeType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class ForestModsApi {

	public static IInternalMethodHandler handler = new FakeInternalMethodHandler();
	public static IBlastFurnaceRecipe blastFurnace;
	public static IAirHeatingPlantRecipe airHeatingPlant;
	public static final ArrayList<Fluid> airHeatingPlantGas = Lists.newArrayList();
	public static final HashMap<Fluid, Integer> fermenterFluid = Maps.newHashMap();
	private static ArrayList<INodeType> nodeTypes = Lists.newArrayList();
	public static ICampfireRecipe campfireRecipe;
	private static Map<ItemStack, List<ItemStack>> charcoalKilnDrops = Maps.newHashMap();

	public static void registerCharcoalDrops(ItemStack woodStack, List<ItemStack> drops) {
		if (getCharcoalDrops(woodStack).isEmpty()) {
			charcoalKilnDrops.put(woodStack, drops);
		} else {
			getCharcoalDrops(woodStack).addAll(drops);
		}
	}

	public static List<ItemStack> getCharcoalDrops(ItemStack woodStack) {
		for(Entry<ItemStack, List<ItemStack>> entry : charcoalKilnDrops.entrySet()) {
			ItemStack stack = entry.getKey();
			if (stack.getItem() == woodStack.getItem()
					&& (stack.getItemDamage() == woodStack.getItemDamage() || stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
					&& ItemStack.areItemStackTagsEqual(stack, woodStack)) {
				return entry.getValue();
			}
		}
		return new ArrayList();
	}

	public static Map<ItemStack, List<ItemStack>> getCharcoalKilnDrops() {
		return Collections.unmodifiableMap(charcoalKilnDrops);
	}

	public static void addAirHeatingPlantGas(Fluid fluid) {
		airHeatingPlantGas.add(fluid);
	}

	public static ArrayList<INodeType> getNodeTypes() {
		return nodeTypes;
	}

	public static void addNodeTypes(INodeType type) {
		nodeTypes.add(type);
	}

	public static INodeType getNodeType(int index) {
		return nodeTypes.get(index);
	}
}
