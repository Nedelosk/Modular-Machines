package nedelosk.modularmachines.common.items.parts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.materials.Tags;
import nedelosk.modularmachines.api.modular.module.producer.producer.IModuleProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.parts.IMachine;
import nedelosk.modularmachines.api.parts.IMachinePart;
import nedelosk.modularmachines.api.parts.IMachinePartProducer;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.modular.utils.MaterialManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemMachinePartProducer extends ItemMachinePart implements IMachinePartProducer {
	
	public HashMap<Integer, String> modules = Maps.newHashMap();
	public int modulesIDs;
	public PartType[][] requiredComponents;
	
	public ItemMachinePartProducer() {
		super(null, "Producer");
	}
	
	public void addModule(String moduleName){
		if(!modules.values().contains(moduleName)){
			modules.put(modulesIDs, moduleName);
			modulesIDs++;
		}
	}
	
	@Override
	public PartType[][] getProducerComponents() {
		return requiredComponents;
	}
	
	@Override
	public HashMap<Integer, String> getModules() {
		return modules;
	}
	
	@Override
	public int getModulesIDs() {
		return modulesIDs;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		int b = 0;
		for(Entry<Integer, String> entry : modules.entrySet()){
			b = entry.getKey();
		    for(Material head : MMRegistry.materials) {
		        if(!head.hasStats(Tags.TAG_MACHINE))
		        	continue;
		        if(head.type == MaterialType.CUSTOM || head.type == MaterialType.CRYTAL)
		        	continue;
		        if(requiredComponents == null){
		        	requiredComponents = new PartType[modules.size()][3];
		        }
				if(requiredComponents[b][0] == null){
					IModuleProducer producer = ModuleRegistry.moduleFactory.createModule(entry.getValue());
					requiredComponents[b] = producer.getRequiredComponents();
				}
		        
		        int a = 0;
		        for(PartType type : requiredComponents[b]){
		        	if(type != null){
			        	Iterator<IMachine> iterator = type.neededPart.iterator();
			        	while(iterator.hasNext()){
			        		IMachine m = iterator.next();
			        		if(m instanceof IMachinePart){
			        			a+=((IMachinePart) m).getMachineComponents().length + 1;
			        		}else{
			        			a+=1;
			        		}
			        	}
		        	}
		        }
		        List<Material> mats = new ArrayList<Material>(a);
		        for(int i = 0; i < a; i++) {
		          mats.add(head);
		        }
	
		        ItemStack tool = buildItem(entry.getKey(), mats);
		        subItems.add(tool);
		    }
		}
	}
	
	@Override
	public Material getMaterial(ItemStack stack) {
		return null;
	}
	
	@Override
	public ModuleStack buildModule(ItemStack stack) {
		IModuleProducer producer = ModuleRegistry.moduleFactory.createModule(modules.get(stack.getTagCompound().getInteger("ID")));
		return producer.creatModule(stack);
	}
	
	@Override
	public boolean validComponent(int id, int slot, ItemStack stack) {
	    if(slot > requiredComponents.length || slot < 0) {
	        return false;
	    }

	    return requiredComponents[id][slot].isValid(stack);
	}
	
	@Override
	public ItemStack buildItemFromStacks(ItemStack[] stacks) {
		for(Entry<Integer, String> entry : modules.entrySet()){
			List<Material> materials = Lists.newArrayList();
			List<Material> partMaterials = Lists.newArrayList();
			List<Material> moduleMaterials = Lists.newArrayList();
	
	        if(requiredComponents == null){
	        	requiredComponents = new PartType[modules.size()][16];
	        }
			if(requiredComponents[entry.getKey()][0] == null){
				IModuleProducer producer = ModuleRegistry.moduleFactory.createModule(entry.getValue());
				requiredComponents[entry.getKey()] = producer.getRequiredComponents();
			}
			
		    if(stacks.length != requiredComponents[entry.getKey()].length || stacks[0] == null) {
		      continue;
		    }
	
		    for(int i = 0; i < stacks.length; i++) {
		    	ItemStack stack = stacks[i];
		    	if(!validComponent(entry.getKey(), i, stack)) {
		    		continue;
		    	}
		    	if(stack.getItem() instanceof ItemMachinePartModule){
		    		for(Material mat : ((ItemMachinePartModule)stack.getItem()).getMaterials(stack))
		    			moduleMaterials.add(mat);
		    		materials.add(MaterialManager.getMaterial(stack));
		    	}
		    	else if(stack.getItem() instanceof ItemMachinePart){
		    		for(Material mat : ((ItemMachinePart)stack.getItem()).getMaterials(stack))
		    			partMaterials.add(mat);
		    		materials.add(MaterialManager.getMaterial(stack));
		    	}else{
		    		materials.add(MaterialManager.getMaterial(stack));
		    	}
		    }
		    for(Material mat : moduleMaterials)
		    	materials.add(mat);
		    for(Material mat : partMaterials)
		    	materials.add(mat);
	
		    if(buildItem(entry.getKey(), materials) == null)
		    	continue;
		    return buildItem(entry.getKey(), materials);
		}
		return null;
	}
	
	@Override
	public ItemStack buildItem(int id, List<Material> materials) {
		ItemStack tool = new ItemStack(this);
		if(buildItemNBT(id, materials) == null)
			return null;
		tool.setTagCompound(buildItemNBT(id, materials));

		return tool;
	}
	
	@Override
	public NBTTagCompound buildItemNBT(int id, List<Material> materials) {
		NBTTagCompound basetag = new NBTTagCompound();
		List<Material> baseMaterials = Lists.newArrayList();
		List<Material> moduleMaterials = Lists.newArrayList();
		List<Material> partsMaterials = Lists.newArrayList();
		int i;
		for(i = 0;i < requiredComponents[id].length;i++){
			if(i == materials.size())
				return null;
			baseMaterials.add(materials.get(i));
		}
		for(;i < requiredComponents[id].length + 5;i++){
			if(i == materials.size())
				return null;
			moduleMaterials.add(materials.get(i));
		}
		for(;i < materials.size();i++){
			if(i == materials.size())
				return null;
			partsMaterials.add(materials.get(i));
		}
		basetag.setTag(Tags.TAG_BASE, buildTag(baseMaterials));
		basetag.setTag(Tags.TAG_MODULE, buildTag(moduleMaterials));
		basetag.setTag(Tags.TAG_PARTS, buildTag(partsMaterials));
		basetag.setInteger("ID", id);

		return basetag;
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass > 4)
			if(pass == 6){
				IModuleProducer producer = ModuleRegistry.moduleFactory.createModule(modules.get(stack.getTagCompound().getInteger("ID")));
				return producer.getColor();
			}
			else{
				return 16777215;
			}
		else{
			return getModuleMaterials(stack)[pass].primaryColor;
		}
	}

	@Override
	public ItemStack getMachine(ItemStack stack) {
        int a = 0;
        for(PartType type : requiredComponents[0]){
        	Iterator<IMachine> iterator = type.neededPart.iterator();
        	while(iterator.hasNext()){
        		IMachine m = iterator.next();
        		if(m instanceof IMachinePart){
        			a+=((IMachinePart) m).getMachineComponents().length;
        		}else{
        			a+=1;
        		}
        	}
        }
        List<Material> mats = new ArrayList<Material>(a);
        for(int i = 0; i < a; i++) {
	          mats.add(MMRegistry.Copper);
	    }
		return buildItem(mats);
	}

	@Override
	public NBTTagCompound buildData(List<Material> materials) {
		return null;
	}
	
	@Override
	public Material[] getModuleMaterials(ItemStack stack) {
		List<Material> listMaterial = new ArrayList();
		NBTTagList list = stack.getTagCompound().getCompoundTag(Tags.TAG_MODULE).getTagList(Tags.TAG_MATERIALS, 8);
		for(int i = 0;i < list.tagCount();i++){
			String identifier = list.getStringTagAt(i);
			for(Material material : MMRegistry.materials){
				if(material.identifier.equals(identifier)){
					listMaterial.add(material);
					continue;
				}
			}
		}
		return listMaterial.toArray(new Material[listMaterial.size()]);
	}

}
