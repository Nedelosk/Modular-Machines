package modularmachines.common.modules;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.components.IModuleComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncModule;
import modularmachines.common.utils.components.ComponentProvider;

public class Module extends ComponentProvider<IModuleComponent> implements IModule {
	/**
	 * The position of the module at the handler.
	 */
	protected IModulePosition position;
	/**
	 * The handler that contains this module.
	 */
	protected IModuleHandler parent;
	protected IModuleContainer container;
	private int index;
	protected final IModuleData data;
	private ItemStack itemStack = ItemStack.EMPTY;
	@Nullable
	private EnumFacing facing;
	
	public Module(IModuleHandler parent, IModulePosition position, IModuleDataContainer container, ItemStack parentItem) {
		this.parent = parent;
		this.container = parent.getProvider().getContainer();
		this.position = position;
		this.itemStack = parentItem;
		this.data = container.getData();
		this.facing = getFacing();
		
		this.container.onModuleAdded(this);
	}
	
	public Module(IModuleHandler parent, IModuleData moduleData, IModulePosition position) {
		this.parent = parent;
		this.container = parent.getProvider().getContainer();
		this.position = position;
		this.facing = getFacing();
		this.data = moduleData;
		
		this.container.onModuleAdded(this);
	}
	
	/**
	 * Writes the current state of this module to the NBT-Data.
	 */
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("Parent", itemStack.serializeNBT());
		return compound;
	}
	
	/**
	 * Reads the current state of this module from the NBT-Data.
	 */
	public void readFromNBT(NBTTagCompound compound) {
		readFromNBT(compound);
		itemStack = new ItemStack(compound.getCompoundTag("Parent"));
	}
	
	/**
	 * @return The module handler that contains this module.
	 */
	public IModuleHandler getParent() {
		return parent;
	}
	
	@Override
	public IModuleProvider getProvider() {
		return parent.getProvider();
	}
	
	/**
	 * @return The module container that contains every module.
	 */
	public IModuleContainer getContainer() {
		return container;
	}
	
	/**
	 * @return The position of this module at the parent.
	 */
	public IModulePosition getPosition() {
		return position;
	}
	
	/**
	 * @return The item that was used to create this module.
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	/**
	 * @return The index of this module.
	 */
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public IModuleData getData() {
		return data;
	}
	
	/**
	 * Returns the given side relative to the facing of this module.
	 */
	public EnumFacing getSide(EnumFacing side) {
		float rotation = getFacingRotation() + side.getHorizontalAngle();
		return EnumFacing.fromAngle(rotation);
	}
	
	/**
	 * Returns true if the given side is the facing of the module.
	 */
	public boolean isFacing(@Nullable EnumFacing side) {
		return side != null && getFacing() == side;
	}
	
	/**
	 * Returns the facing of the module relative to the facing of the module container.
	 */
	public EnumFacing getFacing() {
		if (facing != null) {
			return facing;
		}
		return EnumFacing.fromAngle(getFacingRotation());
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
	
	private float getFacingRotation(Module module) {
		float rotation = (float) -Math.toDegrees(module.position.getRotationAngle());
		IModuleProvider provider = parent.getProvider();
		if (provider instanceof Module) {
			rotation += ((Module) provider).getFacingRotation((Module) provider);
		}
		return rotation;
	}
	
	@Override
	public void sendToClient() {
		ILocatable locatable = container.getLocatable();
		PacketHandler.sendToNetwork(new PacketSyncModule(this), locatable.getCoordinates(), locatable.getWorldObj());
	}
}
