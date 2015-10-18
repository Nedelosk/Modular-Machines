package nedelosk.modularmachines.api.modular.module.basic.basic;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public abstract class Module implements IModule{
	
	protected String moduleModifier;
	protected ArrayList<Type> tiers = Lists.newArrayList();
	private HashMap<Type, String> typeModifiers = Maps.newHashMap();
	
	public Module() {
	}
	
	public Module(String moduleModifier) {
		this.moduleModifier = moduleModifier;
	}
	
	@Override
	public String getModifier(ModuleStack stack){		
		return moduleModifier;
	}
	
	@Override
	public String getTypeModifier(ModuleStack stack){
		if(typeModifiers.get(stack.getType()) != null){
			return typeModifiers.get(stack.getType());
		}
		return null;
	}
	
	@Override
	public void addType(Type tier, String modifier){
		if(!typeModifiers.containsKey(tier))
			typeModifiers.put(tier, modifier);
	}

	@Override
	public String getName(ModuleStack stack) {
		return "module" + getModuleName() + ((getModifier(stack) != null) ? getModifier(stack) : "") + ((getTypeModifier(stack) != null) ? getTypeModifier(stack) : "");
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
	
}
