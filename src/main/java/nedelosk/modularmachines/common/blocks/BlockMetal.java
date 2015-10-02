package nedelosk.modularmachines.common.blocks;

import java.util.List;

import nedelosk.modularmachines.api.modular.material.Material;
import nedelosk.modularmachines.api.modular.material.MaterialType;
import nedelosk.modularmachines.common.blocks.tile.TileMaterial;
import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.tabs.TabModularMachinesComponents;
import nedelosk.modularmachines.common.core.tabs.TabModularMachinesModules;
import nedelosk.modularmachines.common.machines.utils.MaterialManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMetal extends BlockContainer {

	public BlockMetal() {
		super(net.minecraft.block.material.Material.iron);
		setStepSound(soundTypeMetal);
		this.setCreativeTab(TabModularMachinesComponents.instance);
		setBlockName("metal.block");
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		blockIcon = IIconRegister.registerIcon("modularmachines:metal_block");
	}

	public int getRenderColor(ItemStack stack) {
        return MaterialManager.getMaterial(stack).primaryColor;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileMaterial){
			TileMaterial material = (TileMaterial) tile;
			ItemStack stack = new ItemStack(MMBlocks.Metal_Blocks.item(), 1, material.blockMetadata);
			MaterialManager.setMaterial(stack, material.material);
			return stack;
		}
		return super.getPickBlock(target, world, x, y, z, player);
	}
	
	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileMaterial){
			TileMaterial material = (TileMaterial) tile;
			return material.material.primaryColor;
		}
		return super.colorMultiplier(world, x, y, z);
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(item, 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null)
               		list.add(stack);
            }
        }
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileMaterial();
	}
	
}
