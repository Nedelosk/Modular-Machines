package de.nedelosk.forestmods.common.plugins.waila.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import de.nedelosk.forestmods.api.integration.IWailaState;
import de.nedelosk.forestmods.common.blocks.tile.TileModular;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ProviderModular implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileModular modular = (TileModular) accessor.getTileEntity();
		IWailaState data = new WailaData(accessor, config);
		return modular.getModular().getWailaProvider(modular).getWailaHead(itemStack, currenttip, data);
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileModular modular = (TileModular) accessor.getTileEntity();
		IWailaState data = new WailaData(accessor, config);
		return modular.getModular().getWailaProvider(modular).getWailaBody(itemStack, currenttip, data);
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileModular modular = (TileModular) accessor.getTileEntity();
		IWailaState data = new WailaData(accessor, config);
		return modular.getModular().getWailaProvider(modular).getWailaTail(itemStack, currenttip, data);
	}

	public static class WailaData implements IWailaState {

		private IWailaDataAccessor accessor;
		private IWailaConfigHandler config;

		public WailaData(IWailaDataAccessor accessor, IWailaConfigHandler config) {
			this.accessor = accessor;
			this.config = config;
		}

		@Override
		public World getWorld() {
			return accessor.getWorld();
		}

		@Override
		public EntityPlayer getPlayer() {
			return accessor.getPlayer();
		}

		@Override
		public Block getBlock() {
			return accessor.getBlock();
		}

		@Override
		public int getBlockID() {
			return accessor.getBlockID();
		}

		@Override
		public int getMetadata() {
			return accessor.getMetadata();
		}

		@Override
		public TileEntity getTileEntity() {
			return accessor.getTileEntity();
		}

		@Override
		public MovingObjectPosition getPosition() {
			return accessor.getPosition();
		}

		@Override
		public Vec3 getRenderingPosition() {
			return accessor.getRenderingPosition();
		}

		@Override
		public NBTTagCompound getNBTData() {
			return accessor.getNBTData();
		}

		@Override
		public int getNBTInteger(NBTTagCompound tag, String keyname) {
			return accessor.getNBTInteger(tag, keyname);
		}

		@Override
		public double getPartialFrame() {
			return accessor.getPartialFrame();
		}

		@Override
		public ForgeDirection getSide() {
			return accessor.getSide();
		}

		@Override
		public Set<String> getModuleNames() {
			return config.getModuleNames();
		}

		@Override
		public HashMap<String, String> getConfigKeys(String modName) {
			return config.getConfigKeys(modName);
		}

		@Override
		public boolean getConfig(String key, boolean defvalue) {
			return config.getConfig(key, defvalue);
		}

		@Override
		public boolean getConfig(String key) {
			return config.getConfig(key);
		}

		@Override
		public void setConfig(String key, boolean value) {
			config.setConfig(key, value);
		}
	}
}
