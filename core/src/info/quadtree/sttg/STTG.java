package info.quadtree.sttg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import info.quadtree.sttg.world.TerrainType;
import info.quadtree.sttg.world.WorldPosition;
import info.quadtree.sttg.world.WorldState;
import info.quadtree.sttg.world.thing.Person;
import info.quadtree.sttg.world.thing.Traveller;

public class STTG extends ApplicationAdapter implements InputProcessor {
	final public static int CAMERA_HEIGHT = 40;
	final public static int CAMERA_WIDTH = 40;

	SpriteBatch batch;

	WorldState currentWorldState;

	BitmapFont fnt;
	Texture img;

	char[][] mainViewBuffer;
	Color[][] mainViewBufferColor;

	Person traveller;

	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);

		mainViewBuffer = new char[CAMERA_WIDTH][];
		mainViewBufferColor = new Color[CAMERA_WIDTH][];
		for (int x = 0; x < CAMERA_WIDTH; ++x) {
			mainViewBuffer[x] = new char[CAMERA_HEIGHT];
			mainViewBufferColor[x] = new Color[CAMERA_HEIGHT];
		}

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		fnt = new BitmapFont(Gdx.files.internal("mono-18.fnt"), Gdx.files.internal("mono-18.png"), false);

		// DeterministicRNG rng = new DeterministicRNGDecorator(new FixedDRNG(),
		// 10);

		// for (int i = 0; i < 1000; ++i) {
		// System.err.println(i + " = " + rng.randomInt(16, i));
		// }

		// System.err.println(NameGenerator.getInstance().generateName(Gender.Female,
		// rng));

		long miliTime = System.currentTimeMillis();

		currentWorldState = new WorldState(1);
		currentWorldState.seek(WorldState.TICKS_PER_YEAR * 75 + 500000);

		traveller = new Traveller(currentWorldState, new WorldPosition(100, 256));

		System.out.println("Traveller is " + traveller.getName());
		currentWorldState.addThing(traveller);

		long endTime = System.currentTimeMillis();

		System.err.println("Startup in: " + (endTime - miliTime) + "ms");
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.A) {
			traveller.moveTo(traveller.getLocation().add(new WorldPosition(-1, 0)));
		}
		if (keycode == Keys.D) {
			traveller.moveTo(traveller.getLocation().add(new WorldPosition(1, 0)));
		}
		if (keycode == Keys.W) {
			traveller.moveTo(traveller.getLocation().add(new WorldPosition(0, -1)));
		}
		if (keycode == Keys.S) {
			traveller.moveTo(traveller.getLocation().add(new WorldPosition(0, 1)));
		}

		long currentTick = currentWorldState.getCurrentTick();

		if (keycode == Keys.LEFT_BRACKET) {
			currentWorldState = new WorldState(0);
			currentWorldState.seek(currentTick - WorldState.TICKS_PER_YEAR);
			currentWorldState.addThing(traveller);
		}
		if (keycode == Keys.RIGHT_BRACKET) {
			currentWorldState.seek(currentTick + WorldState.TICKS_PER_YEAR);
		}

		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render() {
		WorldPosition cameraLocation = traveller.getLocation();

		for (int x = 0; x < CAMERA_WIDTH; ++x) {
			for (int y = 0; y < CAMERA_HEIGHT; ++y) {
				int worldX = x - CAMERA_WIDTH / 2 + cameraLocation.x;
				int worldY = y - CAMERA_HEIGHT / 2 + cameraLocation.y;

				TerrainType tt = currentWorldState.getTerrainTypeAt(worldX, worldY);

				mainViewBuffer[x][y] = tt.visual;
				mainViewBufferColor[x][y] = tt.color;
			}
		}

		currentWorldState.render(mainViewBuffer, mainViewBufferColor, cameraLocation);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		for (int x = 0; x < CAMERA_WIDTH; ++x) {
			for (int y = 0; y < CAMERA_HEIGHT; ++y) {
				fnt.setColor(mainViewBufferColor[x][y]);
				fnt.draw(batch, "" + mainViewBuffer[x][y], x * 14, Gdx.graphics.getHeight() - (y * 18));
			}
		}

		fnt.setColor(Color.WHITE);
		fnt.draw(batch, "Tick: " + currentWorldState.getCurrentTick(), 14, Gdx.graphics.getHeight() - ((CAMERA_HEIGHT + 1) * 18));

		batch.end();

		while (!traveller.canAct()) {
			currentWorldState.update(1);
		}
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
}
