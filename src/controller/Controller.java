package controller;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import model.Bird;
import model.GroupOfTubes;
import model.Tube;
import view.FlappyBird;
import view.GameView;

public class Controller implements IController {
	private Bird b;// model
	private GameView view;// view
	private GroupOfTubes tubeColumn = new GroupOfTubes();

	public Controller(Bird bird) {
		super();
		this.b = bird;
		this.view = new GameView(b, this);
		view.createView();
		view.addKeyListener(new GameKey(this, view));
		view.eventForMenu();
	}

	@Override
	public void controllRelease() {
		b.fly();
	}

	@Override
	public void changeCharacter(int number) {
		b.changeCharacter(number);
	}

	@Override
	public void restart() {
		b.setLocationForBird();
		tubeColumn.setNewTube();
	}

	@Override
	public void checkCollision() {
		if (b.checkCollision(tubeColumn))
			view.endGame();

	}

	@Override
	public int score() {
		return b.score(tubeColumn);

	}

	private class GameKey extends KeyAdapter {
		private GameView view;
		private IController controler;

		public GameKey(IController controler, GameView view) {
			this.controler = controler;
			this.view = view;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			view.restart();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			controler.controllRelease();
		}
	}

	@Override
	public GroupOfTubes getTubeColumns() {
		return tubeColumn;
	}

}
