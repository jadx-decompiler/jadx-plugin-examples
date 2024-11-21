package jadx.plugins.examples.bulkrename;

import jadx.api.JadxDecompiler;
import jadx.api.JavaClass;
import jadx.api.plugins.JadxPlugin;
import jadx.api.plugins.JadxPluginContext;
import jadx.api.plugins.JadxPluginInfo;
import jadx.api.plugins.JadxPluginInfoBuilder;
import jadx.api.plugins.gui.JadxGuiContext;
import jadx.plugins.examples.bulkrename.data.ClassRenameData;
import jadx.plugins.examples.bulkrename.data.MethodRenameData;
import jadx.plugins.examples.bulkrename.data.RenameData;

import java.util.Objects;

public class BulkRenamePlugin implements JadxPlugin {
	public static final String PLUGIN_ID = "bulk-rename-example";

	private final RenameData renameData = new RenameData();

	@Override
	public JadxPluginInfo getPluginInfo() {
		return JadxPluginInfoBuilder.pluginId(PLUGIN_ID)
				.name("Bulk rename example plugin")
				.description("Apply renames fetched from external source")
				.requiredJadxVersion("1.5.1, r2333")
				.build();
	}

	@Override
	public void init(JadxPluginContext context) {
		JadxGuiContext guiContext = context.getGuiContext();
		if (guiContext != null) {
			context.addPass(new BulkRenamePass(renameData));
			guiContext.addMenuAction("Bulk rename", () -> {
				loadRenameData(renameData);
				reloadCode(context, guiContext);
			});
		}
	}

	/**
	 * Reset code cache for updated classes.
	 * 1. Resolve all classes to JavaClass
	 * 2. Call `unloadCode()`
	 * 3. Reload all opened tabs (TODO: current api don't allow to reload only updated classes)
	 */
	private void reloadCode(JadxPluginContext context, JadxGuiContext guiContext) {
		JadxDecompiler decompiler = context.getDecompiler();
		renameData.getClsRenames().keySet().stream()
				.map(decompiler::searchJavaClassByOrigFullName)
				.filter(Objects::nonNull) // better to throw an error in class not found
				.forEach(JavaClass::unload);
		guiContext.reloadAllTabs();
	}

	static void loadRenameData(RenameData renameData) {
		// this is just an example
		// so prepare data for a simple class (check smali samples in test/resources/samples)
		ClassRenameData clsRenameData = new ClassRenameData("HelloJadx");
		renameData.getClsRenames().put("example.HelloWorld", clsRenameData);
		clsRenameData.getFldNames().put("helloField", "jadxField");
		MethodRenameData mthData = new MethodRenameData("printJadx");
		clsRenameData.getMthData().put("print", mthData);
		mthData.getParamNames().add("jadxStr");
	}
}
