package zordz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Console extends JFrame {

	JTextArea txtArea;
	JTextField field;
	public Console(final Zordz zordz) {
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
					write("> " + cmd);
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
					} else if (args[0].equals("levels")) {
						for (String s : zordz.levels.keySet()) {
							write(" -\t" + s + "  Width: " + zordz.levels.get(s).getWidth() + "  Height: " + zordz.levels.get(s).getHeight());
						}
					} else if (args[0].equals(zordz.hex)) {
						write("Can you guess what that means?");
					} else if (args[0].equals("hurtme")) {
						if (args.length-1 < 1) {
							write("Not enough arguments! Usage: hurtme [damage]");
						}
						try {
							int i = Integer.parseInt(args[1]);
							zordz.player.damage(i);
						} catch (Exception e) {
							write("Usage: hurtme [damage]");
						}
					} else if (args[0].equals("healme")) {
						if (args.length-1 < 1) {
							write("Not enough arguments! Usage: healme [hp]");
						}
						try {
							int i = Integer.parseInt(args[1]);
							zordz.player.heal(i);
						} catch (Exception e) {
							write("Usage: healme [hp]");
						}
					} else if (args[0].equals("speed")) {
						if (args.length-1 < 1) {
							write("Not enough arguments! Usage: speed [sped]");
						}
						try {
							int i = Integer.parseInt(args[1]);
							zordz.player.setSpeed(i / 60f);
						} catch (Exception e) {
							write("Usage: speed [speed]");
						}
					}
				}
			}
		);
		
		setSize(400, 500);
		setVisible(false);
	}
	
	public String time() {
		Date d = new Date();
		int h = d.getHours();
		int m = d.getMinutes();
		int s = d.getSeconds();
		String hours = "" + h, minutes = "" + m, secs = "" + s;
		if (h < 10) {
			hours = "0" + h;
		}
		if (m < 10) {
			minutes = "0" + m;
		}
		if (s < 10) {
			secs = "0" + s;
		}
		return (hours + ":" + minutes + ":" + secs);
	}
	
	public void write(String s) {
		txtArea.append("[" + time() + "] " + s + "\n");
	}
}
