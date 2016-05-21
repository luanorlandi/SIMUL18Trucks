/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Orlandi
 */
public class Surface {
    private final float cY[];       /* coefficients */
    private final float cR[];
    
    public Surface() {
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
    
    public float height(float x) {
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
    
    public float rotation(float x) {
        boolean invertRotation = false;
        float r = 0;
        
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
        
        return r;
    }
}
