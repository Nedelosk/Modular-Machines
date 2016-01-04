package nedelosk.modularmachines.api.producers.machines.recipes;

import java.util.HashMap;
import nedelosk.forestday.api.crafting.OreStack;
import nedelosk.modularmachines.api.recipes.Recipe;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class RecipeModuleAssembler extends Recipe {

	private RecipeModuleAssembler(RecipeItem[] input, RecipeItem[] output, int speedModifier, int energy) {
		super(input, output, speedModifier, energy, "AssemblerModule");
	}

	public RecipeModuleAssembler(RecipeItem[] input, RecipeItem output, int speedModifier, int energy) {
		this(input, new RecipeItem[] { output }, speedModifier, energy);
	}

	public RecipeModuleAssembler(RecipeItem[] input, RecipeItem output, RecipeItem output1, int speedModifier,
			int energy) {
		this(input, new RecipeItem[] { output, output1 }, speedModifier, energy);
	}

	public RecipeModuleAssembler(RecipeItem output, RecipeItem output1, int speedModifier, int energy,
			Object... inputs) {
		this(parseItems(inputs), new RecipeItem[] { output, output1 }, speedModifier, energy);
	}

	public RecipeModuleAssembler(RecipeItem output, int speedModifier, int energy, Object... inputs) {
		this(parseItems(inputs), new RecipeItem[] { output }, speedModifier, energy);
	}

	private static RecipeItem[] parseItems(Object[] recipe) {
		RecipeItem[] input = new RecipeItem[9];
		String shape = "";
		int idx = 0;
		if (recipe[idx] instanceof String[]) {
			String[] parts = ((String[]) recipe[idx++]);

			for (String s : parts) {
				shape += s;
			}

		} else {
			while (recipe[idx] instanceof String) {
				String s = (String) recipe[idx++];
				shape += s;
			}
		}

		HashMap<Character, RecipeItem> itemMap = new HashMap<Character, RecipeItem>();

		for (; idx < recipe.length; idx += 2) {
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
		for (char chr : shape.toCharArray()) {
			input[x++] = itemMap.get(chr);
		}
		return input;
	}
}
