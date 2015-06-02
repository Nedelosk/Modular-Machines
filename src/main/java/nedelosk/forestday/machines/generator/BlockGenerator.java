package nedelosk.forestday.machines.generator;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.machines.brick.generator.heat.TileHeatGenerator;
import nedelosk.forestday.machines.blocks.BlockMachieneBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGenerator extends BlockMachieneBase{

	@SideOnly(Side.CLIENT)
	public IIcon generatorSide;
	@SideOnly(Side.CLIENT)
	public IIcon generatorTop;
	@SideOnly(Side.CLIENT)
	public IIcon generatorBottom;
	
	public BlockGenerator() {
		super(Material.ground);
		this.setStepSound(soundTypeStone);
		this.setCreativeTab(Tabs.tabForestdayBlocks);
		this.setBlockName("machine.generator");
		this.setHardness(3);
		this.setHarvestLevel("pickaxe", 1, 1);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		switch (meta) {
		case 0:
			return new TileHeatGenerator();
		default:
			return null;
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 1; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
    
    
    @Override
    public void registerBlockIcons(IIconRegister register) {
    	this.generatorBottom = register.registerIcon("forestday:walls/loam_brick");
    	this.generatorSide = register.registerIcon("forestday:generator_burning_side");
    	this.generatorTop = register.registerIcon("forestday:generator_burning_top");
    }
    
    @Override
    public IIcon getIcon(int side, int meta) {
    	switch (side) {
		case 0:
			return this.generatorBottom;
		case 1:
			return this.generatorTop;
		default:
			return this.generatorSide;
		}
    }

}
