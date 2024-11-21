package jadx.plugins.examples.bulkrename;

import jadx.gui.JadxGUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Not actually a test, run jadx-gui with loaded plugin and sample.
 * After window open go to 'Plugins -> Bulk rename' menu action.
 * To undo renames go to 'Tools -> Reset code cache'
 */
public class BulkRenameGuiTest extends CommonTest {

	public static void main(String[] args) throws Exception {
		File sampleFile = new BulkRenameGuiTest().getSampleFile("hello.smali");
		List<String> guiArgs = new ArrayList<>();
		// enable debug logging
		guiArgs.add("-v");
		// open sample class
		guiArgs.add("--select-class");
		guiArgs.add("example.HelloWorld");
		// add a sample smali file
		guiArgs.add(sampleFile.getAbsolutePath());
		JadxGUI.main(guiArgs.toArray(new String[0]));
	}
}
