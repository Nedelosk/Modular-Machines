package nedelosk.modularmachines.common.items.parts;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.materials.Stats;
import nedelosk.modularmachines.api.materials.Tags;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.IMachinePartEngine;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.api.parts.PartType.MachinePartType;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.materials.MachineState;
import nedelosk.modularmachines.common.modular.module.basic.energy.ModuleEngine;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMachinePartEngine extends ItemMachinePart implements IMachinePartEngine{

	public ItemMachinePartEngine(String name) {
		super(new PartType[]{ new MachinePartType(ItemRegistry.Rods),
							  new MachinePartType(ItemRegistry.Plates),
							  new MachinePartType(ItemRegistry.Plates) }, name);
	}
	
	@Override
	public ItemStack getMachine(ItemStack stack) {
		List<Material> materials = new ArrayList();
		materials.add(MMRegistry.Steel);
		materials.add(MMRegistry.Bronze);
		materials.add(MMRegistry.Bronze);
		return buildItem(materials);
	}

	@Override
	public NBTTagCompound buildData(List<Material> materials) {
		MachineState state_0 = materials.get(0).getStats(Stats.MACHINE);
		MachineState state_1 = materials.get(1).getStats(Stats.MACHINE);
		MachineState state_2 = materials.get(2).getStats(Stats.MACHINE);
		NBTTagCompound nbtTag = new NBTTagCompound();
		
		int engineSpeed = (state_0.engineSpeed + state_1.engineSpeed + state_2.engineSpeed) / 3;
		
		nbtTag.setIntArray("Speed", new int[]{ engineSpeed, engineSpeed / 2, engineSpeed / 3});
		
		return nbtTag;
	}
	
	@Override
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List subItems) {
	    for(Material head : MMRegistry.materials) {
	        if(!head.hasStats(Tags.TAG_MACHINE))
	        	continue;
	        if(head.type == MaterialType.CRYTAL || head.type == MaterialType.STONE || head.type == MaterialType.WOOD || head.type == MaterialType.CUSTOM)
	        	continue;
	        List<Material> mats = new ArrayList<Material>(requiredComponents.length);

	        for(int i = 0; i < requiredComponents.length; i++) {
	        	mats.add(head);
	        }

	        ItemStack tool = buildItem(mats);
	        subItems.add(tool);
	      }
	}

	@Override
	public int[] getEngineSpeed(ItemStack stack) {
		if(stack == null)
			return null;
		if(!stack.hasTagCompound())
			return null;
		return stack.getTagCompound().getCompoundTag(getTagKey()).getIntArray("Speed");
	}

	@Override
	public ModuleStack buildModule(ItemStack stack) {
		IMachinePartEngine engine = (IMachinePartEngine) stack.getItem();
		Material[] materials = engine.getMaterials(stack);
		MachineState state_0 = materials[1].getStats(Stats.MACHINE);
		MachineState state_1 = materials[2].getStats(Stats.MACHINE);
		int tier = (state_0.tier + state_1.tier) / 2;
		if(tier <= 0)
			tier = 1;
		return new ModuleStack<IModuleEngine>(stack, new ModuleEngine("EngineModular", engine.getEngineSpeed(stack)), tier, false);
	}
	
	@Override
	public Material getMaterial(ItemStack stack) {
		return getMaterials(stack)[1];
	}

}
