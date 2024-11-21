package jadx.plugins.examples.bulkrename.data;

import java.util.HashMap;
import java.util.Map;

public class RenameData {

	private final Map<String, ClassRenameData> clsRenames = new HashMap<>();

	public Map<String, ClassRenameData> getClsRenames() {
		return clsRenames;
	}
}
