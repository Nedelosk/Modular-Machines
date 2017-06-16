package modularmachines.common.core;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.fluids.Fluid;

import net.minecraftforge.fml.client.FMLClientHandler;

public class CommonProxy {

	public void preInit() {
		
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}
	
	public World getRenderWorld() {
		throw new IllegalStateException("Cannot get render world on server");
	}

	public Minecraft getClientInstance() {
		return FMLClientHandler.instance().getClient();
	}

	public EntityPlayer getPlayer() {
		throw new IllegalStateException("Can't call getPlayer on the server");
	}

	public void playButtonClick() {

	}
	
	public void loadModuleModels(){
		
	}
	
	public List<String> addModuleInfo(ItemStack itemStack) {
		return Collections.emptyList();
	}
	
	public void onAssemblerGuiChange(){
	}

	public void registerStateMapper(Block block, IStateMapper mapper) {
	}

	public void registerFluidStateMapper(Block block, Fluid fluid) {
	}

	public void registerBlock(Block block) {
	}

	public void registerItem(Item item) {
	}
	
}
