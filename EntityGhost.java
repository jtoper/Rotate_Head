package net.dawn.src;

import net.dawn.Dawn;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class EntityGhost {

	public static Vector3f pos;

	protected static Vector4f color = new Vector4f(1f, 0f, 0f, 1f);

	public int rotation = 0;

	protected static CubeTerrain terrain;

	protected static boolean renderTop, renderBottom, renderFront, renderBack, renderRight, renderLeft;

	public boolean isAlive = true;

	public EntityGhost(Vector3f pos, CubeTerrain terrain) {
		this.pos = pos;
		this.terrain = terrain;
		isAlive = true;

		renderTop = true;
		renderBottom = true;
		renderFront = true;
		renderBack = true;
		renderRight = true;
		renderLeft = true;
	}

	public void move(float x, float y, float z) {
		Vector3f newCoordinates = new Vector3f(pos.x, pos.y, pos.z);
		newCoordinates.x = 10;
		newCoordinates.z = 10;
		newCoordinates.y = 10;

		// newCoordinates.y -= Dawn.deltaTime * Dawn.FALSE_GRAVITY_SPEED;

		if (collision((float) (newCoordinates.x), pos.y, pos.z)) {
			jump(0.75);
		}
		if (collision((float) (newCoordinates.x + 1), pos.y, pos.z)) {
			jump(0.75);
		}
		if (collision(pos.x, pos.y, newCoordinates.z)) {
			jump(0.75);
		}
		if (collision(pos.x, pos.y, newCoordinates.z + 1)) {
			jump(0.75);
		}

		if (!collision(newCoordinates.x, pos.y, pos.z)) {
			pos.x = newCoordinates.x;
		}
		if (!collision(pos.x, newCoordinates.y, pos.z)) {
			pos.y = newCoordinates.y;
		}
		if (!collision(pos.x, pos.y, newCoordinates.z)) {
			pos.z = newCoordinates.z;
		}

	}

	public static boolean collision(float x, float y, float z) {
		// Simulate a cube cross around the point
		float cubeSize = 1.0f;
		float playerSize = 0.75f;

		Vector3f c1 = new Vector3f(x - cubeSize, y + playerSize, z);
		Vector3f c2 = new Vector3f(x + cubeSize, y - playerSize, z);
		Vector3f c3 = new Vector3f(x, y - playerSize, z);
		Vector3f c4 = new Vector3f(x, y + playerSize, z);
		Vector3f c5 = new Vector3f(x, y - playerSize, z + cubeSize);
		Vector3f c6 = new Vector3f(x, y + playerSize, z - cubeSize);

		if (!terrain.solidAt(c1) && !terrain.solidAt(c2) && !terrain.solidAt(c3) && !terrain.solidAt(c4) && !terrain.solidAt(c5) && !terrain.solidAt(c6)) {
			return false;
		}

		return true;
	}

	public static void setVisibleSides(boolean drawTop, boolean drawBottom, boolean drawFront, boolean drawBack, boolean drawRight, boolean drawLeft) {
		renderTop = drawTop;
		renderBottom = drawBottom;
		renderFront = drawFront;
		renderBack = drawBack;
		renderRight = drawRight;
		renderLeft = drawLeft;
	}

	protected static void render() {

		// renderHead();
		renderRotateHead(0.5f, 1.0f);
		// renderBody();
		// renderLeftArm();
		// renderRightArm();
		// renderLeftLeg();
		// renderRightLeg();

		GL11.glEnd();

		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_COLOR_MATERIAL);

	}

	private static void renderRightLeg() {
		color = new Vector4f(1f, 0f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CubeTerrain.waterTexture.getTextureID());
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColor4f(color.x, color.y, color.z, color.a);

		GL11.glBegin(GL11.GL_QUADS);

		if (renderTop) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y, pos.z - 0.05f + 0.5f / 2);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z - 0.05f + 0.5f / 2);
		}

		if (renderBottom) {
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y - 0.75f, pos.z - 0.1f + 0.6f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y - 0.75f, pos.z - 0.1f + 0.6f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y - 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y - 0.75f, pos.z + 0.05f);
		}

		if (renderFront) {
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y - 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y - 0.75f, pos.z + 0.05f);
		}

		if (renderBack) {
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y - 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y - 0.75f, pos.z + 0.2f);
		}

		if (renderRight) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y - 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y - 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z + 0.2f);
		}

		if (renderLeft) {
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y - 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y - 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y, pos.z + 0.05f);
		}
	}

	private static void renderLeftLeg() {
		color = new Vector4f(1f, 0f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CubeTerrain.waterTexture.getTextureID());
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColor4f(color.x, color.y, color.z, color.a);

		GL11.glBegin(GL11.GL_QUADS);

		if (renderTop) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y, pos.z - 0.05f + 0.5f / 2);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y, pos.z - 0.05f + 0.5f / 2);
		}

		if (renderBottom) {
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y - 0.75f, pos.z - 0.1f + 0.6f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y - 0.75f, pos.z - 0.1f + 0.6f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y - 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y - 0.75f, pos.z + 0.05f);
		}

		if (renderFront) {
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y - 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y - 0.75f, pos.z + 0.2f);
		}

		if (renderBack) {
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y - 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y - 0.75f, pos.z + 0.05f);
		}

		if (renderRight) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y - 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y - 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f, pos.y, pos.z + 0.2f);
		}

		if (renderLeft) {
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y - 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y - 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.3f + 0.15f, pos.y, pos.z + 0.05f);
		}
	}

	private static void renderRightArm() {
		color = new Vector4f(1f, 0f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CubeTerrain.waterTexture.getTextureID());
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColor4f(color.x, color.y, color.z, color.a);

		GL11.glBegin(GL11.GL_QUADS);

		if (renderTop) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z - 0.05f + 0.5f / 2);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y + 0.75f, pos.z - 0.05f + 0.5f / 2);
		}

		if (renderBottom) {
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y, pos.z - 0.1f + 0.6f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z - 0.1f + 0.6f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y, pos.z + 0.05f);
		}

		if (renderFront) {
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y + 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y, pos.z + 0.2f);
		}

		if (renderBack) {
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.15f, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z + 0.05f);
		}

		if (renderRight) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y + 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.6f, pos.y, pos.z + 0.2f);
		}

		if (renderLeft) {
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z + 0.05f);
		}
	}

	private static void renderLeftArm() {
		color = new Vector4f(1f, 0f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CubeTerrain.waterTexture.getTextureID());
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColor4f(color.x, color.y, color.z, color.a);

		GL11.glBegin(GL11.GL_QUADS);

		if (renderTop) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y + 0.75f, pos.z - 0.05f + 0.5f / 2);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y + 0.75f, pos.z - 0.05f + 0.5f / 2);
		}

		if (renderBottom) {
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y, pos.z - 0.1f + 0.6f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y, pos.z - 0.1f + 0.6f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y, pos.z + 0.05f);
		}

		if (renderFront) {
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y + 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y + 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y, pos.z + 0.2f);
		}

		if (renderBack) {
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y, pos.z + 0.05f);
		}

		if (renderRight) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y + 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f + 0.3f / 2, pos.y, pos.z + 0.2f);
		}

		if (renderLeft) {
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y + 0.75f, pos.z + 0.2f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y + 0.75f, pos.z + 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x - 0.15f, pos.y, pos.z + 0.05f);
		}
	}

	protected static void renderHead() {
		color = new Vector4f(1f, 0f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CubeTerrain.waterTexture.getTextureID());
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColor4f(color.x, color.y, color.z, color.a);

		GL11.glBegin(GL11.GL_QUADS);

		if (renderTop) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f + 0.7f / 2);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f + 0.7f / 2);
		}

		if (renderBottom) {
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f + 0.7f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.75f, pos.z - 0.05f + 0.7f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.75f, pos.z - 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f);
		}

		if (renderFront) {
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f + 0.7f / 2);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f + 0.7f / 2);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.75f, pos.z - 0.05f + 0.7f / 2);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f + 0.7f / 2);
		}

		if (renderBack) {
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f);
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2f, pos.y + 0.75f, pos.z - 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.75f, pos.z - 0.05f);
		}

		if (renderRight) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.75f + 0.65f / 2, pos.z - 0.05f);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.75f + 0.65f / 2, pos.z - 0.05f + 0.70f / 2);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f + 0.70f / 2);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f);
		}

		if (renderLeft) {
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.75f + 0.65f / 2, pos.z - 0.05f + 0.70f / 2);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.75f + 0.65f / 2, pos.z - 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.75f, pos.z - 0.05f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.05f, pos.y + 0.75f, pos.z - 0.05f + 0.70f / 2);
		}
	}

	protected static void renderRotateHead(float rot, float size) {
		color = new Vector4f(1f, 0f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CubeTerrain.waterTexture.getTextureID());
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColor4f(color.x, color.y, color.z, color.a);

		GL11.glBegin(GL11.GL_QUADS);

		if (renderTop) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x - rot / size + 0.05f + 0.65f / 2, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x - rot / size + 0.05f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f + 0.7f / 2);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f + 0.65f / 2, pos.y + 0.70f + 0.75f / 2, pos.z - rot - 0.05f + 0.7f / 2);
		}

		if (renderBottom) {
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f + 0.7f / 2 - rot);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f, pos.y + 0.75f, pos.z - 0.05f + 0.7f / 2 + rot);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f, pos.y + 0.75f, pos.z - 0.05f + rot);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f - rot);
		}

		if (renderFront) {
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f + 0.65f / 2, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f + 0.7f / 2 - rot);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f + 0.7f / 2 + rot);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f, pos.y + 0.75f, pos.z - 0.05f + 0.7f / 2 + rot);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f + 0.7f / 2 - rot);
		}

		if (renderBack) {
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f + rot);
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f + 0.65f / 2f, pos.y + 0.70f + 0.75f / 2, pos.z - 0.05f - rot);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f + 0.65f / 2f, pos.y + 0.75f, pos.z - 0.05f - rot);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f, pos.y + 0.75f, pos.z - 0.05f + rot);
		}

		if (renderRight) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f + 0.65f / 2, pos.y + 0.75f + 0.65f / 2, pos.z - 0.05f - rot);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f + 0.65f / 2, pos.y + 0.75f + 0.65f / 2, pos.z - 0.05f + 0.70f / 2 - rot);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f + 0.70f / 2 - rot);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f + 0.65f / 2, pos.y + 0.75f, pos.z - 0.05f - rot);
		}

		if (renderLeft) {
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f, pos.y + 0.75f + 0.65f / 2, pos.z - 0.05f + 0.70f / 2 + rot);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f, pos.y + 0.75f + 0.65f / 2, pos.z - 0.05f + rot);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x - rot + 0.05f, pos.y + 0.75f, pos.z - 0.05f + rot);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + rot + 0.05f, pos.y + 0.75f, pos.z - 0.05f + 0.70f / 2 + rot);
		}
	}

	protected static void renderBody() {
		color = new Vector4f(1f, 0f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CubeTerrain.waterTexture.getTextureID());
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColor4f(color.x, color.y, color.z, color.a);

		GL11.glBegin(GL11.GL_QUADS);

		if (renderTop) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y + 0.75f, pos.z);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y + 0.75f, pos.z + 0.25f);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z + 0.25f);
		}

		if (renderBottom) {
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z + 0.25f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z + 0.25f);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z);
		}

		if (renderFront) {
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z + 0.25f);
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y + 0.75f, pos.z + 0.25f);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z + 0.25f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z + 0.25f);
		}

		if (renderBack) {
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y + 0.75f, pos.z);
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z);
		}

		if (renderRight) {
			GL11.glNormal3f(1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z);
			GL11.glNormal3f(1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y + 0.75f, pos.z + 0.25f);
			GL11.glNormal3f(1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z + 0.25f);
			GL11.glNormal3f(1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x + 0.45f, pos.y, pos.z);
		}

		if (renderLeft) {
			GL11.glNormal3f(-1.0f, 1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y + 0.75f, pos.z + 0.25f);
			GL11.glNormal3f(-1.0f, 1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(pos.x, pos.y + 0.75f, pos.z);
			GL11.glNormal3f(-1.0f, -1.0f, -1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z);
			GL11.glNormal3f(-1.0f, -1.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(pos.x, pos.y, pos.z + 0.25f);
		}
	}

	public void rotate(int x, int y, int z) {
		GL11.glRotatef(-x, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-y, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-z, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-x, -y, -z);
	}

	boolean isJumping = false;

	public void jump(double d) {
		isJumping = true;
		Vector3f newCoordinates = null;
		if (isJumping) {
			newCoordinates = new Vector3f(pos.x, pos.y, pos.z);

			newCoordinates.y += d;
		}
		if (!collision(pos.x, newCoordinates.y, pos.z)) {
			pos.y = newCoordinates.y;
		}
		if (!collision(pos.x, newCoordinates.y + 4, pos.z)) {
			isJumping = false;
		}
	}
}
