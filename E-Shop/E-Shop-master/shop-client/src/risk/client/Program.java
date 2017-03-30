package risk.client;

import risk.client.ui.cui.CUI;
import risk.client.ui.gui.GUI;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Program {
	public static void main(String[] args) throws IOException {
		List<String> argsList = Arrays.asList(args);
        if(argsList.contains("-c")) {
            CUI cui = new CUI();
            cui.run();
        } else {
            GUI gui = new GUI();
        }
	}
}
