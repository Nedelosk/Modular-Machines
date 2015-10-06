package nedelosk.modularmachines.common.blocks;

import java.util.List;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.common.blocks.tile.TileMaterial;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.TabModularMachines;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import nedelosk.modularmachines.common.modular.utils.MaterialManager;
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
		this.setCreativeTab(TabModularMachines.components);
		setBlockName("metal.block");
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		blockIcon = IIconRegister.registerIcon("modularmachines:metal_block");
	}

	public int getRenderColor(ItemStack stack) {
		if(MaterialManager.getMaterial(stack) != null)
			return MaterialManager.getMaterial(stack).primaryColor;
		else
			return 16777215;
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileMaterial){
			TileMaterial material = (TileMaterial) tile;
			ItemStack stack = new ItemStack(MMBlockManager.Metal_Blocks.item(), 1, material.blockMetadata);
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
