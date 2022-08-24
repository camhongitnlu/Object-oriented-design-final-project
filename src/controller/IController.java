package controller;

import model.GroupOfTubes;

public interface IController {

	public void controllRelease();

	public void changeCharacter(int number);

	public void restart();

	public void checkCollision();

	public int score();
	
	public GroupOfTubes getTubeColumns();

}
