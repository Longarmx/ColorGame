/*
 * Copyright 2013 Longarmx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.longarmx.color;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class GuiGame extends Gui implements Disposable{
	
	public List<Component> components = new ArrayList<Component>();
	
	private int buttonSize = 75;
	private float multiplierSize = 1.0f;
	private boolean multiplierGrowing = true;
	
	private Game game;
	
	private ComponentColorButton red;
	private ComponentColorButton green;
	private ComponentColorButton blue;
	private ComponentColorButton dark;
	private ComponentColorButton light;
	
	private ComponentClickableButton back;
	private ComponentClickableButton restart;
	
	public GuiGame(){
		this.game = Main.instance;
		create();
	}
	
	public void create(){
		super.create();
		
		red = new ComponentColorButton(100, 100, buttonSize, buttonSize);
		red.setColor(1, 0, 0, 1);
		components.add(red);
		
		green = new ComponentColorButton(200, 100, buttonSize, buttonSize);
		green.setColor(0, 1, 0, 1);
		components.add(green);
		
		blue = new ComponentColorButton(300, 100, buttonSize, buttonSize);
		blue.setColor(0, 0, 1, 1);
		components.add(blue);
		
		dark = new ComponentColorButton(Main.ORIGINAL_WIDTH - (200 + buttonSize), 100, buttonSize, buttonSize);
		dark.setColor(.3f, .3f, .3f, 1);
		components.add(dark);
		
		light = new ComponentColorButton(Main.ORIGINAL_WIDTH - (100 + buttonSize), 100, buttonSize, buttonSize);
		light.setColor(.8f, .8f, .8f, 1);
		components.add(light);
		
		back = new ComponentClickableButton(100, 30, 250, 50, new ClickManager(){

			@Override
			public void onClick() {
				game.state = States.TITLE;
			}
			
		}).setHighlightColor(1, .5f, .5f).setText("Back to Menu", 2);
		
		restart = new ComponentClickableButton(Main.WIDTH - 250 - 100, 30, 250, 50, new ClickManager(){

			@Override
			public void onClick() {
				game.reset();
			}
			
		}).setHighlightColor(.5f, 1, .5f).setText("Restart", 2);
	}
	
	public void render(SpriteBatch batch){
		update();
		
		for(Component component: components){
			component.render(batch);
		}
		
		manager.setColor(0, 0, 0, 1);
		manager.draw("Score: " + String.valueOf(game.score), 10, Main.ORIGINAL_HEIGHT - 60, 5, batch);
		
		if(game.multiplier > 1){
			manager.setColor(1, 1, 0, .5f);
			manager.draw("x" + game.multiplier, 240 - (int)manager.getTextHeight(4 * multiplierSize)/2, Main.ORIGINAL_HEIGHT - 67 - (int)manager.getTextHeight(4), 4 * multiplierSize, batch);
		}
		
		if(game.player.isDead){
			manager.setColor(1, 0, 0, 1);
			manager.draw("Game Over", Main.ORIGINAL_WIDTH/2 - (int)manager.getTextWidth("Game Over", 5)/2, Main.ORIGINAL_HEIGHT/2 - 25, 5, batch);
			back.render(batch);
			restart.render(batch);
		}else if(game.paused){
			manager.setColor(0, 1, 0, 1);
			manager.draw("Paused", Main.ORIGINAL_WIDTH/2 - (int)manager.getTextWidth("Paused", 5)/2, Main.ORIGINAL_HEIGHT/2 - 25, 5, batch);
		}
		
		if(!game.enemies.isEmpty()){
			manager.setColor(0, 0, 0, 1);
			manager.draw(game.enemies.get(0).getColorString(), 651, 719, 3, batch);
			manager.setColor(game.enemies.get(0).getRed(), game.enemies.get(0).getGreen(), game.enemies.get(0).getBlue(), 1.0f);
			manager.draw(game.enemies.get(0).getColorString(), 650, 720, 3, batch);
		}
	}
	
	public void update(){
		if(game.player.redDown) red.selected = true;
		else red.selected = false;
		
		if(game.player.greenDown) green.selected = true;
		else green.selected = false;
		
		if(game.player.blueDown) blue.selected = true;
		else blue.selected = false;
		
		if(game.player.darkDown) dark.selected = true;
		else dark.selected = false;
		
		if(game.player.lightDown) light.selected = true;
		else light.selected = false;
		
		if(game.multiplier > 1){
			if(multiplierGrowing){
				multiplierSize += .01f;
				if(multiplierSize > 1.2f)	multiplierGrowing = false;
			}else{
				multiplierSize -= .01f;
				if(multiplierSize < 1.0f)	multiplierGrowing = true;
			}
		}
		
		if(game.player.isDead){
			back.update();
			restart.update();
		}
	}
	
	@Override
	public void dispose(){
		super.dispose();
		restart.dispose();
		back.dispose();
	}

}
