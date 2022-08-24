package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.CharConversionException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import controller.Controller;
import controller.IController;
import model.Bird;
import model.GameModel;
import model.GroupOfTubes;
import model.Tube;

public class GameView extends JPanel implements ActionListener, SetCharacterObserver {
	private boolean isRunning = false;
	private boolean gameOver = false;
	private BufferedImage background;
	// model
	private GroupOfTubes tubeColumn;
	private Bird bird;
	// controler
	private IController controler;
	private int score;
	private int highScore;
	private JMenu jmncharacter;
	private JMenuItem characterYellow, characterRed, characterBlue;
	public static int WIDTH = 900;
	public static int HEIGHT = 520;

	public GameView(Bird b, IController controler) {
		try {
			background = ImageIO.read(new File("Assets/background.png"));
		} catch (IOException e) {

		}
		this.bird = b;
		this.controler = controler;
		this.tubeColumn = controler.getTubeColumns();
		bird.register(this);
		setFocusable(true);
		setDoubleBuffered(false);
		Timer timer = new Timer(15, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isRunning) {
			tubeColumn.tick();
			bird.tick();
			controler.checkCollision();
			score = controler.score();

		}

		repaint();
	}

	private ActionListener actionCharacter() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getActionCommand().equals("yellow"))
					controler.changeCharacter(1);
				else if (e.getActionCommand().equals("red"))
					controler.changeCharacter(2);
				else if (e.getActionCommand().equals("blue"))
					controler.changeCharacter(3);
			}
		};
	}

	public void createView() {
		JFrame frame = new JFrame("Flappy Bird");
		JMenuBar jmb = new JMenuBar();
		jmncharacter = new JMenu("Character");
		characterYellow = new JMenuItem("Yellow Bird");
		characterRed = new JMenuItem("Red Bird");
		characterBlue = new JMenuItem("Blue Bird");
		jmncharacter.add(characterYellow);
		jmncharacter.add(characterRed);
		jmncharacter.add(characterBlue);
		jmb.add(jmncharacter);
		frame.setJMenuBar(jmb);

		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public void eventForMenu() {
		characterYellow.addActionListener(actionCharacter());
		characterYellow.setActionCommand("yellow");

		characterRed.addActionListener(actionCharacter());
		characterRed.setActionCommand("red");

		characterBlue.addActionListener(actionCharacter());
		characterBlue.setActionCommand("blue");
	}

	@Override
	public void updateCharacter(int number) {
		if (number == 1) {
			try {
				bird.setImage(ImageIO.read(new File("Assets/bird.png")));
			} catch (IOException e) {
			}
		} else if (number == 2) {
			try {
				bird.setImage(ImageIO.read(new File("Assets/redBird.png")));
			} catch (IOException e) {

			}
		} else {
			try {
				bird.setImage(ImageIO.read(new File("Assets/blueBird.png")));
			} catch (IOException e) {

			}
		}
		repaint();

	}

	public void restart() {
		if (!isRunning) {
			isRunning = true;
			controler.restart();
		}
	}

	public void endGame() {
		isRunning = false;
		gameOver = true;
		if (score > highScore) {
			highScore = score;
		}
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
		if (isRunning) {
			this.bird.render(g2, this);
			this.tubeColumn.render(g2, this);
			g2.setColor(Color.black);
			g.setFont(new Font("Arial", 1, 20));
			g2.drawString("Your score: " + score, 0, 17);
		} else {
			if (gameOver) {
				g2.setColor(Color.WHITE);
				g.setFont(new Font("Arial", 1, 20));
				g2.drawString("Game Over - Press any key to Start again", WIDTH / 2 - 200, HEIGHT / 2 - 20);
			} else {

				g2.setColor(Color.black);
				g.setFont(new Font("Arial", 1, 20));
				g2.drawString("Please choose character and press any key to Start and Play", WIDTH / 2 - 300,
						HEIGHT / 2 - 20);

			}
		}
		g2.setColor(Color.black);
		g.setFont(new Font("Arial", 1, 20));
		g2.drawString("High Score: " + highScore, WIDTH - 160, 20);
	}

	public GroupOfTubes getTubeColumn() {
		return tubeColumn;
	}

	public void setTubeColumn(GroupOfTubes tubeColumn) {
		this.tubeColumn = tubeColumn;
	}

}
