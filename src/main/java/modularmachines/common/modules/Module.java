package modularmachines.common.modules;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.components.IItemCreationListener;
import modularmachines.api.modules.components.IModuleComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.events.Events;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketUpdateModule;
import modularmachines.common.utils.components.ComponentProvider;

public final class Module extends ComponentProvider<IModuleComponent> implements IModule {
	private final IModulePosition position;
	private final IModuleHandler parent;
	private final IModuleContainer container;
	private final int index;
	private final IModuleData data;
	private final ItemStack itemStack;
	@Nullable
	private EnumFacing facing = null;
	
	public Module(IModuleHandler parent, IModulePosition position, IModuleData data, ItemStack parentItem) {
		this.parent = parent;
		this.container = parent.getProvider().getContainer();
		this.position = position;
		this.itemStack = parentItem;
		this.data = data;
		this.facing = getFacing();
		this.container.registerListener(Events.FacingChangeEvent.class, e -> facing = null);
		this.index = this.container.generateIndex(this);
	}
	
	public Module(IModuleHandler parent, IModuleData moduleData, IModulePosition position, NBTTagCompound compound) {
		this.parent = parent;
		this.container = parent.getProvider().getContainer();
		this.position = position;
		this.facing = getFacing();
		this.data = moduleData;
		this.index = this.container.generateIndex(this);
		this.itemStack = new ItemStack(compound.getCompoundTag("Parent"));
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("Parent", itemStack.serializeNBT());
		return compound;
	}
	
	public IModuleHandler getHandler() {
		return parent;
	}
	
	@Override
	public IModuleProvider getProvider() {
		return parent.getProvider();
	}
	
	public IModuleContainer getContainer() {
		return container;
	}
	
	public IModulePosition getPosition() {
		return position;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	@Override
	public ItemStack createItem() {
		ItemStack itemStack = this.itemStack.copy();
		for (IItemCreationListener listener : getComponents(IItemCreationListener.class)) {
			itemStack = listener.createItem(itemStack);
		}
		return itemStack;
	}
	
	public int getIndex() {
		return index;
	}
	
	public IModuleData getData() {
		return data;
	}
	
	public EnumFacing getSide(EnumFacing side) {
		float rotation = getFacingRotation() + side.getHorizontalAngle();
		return EnumFacing.fromAngle(rotation);
	}
	
	public boolean isFacing(@Nullable EnumFacing side) {
		return side != null && getFacing() == side;
	}
	
	public EnumFacing getFacing() {
		if (facing != null) {
			return facing;
		}
		return facing = EnumFacing.fromAngle(getFacingRotation());
	}
	
	private float getFacingRotation() {
		if (facing != null) {
			return facing.getHorizontalAngle();
		}
		EnumFacing facing = container.getLocatable().getFacing();
		if (facing == null) {
			facing = EnumFacing.NORTH;
		}
		float rotation = +facing.getHorizontalAngle();
		rotation += getFacingRotation(this);
		return rotation;
	}
	
	private float getFacingRotation(IModule module) {
		float rotation = (float) -Math.toDegrees(module.getPosition().getRotationAngle());
		IModuleProvider provider = module.getProvider();
		if (provider instanceof IModuleComponent) {
			IModule parent = ((IModuleComponent) provider).getProvider();
			rotation += getFacingRotation(parent);
		}
		return rotation;
	}
	
	@Override
	public void sendToClient() {
		ILocatable locatable = container.getLocatable();
		if (locatable.getWorldObj().isRemote) {
			return;
		}
		PacketHandler.sendToNetwork(new PacketUpdateModule(this), locatable.getCoordinates(), locatable.getWorldObj());
	}
	
	@Override
	public boolean isEmpty() {
		return getData() == ModuleRegistry.INSTANCE.getEmpty() || itemStack.isEmpty();
	}
}
