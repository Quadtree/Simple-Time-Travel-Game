package info.quadtree.sttg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import info.quadtree.sttg.world.TerrainType;
import info.quadtree.sttg.world.WorldPosition;
import info.quadtree.sttg.world.WorldState;
import info.quadtree.sttg.world.thing.Person;

public class STTG extends ApplicationAdapter {
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

		currentWorldState = new WorldState(0);
		currentWorldState.seek(WorldState.TICKS_PER_YEAR * 100 + 500000);

		traveller = new Person(currentWorldState, new WorldPosition(100, 256));
		currentWorldState.addThing(traveller);

		long endTime = System.currentTimeMillis();

		System.err.println("Startup in: " + (endTime - miliTime) + "ms");
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

		batch.end();
	}
}
