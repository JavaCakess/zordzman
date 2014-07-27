package zordz.state;

import java.awt.Color;

import zordz.Options;
import zordz.Zordz;
import zordz.gfx.Drawer;
import zordz.gfx.Text;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.net.GameClient;
import zordz.net.TCPClient;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class MultiplayerState extends State {

	Level titleLevel = new Level(640 / 32, 480 / 32);
	Zordz zordz;
	Button backToTitle = new Button("Go Back", Color.yellow, Zordz.WIDTH / 2 - (Button.WIDTH / 2), Zordz.HEIGHT - Button.HEIGHT - 24, 26, 16);
	ScrollPane servers = new ScrollPane("Servers", 80, 60, 480, (480 / 12 * 9) - 100, 0, 0, null);
	Button connect = new Button("Connect", Color.green, Zordz.WIDTH / 2 - (Button.WIDTH / 2), 3, 26, 16);
	public boolean canPlay;
	public boolean sentLogin = false;
	public String status = "Join a server.";
	public static int connect_cooldown = 0;
	public static int connect_wait = 0;
	public MultiplayerState(Zordz zordz) {
		this.zordz = zordz;
		for (int x = 0; x < titleLevel.getWidth(); x++) {
			for (int y = 0; y < titleLevel.getHeight(); y++) {
				if (x == 0 || y == 0 || x == titleLevel.getWidth() - 1 || y == titleLevel.getHeight() - 1) {
					titleLevel.setTileIDAt(x, y, Tile.FLOWER.getID());
				}
			}
		}
		servers.setEventHandler(
			new ScrollPaneEHandler() {
				public boolean onEvent(ScrollPaneEType event_type) {
					status = "Join a server.";
					return true;
				}
			}
		);
		servers.fullBarSize = 187;
		servers.add("localhost");
	}

	public void render() {
		titleLevel.render();
		servers.render();
		backToTitle.draw();
		connect.draw();
		Drawer.setCol(new Color(200, 200, 200));
		Text.render(status, 78, 480-160, 16, 16);
		Drawer.setCol(Color.white);
	}

	public void tick() {
		if (backToTitle.clicked()) {
			zordz.switchState(zordz.titlestate);
			SoundPlayer.play(Sound.button_clicked);
		} else if (connect.clicked()) {
			if (!sentLogin && connect_cooldown < 1) {
				zordz.tcpclient  = new TCPClient (zordz, servers.getSelected());
				zordz.tcpclient.start();
				zordz.gameclient = new GameClient(zordz, servers.getSelected());
				zordz.gameclient.start();
				try { Thread.sleep(1000); } catch (Exception e) {}
				if (zordz.tcpclient.status != TCPClient.SUCCESSFUL) {
					connect_cooldown = 60;
					if (zordz.tcpclient.status == TCPClient.FAILED_TO_CONNECT)
						status = "Failed to connect.";
					else if (zordz.tcpclient.status == TCPClient.USERNAME_TAKEN)
						status = "Username taken.";
					zordz.tcpclient = null;
					zordz.gameclient = null;
					return;
				}
				zordz.tcpclient.write("LOGIN " + Options.USERNAME + " " + zordz.gameclient.socket.getLocalPort());
			}
			sentLogin = true;
			connect_cooldown = 60;
			
			if (canPlay) {
				zordz.gamestate.initMultiplayer(zordz);
				zordz.gamestate.multiplayer = true;
			
				zordz.switchState(zordz.gamestate);
				SoundPlayer.play(Sound.button_clicked);
			}
		}
	}

	public void onSwitchAway(State to) {

	}

	public void onSwitchTo(State awayFrom) {
		sentLogin = false;
	}

	public int getID() {
		return 5;
	}
}
