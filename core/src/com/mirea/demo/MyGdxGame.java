package com.mirea.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;


public class MyGdxGame extends ApplicationAdapter {
	private ShapeRenderer shapes;
	private Batch batch;

	private ProcedureMapGenerator map;
	private TileMap tileMap;
	private ContentMap contentMap;


	@Override
	public void create() {
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();

		Texture floor = new Texture("floor1.png");
		Texture wall = new Texture("floor2.png");
		Texture content1 = new Texture("box.png");
		Texture content2 = new Texture("chest.png");
		Texture content3 = new Texture("enemy.png");

		tileMap = new TileMap(80, 60);
		tileMap.setTileSize(10);
		tileMap.addBlockTexture(new TextureRegion(wall), Textures.FLOOR_TEXTURE_ID);
		tileMap.addBlockTexture(new TextureRegion(floor), Textures.WALL_TEXTURE_ID);

		contentMap = new ContentMap();
		contentMap.addContent(new Content(new TextureRegion(content1)), Textures.CONTENT_1);
		contentMap.addContent(new Content(new TextureRegion(content2)), Textures.CONTENT_2);
		contentMap.addContent(new Content(new TextureRegion(content3)), Textures.CONTENT_3);

		map = new ProcedureMapGenerator(tileMap, contentMap, 5, 1, 25, 3);
		map.generate();

		contentMap.scale(tileMap.getTileSize());
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
//		map.draw(shapes, 10);

		batch.begin();
		tileMap.draw(batch);
		contentMap.draw(batch);
		batch.end();
		map.drawOverlay(shapes, 10);
	}
	
	@Override
	public void dispose() {
		shapes.dispose();
	}
}
