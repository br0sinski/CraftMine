#version 330 core

in vec3 FragColor;
out vec4 FragColorOutput;

void main() {
    FragColorOutput = vec4(FragColor, 1.0);
}
