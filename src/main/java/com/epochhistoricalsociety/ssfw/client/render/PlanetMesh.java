package com.epochhistoricalsociety.ssfw.client.render;

//import foundy.veil.Veil;
import foundry.veil.api.client.render.vertex.VertexArrayObject;
import foundry.veil.api.client.render.buffer.BufferObject;
import foundry.veil.api.client.render.vertex.VertexLayout;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class PlanetMesh {

    public final VertexArrayObject vao;
    private final BufferObject vbo;

    public PlanetMesh() {
        float[] vertices = {
                -0.5f,-0.5f,0.5f, 0.5f,-0.5f,0.5f, 0.5f,0.5f,0.5f,
                0.5f,0.5f,0.5f,-0.5f,0.5f,0.5f,-0.5f,-0.5f,0.5f,

                -0.5f,-0.5f,-0.5f,-0.5f,0.5f,-0.5f,0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,0.5f,-0.5f,-0.5f,-0.5f,-0.5f,-0.5f,

                -0.5f,0.5f,0.5f,-0.5f,0.5f,-0.5f,-0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,-0.5f,-0.5f,0.5f,-0.5f,0.5f,0.5f,

                0.5f,0.5f,0.5f,0.5f,-0.5f,-0.5f,0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,0.5f,0.5f,0.5f,0.5f,-0.5f,0.5f,

                -0.5f,0.5f,-0.5f,-0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,
                0.5f,0.5f,0.5f,0.5f,0.5f,-0.5f,-0.5f,0.5f,-0.5f,

                -0.5f,-0.5f,-0.5f,0.5f,-0.5f,0.5f,-0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,-0.5f,-0.5f,-0.5f,0.5f,-0.5f,-0.5f
        };

        vao = new VertexArrayObject();
        vbo = new BufferObject(GL15.GL_ARRAY_BUFFER);

        FloatBuffer buffer = MemoryUtil.memAllocFloat(vertices.length);
        buffer.put(vertices).flip();

        vbo.uploadData(buffer, GL15.GL_STATIC_DRAW);
        MemoryUtil.memFree(buffer);

        vao.bind();

        VertexLayout layout = VertexLayout.builder()
                .attribute(0, 3, GL15.GL_FLOAT, false, 0)
                .build();
        layout.apply();

        vao.unbind();
    }
}
