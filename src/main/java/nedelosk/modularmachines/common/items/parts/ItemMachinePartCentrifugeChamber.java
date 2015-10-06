package nedelosk.modularmachines.common.items.parts;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.Stats;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.api.parts.PartType.MachinePartType;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.materials.MachineState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMachinePartCentrifugeChamber extends ItemMachinePart {

	public ItemMachinePartCentrifugeChamber(String name) {
		super(new PartType[]{ new MachinePartType(ItemRegistry.Rods),
							  new MachinePartType(ItemRegistry.Rods),
							  new MachinePartType(ItemRegistry.Burning_Chamber),
							  new MachinePartType(ItemRegistry.Rods),
							  new MachinePartType(ItemRegistry.Rods) }, name);
	}

	@Override
	public ItemStack getMachine(ItemStack stack) {
		List<Material> materials = new ArrayList();
		materials.add(MMRegistry.Iron);
		materials.add(MMRegistry.Iron);
		materials.add(MMRegistry.Iron);
		materials.add(MMRegistry.Iron);
		materials.add(MMRegistry.Iron);
		return buildItem(materials);
	}

	@Override
	public ModuleStack buildModule(ItemStack stack) {
		return null;
	}

	@Override
	public Material getMaterial(ItemStack stack) {
		return getMaterials(stack)[0];
	}

	@Override
	public NBTTagCompound buildData(List<Material> materials) {
		NBTTagCompound nbtTag = new NBTTagCompound();
		MachineState state_0 = materials.get(0).getStats(Stats.MACHINE);
		MachineState state_1 = materials.get(1).getStats(Stats.MACHINE);
		MachineState state_2 = materials.get(2).getStats(Stats.MACHINE);
		MachineState state_3 = materials.get(3).getStats(Stats.MACHINE);
		MachineState state_4 = materials.get(4).getStats(Stats.MACHINE);
		int tier = state_0.tier() + state_1.tier() + state_2.tier() + state_3.tier() + state_4.tier();
		nbtTag.setInteger("Tier", tier / 5);
		return nbtTag;
	}

}
