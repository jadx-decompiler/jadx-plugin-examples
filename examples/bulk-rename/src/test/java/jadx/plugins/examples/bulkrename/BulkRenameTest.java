package jadx.plugins.examples.bulkrename;

import jadx.api.JadxArgs;
import jadx.api.JadxDecompiler;
import jadx.api.JavaClass;
import jadx.plugins.examples.bulkrename.data.RenameData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BulkRenameTest extends CommonTest {

	@Test
	public void testRenamePass() throws Exception {
		JadxArgs args = new JadxArgs();
		args.getInputFiles().add(getSampleFile("hello.smali"));
		try (JadxDecompiler jadx = new JadxDecompiler(args)) {
			RenameData renameData = new RenameData();
			BulkRenamePlugin.loadRenameData(renameData);
			jadx.addCustomPass(new BulkRenamePass(renameData));
			jadx.load();

			JavaClass cls = jadx.getClasses().get(0);
			String clsCode = cls.getCode();
			System.out.println(clsCode);
			assertThat(clsCode)
					.contains("class HelloJadx")
					.contains("public String jadxField")
					.contains("public void printJadx(String jadxStr) {");
		}
	}
}
