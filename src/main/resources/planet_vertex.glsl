#version 150

in vec2 pos;

void main(void)
{   
  gl_Position = vec4(pos.x,pos.y,0.0,1.0);
}
