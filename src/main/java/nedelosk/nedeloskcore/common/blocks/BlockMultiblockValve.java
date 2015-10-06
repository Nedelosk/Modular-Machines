package nedelosk.nedeloskcore.common.blocks;

import java.util.List;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.multiblock.MultiblockModifierValveType.ValveType;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockValve;
import nedelosk.nedeloskcore.common.core.registry.NCBlockManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMultiblockValve extends BlockContainerForest {
	
	public IIcon[] blockIcon;
	public static int renderPass;
	public static ValveType valveType;
	public int renderType = 0;
	
	public BlockMultiblockValve() {
		super(net.minecraft.block.material.Material.ground, CreativeTabs.tabBlock);
		setBlockName("multiblock_valve");
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			renderType = RenderingRegistry.getNextAvailableRenderId();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileMultiblockBase)
		{
			TileMultiblockBase multiblock = (TileMultiblockBase) tile;
			if(multiblock.master != null && multiblock.master.isMultiblock() && multiblock.master.getMultiblock() != null)
			{
				multiblock.master.getMultiblock().onBlockActivated(world, x, y, z, player, side);
				if(((TileMultiblockBase) tile).getContainer(player.inventory) != null || multiblock.master.getMultiblock().hasBlockActivatedFunction())
					return true;
			}
		}
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileMultiblockBase)
		{
			((TileMultiblockBase)tile).onBlockRemoval();
		}
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}
	
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMultiblockBase)
            ((TileMultiblockBase) tile).onBlockAdded();
    }
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		blockIcon = new IIcon[3];
		blockIcon[0] = IIconRegister.registerIcon("nedeloskcore:multiblocks/valve");
		blockIcon[1] = IIconRegister.registerIcon("nedeloskcore:multiblocks/valve_input");
		blockIcon[2] = IIconRegister.registerIcon("nedeloskcore:multiblocks/valve_output");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(renderPass == 0)
		{
			return NCBlockManager.Multiblock.block().getIcon(side, meta);
		}
		return blockIcon[valveType.ordinal()];
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0;i < NCoreApi.getMaterials().size();i++)
			if(!NCoreApi.getMaterials().get(i).oreDict.equals("woodOak"))
				list.add(new ItemStack(item, 1, i));
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return NCoreApi.getMaterials().get(world.getBlockMetadata(x, y, z)).hardness;
	}
	
	@Override
	public int getRenderType() {
		return renderType;
	}
	
	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return NCoreApi.getMaterials().get(world.getBlockMetadata(x, y, z)).resistance / 5.0F;
		
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileMultiblockValve(NCoreApi.getMaterials().get(meta));
	}

}
