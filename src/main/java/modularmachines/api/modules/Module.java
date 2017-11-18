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

import modularmachines.api.modules.containers.IModuleDataContainer;
import modularmachines.api.modules.pages.ModuleComponent;
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
	protected ModuleData data;
	private ItemStack parentItem = ItemStack.EMPTY;
	
	public Module() {
		components = new ArrayList<>();
		createComponents();
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWallType getWallType(){
		return EnumWallType.WALL;
	}

	@SideOnly(Side.CLIENT)
	@Nullable
	public ResourceLocation getWindowLocation(){
		return null;
	}
	
	public List<ModuleComponent> getComponents(){
		return components;
	}
	
	@Nullable
	public ModuleComponent getComponent(int index){
		if(index >= components.size() || index < 0){
			return null;
		}
		return components.get(index);
	}
	
	protected void addComponent(ModuleComponent component){
		if(!components.contains(component)){
			component.setIndex(components.size());
			components.add(component);
		}
	}

	/**
	 * @return The item that the module drop.
	 */
	public List<ItemStack> getDrops(){
		return Collections.singletonList(parentItem);
	}
	
	public void onCreateModule(IModuleHandler parent, IModulePosition position, IModuleDataContainer container, ItemStack parentItem){
		this.parent = parent;
		this.container = parent.getProvider().getContainer();
		this.position = position;
		this.parentItem = parentItem;
		this.data = container.getData();
		
		this.container.onModuleAdded(this);
	}
	
	public void onLoadModule(IModuleHandler parent, IModulePosition position){
		this.parent = parent;
		this.container = parent.getProvider().getContainer();
		this.position = position;
		
		this.container.onModuleAdded(this);
	}
	
	protected void createComponents() {
	}

	public boolean isClean(){
		return true;
	}
	
	public void sendModuleUpdate(){
		
	}
	
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
    	compound.setTag("Parent", parentItem.serializeNBT());
    	compound.setString("Data", data.getRegistryName().toString());
    	return compound;
    }
    
    public void readFromNBT(NBTTagCompound compound) {
		parentItem = new ItemStack(compound.getCompoundTag("Parent"));
		data = GameRegistry.findRegistry(ModuleData.class).getValue(new ResourceLocation(compound.getString("Data")));
	}
	
	public IModuleHandler getParent() {
		return parent;
	}
	
	public IModuleContainer getContainer() {
		return container;
	}
	
	public IModulePosition getPosition() {
		return position;
	}
	
	public ItemStack getParentItem() {
		return parentItem;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public ModuleData getData() {
		return data;
	}
	
	public IItemHandler getItemHandler(){
		return null;
	}
	
	public IFluidHandler getFluidHandler(){
		return null;
	}
	
	@Nullable
	public RayTraceResult collisionRayTrace(Vec3d start, Vec3d end){
		AxisAlignedBB boundingBox = getCollisionBox();
		return boundingBox == null ? null : boundingBox.calculateIntercept(start, end);
	}
	
	@Nullable
	public AxisAlignedBB getCollisionBox(){
		AxisAlignedBB boundingBox = getBoundingBox();
		IModuleProvider provider = parent.getProvider();
		if(boundingBox != null && provider instanceof Module){
			Module module = (Module) provider;
			IModulePosition parentPosition = module.position;
			EnumFacing facing = position.getFacing();
			EnumFacing containerFacing = container.getFacing();
			if(containerFacing == EnumFacing.SOUTH){
				facing = facing.rotateY().rotateY();
			}else if(containerFacing == EnumFacing.EAST){
				facing = facing.rotateY();
			}else if(containerFacing == EnumFacing.WEST){
				facing = facing.rotateY().rotateY().rotateY();
			}
			BoundingBoxHelper helper = new BoundingBoxHelper(facing);
			return helper.rotateBox(boundingBox);
		}
		return boundingBox == null ? null : boundingBox.offset(getBoundingBoxOffset());
	}
	
	protected Vec3d getBoundingBoxOffset(){
		return Vec3d.ZERO;
	}
	
	@Nullable
	protected AxisAlignedBB getBoundingBox(){
		return null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemHandler());
		}else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidHandler());
		}
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return getFluidHandler() != null && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || getItemHandler() != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
	
	/* MODEL */
	protected boolean modelNeedReload;
	
	public void setModelNeedReload(boolean modelNeedReload) {
		this.modelNeedReload = modelNeedReload;
	}
	
	public boolean isModelNeedReload() {
		return modelNeedReload;
	}
	
}
