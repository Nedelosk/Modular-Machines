package nedelosk.modularmachines.api.modules;

import java.util.HashMap;
import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public abstract class Module implements IModule {

	protected String moduleModifier;
	private HashMap<Type, String> typeModifiers = Maps.newHashMap();

	public Module() {
	}

	public Module(String moduleModifier) {
		this.moduleModifier = moduleModifier;
	}

	@Override
	public String getModifier(ModuleStack stack) {
		return moduleModifier;
	}

	@Override
	public String getTypeModifier(ModuleStack stack) {
		if (typeModifiers.get(stack.getType()) != null) {
			return typeModifiers.get(stack.getType());
		}
		return null;
	}

	@Override
	public void addType(Type tier, String modifier) {
		if (!typeModifiers.containsKey(tier))
			typeModifiers.put(tier, modifier);
	}

	@Override
	public String getName(ModuleStack stack, boolean withTypeModifier) {
		return "module" + ((getModifier(stack) != null) ? getModifier(stack) : "") + (withTypeModifier ? ((getTypeModifier(stack) != null) ? getTypeModifier(stack) : "") : "");
	}

	@Override
	public String getRegistryName() {
		return "module" + getModuleName() + moduleModifier;
	}

	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return null;
	}

	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IModule){
			IModule module = (IModule) obj;
			if(module.getModuleName().equals(getModuleName()) && module.getRegistryName().equals(getRegistryName()))
				return true;
		}
		return false;
	}

}
