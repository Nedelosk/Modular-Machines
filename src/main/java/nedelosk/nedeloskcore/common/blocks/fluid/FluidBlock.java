package nedelosk.nedeloskcore.common.blocks.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBlock extends BlockFluidClassic {

	protected Fluid fluid;
	protected String fluidName;
	
	public FluidBlock(Fluid fluid, Material material, String fluidName) {
		super(fluid, material);
		this.fluid = fluid;
		this.fluidName = fluidName;
	}
    @SideOnly(Side.CLIENT)
    protected IIcon[] icons;
	
    @Override
    public IIcon getIcon(int side, int meta) {
      return side != 0 && side != 1 ? this.icons[1] : this.icons[0];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
      icons = new IIcon[] { iconRegister.registerIcon("forestday:fluids/" + this.fluidName + "_still"),
          iconRegister.registerIcon("forestday:fluids/" + this.fluidName + "_flow") };

      fluid.setIcons(icons[0], icons[1]);
    }
    
    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.canDisplace(world, x, y, z);
    }
    
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.displaceIfPossible(world, x, y, z);
    }
    
	@Override
    public String getUnlocalizedName()
    {
        return NRegistry.setUnlocalizedBlockName(fluidName, "nc");
    }
	
}
