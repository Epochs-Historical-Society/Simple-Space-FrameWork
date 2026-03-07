package com.epochhistoricalsociety.ssfw.client.render;

//import foundry.veil.Veil;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PlanetRenderer {

    private final PlanetMesh mesh = new PlanetMesh();
    private ShaderProgram shader;
    private final List<Vector3f> planetPositions = new ArrayList<>();

    public void init() {
        shader = ShaderProgram.load("ssfw:planet");
    }

    public void addPlanet(float x, float y, float z) {
        planetPositions.add(new Vector3f(x, y, z));
    }

    public void render() {
        Minecraft mc = Minecraft.getInstance();

        // Get camera position
        var camera = mc.gameRenderer.getMainCamera();
        Vector3f camPos = new Vector3f(
                (float) camera.getPosition().x,
                (float) camera.getPosition().y,
                (float) camera.getPosition().z
        );

        // Projection matrix from GameRenderer
        Matrix4f projection = mc.gameRenderer.getProjectionMatrix();

        // Simple view matrix translating by negative camera position
        Matrix4f view = new Matrix4f().identity().translate(-camPos.x, -camPos.y, -camPos.z);

        // View-projection matrix
        Matrix4f viewProj = new Matrix4f(projection).mul(view);

        shader.bind();

        for (Vector3f pos : planetPositions) {
            // Model matrix for each planet
            Matrix4f model = new Matrix4f().translate(pos).scale(2f); // scale = planet size
            Matrix4f mvp = new Matrix4f(viewProj).mul(model);
            shader.setUniform("uMVP", mvp);

            mesh.vao.bind();
            mesh.vao.drawArrays(36);
            mesh.vao.unbind();
        }

        shader.unbind();
    }

    public void clear() {
        planetPositions.clear();
    }
}
