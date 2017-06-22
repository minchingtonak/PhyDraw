
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame {
	// TODO add sliders, move ball with cursor, draw with cursor, make reset
	// trail + start button

	public MainFrame() {
		initUI();
	}

	void initUI() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		// MigLayout layout = new MigLayout("ax right");
		BorderLayout bl = new BorderLayout();
		setLayout(bl);

		ControlPanel cp = new ControlPanel(this);
		add(cp, BorderLayout.EAST);

		CanvasPanel ball = new CanvasPanel();
		add(ball, BorderLayout.WEST);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Timer timer = ball.getTimer();
				timer.stop();
			}
		});

		setTitle("PhyDraw");
		setSize(1280, 960);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		cp.postInit();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			MainFrame main = new MainFrame();
			main.setVisible(true);
		});
	}
}
