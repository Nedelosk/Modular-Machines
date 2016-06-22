package de.nedelosk.modularmachines.api.modules.recipes;

import java.util.HashMap;

import de.nedelosk.modularmachines.api.recipes.Recipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.utils.OreStack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class RecipeModuleAssembler extends Recipe {

	public RecipeModuleAssembler(String recipeName, RecipeItem[] inputs, RecipeItem[] outputs, int speedModifier, int energy, String recipeCategory,
			Object[] objects) {
		super(recipeName, inputs, outputs, speedModifier, energy, recipeCategory, objects);
	}

	private RecipeModuleAssembler(String recipeName, RecipeItem[] input, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, input, output, speedModifier, energy, "AssemblerModule");
	}

	public RecipeModuleAssembler(String recipeName, RecipeItem[] input, RecipeItem output, int speedModifier, int energy) {
		this(recipeName, input, new RecipeItem[] { output }, speedModifier, energy);
	}

	public RecipeModuleAssembler(String recipeName, RecipeItem[] input, RecipeItem output, RecipeItem output1, int speedModifier, int energy) {
		this(recipeName, input, new RecipeItem[] { output, output1 }, speedModifier, energy);
	}

	public RecipeModuleAssembler(String recipeName, RecipeItem output, RecipeItem output1, int speedModifier, int energy, Object... inputs) {
		this(recipeName, parseItems(inputs), new RecipeItem[] { output, output1 }, speedModifier, energy);
	}

	public RecipeModuleAssembler(String recipeName, RecipeItem output, int speedModifier, int energy, Object... inputs) {
		this(recipeName, parseItems(inputs), new RecipeItem[] { output }, speedModifier, energy);
	}

	private static RecipeItem[] parseItems(Object[] recipe) {
		RecipeItem[] input = new RecipeItem[9];
		String shape = "";
		int idx = 0;
		if (recipe[idx] instanceof String[]) {
			String[] parts = ((String[]) recipe[idx++]);
			for(String s : parts) {
				shape += s;
			}
		} else {
			while (recipe[idx] instanceof String) {
				String s = (String) recipe[idx++];
				shape += s;
			}
		}
		HashMap<Character, RecipeItem> itemMap = new HashMap<Character, RecipeItem>();
		for(; idx < recipe.length; idx += 2) {
			Character chr = (Character) recipe[idx];
			Object in = recipe[idx + 1];
			if (in instanceof ItemStack) {
				itemMap.put(chr, new RecipeItem(((ItemStack) in).copy()));
			} else if (in instanceof Item) {
				itemMap.put(chr, new RecipeItem(new ItemStack((Item) in)));
			} else if (in instanceof Block) {
				itemMap.put(chr, new RecipeItem(new ItemStack((Block) in, 1, OreDictionary.WILDCARD_VALUE)));
			} else if (in instanceof String) {
				itemMap.put(chr, new RecipeItem(new OreStack((String) in)));
			}
		}
		int x = 0;
		for(char chr : shape.toCharArray()) {
			input[x++] = itemMap.get(chr);
		}
		return input;
	}
}
