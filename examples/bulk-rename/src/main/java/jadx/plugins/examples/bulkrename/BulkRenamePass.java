package jadx.plugins.examples.bulkrename;

import jadx.api.plugins.pass.JadxPassInfo;
import jadx.api.plugins.pass.impl.OrderedJadxPassInfo;
import jadx.api.plugins.pass.types.JadxDecompilePass;
import jadx.core.dex.instructions.args.RegisterArg;
import jadx.core.dex.nodes.ClassNode;
import jadx.core.dex.nodes.FieldNode;
import jadx.core.dex.nodes.MethodNode;
import jadx.core.dex.nodes.RootNode;
import jadx.plugins.examples.bulkrename.data.ClassRenameData;
import jadx.plugins.examples.bulkrename.data.MethodRenameData;
import jadx.plugins.examples.bulkrename.data.RenameData;

import java.util.List;
import java.util.Map;

public class BulkRenamePass implements JadxDecompilePass {

	private final RenameData renameData;

	public BulkRenamePass(RenameData renameData) {
		this.renameData = renameData;
	}

	@Override
	public JadxPassInfo getInfo() {
		return new OrderedJadxPassInfo(
				"BulkRename",
				"Rename everything")
				.after("FinishTypeInference");
	}

	@Override
	public void init(RootNode root) {
	}

	@Override
	public boolean visit(ClassNode cls) {
		String clsFullName = cls.getClassInfo().getFullName();
		ClassRenameData clsRenameData = renameData.getClsRenames().get(clsFullName);
		if (clsRenameData != null) {
			cls.rename(clsRenameData.getClsNewName());
			// rename fields
			Map<String, String> fldNames = clsRenameData.getFldNames();
			for (FieldNode field : cls.getFields()) {
				String newName = fldNames.get(field.getName());
				if (newName != null) {
					field.rename(newName);
				}
			}
			// rename methods
			Map<String, MethodRenameData> mthData = clsRenameData.getMthData();
			for (MethodNode method : cls.getMethods()) {
				MethodRenameData mthRenameData = mthData.get(method.getName());
				if (mthRenameData != null) {
					method.rename(mthRenameData.getNewName());
					// rename method parameters
					List<String> paramNames = mthRenameData.getParamNames();
					if (!paramNames.isEmpty()) {
						List<RegisterArg> args = method.getArgRegs();
						int argsLen = Math.min(args.size(), paramNames.size());
						for (int i = 0; i < argsLen; i++) {
							RegisterArg argReg = args.get(i);
							String newName = paramNames.get(i);
							argReg.setName(newName);
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public void visit(MethodNode mth) {
	}
}
