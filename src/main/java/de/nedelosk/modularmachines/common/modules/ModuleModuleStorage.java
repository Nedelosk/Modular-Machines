package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumModulePosition;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.Module;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.properties.IModuleModuleStorageProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.property.PropertyEnum;
import de.nedelosk.modularmachines.client.model.ModelHandlerDrawer;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleModuleStorage extends Module implements IModuleModuleStorage {

	public final PropertyEnum<EnumStoragePosition> STORAGEDPOSITION = new PropertyEnum("storagedPosition", EnumStoragePosition.class, null);

	public ModuleModuleStorage() {
		super("drawer");
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(STORAGEDPOSITION);
	}

	@Override
	protected boolean showSize(IModuleContainer container) {
		return false;
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		List<String> positions = new ArrayList<>();
		EnumModuleSize size = getSize(container);
		if(size == EnumModuleSize.LARGE) {
			positions.add(EnumStoragePosition.LEFT.getLocName());
			positions.add(EnumStoragePosition.RIGHT.getLocName());
		}else if(size == EnumModuleSize.SMALL) {
			positions.add(EnumStoragePosition.TOP.getLocName());
			positions.add(EnumStoragePosition.BACK.getLocName());
		}
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.storage.position") + positions.toString().replace("[", "").replace("]", ""));
		tooltip.add(Translator.translateToLocal("mm.module.allowed.complexity") + getAllowedComplexity(container));
		super.addTooltip(tooltip, stack, container);
	}

	@Override
	public void sendModuleUpdate(IModuleState state){
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(handler, state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	protected ModelHandlerDrawer createModelHandler(IModuleContainer container){
		return new ModelHandlerDrawer(container);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return createModelHandler(state.getContainer());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(createModelHandler(container));
		return handlers;
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties instanceof IModuleModuleStorageProperties){
			return ((IModuleModuleStorageProperties)properties).getAllowedComplexity(container);
		}
		return Config.defaultAllowedStorageComplexity;
	}

	@Override
	public void assembleModule(IModularAssembler assembler, IModular modular, IModuleStorage storage, IModuleState state) throws ArithmeticException {
		if(storage instanceof IPositionedModuleStorage){
			state.set(STORAGEDPOSITION, ((IPositionedModuleStorage)storage).getPosition());
		}
	}

	@Override
	public EnumStoragePosition getCurrentPosition(IModuleState state) {
		return state.get(STORAGEDPOSITION);
	}

	@Override
	public EnumModulePosition getPosition(IModuleContainer container) {
		return null;
	}

	@Override
	public boolean isValidForPosition(EnumStoragePosition position, IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties instanceof IModuleModuleStorageProperties){
			return ((IModuleModuleStorageProperties)properties).isValidForPosition(position, container);
		}
		return false;
	}
}
