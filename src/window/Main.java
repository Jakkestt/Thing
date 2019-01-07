package window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.joml.*;

import org.lwjgl.opengl.GL;

public class Main {
    
    public Main() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable To Initialize GLFW");
        }
        
        Window win = new Window();
        win.setSize(1200, 900);
        win.createWindow("Game");
        
        GL.createCapabilities();
        
        Camera camera = new Camera(640, 480);
        
        glEnable(GL_TEXTURE_2D);
        
        Texture tex = new Texture("something.png");
        
        Matrix4f scale = new Matrix4f()
        		.translate(new Vector3f(100, 0, 0))
        		.scale(128);
        
        Matrix4f target = new Matrix4f();
        
        glClearColor(0, 0, 0, 1);
        
        float x = 0;
        float y = 0;
        float angle = 0;
        
        float[] vertices = new float[] {
        		-0.5f, 0.5f, 0,	//TOP LEFT     0
        		0.5f, 0.5f, 0, 	//TOP RIGHT    1
        		0.5f, -0.5f, 0, //BOTTOM RIGHT 2
        		-0.5f, -0.5f, 0,//BOTTOM LEFT  3
        };
        
        float[] texture = new float[] {
        		0,0,
        		1,0,
        		1,1,
        		0,1,
        };
        
        int[] indices = new int[] {
        		0,1,2,
        		2,3,0
        };
        
        float[] coords = new float[] {
        		0.5f, 1.0f, 0,
        		1.0f, 1.0f, 0,
        		1.0f, 0.5f, 0,
        		0.5f, 0.5f, 0,
        };
        
        Model model = new Model(vertices, texture, indices);
        
        Model square = new Model(coords, texture, indices);
        
        Shader shader = new Shader("shader");
        
        while (!win.shouldClose()) {
        	target = scale;
        	
            double time = glfwGetTime();
            glfwPollEvents();
            
            glClear(GL_COLOR_BUFFER_BIT);
            
            tex.bind(0);
            shader.bind();
            shader.setUniform("sampler", 0);
            shader.setUniform("projection", camera.getProjection().mul(target));
            
            model.render();
            
            square.render();

            glLoadIdentity();
            glTranslatef(-x, -y, 0.0f);

            camera.setPosition(new Vector3f(-100 + x, 0 - y, 0));
            
            win.swapBuffers();
        }
    }
    
    public static void main(String[] args) {
        new Main();
    }    
}