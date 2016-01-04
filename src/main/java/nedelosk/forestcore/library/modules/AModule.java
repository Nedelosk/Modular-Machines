package nedelosk.forestcore.library.modules;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestcore.library.modules.manager.IObjectManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public abstract class AModule implements IModule {

	public final String name;

	public AModule(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public static void addShapedRecipe(ItemStack stack, Object... obj) {
		GameRegistry.addRecipe(new ShapedOreRecipe(stack, obj));
	}

	public static void addShapelessRecipe(ItemStack stack, Object... obj) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(stack, obj));
	}

	@Override
	public void preInit(IModuleManager manager) {
	}

	@Override
	public void init(IModuleManager manager) {
	}

	@Override
	public void postInit(IModuleManager manager) {
	}

	@Override
	public void onRegisterObject(IObjectManager manager) {
	}

}
