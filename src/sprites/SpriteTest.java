package sprites;

import static org.junit.Assert.*;

import org.junit.Test;

public class SpriteTest {

	@Test
	public void test() {
		// Set up
		BannerSprite bannerSprite = new BannerSprite(50, 60);
		
		BarSprite barSprite = new BarSprite(50, 60, 70, 80);
		
		TextSprite textSprite = new TextSprite(50, 60, "Hello");
		SystemTextCenterFloat floatSprite = new SystemTextCenterFloat(50, 60, "Hello");
		
		DotSpriteBackground dotSpriteBackground = new DotSpriteBackground(5, 5, 5, 5, false, 5, 5);
		dotSpriteBackground.setScreenSize(50, 50);
		
		// Test getters
		assertTrue(bannerSprite.getX() == 50);
		assertTrue(bannerSprite.getY() == 60);
		
		assertTrue(barSprite.getWidth() == 70);
		
		assertTrue(textSprite.getText().equals("Hello"));
		
		assertTrue(dotSpriteBackground.getScreenHeight() == 50);
		
		assertFalse(floatSprite.shouldRemove());
		
		for(int i = 0 ; i < 2000 ; i++) {
			floatSprite.update();
		}
		
		assertTrue(floatSprite.shouldRemove());
		

	}

}
