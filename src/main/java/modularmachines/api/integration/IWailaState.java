package modularmachines.api.integration;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import modularmachines.api.modules.state.IModuleState;

public interface IWailaState {

	// Config
	Set<String> getModuleNames();

	HashMap<String, String> getConfigKeys(String modName);

	boolean getConfig(String key, boolean defvalue);

	boolean getConfig(String key);

	// Accessor
	World getWorld();

	EntityPlayer getPlayer();

	Block getBlock();

	int getMetadata();

	IBlockState getBlockState();

	TileEntity getTileEntity();

	@Nullable
	IModuleState getState();

	RayTraceResult getMOP();

	BlockPos getPosition();

	Vec3d getRenderingPosition();

	NBTTagCompound getNBTData();

	int getNBTInteger(NBTTagCompound tag, String keyname);

	double getPartialFrame();

	EnumFacing getSide();

	ItemStack getStack();

	void setState(IModuleState state);
}
