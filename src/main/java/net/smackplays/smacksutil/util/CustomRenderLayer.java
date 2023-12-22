package net.smackplays.smacksutil.util;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

import java.util.OptionalDouble;

public class CustomRenderLayer extends RenderLayer {
    public static final RenderLayer LINES = RenderLayer.of("lines", VertexFormats.LINES,
            VertexFormat.DrawMode.LINES, 1536,
            MultiPhaseParameters.builder()
                    .program(LINES_PROGRAM)
                    .lineWidth(new LineWidth(OptionalDouble.empty()))
                    .layering(VIEW_OFFSET_Z_LAYERING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .target(OUTLINE_TARGET)
                    .writeMaskState(ALL_MASK)
                    .cull(DISABLE_CULLING)
                    .build(false));

    /*
    public static final RenderLayer LINES_TRANSLUCENT = RenderLayer.of("lines", VertexFormats.LINES,
            VertexFormat.DrawMode.LINES, 1536,
            MultiPhaseParameters.builder()
                    .program(LINES_PROGRAM)
                    .lineWidth(new RenderPhase.LineWidth(OptionalDouble.empty()))
                    .layering(NO_LAYERING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .target(ITEM_ENTITY_TARGET)
                    .writeMaskState(ALL_MASK)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .depthTest(LEQUAL_DEPTH_TEST)
                    .build(false));
     */

    public CustomRenderLayer(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }
}
