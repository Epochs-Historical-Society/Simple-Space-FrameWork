#version 150

in vec3 Position;
in vec4 Color;

out vec4 vertexColor;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

void main() {
    vertexColor = Color;
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}
