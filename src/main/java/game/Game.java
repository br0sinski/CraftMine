package game;

import camera.GameCamera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import textures.Skybox;

import java.nio.*;

public class Game {

    public static long window;
    private static GameCamera camera;
    private Skybox skybox;

    private static final int WIDTH = 800, HEIGHT = 600;

    private double lastTime;

    public void init() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "CraftMine - Totally not a Minecraft Clone I wrote just for fun :D", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        camera = new GameCamera(new Vector3f(0, 1, 5)); // Create the camera at position (0, 1, 5)
        skybox = new Skybox();
        skybox.create();



        GLFW.glfwSetCursorPosCallback(window, (window, xpos, ypos) -> camera.updateMousePosition((float) xpos, (float) ypos));

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1); // Enable v-sync
        GLFW.glfwShowWindow(window);

        GL.createCapabilities(); // This is important for OpenGL functions

        // Set OpenGL settings (clear color, etc.)
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Initialize the time variable to calculate deltaTime
        lastTime = GLFW.glfwGetTime();
    }

    public void loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Calculate deltaTime for consistent movement speed
            double currentTime = GLFW.glfwGetTime();
            float deltaTime = (float) (currentTime - lastTime);
            lastTime = currentTime;

            // Update camera movement
            camera.move(deltaTime); // Pass deltaTime to move the camera

            // Clear the screen
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // Here you would use your camera's view matrix for 3D rendering
            // For now, we will just clear the screen and use a basic camera
            Matrix4f viewMatrix = camera.getViewMatrix();

            // Swap the buffers and poll events
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    public void cleanUp() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        skybox.cleanup();
    }

    public void run() {
        Game game = new Game();
        game.init();
        game.loop();
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }
}
