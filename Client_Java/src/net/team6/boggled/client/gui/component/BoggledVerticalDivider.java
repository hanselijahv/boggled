package net.team6.boggled.client.gui.component;

import net.team6.boggled.client.state.State;
import net.team6.boggled.common.core.Size;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class BoggledVerticalDivider extends BoggledComponent {
	public BoggledVerticalDivider() {
		this.setSize(new Size(100, 2));
	}

	@Override
	public Image getSprite() {
		Image image = new BufferedImage(getSize().getWidth(), getSize().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = (Graphics2D) image.getGraphics();

		graphics.setColor(Color.GRAY);
		graphics.fillRect(0, 0, getSize().getWidth(), getSize().getHeight());

		graphics.dispose();
		return image;
	}

	@Override
	public void update(State state) throws SQLException {

	}
}