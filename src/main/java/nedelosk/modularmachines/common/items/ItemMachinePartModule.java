package nedelosk.modularmachines.common.items;

import java.util.List;

import nedelosk.modularmachines.api.modular.material.Material;
import nedelosk.modularmachines.api.modular.module.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.parts.IMachinePartProducer;
import nedelosk.modularmachines.api.modular.parts.PartType;
import nedelosk.modularmachines.api.modular.parts.PartType.MachinePartType;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMachinePartModule extends ItemMachinePart implements IMachinePartProducer {
	
	public ItemMachinePartModule(String name) {
		super(new PartType[]{ new MachinePartType(ItemRegistry.Connection_Wires),
							  new MachinePartType(ItemRegistry.Connection_Wires),
							  new MachinePartType(ItemRegistry.Plates),
							  new MachinePartType(ItemRegistry.Connection_Wires),
							  new MachinePartType(ItemRegistry.Connection_Wires) }, name);
	}

	@Override
	public ModuleStack buildModule(ItemStack stack) {
		return null;
	}

	@Override
	public NBTTagCompound buildData(List<Material> materials) {
		return new NBTTagCompound();
	}
	
	@Override
	public Material getMaterial(ItemStack stack) {
		return getMaterials(stack)[2];
	}

}
