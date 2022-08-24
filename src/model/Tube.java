package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.SetCharacterObserver;

public class Tube extends GameModel {

	public Tube(int x, int y, int w, int h, int number) {
		super(x, y, w, h);
		rect = new Rectangle(x, y, w, h);
		try {
			if (number == 1)
				image = ImageIO.read(new File("Assets/chimney.png"));
			else if (number == 2)
				image = ImageIO.read(new File("Assets/chimney2.png"));
		} catch (IOException e) {

		}
	}

	@Override
	public void tick() {
		this.x -= 5;
		rect.setLocation(x, y);
	}

	@Override
	public void render(Graphics2D g, ImageObserver obs) {
		g.drawImage(image, x, y, obs);
	}

}