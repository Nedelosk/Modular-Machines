package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.api.modules.pages.ModuleComponent;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.utils.BoundingBoxHelper;

public class Module implements ICapabilityProvider {
	
	protected final List<ModuleComponent> components;
	/**
	 * The position of the module at the handler.
	 */
	protected IModulePosition position;
	/**
	 * The handler that contains this module.
	 */
	protected IModuleHandler parent;
	protected IModuleContainer container;
	protected int index;
	protected IModuleData data;
	private ItemStack itemStack = ItemStack.EMPTY;
	
	public Module() {
		components = new ArrayList<>();
		createComponents();
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWallType getWallType() {
		return EnumWallType.WALL;
	}
	
	@SideOnly(Side.CLIENT)
	@Nullable
	public ResourceLocation getWallModelLocation() {
		return null;
	}
	
	/**
	 * @return A list with all components of this module
	 */
	public List<ModuleComponent> getComponents() {
		return components;
	}
	
	/**
	 * Returns the component at the give index of the component list.
	 */
	@Nullable
	public ModuleComponent getComponent(int index) {
		if (index >= components.size() || index < 0) {
			return null;
		}
		return components.get(index);
	}
	
	/**
	 * Adds a component to this module.
	 *
	 * You can use {@link #createComponents()} to add components.
	 */
	protected void addComponent(ModuleComponent component) {
		if (!components.contains(component)) {
			component.setIndex(components.size());
			components.add(component);
		}
	}
	
	/**
	 * Can be used to add components to this module.
	 * Called at the module constructor.
	 */
	public void createComponents() {
	}
	
	/**
	 * A list with all items should drop if the block that contains this module will be destroyed or if a player removes
	 * this module from the container.
	 */
	public List<ItemStack> getDrops() {
		return Collections.singletonList(createItem());
	}
	
	/**
	 * Creates a that represents the current state of this module.
	 */
	protected ItemStack createItem() {
		return itemStack.copy();
	}
	
	/**
	 * Called after this module was added to the give parent.
	 */
	public void onCreateModule(IModuleHandler parent, IModulePosition position, IModuleDataContainer container, ItemStack parentItem) {
		this.parent = parent;
		this.container = parent.getProvider().getContainer();
		this.position = position;
		this.itemStack = parentItem;
		this.data = container.getData();
		
		this.container.onModuleAdded(this);
	}
	
	/**
	 * Called when the {@link IModuleHandler} load this module from the NBT-Data.
	 *
	 * Called before {@link #readFromNBT(NBTTagCompound)}.
	 */
	public void onLoadModule(IModuleHandler parent, IModulePosition position) {
		this.parent = parent;
		this.container = parent.getProvider().getContainer();
		this.position = position;
		
		this.container.onModuleAdded(this);
	}
	
	public boolean isClean() {
		return true;
	}
	
	public void sendModuleUpdate() {
		
	}
	
	/**
	 * Writes the current state of this module to the NBT-Data.
	 */
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Parent", itemStack.serializeNBT());
		compound.setString("Data", data.getRegistryName().toString());
		return compound;
	}
	
	/**
	 * Reads the current state of this module from the NBT-Data.
	 */
	public void readFromNBT(NBTTagCompound compound) {
		itemStack = new ItemStack(compound.getCompoundTag("Parent"));
		data = GameRegistry.findRegistry(IModuleData.class).getValue(new ResourceLocation(compound.getString("Data")));
	}
	
	/**
	 * @return The module handler that contains this module.
	 */
	public IModuleHandler getParent() {
		return parent;
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
	
	@Nullable
	protected IItemHandler getItemHandler() {
		return null;
	}
	
	@Nullable
	protected IFluidHandler getFluidHandler() {
		return null;
	}
	
	@Nullable
	public RayTraceResult collisionRayTrace(Vec3d start, Vec3d end) {
		AxisAlignedBB boundingBox = getCollisionBox();
		return boundingBox == null ? null : boundingBox.calculateIntercept(start, end);
	}
	
	@Nullable
	public final AxisAlignedBB getCollisionBox() {
		AxisAlignedBB boundingBox = getBoundingBox();
		IModuleProvider provider = parent.getProvider();
		if (boundingBox != null && provider instanceof Module) {
			Module module = (Module) provider;
			IModulePosition parentPosition = module.position;
			EnumFacing facing = position.getFacing();
			EnumFacing containerFacing = container.getFacing();
			if (containerFacing == null) {
				containerFacing = EnumFacing.NORTH;
			}
			double rotation = containerFacing.getHorizontalAngle();
			rotation += parentPosition.getRotationAngle();
			rotation += facing.getHorizontalAngle();
			/*if (containerFacing == EnumFacing.SOUTH) {
				facing = facing.rotateY().rotateY();
			} else if (containerFacing == EnumFacing.EAST) {
				facing = facing.rotateY().rotateY().rotateY();
			} else if (containerFacing == EnumFacing.WEST) {
				facing = facing.rotateY();
			}*/
			BoundingBoxHelper helper = new BoundingBoxHelper(EnumFacing.fromAngle(rotation));
			return helper.rotateBox(boundingBox);
		}
		return boundingBox;
	}
	
	@Nullable
	protected AxisAlignedBB getBoundingBox() {
		return null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemHandler());
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidHandler());
		}
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return getFluidHandler() != null && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || getItemHandler() != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
	
	/* MODEL */
	@SideOnly(Side.CLIENT)
	private boolean modelNeedReload;
	@SideOnly(Side.CLIENT)
	private IModuleModelState modelState;
	
	@SideOnly(Side.CLIENT)
	public void setModelNeedReload(boolean modelNeedReload) {
		this.modelNeedReload = modelNeedReload;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isModelNeedReload() {
		return modelNeedReload;
	}
	
	@SideOnly(Side.CLIENT)
	public void setModelState(@Nullable IModuleModelState modelState) {
		this.modelState = modelState;
	}
	
	@SideOnly(Side.CLIENT)
	public IModuleModelState getModelState() {
		return modelState;
	}
}
