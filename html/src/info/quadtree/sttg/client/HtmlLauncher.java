package info.quadtree.sttg.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import info.quadtree.sttg.STTG;

public class HtmlLauncher extends GwtApplication {

	@Override
	public ApplicationListener createApplicationListener() {
		return new STTG();
	}

	@Override
	public GwtApplicationConfiguration getConfig() {
		return new GwtApplicationConfiguration(1024, 768);
	}
}