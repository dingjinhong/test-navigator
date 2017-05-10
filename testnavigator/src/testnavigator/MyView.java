package testnavigator;

import java.util.ArrayList;

import org.eclipse.ui.navigator.CommonNavigator;

import testnavigator.mode.Mode;

public class MyView extends CommonNavigator {
	protected Object getInitialInput() {
		ArrayList<Mode> al=new ArrayList<Mode>();
		Mode mode=new Mode();
		mode.setName("family");
		mode.getChildren().add("djh");
		mode.getChildren().add("ql");
		al.add(mode);
		return al;
	}

}
