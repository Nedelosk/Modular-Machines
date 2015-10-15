package nedelosk.modularmachines.api.modular.module.basic.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import akka.japi.Pair;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.tier.Tiers;
import nedelosk.modularmachines.api.modular.tier.Tiers.Tier;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class Module implements IModule{
	
	protected String moduleModifier;
	public ArrayList<Tier> tiers = Lists.newArrayList();
	public HashMap<Tier, String> tierModifiers = Maps.newHashMap();
	
	public Module() {
	}
	
	public Module(String moduleModifier) {
		this.moduleModifier = moduleModifier;
	}
	
	public Module(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		readFromNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Modifier"))
			this.moduleModifier = nbt.getString("Modifier");
		NBTTagList list = nbt.getTagList("TierModifier", 10);
		for(int i = 0;i < list.tagCount();i++){
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			String modifier = nbtTag.getString("Modifier");
			String name = nbtTag.getString("Name");
			tierModifiers.put(Tiers.getTier(name), modifier);
		}
	}

	@Override
	public void update(IModular modular, ModuleStack stack) {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if(moduleModifier != null)
			nbt.setString("Modifier", moduleModifier);
		NBTTagList list = new NBTTagList();
		for(Entry<Tier, String> entry : tierModifiers.entrySet()){
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setString("Modifier", entry.getValue());
			nbtTag.setString("Name", entry.getKey().getName());
			list.appendTag(nbtTag);
		}
		nbt.setTag("TierModifier", list);
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
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return null;
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return null;
	}

	public void addTier(Tier tier){
		tiers.add(tier);
	}
	
	public void addTier(Tier tier, String modifier){
		addTier(tier);
		tierModifiers.put(tier, modifier);
	}
	
	@Override
	public ArrayList<Tier> getTiers() {
		return tiers;
	}
	
}
