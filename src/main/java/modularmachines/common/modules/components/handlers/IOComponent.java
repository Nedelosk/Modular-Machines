package modularmachines.common.modules.components.handlers;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;

import modularmachines.api.IIOConfigurable;
import modularmachines.api.IOMode;
import modularmachines.api.IScrewdriver;
import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.block.IInteractionComponent;
import modularmachines.api.modules.components.handlers.IIOComponent;
import modularmachines.common.modules.components.ModuleComponent;
import modularmachines.common.utils.TickHelper;

public class IOComponent extends ModuleComponent implements IIOComponent, IInteractionComponent, INBTWritable,
		INBTReadable, INetworkComponent, ITickable {
	private final Map<EnumFacing, IOMode> faceModes;
	private final TickHelper tickHelper = new TickHelper();
	private IOMode mode;
	
	public IOComponent() {
		this.faceModes = new EnumMap<>(EnumFacing.class);
		this.mode = IOMode.NONE;
		for (EnumFacing facing : EnumFacing.VALUES) {
			faceModes.put(facing, IOMode.NONE);
		}
	}
	
	@Override
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
		if (heldItem.getItem() instanceof IScrewdriver) {
			IScrewdriver screwdriver = (IScrewdriver) heldItem.getItem();
			if (player.isSneaking()) {
				clearAllModes();
				if (!player.world.isRemote) {
					player.sendStatusMessage(new TextComponentTranslation("mm.message.screwdriver.reset", new TextComponentTranslation(IOMode.NONE.getUnlocalizedName())), true);
				}
				return true;
			}
			EnumFacing facing = screwdriver.getSelectedFacing(heldItem);
			IOMode newMode;
			if (facing == null) {
				newMode = IOMode.getNext(mode);
			} else {
				newMode = IOMode.getNext(faceModes.get(facing));
			}
			setMode(facing, newMode);
			if (!player.world.isRemote) {
				player.sendStatusMessage(new TextComponentTranslation("mm.message.screwdriver.module", new TextComponentTranslation(newMode.getUnlocalizedName())), true);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void update() {
		tickHelper.onTick();
		if (!tickHelper.updateOnInterval(5)) {
			return;
		}
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (supportsMode(IOMode.PULL, facing)) {
				doPull(facing);
			}
			if (supportsMode(IOMode.PUSH, facing)) {
				doPush(facing);
			}
		}
	}
	
	@Override
	public void doPull(EnumFacing facing) {
		provider.getComponents(IIOConfigurable.class).stream().filter(c -> c != this).forEach(c -> c.doPull(facing));
	}
	
	@Override
	public void doPush(EnumFacing facing) {
		provider.getComponents(IIOConfigurable.class).stream().filter(c -> c != this).forEach(c -> c.doPush(facing));
	}
	
	@Override
	public IOMode getMode(@Nullable EnumFacing facing) {
		if (facing == null) {
			return mode;
		}
		return faceModes.get(facing);
	}
	
	@Override
	public void setMode(@Nullable EnumFacing facing, IOMode mode) {
		if (facing == null) {
			this.mode = mode;
		} else {
			faceModes.put(facing, mode);
		}
		provider.sendToClient();
		provider.getContainer().getLocatable().markForNotifyNeighbours();
	}
	
	@Override
	public void clearAllModes() {
		this.mode = IOMode.NONE;
		for (EnumFacing facing : EnumFacing.VALUES) {
			faceModes.put(facing, IOMode.NONE);
		}
		provider.getContainer().getLocatable().markForNotifyNeighbours();
		provider.sendToClient();
	}
	
	@Override
	public boolean supportsMode(IOMode mode, @Nullable EnumFacing facing) {
		IOMode componentMode = getMode(facing);
		if (componentMode == IOMode.DISABLED) {
			return false;
		}
		if (facing != null && componentMode == IOMode.NONE) {
			componentMode = getMode(null);
		}
		switch (mode) {
			case DISABLED:
				return false;
			case INPUT:
				return componentMode.acceptsInput();
			case OUTPUT:
				return componentMode.acceptsOutput();
			case PUSH:
				return componentMode.pushes();
			case PULL:
				return componentMode.pulls();
			case NONE:
			case PUSH_PULL:
				return componentMode == mode;
		}
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList tagList = compound.getTagList("Facings", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte facing = tagCompound.getByte("Facing");
			byte mode = tagCompound.getByte("Mode");
			faceModes.put(EnumFacing.getFront(facing), IOMode.get(mode));
		}
		mode = IOMode.get(compound.getByte("Mode"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList tagList = new NBTTagList();
		for (Map.Entry<EnumFacing, IOMode> entry : faceModes.entrySet()) {
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setByte("Facing", (byte) entry.getKey().ordinal());
			tagCompound.setByte("Mode", (byte) entry.getValue().ordinal());
			tagList.appendTag(tagCompound);
		}
		compound.setTag("Facings", tagList);
		compound.setByte("Mode", (byte) mode.ordinal());
		return compound;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
		for (Map.Entry<EnumFacing, IOMode> entry : faceModes.entrySet()) {
			data.writeByte(entry.getValue().ordinal());
		}
		data.writeByte(mode.ordinal());
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		for (EnumFacing facing : EnumFacing.VALUES) {
			faceModes.put(facing, IOMode.get(data.readByte()));
		}
		mode = IOMode.get(data.readByte());
	}
}
