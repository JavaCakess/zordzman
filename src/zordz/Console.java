package zordz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Console extends JFrame {

	JTextArea txtArea;
	JTextField field;
	public Console() {
		super("Zordzman Console v0.1.0");
		
		txtArea = new JTextArea();
		txtArea.setEditable(false);
		add(new JScrollPane(txtArea), BorderLayout.CENTER);
		
		
		field = new JTextField();
		add(field, BorderLayout.SOUTH);
		field.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					String cmd = ae.getActionCommand();
					field.setText("");
					write("] " + cmd);
					String[] args = cmd.split(" ");
					if (args[0].equals("volume")) {
						if (args.length-1 < 1) {
							write("Not enough arguments! Usage: volume [level]");
						}
						try {
							int i = Integer.parseInt(args[1]);
							if (i < 0 || i > 100) {
								write("Volume must be 0-100");
								return;
							}
							Options.SOUND_LEVEL = i;
						} catch (Exception e) {
							write("Usage: volume [level]");
						}
					}
				}
			}
		);
		
		setSize(400, 500);
		setVisible(true);
	}
	
	public void write(String s) {
		txtArea.append(s + "\n");
	}
}
