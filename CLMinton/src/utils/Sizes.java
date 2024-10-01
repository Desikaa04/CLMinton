package utils;

public enum Sizes {
	SCENE_SIZES(1050, 600);

	
	private int width;
	private int height;
	private int size;
	
	private Sizes(int w, int h) {
		this.width = w;
		this.height = h;
	}
	
	private Sizes(int s) {
		this.size = s;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSize() {
		return size;
	}
}
