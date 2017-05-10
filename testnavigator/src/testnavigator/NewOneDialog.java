package testnavigator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewOneDialog extends Dialog {
	Text text=null;
	String value="";
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public NewOneDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	protected void configureShell(Shell s){
		super.configureShell(s);
		s.setText("NewFlowNodeDialog");
	}
	protected Control createDialogArea(Composite parent){
		Composite com=(Composite) super.createDialogArea(parent);
		Group group=new Group(com,SWT.NONE);
		GridLayout gl=new GridLayout();
		gl.numColumns=2;
		gl.marginWidth=10;
		gl.marginHeight=10;
		group.setText("new");
		group.setLayout(gl);
		Label label=new Label(group,SWT.NONE);
		label.setText("name");
		text=new Text(group,SWT.NONE);
		
		return parent;
		
	}
	protected void createButtonForButtonBar(Composite parent){
		createButton(parent,0,"OK",true);
		createButton(parent,1,"Cancel",false);
	}
	protected void buttonPressed(int buttonid){
		//String path=getPluginPath();
		if(0==buttonid){
			 value=text.getText();
			close();
		}else{
			close();
		}
	}
}
