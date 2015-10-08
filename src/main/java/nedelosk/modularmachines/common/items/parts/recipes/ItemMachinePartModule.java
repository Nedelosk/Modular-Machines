package nedelosk.modularmachines.common.items.parts.recipes;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.materials.Tags;
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

public class ItemMachinePartModule extends ItemMachinePart {
	
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
	public ItemStack getMachine(ItemStack stack) {
		List<Material> materials = Lists.newArrayList();
		materials.add(MMRegistry.Copper);
		materials.add(MMRegistry.Copper);
		materials.add(MMRegistry.Iron);
		materials.add(MMRegistry.Copper);
		materials.add(MMRegistry.Copper);
		return buildItem(materials);
	}

	@Override
	public NBTTagCompound buildData(List<Material> materials) {
		return new NBTTagCompound();
	}
	
	@Override
	public Material getMaterial(ItemStack stack) {
		return getMaterials(stack)[2];
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
