package jadx.plugins.examples.bulkrename.data;

import java.util.ArrayList;
import java.util.List;

public class MethodRenameData {
	private final String newName;
	private final List<String> paramNames = new ArrayList<>();

	public MethodRenameData(String newName) {
		this.newName = newName;
	}

	public String getNewName() {
		return newName;
	}

	public List<String> getParamNames() {
		return paramNames;
	}
}
