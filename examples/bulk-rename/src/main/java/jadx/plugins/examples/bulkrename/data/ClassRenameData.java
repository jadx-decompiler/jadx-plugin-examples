package jadx.plugins.examples.bulkrename.data;

import java.util.HashMap;
import java.util.Map;

public class ClassRenameData {
	private final String clsNewName;
	private Map<String, String> fldNames = new HashMap<>();
	private Map<String, MethodRenameData> mthData = new HashMap<>();


	public ClassRenameData(String clsNewName) {
		this.clsNewName = clsNewName;
	}

	public String getClsNewName() {
		return clsNewName;
	}

	public Map<String, String> getFldNames() {
		return fldNames;
	}

	public Map<String, MethodRenameData> getMthData() {
		return mthData;
	}
}
