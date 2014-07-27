package zordz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import zordz.entity.FoodKit;
import zordz.entity.HealthPickup;
import zordz.entity.Key;
import zordz.entity.Player;

public class Console extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 710334028696985287L;
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
							write("Not enough arguments! Usage: speed [speed]");
						}
						try {
							int i = Integer.parseInt(args[1]);
							zordz.player.setSpeed(i / 60f);
						} catch (Exception e) {
							write("Usage: speed [speed]");
						}
					} else if (args[0].equals("damage_feedback")) {
						Options.DAMAGE_FEEDBACK = !Options.DAMAGE_FEEDBACK;
						if (Options.DAMAGE_FEEDBACK) write("Damage feedback turned on.");
						else						 write("Damage feedback turned off.");
					} else if (args[0].equals("add_player")) {
						if (args.length-1 < 2) {
							write("Not enough arguments! Usage: add_player [x] [y]");
						}
						try {
							float x = Float.parseFloat(args[1]);
							float y = Float.parseFloat(args[2]);
							Player bot = new Player(zordz.level, x, y, "bot" + zordz.level.botcount);
							bot.setBot(true);
							zordz.level.add(bot);
							zordz.level.botcount++;
						} catch (Exception e) {
							write("Usage: add_player [x] [y]");
						}
					} else if (args[0].equals("restart")) {
						zordz.player.damage(zordz.player.getHealth());
						zordz.gamestate.init(zordz);
					} else if (args[0].equals("players_mimic")) {
						Options.PLAYERS_MIMIC = !Options.PLAYERS_MIMIC;
					} else if (args[0].equals("burn")) {
						if (args.length-1 < 1) {
							write("Not enough arguments! Usage: burn [ticks]");
						}
						try {
							int i = Integer.parseInt(args[1]);
							zordz.player.setBurnTicks(i);
						} catch (Exception e) {
							write("Usage: burn [ticks]");
						}
					} else if (args[0].equals("entity")) {
						if (args.length-1 < 3) {
							write("Not enough arguments! Usage: entity [entity_name] [x] [y]");
						}
						try {
							String name  = args[1];
							float x = Float.parseFloat(args[2]);
							float y = Float.parseFloat(args[3]);
							
							switch (name) {
							case "health_pickup":
								zordz.level.add(new HealthPickup(zordz.level, x, y));
								break;
							case "foodkit":
								zordz.level.add(new FoodKit(zordz.level, x, y));
								break;
							case "red_key":
								zordz.level.add(new Key(zordz.level, x, y, 0));
								break;
							}
						} catch (Exception e) {
							write("Usage: entity [entity_name] [x] [y]");
						}
					} else if (args[0].equals("bleed")) {
						if (args.length-1 < 1) {
							write("Not enough arguments! Usage: bleed [ticks]");
						}
						try {
							int i = Integer.parseInt(args[1]);
							zordz.player.setBleedTicks(i);
						} catch (Exception e) {
							write("Usage: bleed [ticks]");
						}
					} else if (args[0].equals("help")) {
						write("volume, levels, damage_feedback, speed, healme, hurtme\n\tadd_player, burn, restart, players_mimic, entity");
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
