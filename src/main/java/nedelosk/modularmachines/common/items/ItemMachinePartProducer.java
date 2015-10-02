package nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modular.material.Material;
import nedelosk.modularmachines.api.modular.material.Tags;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import nedelosk.modularmachines.api.modular.module.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.parts.IMachinePartProducer;
import nedelosk.modularmachines.api.modular.parts.PartType;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.machines.utils.MaterialManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class ItemMachinePartProducer extends ItemMachinePart implements IMachinePartProducer {
	
	public String moduleName;
	public Object[] components;
	
	public ItemMachinePartProducer(Object[] types, String name, String moduleName) {
		super(null, name);
		this.moduleName = moduleName;
		components = types;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
	    for(int i = 0;i < 3;i++) {
	        if(!head.hasStats(Tags.TAG_MACHINE))
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
	public ModuleStack buildModule(ItemStack stack) {
		return ModularManager.moduleFactory.createModule(stack, moduleName);
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
	public ItemStack buildItemFromStacks(ItemStack[] stacks) {
	    List<Material> materials = new ArrayList<Material>(stacks.length);

	    if(stacks.length != requiredComponents.length) {
	      return null;
	    }

	    for(int i = 0; i < stacks.length; i++) {
	    	ItemStack stack = stacks[i];
	    	if(!validComponent(i, stacks[i])) {
	    		return null;
	      }

	      materials.add(MaterialManager.getMaterial(stacks[i]));
	    }

	    return buildItem(materials);
	}
	
	@Override
	public ItemStack buildItem(List<Material> materials) {
		ItemStack tool = new ItemStack(this);
		tool.setTagCompound(buildItemNBT(materials));

		return tool;
	}

	@Override
	public boolean validComponent(int slot, ItemStack stack) {
	    if(slot > requiredComponents.length || slot < 0) {
	        return false;
	    }
	    if(components[slot] instanceof ItemStack){
	    	ItemStack  stackC = (ItemStack) components[slot];
	    	if(stackC.getItem() == stack.getItem() && stackC.getItemDamage() == stack.getItemDamage() && ItemStack.areItemStackTagsEqual(stack, stack))
	    		return true;
	    }

	    return requiredComponents[slot].isValid(stack);
	}
	
	@Override
	public NBTTagCompound buildItemNBT(List<Material> materials) {
		NBTTagCompound basetag = new NBTTagCompound();

		basetag.setTag(Tags.TAG_BASE, buildTag(materials));
		basetag.setTag(Tags.TAG_MACHINE, buildTag(materials));
		basetag.setTag(getTagKey(), buildData(materials));

		return basetag;
	}
	
	public NBTTagCompound buildModuleTag(){
		return null;
	}
	
	@Override
	public NBTTagCompound buildTag(List<Material> materials){
	    NBTTagCompound base = new NBTTagCompound();
	    NBTTagList materialList = new NBTTagList();

	    for(Material material : materials) {
	      materialList.appendTag(new NBTTagString(material.identifier));
	    }

	    base.setTag(Tags.TAG_MATERIALS, materialList);

	    return base;
	}

}
