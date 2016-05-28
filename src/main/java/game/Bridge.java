/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import br.usp.icmc.vicg.gl.util.Shader;
import java.util.ArrayList;
import javax.media.opengl.GL3;

/**
 *
 * @author Orlandi
 */
public class Bridge implements Surface {
    private ArrayList<Object> bridgeList;
    
    private float distance = 59.9f;     /* distance between each bridge */
    
    private final float cY[];           /* coefficients */
    private final float cR[];
    
    public Bridge(GL3 gl, Shader shader, String path) {
        bridgeList = new ArrayList();
        
        bridgeList.add(new Object("bridge1", gl, shader, path));
        bridgeList.add(new Object("bridge2", gl, shader, path));
        bridgeList.add(new Object("bridge3", gl, shader, path));
        
        bridgeList.get(0).scale(30.0f);
        bridgeList.get(0).rotate(0.0f, 180.0f, 0.0f);
        bridgeList.get(0).translate(-distance, 0.0f, 0.0f);
        
        bridgeList.get(1).scale(30.0f);
        bridgeList.get(1).rotate(0.0f, 180.0f, 0.0f);
        
        bridgeList.get(2).scale(30.0f);
        bridgeList.get(2).rotate(0.0f, 180.0f, 0.0f);
        bridgeList.get(2).translate(distance, 0.0f, 0.0f);
        
        cY = new float[5];
        cY[0] = (float) -1.75687270222880e+002;
        cY[1] = (float) -1.73677692452706e+000;
        cY[2] = (float) -2.17934186065177e+000;
        cY[3] = (float) -7.55762104308739e-002;
        cY[4] = (float)  -8.83708721852949e-004;
        
        cR = new float[5];
        cR[0] = (float) 1.06358436789827e+002;
        cR[1] = (float) 5.35603485093407e+002;
        cR[2] = (float) 5.84694281292338e+001;
        cR[3] = (float) 2.62318796899470e+000;
        cR[4] = (float) 4.24757612400653e-002;
    }
    
    public void reposition(float truckPos) {
        
        float forwardTransition = bridgeList.get(1).getPosX() + distance/2;
        float backwardTransition = bridgeList.get(1).getPosX() - distance/2;
        
        if(truckPos > forwardTransition) {
            bridgeList.get(0).translate(distance, 0.0f, 0.0f);
            bridgeList.get(1).translate(distance, 0.0f, 0.0f);
            bridgeList.get(2).translate(distance, 0.0f, 0.0f);
        } else if(truckPos < backwardTransition) {
            bridgeList.get(0).translate(-distance, 0.0f, 0.0f);
            bridgeList.get(1).translate(-distance, 0.0f, 0.0f);
            bridgeList.get(2).translate(-distance, 0.0f, 0.0f);
        }
    }
    
    @Override
    public float height(float x) {
        x %= distance;
        
        if(x > distance/2) {
            x -= distance;
        } else if(x < -distance/2) {
            x += distance;
        }
        
        if(x > 0) {
            x *= -1;
        }
        
        float y = 0;
        
        for(int i = 0; i < cY.length; i++) {
            y += cY[i] * Math.pow(x, i);
        }
        
        y /= 1000;
        
        return y;
    }
    
    @Override
    public float rotation(float x) {
        boolean invertRotation = false;
        float r = 0;
        
        x %= distance;
        
        if(x > distance/2) {
            x -= distance;
        } else if(x < -distance/2) {
            x += distance;
        }
        
        if(x > 0) {
            x *= -1;
            invertRotation = true;
        }
        
        
        for(int i = 0; i < cR.length; i++) {
            r += cR[i] * Math.pow(x, i);
        }
        
        r /= 1000;
        
        if(invertRotation) {
            r *= -1;
        }
        
        /* adjustment */
        if(Math.abs(r) < 1.0f) {
            r /= Math.pow(1 + (1 - Math.abs(r)), 1.5f);
        }
        
        return r;
    }
    
    public void draw() {
        bridgeList.get(0).draw();
        bridgeList.get(1).draw();
        bridgeList.get(2).draw();
    }

    public ArrayList<Object> getBridgeList() {
        return bridgeList;
    }
}
