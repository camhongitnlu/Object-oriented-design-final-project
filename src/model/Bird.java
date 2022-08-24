package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import model.SoundPlayer;
import view.FlappyBird;
import view.GameView;
import view.SetCharacterObserver;

public class Bird extends GameModel implements Subject {
	private int dy;
	private int number, score;
	private SoundPlayer hitSound, flapSound, getScoreSound;
	private List<SetCharacterObserver> listCharacter = new ArrayList<SetCharacterObserver>();

	public Bird(int x, int y, int width, int height) {
		super(x, y, width, height);
		try {
			image = ImageIO.read(new File("Assets/bird.png"));
		} catch (IOException e) {

		}
		rect = new Rectangle(x, y, width, height);
		this.dy = 4;
		flapSound = new SoundPlayer(new File("Assets/fap.wav"));
		hitSound = new SoundPlayer(new File("Assets/fall.wav"));
		getScoreSound = new SoundPlayer(new File("Assets/getpoint.wav"));
	}

	@Override
	public void tick() {
		if (dy < 5) {
			dy += 2;
		}
		this.y += dy;
		rect.setLocation(x, y);
		checkBorder();
	}

	public void fly() {
		if (dy > 0) {
			dy = 0;
		}
		dy -= 15;
		flapSound.play();
	}

	public void checkBorder() {
		if (y < 0)
			this.y = 0;
	}

	public boolean checkCollision(GroupOfTubes tubeColumn) {
		Rectangle birdRect = this.getRect();
		Rectangle tubeRect;
		for (int i = 0; i < tubeColumn.getTubes().size(); i++) {
			tubeRect = tubeColumn.getTubes().get(i).getRect();
			if (birdRect.intersects(tubeRect) || this.getY() + this.getHeight() > GameView.HEIGHT) {
				this.getHitSound().play();
				score = 0;
				return true;
			}
		}
		return false;
	}

	public int score(GroupOfTubes tubeColumn) {
		for (Tube t : tubeColumn.getTubes()) {
			if (this.getX() + this.getWidth() / 2 > t.getX() + t.getWidth() / 2 - 5
					&& this.getX() + this.getWidth() / 2 < t.getX() + t.getWidth() / 2 + 5) {
				score++;
				this.getGetScoreSound().play();
			}
		}
		return score;
	}

	@Override
	public void render(Graphics2D g, ImageObserver obs) {
		g.drawImage(image, x, y, obs);
	}

	// character
	@Override
	public void register(SetCharacterObserver obs) {
		listCharacter.add(obs);
	}

	@Override
	public void remove(SetCharacterObserver obs) {
		listCharacter.remove(obs);
	}

	@Override
	public void notifyToObserver() {
		for (SetCharacterObserver c : listCharacter)
			c.updateCharacter(this.number);

	}

	public void changeCharacter(int number) {
		this.number = number;
		notifyToObserver();
	}

	public void setLocationForBird() {
		this.x = GameView.WIDTH / 2 - 200;
		this.y = GameView.HEIGHT / 2 - 50;
		this.width = 50;
		this.height = 49;
	}

	// sound
	public SoundPlayer getHitSound() {
		return hitSound;
	}

	public SoundPlayer getFlapSound() {
		return flapSound;
	}

	public SoundPlayer getGetScoreSound() {
		return getScoreSound;
	}

}
