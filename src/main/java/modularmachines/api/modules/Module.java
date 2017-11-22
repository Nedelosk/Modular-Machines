package modularmachines.api.modules;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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

import modularmachines.api.components.IComponentProvider;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.api.modules.pages.PageComponent;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.utils.BoundingBoxHelper;

public class Module implements ICapabilityProvider, INBTReadable, INBTWritable {
	
	@Nullable
	protected final IComponentProvider<IModuleComponent> componentProvider;
	protected final List<PageComponent> components;
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
	protected IModuleData data;
	private ItemStack itemStack = ItemStack.EMPTY;
	private EnumFacing facing;
	
	public Module() {
		components = new ArrayList<>();
		componentProvider = ModuleManager.factory != null ? ModuleManager.factory.createComponentProvider() : null;
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
	public List<PageComponent> getComponents() {
		return components;
	}
	
	/**
	 * Returns the component at the give index of the component list.
	 */
	@Nullable
	public PageComponent getComponent(int index) {
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
	protected void addComponent(PageComponent component) {
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
	
	@Nullable
	public IComponentProvider<IModuleComponent> getComponentProvider() {
		return componentProvider;
	}
	
	protected void addComponent(IModuleComponent component) {
		if (componentProvider != null) {
			componentProvider.addComponent(component);
		}
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
		this.facing = getFacing();
		
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
	
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		return false;
	}
	
	public void onClick(EntityPlayer player, RayTraceResult hit) {
	}
	
	@Nullable
	public RayTraceResult collisionRayTrace(Vec3d start, Vec3d end) {
		RayTraceResult traceResult = rayTrace(start, end, this, getCollisionBox());
		if (traceResult == null) {
			return null;
		}
		double distance = traceResult.hitVec.squareDistanceTo(start);
		if (this instanceof IModuleProvider) {
			IModuleHandler handler = ((IModuleProvider) this).getHandler();
			Optional<RayTraceResult> result = handler.getModules().stream().map(m -> m.collisionRayTrace(start, end)).filter(Objects::nonNull).min(Comparator.comparingDouble(hit -> hit.hitVec.squareDistanceTo(start)));
			if (result.isPresent()) {
				RayTraceResult rayTraceResult = result.get();
				double secondD = rayTraceResult.hitVec.squareDistanceTo(start);
				if (secondD <= distance) {
					return rayTraceResult;
				}
			}
		}
		return traceResult;
	}
	
	@Nullable
	protected RayTraceResult rayTrace(Vec3d start, Vec3d end, Module module, @Nullable AxisAlignedBB boundingBox) {
		if (boundingBox == null) {
			return null;
		}
		RayTraceResult rayTrace = boundingBox.calculateIntercept(start, end);
		if (rayTrace == null) {
			return null;
		}
		RayTraceResult result = new RayTraceResult(rayTrace.hitVec, rayTrace.sideHit);
		result.subHit = module.getIndex();
		result.hitInfo = rayTrace;
		return result;
	}
	
	@Nullable
	public final AxisAlignedBB getCollisionBox() {
		AxisAlignedBB boundingBox = getBoundingBox();
		IModuleProvider provider = parent.getProvider();
		if (boundingBox != null && provider instanceof Module) {
			BoundingBoxHelper helper = new BoundingBoxHelper(getFacing());
			return helper.rotateBox(boundingBox).offset(position.getOffset());
		}
		return boundingBox;
	}
	
	@Nullable
	protected AxisAlignedBB getBoundingBox() {
		return null;
	}
	
	public boolean canHandle(@Nullable EnumFacing side) {
		return isFacing(side);
	}
	
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemHandler());
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidHandler());
		}
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return getFluidHandler() != null && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || getItemHandler() != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
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
