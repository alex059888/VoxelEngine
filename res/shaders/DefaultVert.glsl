#version 400 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCoords;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 transform;

out vec4 fColor;
out vec2 fTexCoords;

void main()
{
    gl_Position = projection * view * transform * vec4(aPos, 1.0f);
    fColor = aColor;
    fTexCoords = aTexCoords;
}