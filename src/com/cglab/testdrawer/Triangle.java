package com.cglab.testdrawer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Triangle {

	//All the buffers used for rendering
	private FloatBuffer vertexBuffer = null; 	
	private ShortBuffer indicesBuffer = null; 
	private FloatBuffer colorBuffer = null;
 
	private int numOfIndices = 0;  
 
    private float vertexList[] = { 
   		-1.0f,-1.0f,0.0f, //left
   		1.0f,-1.0f,0.0f, //right
   		0.0f,1.0f,0.0f	//top
    };  
 
 
    // Set triangle border buffer with vertex indices
    private short indexList[] = { 
      0,1,2
    };
 
 
    private float colorList[] = { 
 	       1.0f, 0.0f, 0.0f, 1.0f,
 	       1.0f, 1.0f, 0.0f, 1.0f,
 	       0.0f, 1.0f, 0.0f, 1.0f
 	     }; 
 
 
    public Triangle(){
 
    	// Set vertex buffer
	     ByteBuffer vbb = ByteBuffer.allocateDirect(vertexList.length * 4); 
	     vbb.order(ByteOrder.nativeOrder());
	     vertexBuffer = vbb.asFloatBuffer();
	     vertexBuffer.put(vertexList); 
	     vertexBuffer.position(0); 
 
	     // Set indices buffer
	     numOfIndices = indexList.length; 
	     ByteBuffer tbibb = ByteBuffer.allocateDirect(numOfIndices * 2); 
	     tbibb.order(ByteOrder.nativeOrder());
	     indicesBuffer = tbibb.asShortBuffer();
	     indicesBuffer.put(indexList); 
	     indicesBuffer.position(0);
 
	     // Set color buffer
	     ByteBuffer cbb = ByteBuffer.allocateDirect(colorList.length * 4); 
	     cbb.order(ByteOrder.nativeOrder());
	     colorBuffer = cbb.asFloatBuffer();
	     colorBuffer.put(colorList); 
	     colorBuffer.position(0);
 
    }
 
    public void draw(GL10 gl){
 
 
	     // the vertex array is enabled for writing and used during rendering
	     gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	     // the color array is enabled for writing and used during rendering
	     gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
 
 
 
	     gl.glColorPointer(4, GL10.GL_FLOAT, 0, this.colorBuffer);
	     /* specifies the location and data format of an array of 
	     vertex coordinates to use when rendering */
	     gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer); 
 
	     //DRAW THE TRIANGLE
	     gl.glDrawElements(GL10.GL_TRIANGLES, this.numOfIndices, 
	       GL10.GL_UNSIGNED_SHORT, this.indicesBuffer);
 
	     gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	     gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
