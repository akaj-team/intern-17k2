package vn.asiantech.internship.day26;

/**
 * Created by at-dinhvo on 7/17/17.
 */

public class Parameter {
    private float a;
    private float b;
    private float c;
    /*private Parameter parameter;

    public Parameter getInstance(){
        if(parameter == null){
            parameter = new Parameter(a, b, c);
        }
        return parameter;
    }*/

    public Parameter(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void setA(float a) {
        this.a = a;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void setC(float c) {
        this.c = c;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    public float getC() {
        return c;
    }

    public float getDelta() {
        return b * b - 4 * a * c;
    }
}
