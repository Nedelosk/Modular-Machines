package de.nedelosk.forestmods.api.integration;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IWailaState {

	World getWorld();

	EntityPlayer getPlayer();

	Block getBlock();

	int getBlockID();

	int getMetadata();

	TileEntity getTileEntity();

	MovingObjectPosition getPosition();

	Vec3 getRenderingPosition();

	NBTTagCompound getNBTData();

	int getNBTInteger(NBTTagCompound tag, String keyname);

	double getPartialFrame();

	ForgeDirection getSide();

	Set<String> getModuleNames();

	HashMap<String, String> getConfigKeys(String modName);

	boolean getConfig(String key, boolean defvalue);

	boolean getConfig(String key);

	void setConfig(String key, boolean value);
}
