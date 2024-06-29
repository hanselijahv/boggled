package net.team6.boggled.client.gui.container;
import net.team6.boggled.common.core.Position;
import net.team6.boggled.client.gui.component.BoggledComponent;
import net.team6.boggled.common.core.Size;

import java.util.ArrayList;
import java.util.List;

public class GridContainer extends BoggledContainer {
	private List<BoggledComponent> components;
	private int columns;

	public GridContainer(int columns) {
		this.columns = columns;
		this.components = new ArrayList<>();
	}

	@Override
	protected Size calculateContentSize() {
		// Calculate the size of the grid based on the size of the child components and the number of columns
		int maxChildWidth = components.stream().mapToInt(component -> component.getSize().getWidth()).max().orElse(0);
		int maxChildHeight = components.stream().mapToInt(component -> component.getSize().getHeight()).max().orElse(0);
		int totalWidth = maxChildWidth * columns;
		int totalHeight = maxChildHeight * ((components.size() + columns - 1) / columns);
		return new Size(totalWidth, totalHeight);
	}

	@Override
	protected void calculateContentPosition() {
		// Position the child components in a grid
		int maxChildWidth = components.stream().mapToInt(component -> component.getSize().getWidth()).max().orElse(0);
		int maxChildHeight = components.stream().mapToInt(component -> component.getSize().getHeight()).max().orElse(0);
		for (int i = 0; i < components.size(); i++) {
			int x = (i % columns) * maxChildWidth;
			int y = (i / columns) * maxChildHeight;
			components.get(i).setRelativePosition(new Position(x, y));
		}
	}

	@Override
	public void addUIComponent(BoggledComponent component) {
		super.addUIComponent(component);
		components.add(component);
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
}