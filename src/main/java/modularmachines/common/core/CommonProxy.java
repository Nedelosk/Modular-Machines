package modularmachines.common.core;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;

import modularmachines.api.modules.IModule;

public class CommonProxy {
	
	public void preInit() {
		
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}
	
	public void playButtonClick() {
	
	}
	
	public void registerModuleModels() {
		
	}
	
	public List<String> addModuleInfo(ItemStack itemStack) {
		return Collections.emptyList();
	}
	
	public void registerStateMapper(Block block, IStateMapper mapper) {
	}
	
	public void registerFluidStateMapper(Block block, Fluid fluid) {
	}
	
	public void registerBlock(Block block) {
	}
	
	public void registerItem(Item item) {
	}
	
	public void addComponents(IModule module) {
	
	}
	
	public void markForModelUpdate(IModule module) {
	
	}
	
}
