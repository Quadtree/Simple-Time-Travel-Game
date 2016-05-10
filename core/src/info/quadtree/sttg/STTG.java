package info.quadtree.sttg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import info.quadtree.sttg.world.DeterministicRNG;
import info.quadtree.sttg.world.DeterministicRNGDecorator;
import info.quadtree.sttg.world.FixedDRNG;
import info.quadtree.sttg.world.Gender;
import info.quadtree.sttg.world.NameGenerator;

public class STTG extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont fnt;

	Texture img;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		fnt = new BitmapFont(Gdx.files.internal("mono-18.fnt"), Gdx.files.internal("mono-18.png"), false);

		DeterministicRNG rng = new DeterministicRNGDecorator(new FixedDRNG(), 10);

		// for (int i = 0; i < 1000; ++i) {
		// System.err.println(i + " = " + rng.randomInt(16, i));
		// }

		System.err.println(NameGenerator.getInstance().generateName(Gender.Female, rng));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 30, 0);

		batch.setColor(Color.BROWN);

		fnt.setColor(Color.CYAN);
		fnt.draw(batch, "Inv? ≈≈≈~≈", 50, 300);

		batch.end();
	}
}
