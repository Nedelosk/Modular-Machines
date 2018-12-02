package modularmachines.api.ingredients;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

public enum IngredientType {
	NONE,
	ITEM(ItemStack.class),
	ORE(OreStack.class),
	FLUID(FluidStack.class),
	ENERGY(Integer.class);
	
	private static final Map<Class, IngredientType> TYPES = new HashMap<>();
	private Class<?> ingredientClass;
	
	IngredientType() {
		this.ingredientClass = null;
		register();
	}
	
	IngredientType(Class<?> ingredientClass) {
		this.ingredientClass = ingredientClass;
		register();
	}
	
	private void register() {
		TYPES.put(ingredientClass, this);
	}
	
	public Class<?> getIngredientClass() {
		return ingredientClass;
	}
	
	public static IngredientType getType(Object ingredient) {
		return TYPES.getOrDefault(ingredient.getClass(), NONE);
	}
}
