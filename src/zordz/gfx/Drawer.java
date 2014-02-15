package zordz.gfx;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;

import org.newdawn.slick.opengl.Texture;

public class Drawer {

	public static void setCol(Color c) {
		glColor3f((float)c.getRed()/255.0f, (float)c.getGreen()/255.0f, (float)c.getBlue()/255.0f);
	}
	
	public static void draw(SpriteSheet ss, int xtile, int ytile, float x, float y,
			float w, float h) {
		Texture tex = ss.tex;
		float xt = (xtile * ss.tW);
		float yt = (ytile * ss.tH);

		glBindTexture(GL_TEXTURE_2D, tex.getTextureID());
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glBegin(GL_QUADS);
		{
			glTexCoord2f((xt) / ss.tex.getTextureWidth(), (yt) / ss.tex.getTextureHeight());
			glVertex2f(x, y);
			glTexCoord2f((xt + ss.tW) / ss.tex.getTextureWidth(), (yt) / ss.tex.getTextureHeight());
			glVertex2f(x + w, y);
			glTexCoord2f((xt + ss.tW) / ss.tex.getTextureWidth(), (yt + ss.tH) / ss.tex.getTextureHeight());
			glVertex2f(x + w, y + h);
			glTexCoord2f((xt) / ss.tex.getTextureWidth(), (yt + ss.tH) / ss.tex.getTextureHeight());
			glVertex2f(x, y + h);
		}
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
