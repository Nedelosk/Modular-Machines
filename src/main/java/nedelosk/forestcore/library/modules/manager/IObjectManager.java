package nedelosk.forestcore.library.modules.manager;

public interface IObjectManager<O> {

	void register(O object, Object... objects);
	
	O getObject();
	
}
