package nedelosk.modularmachines.common.items.parts.recipes;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.materials.Tags;
import nedelosk.modularmachines.api.materials.stats.MachineState;
import nedelosk.modularmachines.api.materials.stats.Stats;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.api.parts.PartType.MachinePartType;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.items.parts.ItemMachinePart;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMachinePartBurningChamber extends ItemMachinePart {

	public ItemMachinePartBurningChamber(String name) {
		super(new PartType[]{ new MachinePartType(ItemRegistry.Plates),
							  new MachinePartType(ItemRegistry.Plates),
							  new MachinePartType(ItemRegistry.Plates),
							  new MachinePartType(ItemRegistry.Plates),
							  new MachinePartType(ItemRegistry.Plates), 
							  new MachinePartType(ItemRegistry.Plates), 
							  new MachinePartType(ItemRegistry.Plates),
							  new MachinePartType(ItemRegistry.Plates) }, name);
	}

	@Override
	public ItemStack getMachine(ItemStack stack) {
		List<Material> materials = new ArrayList();
		materials.add(MMRegistry.Iron);
		materials.add(MMRegistry.Iron);
		materials.add(MMRegistry.Iron);
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
		MachineState state_5 = materials.get(5).getStats(Stats.MACHINE);
		MachineState state_6 = materials.get(6).getStats(Stats.MACHINE);
		MachineState state_7 = materials.get(7).getStats(Stats.MACHINE);
		int tier = state_0.tier() + state_1.tier() + state_2.tier() + state_3.tier() + state_4.tier() + state_5.tier() + state_6.tier() + state_7.tier();
		nbtTag.setInteger("Tier", tier / 8);
		return nbtTag;
	}

	@Override
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List subItems) {
	    for(Material head : MMRegistry.materials) {
	        if(!head.hasStats(Tags.TAG_MACHINE))
	        	continue;
	        if(head.type == MaterialType.CRYTAL || head.type == MaterialType.STONE || head.type == MaterialType.WOOD || head.type == MaterialType.CUSTOM || head.type == MaterialType.PLACE_HOLDER)
	        	continue;
	        List<Material> mats = new ArrayList<Material>(requiredComponents.length);

	        for(int i = 0; i < requiredComponents.length; i++) {
	        	mats.add(head);
	        }

	        ItemStack tool = buildItem(mats);
	        subItems.add(tool);
	      }
	}
	
}
