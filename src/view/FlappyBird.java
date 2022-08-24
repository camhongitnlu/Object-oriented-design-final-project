package view;

import java.awt.Dimension;

import javax.swing.JFrame;

import controller.Controller;
import controller.IController;
import model.Bird;
import model.GroupOfTubes;

public class FlappyBird {
	public static void main(String[] args) {
		Bird b = new Bird(GameView.WIDTH / 2 - 200, GameView.HEIGHT / 2 - 50, 50, 49);
		IController controler = new Controller(b);
	}
}
