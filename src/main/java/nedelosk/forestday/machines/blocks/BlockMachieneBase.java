package nedelosk.forestday.machines.blocks;

import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.network.GuiHandler;
import nedelosk.forestday.common.registrys.ForestdayRegistry;
import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockMachieneBase extends BlockContainer {

	protected BlockMachieneBase(Material mat, String name) {
		super(mat);
		this.name = name;
		this.setCreativeTab(Tabs.tabForestdayBlocks);
		this.setHardness(1.0F);
	}
	
	protected BlockMachieneBase(Material mat) {
		super(mat);
		this.setCreativeTab(Tabs.tabForestdayBlocks);
	}
	
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	private String name;
	
	@Override
    public String getUnlocalizedName()
    {
		if(name != null)
		{
        return ForestdayRegistry.setUnlocalizedBlockName(this.name);
		}
		return super.getUnlocalizedName();
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		player.openGui(Forestday.instance, 0, player.worldObj, x, y, z);	
	  return true;
	}

}
