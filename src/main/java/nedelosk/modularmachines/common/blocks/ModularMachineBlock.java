package nedelosk.modularmachines.common.blocks;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.utils.WorldUtil;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ModularMachineBlock extends ModularBlock {

	public ModularMachineBlock() {
		super(Material.iron);
		setStepSound(soundTypeMetal);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setBlockName("modular");
		setBlockTextureName("iron_block");
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileModular();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		player.openGui(ModularMachines.instance, 0, player.worldObj, x, y, z);
		return true;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs ptab, List list) {
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IModularTileEntity) {
			IModularTileEntity modular = (IModularTileEntity) tile;
			EntityPlayer player = WorldUtil.getPlayer(world, modular.getOwner());
			if (player == null || !player.capabilities.isCreativeMode) {
				ItemStack stack = new ItemStack(block, 1, meta);
				NBTTagCompound nbtTag = new NBTTagCompound();
				modular.writeToNBT(nbtTag);
				stack.setTagCompound(nbtTag);
				WorldUtil.dropItem(world, x, y, z, stack);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		ItemStack stack = new ItemStack(this);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IModularTileEntity) {
			IModularTileEntity modular = (IModularTileEntity) tile;
			NBTTagCompound nbtTag = new NBTTagCompound();
			modular.writeToNBT(nbtTag);
			stack.setTagCompound(nbtTag);
		}
		return stack;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		int heading = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		IModularTileEntity tile = (IModularTileEntity) world.getTileEntity(x, y, z);
		tile.setFacing(getFacingForHeading(heading));
		if (world.isRemote) {
			return;
		}
		EntityPlayer player = (EntityPlayer) entity;
		tile.setOwner(player.getGameProfile());
		world.markBlockForUpdate(x, y, z);
	}

	protected short getFacingForHeading(int heading) {
		switch (heading) {
		case 0:
			return 2;
		case 1:
			return 5;
		case 2:
			return 3;
		case 3:
		default:
			return 4;
		}
	}

}
