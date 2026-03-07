package com.epochhistoricalsociety.ssfw.client.render;

import com.epochhistoricalsociety.ssfw.SimpleSpaceFrameWork;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class VeilCubeRenderer {

    private static final ResourceLocation CUBE_SHADER = ResourceLocation.parse("ssfw:cube");

    /**
     * Called from client setup – kept for future expansion.
     */
    public static void init() {
        // No explicit initialization required for Veil shader programs;
        // they are loaded automatically from assets/ssfw/pinwheel/shaders/program.
    }

    public static void render(PoseStack poseStack, Matrix4f projection) {
        ShaderProgram shader = VeilRenderSystem.setShader(CUBE_SHADER);
        if (shader == null) {
            SimpleSpaceFrameWork.LOGGER.warn("Veil cube shader '{}' not found or failed to load", CUBE_SHADER);
            return;
        }

        Matrix4f modelView = poseStack.last().pose();

        shader.setDefaultUniforms(VertexFormat.Mode.QUADS, modelView, projection);
        shader.bind();

        BufferBuilder builder = Tesselator.getInstance().begin(
                VertexFormat.Mode.QUADS,
                DefaultVertexFormat.POSITION
        );

        // Front (+Z)
        builder.addVertex(modelView, -1, -1,  1);
        builder.addVertex(modelView,  1, -1,  1);
        builder.addVertex(modelView,  1,  1,  1);
        builder.addVertex(modelView, -1,  1,  1);

        // Back (-Z)
        builder.addVertex(modelView, -1, -1, -1);
        builder.addVertex(modelView, -1,  1, -1);
        builder.addVertex(modelView,  1,  1, -1);
        builder.addVertex(modelView,  1, -1, -1);

        // Left (-X)
        builder.addVertex(modelView, -1, -1, -1);
        builder.addVertex(modelView, -1, -1,  1);
        builder.addVertex(modelView, -1,  1,  1);
        builder.addVertex(modelView, -1,  1, -1);

        // Right (+X)
        builder.addVertex(modelView, 1, -1, -1);
        builder.addVertex(modelView, 1,  1, -1);
        builder.addVertex(modelView, 1,  1,  1);
        builder.addVertex(modelView, 1, -1,  1);

        // Bottom (-Y)
        builder.addVertex(modelView, -1, -1, -1);
        builder.addVertex(modelView,  1, -1, -1);
        builder.addVertex(modelView,  1, -1,  1);
        builder.addVertex(modelView, -1, -1,  1);

        // Top (+Y)
        builder.addVertex(modelView, -1, 1, -1);
        builder.addVertex(modelView, -1, 1,  1);
        builder.addVertex(modelView,  1, 1,  1);
        builder.addVertex(modelView,  1, 1, -1);

        BufferUploader.drawWithShader(builder.build());

        ShaderProgram.unbind();
    }
}
