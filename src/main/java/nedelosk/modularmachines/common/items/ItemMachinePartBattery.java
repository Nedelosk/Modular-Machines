package nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modular.material.Material;
import nedelosk.modularmachines.api.modular.material.MaterialType;
import nedelosk.modularmachines.api.modular.material.Stats;
import nedelosk.modularmachines.api.modular.material.Tags;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleBattery;
import nedelosk.modularmachines.api.modular.module.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.parts.IMachinePartBattery;
import nedelosk.modularmachines.api.modular.parts.PartType;
import nedelosk.modularmachines.api.modular.parts.PartType.MachinePartType;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.machines.module.energy.ModuleBattery;
import nedelosk.modularmachines.common.machines.stats.EnergyConductsState;
import nedelosk.modularmachines.common.machines.stats.EnergyStorageState;
import nedelosk.modularmachines.common.machines.stats.MachineState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMachinePartBattery extends ItemMachinePartEnergy implements IMachinePartBattery {

	public ItemMachinePartBattery(String name) {
		super(new PartType[]{ new PartType(ItemRegistry.Connection_Wires, Stats.ENERGY_CONDUCTS),
							  new MachinePartType(ItemRegistry.Plates),
							  new PartType(ItemRegistry.Energy_Crystal, Stats.ENERGY_STORAGE),
							  new MachinePartType(ItemRegistry.Plates),
							  new PartType(ItemRegistry.Connection_Wires, Stats.ENERGY_CONDUCTS) }, name);
	}
	
	@Override
	public ItemStack getMachine(ItemStack stack) {
		List<Material> materials = new ArrayList();
		materials.add(MMRegistry.Copper);
		materials.add(MMRegistry.Iron);
		materials.add(MMRegistry.Ruby);
		materials.add(MMRegistry.Iron);
		materials.add(MMRegistry.Copper);
		return buildItem(materials);
	}

	@Override
	public NBTTagCompound buildData(List<Material> materials) {
		EnergyConductsState connectionWires_0 = materials.get(0).getStats(Stats.ENERGY_CONDUCTS);
		Material plates_0 = materials.get(1);
		EnergyStorageState energyCrystal = materials.get(2).getStats(Stats.ENERGY_STORAGE);
		Material plates_1 = materials.get(3);
		EnergyConductsState connectionWires_1 = materials.get(4).getStats(Stats.ENERGY_CONDUCTS);
		NBTTagCompound nbtTag = new NBTTagCompound();
		
		int energyReceiveRate = connectionWires_0.energyConducts;
		int energyExtractionRate = connectionWires_1.energyConducts;
		int energyMax = energyCrystal.energyStorage;
		
		nbtTag.setInteger("EnergyReceiveRate", energyReceiveRate);
		nbtTag.setInteger("EnergyExtractionRate", energyExtractionRate);
		nbtTag.setInteger("EnergyMax", energyMax);
		
		return nbtTag;
	}
	
	@Override
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List subItems) {
	    for(Material head : MMRegistry.materials) {
	        if(!head.hasStats(Tags.TAG_MACHINE))
	        	continue;
	        if(head.type == MaterialType.CRYTAL || head.type == MaterialType.STONE || head.type == MaterialType.WOOD)
	        	continue;
	        if(!head.hasStats(Stats.ENERGY_CONDUCTS))
	        	continue;
	        List<Material> mats = new ArrayList<Material>(requiredComponents.length);

	        for(int i = 0; i < requiredComponents.length; i++) {
	        	if(i == 2)
	        		mats.add(MMRegistry.Ruby);
	        	else	
	        		mats.add(head);
	        }

	        ItemStack tool = buildItem(mats);
	        subItems.add(tool);
	      }
	}

	@Override
	public int getEnergyReceiveRate(ItemStack stack) {
		if(stack == null)
			return 0;
		if(!stack.hasTagCompound())
			return 0;
		return stack.getTagCompound().getCompoundTag(getTagKey()).getInteger("EnergyReceiveRate");
	}

	@Override
	public int getEnergyExtractionRate(ItemStack stack) {
		if(stack == null)
			return 0;
		if(!stack.hasTagCompound())
			return 0;
		return stack.getTagCompound().getCompoundTag(getTagKey()).getInteger("EnergyExtractionRate");
	}

	@Override
	public int getEnergyMax(ItemStack stack) {
		if(stack == null)
			return 0;
		if(!stack.hasTagCompound())
			return 0;
		return stack.getTagCompound().getCompoundTag(getTagKey()).getInteger("EnergyMax");
	}
	
	@Override
	public int getEnergy(ItemStack stack) {
		if(stack == null)
			return 0;
		if(!stack.hasTagCompound())
			return 0;
		return stack.getTagCompound().getCompoundTag(getTagKey()).getInteger("Energy");
	}

	@Override
	public ModuleStack buildModule(ItemStack stack) {
		IMachinePartBattery batteryItem = (IMachinePartBattery) stack.getItem();
		Material[] materials = batteryItem.getMaterials(stack);
		MachineState state_0 = materials[1].getStats(Stats.MACHINE);
		MachineState state_1 = materials[3].getStats(Stats.MACHINE);
		int tier = (state_0.tier + state_1.tier) / 2;
		if(tier <= 0)
			tier = 1;
		return new ModuleStack<IModuleBattery>(stack, new ModuleBattery("BatteryModular", batteryItem.getEnergyMax(stack), batteryItem.getEnergyReceiveRate(stack), batteryItem.getEnergyExtractionRate(stack)), tier, false); 
	}

	@Override
	public Material getMaterial(ItemStack stack) {
		return getMaterials(stack)[2];
	}

}
