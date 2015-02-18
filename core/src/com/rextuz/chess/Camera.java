package com.rextuz.chess;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {

	public Camera(int width, int height) {
		super(width, height);
        this.position.x=width/2;
        this.position.y=height/2;
	}
	
}
