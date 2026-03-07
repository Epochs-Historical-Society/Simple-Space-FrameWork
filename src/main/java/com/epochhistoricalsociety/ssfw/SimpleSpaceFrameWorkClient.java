package com.epochhistoricalsociety.ssfw;

import com.epochhistoricalsociety.ssfw.client.render.VeilCubeRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

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

        poseStack.pushPose();

        // Render a cube at a fixed world position near spawn
        double x = 0.5;
        double y = 120.0;
        double z = 0.5;

        poseStack.translate(x, y, z);
        poseStack.scale(1.0F, 1.0F, 1.0F);

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
