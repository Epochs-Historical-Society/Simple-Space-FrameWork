package com.epochhistoricalsociety.ssfw;

import com.epochhistoricalsociety.ssfw.client.render.VeilCubeRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;
import com.mojang.blaze3d.vertex.PoseStack;

@EventBusSubscriber(
        modid = SimpleSpaceFrameWork.MODID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class SimpleSpaceFrameWorkClient {

    private static boolean loggedFirstRender = false;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        // Initialize our Veil mesh + shader
        VeilCubeRenderer.init();

        // Register our world render callback on the main NeoForge event bus
        NeoForge.EVENT_BUS.addListener(SimpleSpaceFrameWorkClient::renderLevel);

        SimpleSpaceFrameWork.LOGGER.info("Registered Veil cube renderer on NeoForge.EVENT_BUS");
    }

    public static void renderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            return;
        }

        PoseStack poseStack = event.getPoseStack();

        net.minecraft.world.phys.Vec3 camera =net.minecraft.client.Minecraft.getInstance()
            .gameRenderer.getMainCamera()
            .getPosition();
        net.minecraft.client.player.LocalPlayer player =net.minecraft.client.Minecraft.getInstance()
            .player;

        if (player == null) return;

        double x = 0.5;
        double y = 120.0;
        double z = 0.5;

        poseStack.pushPose();


        poseStack.translate(x,y,z);
        poseStack.translate(-camera.x, -camera.y, -camera.z);
        poseStack.scale(2.0F, 2.0F, 2.0F);

        if (!loggedFirstRender) {
            SimpleSpaceFrameWork.LOGGER.info(
                    "Rendering Veil test cube at world position ({}, {}, {})",
                    x, y, z
            );
            loggedFirstRender = true;
        }

        VeilCubeRenderer.render(
            poseStack,
            event.getProjectionMatrix()
        );

        poseStack.popPose();
    }


}
