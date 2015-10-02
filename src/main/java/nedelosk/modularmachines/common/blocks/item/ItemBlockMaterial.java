package nedelosk.modularmachines.common.blocks.item;

import nedelosk.modularmachines.api.modular.material.Material;
import nedelosk.modularmachines.common.blocks.tile.TileMaterial;
import nedelosk.modularmachines.common.machines.utils.MaterialManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBlockMaterial extends ItemBlock {

    public ItemBlockMaterial(Block p_i45328_1_) {
		super(p_i45328_1_);
	}
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Material materialM = MaterialManager.getMaterial(stack);
        if(materialM == null)
        	return super.getItemStackDisplayName(stack);

        if (StatCollector.canTranslate("tile.metal.block.name") && StatCollector.canTranslate("material." + materialM.identifier))
        {
            return materialM.localizedName() + " " + StatCollector.translateToLocal("tile.metal.block.name");
        }
        return super.getItemStackDisplayName(stack);
    }

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {

       if (!world.setBlock(x, y, z, field_150939_a, metadata, 3))
       {
           return false;
       }
       
       if(!(world.getTileEntity(x, y, z) instanceof TileMaterial)){
    	   world.setBlock(x, y, z, Blocks.air, metadata, 3);
    	   return false;
       }
       TileMaterial material = (TileMaterial) world.getTileEntity(x, y, z);
       material.setMaterial(MaterialManager.getMaterial(stack));

       if (world.getBlock(x, y, z) == field_150939_a)
       {
           field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
           field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
       }

       return true;
    }
	
}
