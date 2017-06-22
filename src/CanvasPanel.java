
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

class CanvasPanel extends JPanel implements ActionListener {

	private JFrame topFrame;
	private ControlPanel p;

	// Canvas properties
	private Timer timer;
	private int delay = 10;

	private int width;
	private int height;
	private int frameWidth;
	private int frameHeight;

	// Ball properties
	private int ballSize = 30;
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double xAccel;
	private double yAccel;
	private int trailStep = 1;
	private int dotsStep = 1;
	private boolean drawBall = false;
	private boolean drawTrail = false;
	private boolean drawDots = false;
	private boolean goingLeft = false;

	private ArrayList<double[]> positionList_trail = new ArrayList<double[]>(0);
	private ArrayList<double[]> positionList_dots = new ArrayList<double[]>(0);
	private ArrayList<double[]> velocityList = new ArrayList<double[]>(0);

	public CanvasPanel() {
		initUI();
		initTimer();
		initBall(0, 0, 2, 0, 0, 1);
	}

	private void initUI() {
		setBackground(Color.BLACK);
	}

	// Timer methods

	void initTimer() {
		timer = new Timer(delay, this);
		timer.start();
	}

	void toggleTimerRunning() {
		if (timer.isRunning()) {
			timer.stop();
		} else {
			timer.start();
		}
	}

	public Timer getTimer() {
		return timer;
	}

	// Ball methods

	private void initBall(double xPos, double yPos, double xVel, double yVel, double xAccel, double yAccel) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		this.xAccel = xAccel;
		this.yAccel = yAccel;
	}

	void iterate() {
		iterateArrays();
		xPos += xVel;
		yPos += yVel;
		xVel += xAccel;
		yVel += yAccel;
		checkBounds();
	}

	private void iterateArrays() {
		double[] temp = { xPos, yPos };
		positionList_trail.add(temp);
		positionList_dots.add(temp);
		double[] temp2 = { xVel, yVel };
		velocityList.add(temp2);
	}

	private void checkBounds() {
		if (xPos >= width - ballSize || xPos <= 0) {
			goingLeft = !goingLeft;
			xVel *= -1;
		}
		if (yPos >= height - ballSize - 30 || yPos < 0) {
			yVel -= yVel * 0.05;
			yVel *= -1;
		}
	}

	void drawBall(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.WHITE);
		iterate();
		g2d.fillOval((int) xPos, (int) yPos, ballSize, ballSize);
	}

	private void drawBallTrail(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int step = this.trailStep;
		float c = 0;
		for (int i = 0; i < positionList_trail.size() - step; i++, c += 0.1) {
			g2d.setPaint(Color.getHSBColor(c, 1.0f, 1.0f));
			g2d.drawLine((int) positionList_trail.get(i)[0] + (ballSize / 2),
					(int) positionList_trail.get(i)[1] + (ballSize / 2),
					(int) positionList_trail.get(i + step)[0] + (ballSize / 2),
					(int) positionList_trail.get(i + step)[1] + (ballSize / 2));
		}
	}

	private void drawBallDots(Graphics g) {	
		Graphics2D g2d = (Graphics2D) g;
		int step = dotsStep;
		float c = 0;
		for (int i = 0; i < positionList_dots.size() - step; i += step, c += 0.1) {
			g2d.setPaint(Color.getHSBColor(c, 1.0f, 1.0f));
			g2d.drawOval((int) positionList_dots.get(i)[0], (int) positionList_dots.get(i)[1], ballSize, ballSize);
		}
	}

	// Ball getters/setters

	public void toggleBall() {
		drawBall = !drawBall;
		positionList_dots.clear();
		positionList_trail.clear();
		initBall(0, 0, getXVel(), 0, 0, 1);
	}

	public void toggleTrail() {
		drawTrail = !drawTrail;
	}

	public void toggleDots() {
		drawDots = !drawDots;
	}

	public void addBallSize() {
		ballSize += 5;
	}

	public void subBallSize() {
		if (ballSize != 5) {
			ballSize -= 5;
		}
	}

	public void addXVel() {
		if (goingLeft) {
			xVel -= 1;
		} else {
			xVel += 1;
		}
	}

	public void subtractXVel() {
		if (goingLeft) {
			xVel += 1;
		} else {
			xVel -= 1;
		}
	}

	public void addTrailStep() {
		this.trailStep++;
	}

	public void subTrailStep() {
		if (trailStep > 1) {
			trailStep--;
		}
	}

	public void addDotsStep() {
		dotsStep++;
	}

	public void subDotsStep() {
		if (dotsStep > 1) {
			dotsStep--;
		}
	}

	public double getXVel() {
		return xVel;
	}

	private void updateSizes() {
		topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		p = getControlPanel(topFrame.getContentPane().getComponents());
		this.frameWidth = topFrame.getWidth();
		this.frameHeight = topFrame.getHeight();
		this.width = getWidth();
		this.height = getHeight();
	}

	public void clearTrail(int trail) {
		if (trail == 1) {
			positionList_trail.clear();
		} else if (trail == 2) {
			positionList_dots.clear();
		}
	}

	ControlPanel getControlPanel(Component[] c) {
		for (Component comp : c) {
			if (comp instanceof ControlPanel) {
				ControlPanel p = (ControlPanel) comp;
				return p;
			}
		}
		System.out.println("Control panel not found!");
		return null;
	}

	@Override
	protected void paintComponent(Graphics g) {
		updateSizes();
		super.paintComponent(g);
		if (drawBall) {
			drawBall(g);
		}
		if (drawTrail) {
			drawBallTrail(g);
		}
		if (drawDots) {
			drawBallDots(g);
		}
		setSize(frameWidth - p.getWidth() - 22, frameHeight - 50);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}