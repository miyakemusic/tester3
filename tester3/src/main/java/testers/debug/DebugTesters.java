package testers.debug;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.miyake.demo.entities.MyTesterEntity;
import com.miyake.demo.jsonobject.ProjectJson;

import testers.MyHttpClient;
import testers.RestClient;

class Account {
	public Account(String account, String password) {
		this.account = account;
		this.password = password;
	}
	String account;
	String password;
}
public class DebugTesters extends JFrame {

	public static void main(String[] args) {
		List<Account> accounts = Arrays.asList(
				new Account("20220407-002", "20220407-002"),
				new Account("20220407-003", "20220407-003"),
				new Account("20220407-004", "20220407-004")
				);
		new DebugTesters("localhost", accounts).setVisible(true);
	}
	
	public DebugTesters(String host, List<Account> accounts) {
//		this.setLayout(new FlowLayout());
		this.setSize(new Dimension(800, 500));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		for (Account account : accounts) {
					this.add(new DebugTesterPane(host, account));	
		}
	
	}
}
