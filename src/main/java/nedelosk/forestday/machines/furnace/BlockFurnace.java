package nedelosk.forestday.machines.furnace;

import java.util.List;

import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.machines.brick.furnace.coke.TileCokeFurnace;
import nedelosk.forestday.common.machines.brick.furnace.fluidheater.TileFluidHeater;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFurnace extends BlockContainer {

	public BlockFurnace() {
		super(Material.iron);
		this.setCreativeTab(Tabs.tabForestdayBlocks);
		this.setBlockName("machine.furnace");
	}

	@SideOnly(Side.CLIENT)
	public IIcon furnaceCokeSide;
	@SideOnly(Side.CLIENT)
	public IIcon furnaceFluidHeaterSide;
	@SideOnly(Side.CLIENT)
	public IIcon furnaceTop;
	@SideOnly(Side.CLIENT)
	public IIcon furnaceBottom;

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float par7, float par8, float par9) {
		if (world.getTileEntity(x, y, z) instanceof TileCokeFurnace) {
			FMLNetworkHandler.openGui(player, Forestday.instance, 0, world, x,
					y, z);
		}
		if (world.getTileEntity(x, y, z) instanceof TileFluidHeater) {
			FMLNetworkHandler.openGui(player, Forestday.instance, 0, world, x,
					y, z);
		}

		return true;
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case 0:
			return new TileCokeFurnace();
		case 1:
			return new TileFluidHeater();
		default:
			return null;
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.furnaceBottom = register
				.registerIcon("forestday:walls/loam_brick");
		this.furnaceCokeSide = register
				.registerIcon("forestday:furnace_coke_side");
		this.furnaceFluidHeaterSide = register
				.registerIcon("forestday:furnace_fluid_heater_side");
		this.furnaceTop = register.registerIcon("forestday:furnace_coke_top");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta == 0) {
			switch (side) {
			case 0:
				return this.furnaceBottom;
			case 1:
				return this.furnaceTop;
			default:
				return this.furnaceCokeSide;
			}
		}
		if (meta == 1) {
			{
				switch (side) {
				case 0:
					return this.furnaceBottom;
				case 1:
					return this.furnaceTop;
				default:
					return this.furnaceFluidHeaterSide;
				}
			}
		} else {
			return null;
		}
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 2; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

}
