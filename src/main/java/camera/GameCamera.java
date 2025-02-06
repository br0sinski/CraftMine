package camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import game.Game;

import static org.lwjgl.glfw.GLFW.*;

public class GameCamera {
    private Vector3f position;
    private float pitch, yaw; // Rotation angles

    private float speed = 0.05f; // Movement speed
    private float sensitivity = 0.1f; // Mouse sensitivity

    public GameCamera(Vector3f position) {
        this.position = position;
        this.pitch = 0;
        this.yaw = -90; // Start looking straight
    }

    public void move(float deltaTime) {
        float cameraSpeed = speed * deltaTime;

        if (GLFW.glfwGetKey(Game.window, GLFW_KEY_W) == GLFW_PRESS) {
            position.x += Math.sin(Math.toRadians(yaw)) * cameraSpeed;
            position.z -= Math.cos(Math.toRadians(yaw)) * cameraSpeed;
        }
        if (GLFW.glfwGetKey(Game.window, GLFW_KEY_S) == GLFW_PRESS) {
            position.x -= Math.sin(Math.toRadians(yaw)) * cameraSpeed;
            position.z += Math.cos(Math.toRadians(yaw)) * cameraSpeed;
        }
        if (GLFW.glfwGetKey(Game.window, GLFW_KEY_A) == GLFW_PRESS) {
            position.x -= Math.cos(Math.toRadians(yaw)) * cameraSpeed;
            position.z -= Math.sin(Math.toRadians(yaw)) * cameraSpeed;
        }
        if (GLFW.glfwGetKey(Game.window, GLFW_KEY_D) == GLFW_PRESS) {
            position.x += Math.cos(Math.toRadians(yaw)) * cameraSpeed;
            position.z += Math.sin(Math.toRadians(yaw)) * cameraSpeed;
        }

        // Up/Down (vertical movement, optional)
        if (GLFW.glfwGetKey(Game.window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            position.y += cameraSpeed;
        }
        if (GLFW.glfwGetKey(Game.window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            position.y -= cameraSpeed;
        }
    }

    public void rotate(float dx, float dy) {
        yaw += dx * sensitivity;
        pitch -= dy * sensitivity;

        if (pitch > 89) pitch = 89;
        if (pitch < -89) pitch = -89;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f view = new Matrix4f();

        // LookAt: Position is negated to move the world in the opposite direction
        view.lookAt(position, new Vector3f((float) (position.x + Math.sin(Math.toRadians(yaw))),
                        (float) (position.y + Math.sin(Math.toRadians(pitch))),
                        (float) (position.z - Math.cos(Math.toRadians(yaw)))),
                new Vector3f(0, 1, 0));

        return view;
    }

    public void updateMousePosition(float mouseX, float mouseY) {
        // Calculate the difference in mouse movement and rotate camera accordingly
        float dx = mouseX - (Game.getWIDTH() / 2);
        float dy = mouseY - (Game.getHEIGHT() / 2);

        rotate(dx, dy);

        // Reset mouse to center for continuous movement
        GLFW.glfwSetCursorPos(Game.window, Game.getWIDTH() / 2, Game.getHEIGHT() / 2);
    }

    public Vector3f getPosition() {
        return position;
    }
}
