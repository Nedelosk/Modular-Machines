package nedelosk.modularmachines.common.modular.module.basic.basic;

import java.util.HashMap;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.basic.Module;
import nedelosk.modularmachines.api.modular.tier.Tiers.Tier;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.client.renderers.modules.ModularMachineRenderer;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleCasing extends Module implements IModuleCasing {

	public HashMap<Tier, Integer> slots = Maps.newHashMap();
	
	public ModuleCasing() {
	}
	
	public ModuleCasing(NBTTagCompound nbt) {
		super(nbt);
	}
	
	public ModuleCasing(String modifier) {
		super(modifier);
	}
	
	@Override
	public String getModuleName() {
		return "Casing";
	}
	
	public void addTier(Tier tier, int slots) {
		super.addTier(tier);
		this.slots.put(tier, slots);
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.CasingRenderer(moduleStack);
	}

}
