package com.longarmx.color;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ClickableButton extends Component{
	
	private static int srcX = 50;
	private static int srcY = 50;
	private static int srcWidth = 50;
	private static int srcHeight = 50;
	
	private String text = "";
	private int scale = 1;
	private FontManager manager;
	private ClickManager clickManager;
	
	private float r = .9f;
	private float g = .9f;
	private float b = .9f;
	
	public ClickableButton(int x, int y, ClickManager clickManager) {
		super(x, y, srcX, srcY, srcWidth, srcHeight);
		this.clickManager = clickManager;
		create();
	}

	public ClickableButton(int x, int y, int width, int height, ClickManager clickManager) {
		super(x, y, width, height, srcX, srcY, srcWidth, srcHeight);
		this.clickManager = clickManager;
		create();
	}
	
	private void create(){
		manager = new FontManager();
	}
	
	public void setText(String text, int scale){
		this.text = text;
		this.scale = scale;
	}
	
	public void setHighlightColor(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void render(SpriteBatch batch){
		if(selected){
			batch.setColor(r, g, b, 1);
		}else{
			batch.setColor(1, 1, 1, 1);
		}
		super.render(batch);
		manager.draw(text, x + width/2 - (int)manager.getTextWidth(text, scale)/2, y + height/2 - (int)manager.getTextHeight(scale)/2, scale, batch);
	}

	@Override
	public void update(){
		if(Input.mouseX > x && Input.mouseX < x+width && Input.mouseY > y && Input.mouseY < y+height){
			selected = true;
			if(Input.mouseDown){
				clickManager.onClick();
			}
		}else{
			selected = false;
		}
	}

}
