package window;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    
    public Main() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable To Initialize GLFW");
        }
        
        long win = glfwCreateWindow(800, 600, "Hello World!", NULL, NULL);
        if (win == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed To Create The GLFW Window");
        }
        
        glfwMakeContextCurrent(win);
        GL.createCapabilities();
        
        glEnable(GL_TEXTURE_2D);
        
        //Texture tex = new Texture("something.png");
        
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
        
        while (!glfwWindowShouldClose(win)) {
            double time = glfwGetTime();
            glfwPollEvents();
            
            if(glfwGetKey(win, GLFW_KEY_D) == GL_TRUE) {
            	angle -= 0.01;
            	}
            
            if(glfwGetKey(win, GLFW_KEY_A) == GL_TRUE) {
            	angle += 0.01;
            }
            
            if(glfwGetKey(win, GLFW_KEY_W) == GL_TRUE) {
            	y += 0.01;
            }
            
            if(glfwGetKey(win, GLFW_KEY_S) == GL_TRUE) {
            	y -= 0.01;
            }
            
            glClear(GL_COLOR_BUFFER_BIT);
            
            //tex.bind();
            shader.bind();
            model.render();
            
            square.render();

            glLoadIdentity();
            glTranslatef(-x, -y, 0.0f);
            
            glfwSwapBuffers(win);
            
            if(glfwGetKey(win, GLFW_KEY_ESCAPE) == GL_TRUE) {
                glfwSetWindowShouldClose(win, true);
            }
        }
        
        glfwDestroyWindow(win);
        glfwTerminate();
    }
    
    public static void main(String[] args) {
        new Main();
    }    
}