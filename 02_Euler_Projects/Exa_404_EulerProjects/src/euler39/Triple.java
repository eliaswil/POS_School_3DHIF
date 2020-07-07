/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler39;

import java.util.Arrays;

/**
 *
 * @author Elias Wilfinger
 */
public class Triple {
    private int p;
    private int[] triangle = new int[3];

    public Triple(int p, int a, int b, int c) {
        this.p = p;
        triangle[0] = a;
        triangle[1] = b;
        triangle[2] = c;
        
        Arrays.sort(triangle);
        
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int[] getTriangle() {
        return triangle;
    }

    public void setTriangle(int[] triangle) {
        this.triangle = triangle;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.p;
        hash = 59 * hash + Arrays.hashCode(this.triangle);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Triple other = (Triple) obj;
        if (this.p != other.p) {
            return false;
        }
        if (!Arrays.equals(this.triangle, other.triangle)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%d -> %s", p, Arrays.toString(triangle));
    }
    
    
    
}
