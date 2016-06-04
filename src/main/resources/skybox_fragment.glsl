#version 150

struct LightProperties
{
	vec4 position;
	vec4 ambientColor;
	vec4 diffuseColor;
	vec4 specularColor;
};

struct MaterialProperties
{
	vec4 ambientColor;
	vec4 diffuseColor;
	vec4 specularColor;
	float specularExponent;
};

uniform	LightProperties u_light;
uniform	MaterialProperties u_material;
uniform mat4 u_viewMatrix;
uniform mat4 u_modelMatrix;

uniform sampler2D u_texture;
uniform bool u_is_texture;

in vec3 v_normal;
in vec2 v_texcoord;
in vec4 v_position;

out vec4 fragColor;

void main(void)
{
    vec4 color = vec4(0, 0 , 0, 0);
	
	vec3 normal = normalize(v_normal);

    vec3 direction = normalize(vec3(u_viewMatrix * u_light.position));

	float nDotL = max(dot(direction, normal), 0.0);

	if (nDotL > 0.0)
	{
        if(u_is_texture) {
            color = texture(u_texture, v_texcoord);
        }
	}
	
	fragColor = color;
}
