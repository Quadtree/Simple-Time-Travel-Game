package info.quadtree.sttg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class STTG extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont fnt;

	Texture img;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		fnt = new BitmapFont(Gdx.files.internal("mono.fnt"), Gdx.files.internal("mono.png"), false);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 30, 0);

		batch.setColor(Color.BROWN);

		fnt.setColor(Color.CYAN);
		fnt.draw(batch, "Inv?", 50, 300);

		batch.end();
	}
}
