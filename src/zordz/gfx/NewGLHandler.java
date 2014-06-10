package zordz.gfx;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;



public class NewGLHandler {

	public static final int FLIP_NORMAL = 0;
	public static final int FLIP_UPSIDE_DOWN = 1;
	public static final int FLIP_VERTICES_BACK = 2;
	public static final int FLIP_VERTICES_FORWARD = 3;
	public static final int FLIP_HORIZONTAL = 4;


	public static final int CLEAR_PIXEL_RENDERING = 5;
	private static boolean clear_pixels = true;
	
	public static boolean on = true; 

	private static ArrayList<Texture> textures = new ArrayList<Texture>();
	private static Map<Byte, Texture> textureIDs = new HashMap<Byte, Texture>();

	/**
	 * Initializes OpenGL for 3D.
	 * @param perspAngle : Perspective Angle.
	 * @param width : The width of your screen.
	 * @param height : The height of your screen.
	 * @param zNear : The nearest z.
	 * @param zFar : The furthest z.
	 * @author JavaCakess@CJAF
	 */
	public static void init3D(float perspAngle, float width, float height, float zNear, float zFar){
		if (!on) return;
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective((float) perspAngle, width / height, zNear, zFar);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
	}
	/**
	 * This filters out a certain color.
	 * @param r : The R value of the color.
	 * @param g : The G value of the color.
	 * @param b : The B value of the color.
	 * @param alpha
	 * @author JavaCakess@CJAF
	 */
	public static void filterColor(float r, float g, float b, float alpha){
		if (!on) return;
		glClearColor(r, g, b, alpha);
	}
	/**
	 * Cleans the screen.
	 * @author JavaCakess@CJAF
	 */
	public static void wipeScreen(){
		if (!on) return;
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	/**
	 * Initializes OpenGL for 2D.
	 * @param width : Width of your screen.
	 * @param height : Height of your screen.
	 * @author JavaCakess@CJAF
	 */
	public static void init2D(float width, float height){
		if (!on) return;
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	/**
	 * Draws a 2-dimensional point at 'x' and 'y'.
	 * @param x : The x position of the point.
	 * @param y : The y position of the point.
	 * @author JavaCakess@CJAF
	 */
	public static void draw2DPoint(float x, float y){
		if (!on) return;
		glBegin(GL_POINTS);
		glVertex2f(x, y);
		glEnd();
	}
	/**
	 * Draws a 3-dimensional point at 'x', 'y', and 'z'.
	 * @param x : The x position of the point.
	 * @param y : The y position of the point.
	 * @param z : The z position of the point.
	 * @author JavaCakess@CJAF
	 */
	public static void draw3DPoint(float x, float y, float z){
		if (!on) return;
		glBegin(GL_POINTS);
		glVertex3f(x, y, z);
		glEnd();
	}
	/**
	 * Draws a 2-dimensional line. It does this by connecting two points together.
	 * @param x1 : The x position of the first point.
	 * @param y1 : The y position of the first point.
	 * @param x2 : The x position of the second point.
	 * @param y2 : The y position of the second point.
	 * @author JavaCakess@CJAF
	 */
	public static void draw2DLine(float x1, float y1, float x2, float y2){
		if (!on) return;
		glBegin(GL_LINES);
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		glEnd();
	}
	/**
	 * Draws a 3-dimensional line. It does this by connecting two points together.
	 * @param p1x : The x position of the first point.
	 * @param p1y : The y position of the first point.
	 * @param p1z : The z position of the first point.
	 * @param p2x : The x position of the second point.
	 * @param p2y : The y position of the second point.
	 * @param p2z : The z position of the second point.
	 * @author JavaCakess@CJAF
	 */
	public static void draw3DLine(float p1x, float p1y, float p1z, float p2x, float p2y, float p2z){
		if (!on) return;
		glBegin(GL_LINES);
		glVertex3f(p1x, p1y, p1z);
		glVertex3f(p2x, p2y, p2z);
		glEnd();
	}
	/**
	 * Draws a 2D rectangle.
	 * @param x : The x position of the rectangle.
	 * @param y : The y position of the rectangle.
	 * @param w : The width of the rectangle.
	 * @param h : The height of the rectangle.
	 * @param filled : If it is a filled rectangle or not.
	 * @author JavaCakess@CJAF
	 */
	public static void draw2DRect(float x, float y, float w, float h, boolean filled){
		if (!on) return;
		if(filled){
			glBegin(GL_QUADS);
			glVertex2f(x, y);
			glVertex2f(x+w, y);
			glVertex2f(x+w, y+h);
			glVertex2f(x, y+h);
			glEnd();
		}else{
			glBegin(GL_LINES);
			draw2DLine(x, y, x+w, y);
			draw2DLine(x+w, y, x+w, y+h);
			draw2DLine(x, y, x, y+h);
			draw2DLine(x, y+h, x+w, h+y);
			glEnd();
		}	
	}

	/**
	 * Automatically loads a texture for you.
	 * @param img : The pathname of the texture file. Note this must be PNG.
	 * @return : The texture that it loaded.
	 * @author JavaCakess@CJAF
	 */
	public static Texture loadTexture(String img){
		if (!on) return null;
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File(img)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("NewGLHandler: I found no file with the name "+img+"!");

	}


	/**
	 * Draws a texture 2D like.
	 * @param texture : The texture to be drawn.
	 * @param x : The texture's x position to be drawn at.
	 * @param y : The texture's y position to be drawn at.
	 * @param w : The width. Note that this does not have to be the size of
	 * the actual texture itself, the graphics will stretch to the given width and height.
	 * @param h : The height. Note that this does not have to be the size of the
	 * actual texture itself, the graphics will stretch to the given width and height.
	 */
	public static void drawTexture2D(Texture texture, float x, float y, float w, float h){
		if (!on) return;
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		if(clear_pixels){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		}
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y);
		glTexCoord2f(1, 0);
		glVertex2f(x+w, y);
		glTexCoord2f(1, 1);
		glVertex2f(x+w, y+h);
		glTexCoord2f(0, 1);
		glVertex2f(x, y+h);
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
		if(!textures.contains(texture)) textures.add(texture);
	}
	/**
	 * Draws a texture with the specified options (o)
	 * @param texture : The texture to be drawn.
	 * @param x : The texture's x position to be drawn at.
	 * @param y : The texture's y position to be drawn at.
	 * @param w : The width. Note that this does not have to be the size of
	 * the actual texture itself, the graphics will stretch to the given width and height.
	 * @param h : The height. Note that this does not have to be the size of the
	 * actual texture itself, the graphics will stretch to the given width and height.
	 * @param o : How to flip the image. e.g. use FLIP_UPSIDE_DOWN to flip the image upside down when rendered.
	 */
	public static void drawTexture2D(Texture texture, float x, float y, float w, float h, int o){
		if (!on) return;
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		if(clear_pixels){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		}
		glBegin(GL_QUADS);
		switch(o){
		case FLIP_NORMAL:
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 0);
			glVertex2f(x+w, y);
			glTexCoord2f(1, 1);
			glVertex2f(x+w, y+h);
			glTexCoord2f(0, 1);
			glVertex2f(x, y+h);
			break;
		case FLIP_UPSIDE_DOWN:
			glTexCoord2f(0, 1);
			glVertex2f(x, y);
			glTexCoord2f(1, 1);
			glVertex2f(x+w, y);
			glTexCoord2f(1, 0);
			glVertex2f(x+w, y+h);
			glTexCoord2f(0, 0);
			glVertex2f(x, y+h);
			break;
		case FLIP_VERTICES_BACK:
			glTexCoord2f(0, 0);
			glVertex2f(x, y+h);
			glTexCoord2f(1, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 1);
			glVertex2f(x+w, y);
			glTexCoord2f(0, 1);
			glVertex2f(x+w, y+h);
			break;
		case FLIP_VERTICES_FORWARD:
			glTexCoord2f(0, 0);
			glVertex2f(x+w, y);
			glTexCoord2f(1, 0);
			glVertex2f(x+w, y+h);
			glTexCoord2f(1, 1);
			glVertex2f(x, y+h);
			glTexCoord2f(0, 1);
			glVertex2f(x, y);
			break;
		case FLIP_HORIZONTAL:
			glTexCoord2f(0, 0);
			glVertex2f(x+w, y);
			glTexCoord2f(1, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 1);
			glVertex2f(x, y+h);
			glTexCoord2f(0, 1);
			glVertex2f(x+w, y+h);
			break;
		}
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
		if(!textures.contains(texture)) textures.add(texture);
	}
	/**
	 * This releases all the textures that were drawn.
	 * @author JavaCakess@CJAF
	 */
	public static void releaseAllTextures(){
		
		if (!on) return;
		for(Texture r : textures){
			r.release();
		}
	}
	/**
	 * Pushes the 'camera' by the given x, y, and z.
	 * @param x : How much to push the camera on the x-axis.
	 * @param y : How much to push the camera on the y-axis.
	 * @param z : How much to push the camera on the z-axis.
	 * @author JavaCakess@CJAF
	 */
	public static void pushAll3D(float x, float y, float z){
		if (!on) return;
		glTranslatef(x, y, z);
	}
	/**
	 * Pushes the 'camera' given by the x and y.
	 * @param x : How much to push the camera on the x-axis.
	 * @param y : How much to push the camera on the y-axis.
	 * @author JavaCakess@CJAF
	 */
	public static void pushAll2D(float x, float y){
		if (!on) return;
		glTranslatef(x, y, 0);
	}
	/**
	 * Pushes the 'camera' on the x-axis by the given amount.
	 * @param x : How much to push the camera on the x-axis.
	 * @author JavaCakess@CJAF
	 */
	public static void pushX3D(float x){
		if (!on) return;
		glTranslatef(x, 0, 0);
	}
	/**
	 * Pushes the 'camera' on the y-axis by the given amount.
	 * @param y : How much to push the camera on the y-axis.
	 * @author JavaCakess@CJAF
	 */
	public static void pushY3D(float y){
		glTranslatef(0, y, 0);
	}
	/**
	 * Pushes the 'camera' on the z-axis by the given amount.
	 * @param z : How much to push the camera on the z-axis.
	 * @author JavaCakess@CJAF
	 */
	public static void pushZ3D(float z){
		if (!on) return;
		glTranslatef(0, 0, z);
	}
	/**
	 * Rotates the camera on the Y axis.
	 * @param angle : Angle.
	 * @param y : Amount.
	 * @author JavaCakess@CJAF
	 */
	public static void rotateY3D(float angle, float y){
		if (!on) return;
		glRotatef(angle, 0, y, 0);
	}
	/**
	 * Rotates the camera on the X axis.
	 * @param angle : Angle
	 * @param x : amount.
	 * @author JavaCakess@CJAF
	 */
	public static void rotateX3D(float angle, float x){
		if (!on) return;
		glRotatef(angle, x, 0, 0);
	}
	/**
	 * Rotates the camera on the X axis.
	 * @param angle : Angle
	 * @param z : Amount.
	 * @author JavaCakess@CJAF
	 */
	public static void rotateZ3D(float angle, float z){
		if (!on) return;
		glRotatef(angle, 0, 0, z);
	}
	/**
	 * <p>Sets the current color to render with. e.g. drawing a quad straight after calling</p>
		<p><code>setCurrentColor(new float[]{1.0f, 1.0f, 0.0f})</code></p>
		<p>will render the quad yellow. The format for the array of values is R, G, B. The values range from 0.0f - 1.0f</p>
	 * @param colors : The array of colors. NewGLHandler reads the first element for red, second for green, third for blue.
	 * If alpha is included, it will need a fourth element, opacity.
	 * @param alpha : If alpha is included.
	 */
	public static void setCurrentColor(float[] colors, boolean alpha) {
		if (!on) return;
		if(!alpha){
			glColor3f(colors[0], colors[1], colors[2]);
		}else{
			GL11.glColor4f(colors[0], colors[1], colors[2], colors[3]);
		}
	}
	/**
	 * <p>Sets the current color to render with. e.g. drawing a quad straight after calling</p>
		<p><code>setCurrentColor(new int[]{255, 255, 0})</code></p>
		<p>will render the quad yellow. The format for the array of values is R, G, B. The values range from 0 - 255</p>
	 * @param colors : The array of colors. NewGLHandler reads the first element for red, second for green, third for blue.
	 * If alpha is included, it will need a fourth element, opacity.
	 * @param alpha : If alpha is included.
	 */
	public static void setCurrentColor(int[] colors, boolean alpha){
		if (!on) return;
		if(!alpha){
			glColor3f(colors[0] / 255, colors[1] / 255, colors[2] / 255);
		}else{
			GL11.glColor4f(colors[0] / 255, colors[1] / 255, colors[2] / 255, colors[3] / 100);
		}
	}
	/**
	 * Loads a texture into NewGLHandler's hashmap of textures.
	 * @param res : The location of the image. Note, must be .png.
	 * @param id : What key that the texture will have to access it.
	 */
	public static void loadTextureByteID(String res, byte id){
		if (!on) return;
		Texture t = loadTexture(res);
		textureIDs.put(new Byte(id), t);
	}
	/**
	 * Deletes a texture from NewGLHandler's hashmap of textures.
	 * @param id : What texture to delete. Note you must know its ID.
	 */
	public static void deleteTextureByteID(byte id){
		if (!on) return;
		textureIDs.get(id).release();
		textureIDs.remove(id);
	}
	/**
	 * Draws a texture.
	 * @param id : The ID of the texture.
	 * @param x : The x pos.
	 * @param y : The y pos.
	 * @param w : The width. Note this can be any size, GL will stretch it to the specified width and height.
	 * @param h : The height. Note this can be any size, GL will stretch it to the specified width and height.
	 */
	public static void drawTexByteID(byte id, float x, float y, float w, float h){
		if (!on) return;
		drawTexture2D(textureIDs.get(id), x, y, w, h);
	}
	/**
	 * Draws a texture with the specified option.
	 * @param id : The ID of the texture.
	 * @param x : The x pos.
	 * @param y : The y pos.
	 * @param w : The width. Note this can be any size, GL will stretch it to the specified width and height.
	 * @param h : The height. Note this can be any size, GL will stretch it to the specified width and height.
	 * @param o : How to flip the image. e.g. use FLIP_UPSIDE_DOWN to flip the image upside down when rendered.
	 */
	public static void drawTexByteID(byte id, float x, float y, float w, float h, int o){
		if (!on) return;
		drawTexture2D(textureIDs.get(id), x, y, w, h, o);
	}

