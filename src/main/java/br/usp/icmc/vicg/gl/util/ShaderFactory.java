/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.icmc.vicg.gl.util;

/**
 *
 * @author paulovich
 */
public class ShaderFactory {

  public enum ShaderType {

    SIMPLE_SHADER,
    TRANSFORM_SHADER,
    MODEL_MATRIX_SHADER,
    MODEL_PROJECTION_MATRIX_SHADER,
    VIEW_MODEL_PROJECTION_MATRIX_SHADER,
    LIGHT_SHADER,
    TEXTURE_SHADER,
    NORMALMAP_SHADER,
    COMPLETE_SHADER,
    SKYBOX_SHADER
  };

  public static Shader getInstance(ShaderType type) {
    if (null != type) switch (type) {
          case SIMPLE_SHADER:
              return new Shader("simple_vertex.glsl", "simple_fragment.glsl");
          case TRANSFORM_SHADER:
              return new Shader("transform_vertex.glsl", "simple_fragment.glsl");
          case MODEL_MATRIX_SHADER:
              return new Shader("model_vertex.glsl", "simple_fragment.glsl");
          case MODEL_PROJECTION_MATRIX_SHADER:
              return new Shader("model_projection_vertex.glsl", "simple_fragment.glsl");
          case VIEW_MODEL_PROJECTION_MATRIX_SHADER:
              return new Shader("view_model_projection_vertex.glsl", "simple_fragment.glsl");
          case LIGHT_SHADER:
              return new Shader("light_vertex.glsl", "light_fragment.glsl");
          case TEXTURE_SHADER:
              return new Shader("texture_vertex.glsl", "texture_fragment.glsl");
          case NORMALMAP_SHADER:
              return new Shader("normalmap_vertex.glsl", "normalmap_fragment.glsl");
          case COMPLETE_SHADER:
              return new Shader("complete_vertex.glsl", "complete_fragment.glsl");
          case SKYBOX_SHADER:
              return new Shader("skybox_vertex.glsl", "skybox_fragment.glsl");
          default:
              break;
      }
    return null;
  }
}
