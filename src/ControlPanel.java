
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

class ControlPanel extends JPanel {

	JFrame topFrame;
	CanvasPanel canvPanel;

	public ControlPanel(JFrame mainFrame) {
		topFrame = mainFrame;
		initUI();
	}

	private void initUI() {

		MigLayout layout = new MigLayout("wrap 2", "align 50%");
		setLayout(layout);
		setBorder(BorderFactory.createLineBorder(Color.black));

		JButton ballButton = new JButton("Toggle Ball");
		ballButton.addActionListener((ActionEvent e) -> {
			canvPanel.toggleBall();
		});
		
		JLabel ballSizeLabel = new JLabel("Ball Size");
		
		JButton addBallSizeButton = new JButton("+");
		addBallSizeButton.addActionListener((ActionEvent e) -> {
			canvPanel.addBallSize();
		});
		
		JButton subBallSizeButton = new JButton("-");
		subBallSizeButton.addActionListener((ActionEvent e) -> {
			canvPanel.subBallSize();
		});
		
		JButton dotButton = new JButton("Dots");
		dotButton.addActionListener((ActionEvent e) -> {
			canvPanel.toggleDots();
			canvPanel.clearTrail(2);
		});
		
		JLabel dotsStepLabel = new JLabel("Number of Dots");
		
		JButton addDotsButton = new JButton("-");
		addDotsButton.addActionListener((ActionEvent e) -> {
			canvPanel.addDotsStep();
		});

		JButton subDotsButton = new JButton("+");
		subDotsButton.addActionListener((ActionEvent e) -> {
			canvPanel.subDotsStep();
		});
		
		JButton trailButton = new JButton("Toggle Trail");
		trailButton.addActionListener((ActionEvent e) -> {
			canvPanel.toggleTrail();
			canvPanel.clearTrail(1);
		});
		
		JLabel trailStepLabel = new JLabel("Trail Fineness");
		
		JButton addStepButton = new JButton("-");
		addStepButton.addActionListener((ActionEvent e) -> {
			canvPanel.addTrailStep();
		});

		JButton subStepButton = new JButton("+");
		subStepButton.addActionListener((ActionEvent e) -> {
			canvPanel.subTrailStep();
		});
		
		JLabel XVelLabel = new JLabel("X Velocity");

		JButton addButton = new JButton("+1");
		addButton.addActionListener((ActionEvent e) -> {
			canvPanel.addXVel();
		});

		JButton subButton = new JButton("-1");
		subButton.addActionListener((ActionEvent e) -> {
			canvPanel.subtractXVel();
		});
		
		JPanel ballPanel = new JPanel();
		MigLayout ballPanelLayout = new MigLayout("wrap 2", "align 50%");
		ballPanel.setLayout(ballPanelLayout);
		ballPanel.add(ballButton, "span 2");
		ballPanel.add(ballSizeLabel, "span 2");
		ballPanel.add(subBallSizeButton);
		ballPanel.add(addBallSizeButton);
		ballPanel.setBorder(BorderFactory.createTitledBorder("Ball Options"));
		
		JPanel dotsPanel = new JPanel();
		MigLayout dotsPanelLayout = new MigLayout("wrap 2", "align 50%");
		dotsPanel.setLayout(dotsPanelLayout);
		dotsPanel.add(ballPanel, "wrap");
		dotsPanel.add(dotButton, "span 2");
		dotsPanel.add(dotsStepLabel, "span 2");
		dotsPanel.add(addDotsButton);
		dotsPanel.add(subDotsButton);
		dotsPanel.setBorder(BorderFactory.createTitledBorder("Dots Options"));
		
		JPanel trailPanel = new JPanel();
		MigLayout trailPanelLayout = new MigLayout("wrap 2", "align 50%");
		trailPanel.setLayout(trailPanelLayout);
		trailPanel.add(trailButton, "span 2");
		trailPanel.add(trailStepLabel, "span 2");
		trailPanel.add(addStepButton);
		trailPanel.add(subStepButton);
		trailPanel.setBorder(BorderFactory.createTitledBorder("Ball Trail Options"));
		
		JPanel xVelPanel = new JPanel();
		MigLayout xVelPanelLayout = new MigLayout();
		xVelPanel.setLayout(xVelPanelLayout);
		xVelPanel.add(XVelLabel, "span 2, wrap");
		xVelPanel.add(addButton);
		xVelPanel.add(subButton);
		xVelPanel.setBorder(BorderFactory.createTitledBorder("Velocity"));
		
		add(ballPanel, "span");
		add(trailPanel, "span");
		add(dotsPanel, "span");
		add(xVelPanel, "span");
	}

	public void postInit() {
		setGraphicsPanel();
	}

	private void setGraphicsPanel() {
		canvPanel = getCanvasPanel(topFrame.getContentPane().getComponents());
	}

	private CanvasPanel getCanvasPanel(Component[] c) {
		for (Component comp : c) {
			if (comp instanceof CanvasPanel) {
				CanvasPanel p = (CanvasPanel) comp;
				return p;
			}

		}
		System.out.println("Canvas panel not found!");
		return null;
	}
}