	/**
	 * Enables an option/mode.
	 * @param i : What option/mode to enable. e.g. call <code>enable(NewGLHandler.CLEAR_PIXEL_RENDING)</code> to enable GL_NEAREST rendering.
	 */
	public static void enable(int i){
		if (!on) return;
		switch(i){
		case CLEAR_PIXEL_RENDERING:
			clear_pixels = true;
			break;
		}
	}
	/**
	 * Disables an option/mode.
	 * @param i : What option/mode to disable. e.g. call <code>disable(NewGLHandler.CLEAR_PIXEL_RENDING)</code> to disable GL_NEAREST rendering.
	 */
	public static void disable(int i){
		if (!on) return;
		switch(i){
		case CLEAR_PIXEL_RENDERING:
			clear_pixels = false;
			break;
		}
	}
	/**
	 * Wipes NewGLHandler's hashmap of textures with byte ids.
	 */
	public static void clearAllTexturesByteID(){
		if (!on) return;
		textureIDs.clear();
	}
	/**
	 * Basically the same as calling <code>setCurrentColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f}, true)</code>.
	 */
	public static void resetColors(){
		if (!on) return;
		setCurrentColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f}, true);
	}
	/**
	 * Gets the texture that has the specified id.
	 * @param id : The ID.
	 * @return The texture that has that ID.
	 */
	public static Texture getTexByteID(byte id) {
		if (!on) return null;
		return textureIDs.get(new Byte(id));
	}

	
}
