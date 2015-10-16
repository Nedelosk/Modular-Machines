package nedelosk.modularmachines.api.modular.module.basic.basic;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.tier.Tiers.Tier;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public abstract class Module implements IModule{
	
	protected String moduleModifier;
	protected ArrayList<Tier> tiers = Lists.newArrayList();
	private HashMap<Tier, String> tierModifiers = Maps.newHashMap();
	private HashMap<Tier, IProducer> tierProducer = Maps.newHashMap();
	
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
	public String getTierModifier(ModuleStack stack){
		if(tierModifiers.get(stack.getTier()) != null){
			return tierModifiers.get(stack.getTier());
		}
		return null;
	}

	@Override
	public String getName(ModuleStack stack) {
		return "module" + getModuleName() + ((getModifier(stack) != null) ? getModifier(stack) : "") + ((getTierModifier(stack) != null) ? getTierModifier(stack) : "") ;
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
	public void addTier(Tier tier){
		if(!tiers.contains(tier))
			tiers.add(tier);
	}
	
	@Override
	public void addTier(Tier tier, String modifier, IProducer producer){
		addTier(tier);
		if(!tierModifiers.containsKey(tier))
			tierModifiers.put(tier, modifier);
		if(!tierProducer.containsKey(tier))
			tierProducer.put(tier, producer);
	}
	
	@Override
	public ArrayList<Tier> getTiers() {
		return tiers;
	}
	
	@Override
	public HashMap<Tier, IProducer> getProducer() {
		return tierProducer;
	}
	
}
