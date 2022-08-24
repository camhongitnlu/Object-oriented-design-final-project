package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import view.FlappyBird;
import view.GameView;

public class GroupOfTubes {
	private List<Tube> tubes;

	public GroupOfTubes() {
		tubes = new ArrayList<Tube>();
		initTubes(true);
	}

	public int getRandomY() {
		Random random = new Random();
		int a;
		a = random.nextInt(10);

		return a * 15;
	}

	private void initTubes(boolean start) {

		for (int i = 0; i < 3; i++) {
			int ranway = getRandomY();
			if (start) {
				tubes.add(new Tube(900 + i * 450, -300 + ranway, 70, 378, 2));
				tubes.add(new Tube(900 + i * 450, 220 + ranway, 70, 378, 1));
			} else {
				int size = tubes.size() - 1;
				tubes.add(new Tube(tubes.get(size).getX() + 450 + ranway, -300 + ranway, 70, 378, 2));
				tubes.add(new Tube(tubes.get(size).getX() + 450 + ranway, 220 + ranway, 70, 378, 1));
			}
		}
	}

	public void tick() {
		for (int i = 0; i < tubes.size(); i++) {
			tubes.get(i).tick();
			if (tubes.get(i).getX() + tubes.get(i).getWidth() < 0) {
				tubes.remove(tubes.get(i));
				initTubes(false);
			}
		}
	}

	public List<Tube> getTubes() {
		return tubes;
	}

	public void setTubes(List<Tube> tubes) {
		this.tubes = tubes;
	}

	public void render(Graphics2D g, ImageObserver obs) {
		for (Tube t : tubes) {
			t.render(g, obs);
		}
	}

	public void setNewTube() {
		tubes.clear();
		initTubes(true);
	}
}
