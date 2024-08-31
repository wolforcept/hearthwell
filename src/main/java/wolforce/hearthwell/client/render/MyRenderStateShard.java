//package wolforce.hearthwell.client.render;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.math.MatrixUtil;
//import net.minecraft.Util;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraft.client.renderer.RenderStateShard;
//import net.minecraft.client.renderer.ShaderInstance;
//import net.minecraft.client.renderer.texture.TextureAtlas;
//import net.minecraft.client.renderer.texture.TextureManager;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import org.apache.commons.lang3.tuple.Triple;
//import org.joml.Matrix4f;
//import org.joml.Vector3f;
//
//import java.util.Objects;
//import java.util.Optional;
//import java.util.OptionalDouble;
//import java.util.function.Supplier;
//
//@SuppressWarnings({ "unused", "deprecation", "resource" })
//@OnlyIn(Dist.CLIENT)
//public abstract class MyRenderStateShard {
//	private static final float VIEW_SCALE_Z_EPSILON = 0.99975586F;
//	public final String name;
//	public Runnable setupState;
//	private final Runnable clearState;
//	public static final MyRenderStateShard.TransparencyStateShard NO_TRANSPARENCY = new MyRenderStateShard.TransparencyStateShard(
//			"no_transparency", () -> {
//				RenderSystem.disableBlend();
//			}, () -> {
//			});
//	public static final MyRenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new MyRenderStateShard.TransparencyStateShard(
//			"additive_transparency", () -> {
//				RenderSystem.enableBlend();
//				RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
//			}, () -> {
//				RenderSystem.disableBlend();
//				RenderSystem.defaultBlendFunc();
//			});
//	public static final MyRenderStateShard.TransparencyStateShard LIGHTNING_TRANSPARENCY = new MyRenderStateShard.TransparencyStateShard(
//			"lightning_transparency", () -> {
//				RenderSystem.enableBlend();
//				RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
//			}, () -> {
//				RenderSystem.disableBlend();
//				RenderSystem.defaultBlendFunc();
//			});
//	public static final MyRenderStateShard.TransparencyStateShard GLINT_TRANSPARENCY = new MyRenderStateShard.TransparencyStateShard(
//			"glint_transparency", () -> {
//				RenderSystem.enableBlend();
//				RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE,
//						GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
//			}, () -> {
//				RenderSystem.disableBlend();
//				RenderSystem.defaultBlendFunc();
//			});
//	public static final MyRenderStateShard.TransparencyStateShard CRUMBLING_TRANSPARENCY = new MyRenderStateShard.TransparencyStateShard(
//			"crumbling_transparency", () -> {
//				RenderSystem.enableBlend();
//				RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR,
//						GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//			}, () -> {
//				RenderSystem.disableBlend();
//				RenderSystem.defaultBlendFunc();
//			});
//	public static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
//			"translucent_transparency", () -> {
//				RenderSystem.enableBlend();
//				RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
//						GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//			}, () -> {
//				RenderSystem.disableBlend();
//				RenderSystem.defaultBlendFunc();
//			});
//	public static final MyRenderStateShard.ShaderStateShard NO_SHADER = new MyRenderStateShard.ShaderStateShard();
//	public static final MyRenderStateShard.ShaderStateShard POSITION_COLOR_LIGHTMAP_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getPositionColorLightmapShader);
//	public static final MyRenderStateShard.ShaderStateShard POSITION_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getPositionShader);
//	public static final MyRenderStateShard.ShaderStateShard POSITION_COLOR_TEX_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getPositionColorTexShader);
//	public static final MyRenderStateShard.ShaderStateShard POSITION_TEX_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getPositionTexShader);
//	public static final MyRenderStateShard.ShaderStateShard POSITION_COLOR_TEX_LIGHTMAP_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getPositionColorTexLightmapShader);
//	public static final MyRenderStateShard.ShaderStateShard POSITION_COLOR_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getPositionColorShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_SOLID_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeSolidShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_CUTOUT_MIPPED_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeCutoutMippedShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_CUTOUT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeCutoutShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_TRANSLUCENT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeTranslucentShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_TRANSLUCENT_MOVING_BLOCK_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeTranslucentMovingBlockShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_TRANSLUCENT_NO_CRUMBLING_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeTranslucentNoCrumblingShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ARMOR_CUTOUT_NO_CULL_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeArmorCutoutNoCullShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_SOLID_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntitySolidShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityCutoutShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityCutoutNoCullShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_NO_CULL_Z_OFFSET_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityCutoutNoCullZOffsetShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeItemEntityTranslucentCullShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityTranslucentCullShader);
//	public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_TRANSLUCENT_SHADER = new RenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityTranslucentShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_SMOOTH_CUTOUT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntitySmoothCutoutShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_BEACON_BEAM_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeBeaconBeamShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_DECAL_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityDecalShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_NO_OUTLINE_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityNoOutlineShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_SHADOW_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityShadowShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_ALPHA_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityAlphaShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_EYES_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEyesShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENERGY_SWIRL_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEnergySwirlShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_LEASH_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeLeashShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_WATER_MASK_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeWaterMaskShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_OUTLINE_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeOutlineShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ARMOR_GLINT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeArmorGlintShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeArmorEntityGlintShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_GLINT_TRANSLUCENT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeGlintTranslucentShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_GLINT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeGlintShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_GLINT_DIRECT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeGlintDirectShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_GLINT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityGlintShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEntityGlintDirectShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_CRUMBLING_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeCrumblingShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_TEXT_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeTextShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_TEXT_INTENSITY_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeTextIntensityShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_TEXT_SEE_THROUGH_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeTextSeeThroughShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeTextIntensitySeeThroughShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_LIGHTNING_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeLightningShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_TRIPWIRE_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeTripwireShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_END_PORTAL_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEndPortalShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_END_GATEWAY_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeEndGatewayShader);
//	public static final MyRenderStateShard.ShaderStateShard RENDERTYPE_LINES_SHADER = new MyRenderStateShard.ShaderStateShard(
//			GameRenderer::getRendertypeLinesShader);
//	public static final MyRenderStateShard.TextureStateShard BLOCK_SHEET_MIPPED = new MyRenderStateShard.TextureStateShard(
//			TextureAtlas.LOCATION_BLOCKS, false, true);
//	public static final MyRenderStateShard.TextureStateShard BLOCK_SHEET = new MyRenderStateShard.TextureStateShard(
//			TextureAtlas.LOCATION_BLOCKS, false, false);
//	public static final MyRenderStateShard.EmptyTextureStateShard NO_TEXTURE = new MyRenderStateShard.EmptyTextureStateShard();
//	public static final MyRenderStateShard.TexturingStateShard DEFAULT_TEXTURING = new MyRenderStateShard.TexturingStateShard(
//			"default_texturing", () -> {
//			}, () -> {
//			});
//	public static final MyRenderStateShard.TexturingStateShard GLINT_TEXTURING = new MyRenderStateShard.TexturingStateShard(
//			"glint_texturing", () -> {
//				setupGlintTexturing(8.0F);
//			}, () -> {
//				RenderSystem.resetTextureMatrix();
//			});
//	public static final MyRenderStateShard.TexturingStateShard ENTITY_GLINT_TEXTURING = new MyRenderStateShard.TexturingStateShard(
//			"entity_glint_texturing", () -> {
//				setupGlintTexturing(0.16F);
//			}, () -> {
//				RenderSystem.resetTextureMatrix();
//			});
//	public static final RenderStateShard.LightmapStateShard LIGHTMAP = new RenderStateShard.LightmapStateShard(true);
//	public static final MyRenderStateShard.LightmapStateShard NO_LIGHTMAP = new MyRenderStateShard.LightmapStateShard(false);
//	public static final RenderStateShard.OverlayStateShard OVERLAY = new RenderStateShard.OverlayStateShard(true);
//	public static final MyRenderStateShard.OverlayStateShard NO_OVERLAY = new MyRenderStateShard.OverlayStateShard(false);
//	public static final MyRenderStateShard.CullStateShard CULL = new MyRenderStateShard.CullStateShard(true);
//	public static final RenderStateShard.CullStateShard NO_CULL = new RenderStateShard.CullStateShard(false);
//	public static final MyRenderStateShard.DepthTestStateShard NO_DEPTH_TEST = new MyRenderStateShard.DepthTestStateShard("always", 519);
//	public static final MyRenderStateShard.DepthTestStateShard EQUAL_DEPTH_TEST = new MyRenderStateShard.DepthTestStateShard("==", 514);
//	public static final MyRenderStateShard.DepthTestStateShard LEQUAL_DEPTH_TEST = new MyRenderStateShard.DepthTestStateShard("<=", 515);
//	public static final MyRenderStateShard.WriteMaskStateShard COLOR_DEPTH_WRITE = new MyRenderStateShard.WriteMaskStateShard(true, true);
//	public static final MyRenderStateShard.WriteMaskStateShard COLOR_WRITE = new MyRenderStateShard.WriteMaskStateShard(true, false);
//	public static final MyRenderStateShard.WriteMaskStateShard DEPTH_WRITE = new MyRenderStateShard.WriteMaskStateShard(false, true);
//	public static final MyRenderStateShard.LayeringStateShard NO_LAYERING = new MyRenderStateShard.LayeringStateShard("no_layering", () -> {
//	}, () -> {
//	});
//	public static final MyRenderStateShard.LayeringStateShard POLYGON_OFFSET_LAYERING = new MyRenderStateShard.LayeringStateShard(
//			"polygon_offset_layering", () -> {
//				RenderSystem.polygonOffset(-1.0F, -10.0F);
//				RenderSystem.enablePolygonOffset();
//			}, () -> {
//				RenderSystem.polygonOffset(0.0F, 0.0F);
//				RenderSystem.disablePolygonOffset();
//			});
//	public static final MyRenderStateShard.LayeringStateShard VIEW_OFFSET_Z_LAYERING = new MyRenderStateShard.LayeringStateShard(
//			"view_offset_z_layering", () -> {
//				PoseStack posestack = RenderSystem.getModelViewStack();
//				posestack.pushPose();
//				posestack.scale(0.99975586F, 0.99975586F, 0.99975586F);
//				RenderSystem.applyModelViewMatrix();
//			}, () -> {
//				PoseStack posestack = RenderSystem.getModelViewStack();
//				posestack.popPose();
//				RenderSystem.applyModelViewMatrix();
//			});
//	public static final MyRenderStateShard.OutputStateShard MAIN_TARGET = new MyRenderStateShard.OutputStateShard("main_target", () -> {
//	}, () -> {
//	});
//	public static final MyRenderStateShard.OutputStateShard OUTLINE_TARGET = new MyRenderStateShard.OutputStateShard("outline_target",
//			() -> {
//				Minecraft.getInstance().levelRenderer.entityTarget().bindWrite(false);
//			}, () -> {
//				Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
//			});
//	public static final MyRenderStateShard.OutputStateShard TRANSLUCENT_TARGET = new MyRenderStateShard.OutputStateShard(
//			"translucent_target", () -> {
//				if (Minecraft.useShaderTransparency()) {
//					Minecraft.getInstance().levelRenderer.getTranslucentTarget().bindWrite(false);
//				}
//
//			}, () -> {
//				if (Minecraft.useShaderTransparency()) {
//					Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
//				}
//
//			});
//	public static final MyRenderStateShard.OutputStateShard PARTICLES_TARGET = new MyRenderStateShard.OutputStateShard("particles_target",
//			() -> {
//				if (Minecraft.useShaderTransparency()) {
//					Minecraft.getInstance().levelRenderer.getParticlesTarget().bindWrite(false);
//				}
//
//			}, () -> {
//				if (Minecraft.useShaderTransparency()) {
//					Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
//				}
//
//			});
//	public static final MyRenderStateShard.OutputStateShard WEATHER_TARGET = new MyRenderStateShard.OutputStateShard("weather_target",
//			() -> {
//				if (Minecraft.useShaderTransparency()) {
//					Minecraft.getInstance().levelRenderer.getWeatherTarget().bindWrite(false);
//				}
//
//			}, () -> {
//				if (Minecraft.useShaderTransparency()) {
//					Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
//				}
//
//			});
//	public static final MyRenderStateShard.OutputStateShard CLOUDS_TARGET = new MyRenderStateShard.OutputStateShard("clouds_target", () -> {
//		if (Minecraft.useShaderTransparency()) {
//			Minecraft.getInstance().levelRenderer.getCloudsTarget().bindWrite(false);
//		}
//
//	}, () -> {
//		if (Minecraft.useShaderTransparency()) {
//			Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
//		}
//
//	});
//	public static final MyRenderStateShard.OutputStateShard ITEM_ENTITY_TARGET = new MyRenderStateShard.OutputStateShard(
//			"item_entity_target", () -> {
//				if (Minecraft.useShaderTransparency()) {
//					Minecraft.getInstance().levelRenderer.getItemEntityTarget().bindWrite(false);
//				}
//
//			}, () -> {
//				if (Minecraft.useShaderTransparency()) {
//					Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
//				}
//
//			});
//	public static final MyRenderStateShard.LineStateShard DEFAULT_LINE = new MyRenderStateShard.LineStateShard(OptionalDouble.of(1.0D));
//
//	public MyRenderStateShard(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
//		this.name = p_110161_;
//		this.setupState = p_110162_;
//		this.clearState = p_110163_;
//	}
//
//	public void setupRenderState() {
//		this.setupState.run();
//	}
//
//	public void clearRenderState() {
//		this.clearState.run();
//	}
//
//	public String toString() {
//		return this.name;
//	}
//
//	private static void setupGlintTexturing(float p_110187_) {
//		long i = (long)((double)Util.getMillis() * Minecraft.getInstance().options.glintSpeed().get() * 8.0D);
//		float f = (float)(i % 110000L) / 110000.0F;
//		float f1 = (float)(i % 30000L) / 30000.0F;
//		Matrix4f matrix4f = (new Matrix4f()).translation(-f, f1, 0.0F);
//		matrix4f.rotateZ(0.17453292F).scale(p_110187_);
//		RenderSystem.setTextureMatrix(matrix4f);
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class BooleanStateShard extends MyRenderStateShard {
//		private final boolean enabled;
//
//		public BooleanStateShard(String p_110229_, Runnable p_110230_, Runnable p_110231_, boolean p_110232_) {
//			super(p_110229_, p_110230_, p_110231_);
//			this.enabled = p_110232_;
//		}
//
//		public String toString() {
//			return this.name + "[" + this.enabled + "]";
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class CullStateShard extends MyRenderStateShard.BooleanStateShard {
//		public CullStateShard(boolean p_110238_) {
//			super("cull", () -> {
//				if (!p_110238_) {
//					RenderSystem.disableCull();
//				}
//
//			}, () -> {
//				if (!p_110238_) {
//					RenderSystem.enableCull();
//				}
//
//			}, p_110238_);
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class DepthTestStateShard extends MyRenderStateShard {
//		private final String functionName;
//
//		public DepthTestStateShard(String p_110246_, int p_110247_) {
//			super("depth_test", () -> {
//				if (p_110247_ != 519) {
//					RenderSystem.enableDepthTest();
//					RenderSystem.depthFunc(p_110247_);
//				}
//
//			}, () -> {
//				if (p_110247_ != 519) {
//					RenderSystem.disableDepthTest();
//					RenderSystem.depthFunc(515);
//				}
//
//			});
//			this.functionName = p_110246_;
//		}
//
//		public String toString() {
//			return this.name + "[" + this.functionName + "]";
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class EmptyTextureStateShard extends MyRenderStateShard {
//		public EmptyTextureStateShard(Runnable p_173117_, Runnable p_173118_) {
//			super("texture", p_173117_, p_173118_);
//		}
//
//		EmptyTextureStateShard() {
//			super("texture", () -> {
//			}, () -> {
//			});
//		}
//
//		public Optional<ResourceLocation> cutoutTexture() {
//			return Optional.empty();
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class LayeringStateShard extends MyRenderStateShard {
//		public LayeringStateShard(String p_110267_, Runnable p_110268_, Runnable p_110269_) {
//			super(p_110267_, p_110268_, p_110269_);
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class LightmapStateShard extends MyRenderStateShard.BooleanStateShard {
//		public LightmapStateShard(boolean p_110271_) {
//			super("lightmap", () -> {
//				if (p_110271_) {
//					Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
//				}
//
//			}, () -> {
//				if (p_110271_) {
//					Minecraft.getInstance().gameRenderer.lightTexture().turnOffLightLayer();
//				}
//
//			}, p_110271_);
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class LineStateShard extends MyRenderStateShard {
//		private final OptionalDouble width;
//
//		public LineStateShard(OptionalDouble p_110278_) {
//			super("line_width", () -> {
//				if (!Objects.equals(p_110278_, OptionalDouble.of(1.0D))) {
//					if (p_110278_.isPresent()) {
//						RenderSystem.lineWidth((float) p_110278_.getAsDouble());
//					} else {
//						RenderSystem.lineWidth(Math.max(2.5F, (float) Minecraft.getInstance().getWindow().getWidth() / 1920.0F * 2.5F));
//					}
//				}
//
//			}, () -> {
//				if (!Objects.equals(p_110278_, OptionalDouble.of(1.0D))) {
//					RenderSystem.lineWidth(1.0F);
//				}
//
//			});
//			this.width = p_110278_;
//		}
//
//		public String toString() {
//			return this.name + "[" + (this.width.isPresent() ? this.width.getAsDouble() : "window_scale") + "]";
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class MultiTextureStateShard extends MyRenderStateShard.EmptyTextureStateShard {
//		private final Optional<ResourceLocation> cutoutTexture;
//
//		MultiTextureStateShard(ImmutableList<Triple<ResourceLocation, Boolean, Boolean>> p_173123_) {
//			super(() -> {
//				int i = 0;
//
//				for (Triple<ResourceLocation, Boolean, Boolean> triple : p_173123_) {
//					TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
//					texturemanager.getTexture(triple.getLeft()).setFilter(triple.getMiddle(), triple.getRight());
//					RenderSystem.setShaderTexture(i++, triple.getLeft());
//				}
//
//			}, () -> {
//			});
//			this.cutoutTexture = p_173123_.stream().findFirst().map(Triple::getLeft);
//		}
//
//		public Optional<ResourceLocation> cutoutTexture() {
//			return this.cutoutTexture;
//		}
//
//		public static MyRenderStateShard.MultiTextureStateShard.Builder builder() {
//			return new MyRenderStateShard.MultiTextureStateShard.Builder();
//		}
//
//		@OnlyIn(Dist.CLIENT)
//		public static final class Builder {
//			private final ImmutableList.Builder<Triple<ResourceLocation, Boolean, Boolean>> builder = new ImmutableList.Builder<>();
//
//			public MyRenderStateShard.MultiTextureStateShard.Builder add(ResourceLocation p_173133_, boolean p_173134_, boolean p_173135_) {
//				this.builder.add(Triple.of(p_173133_, p_173134_, p_173135_));
//				return this;
//			}
//
//			public MyRenderStateShard.MultiTextureStateShard build() {
//				return new MyRenderStateShard.MultiTextureStateShard(this.builder.build());
//			}
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static final class OffsetTexturingStateShard extends MyRenderStateShard.TexturingStateShard {
//		public OffsetTexturingStateShard(float p_110290_, float p_110291_) {
//			super("offset_texturing", () -> {
//				RenderSystem.setTextureMatrix(new Matrix4f().translation(p_110290_, p_110291_, 0.0F));
//			}, () -> {
//				RenderSystem.resetTextureMatrix();
//			});
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class OutputStateShard extends MyRenderStateShard {
//		public OutputStateShard(String p_110300_, Runnable p_110301_, Runnable p_110302_) {
//			super(p_110300_, p_110301_, p_110302_);
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class OverlayStateShard extends MyRenderStateShard.BooleanStateShard {
//		public OverlayStateShard(boolean p_110304_) {
//			super("overlay", () -> {
//				if (p_110304_) {
//					Minecraft.getInstance().gameRenderer.overlayTexture().setupOverlayColor();
//				}
//
//			}, () -> {
//				if (p_110304_) {
//					Minecraft.getInstance().gameRenderer.overlayTexture().teardownOverlayColor();
//				}
//
//			}, p_110304_);
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class ShaderStateShard extends MyRenderStateShard {
//		private final Optional<Supplier<ShaderInstance>> shader;
//
//		public ShaderStateShard(Supplier<ShaderInstance> p_173139_) {
//			super("shader", () -> {
//				RenderSystem.setShader(p_173139_);
//			}, () -> {
//			});
//			this.shader = Optional.of(p_173139_);
//		}
//
//		public ShaderStateShard() {
//			super("shader", () -> {
//				RenderSystem.setShader(() -> {
//					return null;
//				});
//			}, () -> {
//			});
//			this.shader = Optional.empty();
//		}
//
//		public String toString() {
//			return this.name + "[" + this.shader + "]";
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class TextureStateShard extends MyRenderStateShard.EmptyTextureStateShard {
//		private final Optional<ResourceLocation> texture;
//		public boolean blur;
//		public boolean mipmap;
//
//		public TextureStateShard(ResourceLocation p_110333_, boolean p_110334_, boolean p_110335_) {
//			super(() -> {
//				TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
//				texturemanager.getTexture(p_110333_).setFilter(p_110334_, p_110335_);
//				RenderSystem.setShaderTexture(0, p_110333_);
//			}, () -> {
//			});
//			this.texture = Optional.of(p_110333_);
//			this.blur = p_110334_;
//			this.mipmap = p_110335_;
//		}
//
//		public String toString() {
//			return this.name + "[" + this.texture + "(blur=" + this.blur + ", mipmap=" + this.mipmap + ")]";
//		}
//
//		public Optional<ResourceLocation> cutoutTexture() {
//			return this.texture;
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class TexturingStateShard extends MyRenderStateShard {
//		public TexturingStateShard(String p_110349_, Runnable p_110350_, Runnable p_110351_) {
//			super(p_110349_, p_110350_, p_110351_);
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class TransparencyStateShard extends MyRenderStateShard {
//		public TransparencyStateShard(String p_110353_, Runnable p_110354_, Runnable p_110355_) {
//			super(p_110353_, p_110354_, p_110355_);
//		}
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static class WriteMaskStateShard extends MyRenderStateShard {
//		private final boolean writeColor;
//		private final boolean writeDepth;
//
//		public WriteMaskStateShard(boolean p_110359_, boolean p_110360_) {
//			super("write_mask_state", () -> {
//				if (!p_110360_) {
//					RenderSystem.depthMask(p_110360_);
//				}
//
//				if (!p_110359_) {
//					RenderSystem.colorMask(p_110359_, p_110359_, p_110359_, p_110359_);
//				}
//
//			}, () -> {
//				if (!p_110360_) {
//					RenderSystem.depthMask(true);
//				}
//
//				if (!p_110359_) {
//					RenderSystem.colorMask(true, true, true, true);
//				}
//
//			});
//			this.writeColor = p_110359_;
//			this.writeDepth = p_110360_;
//		}
//
//		public String toString() {
//			return this.name + "[writeColor=" + this.writeColor + ", writeDepth=" + this.writeDepth + "]";
//		}
//	}
//}