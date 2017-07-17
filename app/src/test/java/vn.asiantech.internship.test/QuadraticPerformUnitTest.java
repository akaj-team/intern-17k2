package vn.asiantech.internship.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day31.Quadratic;
import vn.asiantech.internship.day31.QuadraticPerform;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 11/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuadraticPerformUnitTest {

    @Mock
    private Quadratic mQuadratic;

    @Test
    public void checkParameter() {
        Mockito.when(mQuadratic.getA()).thenReturn(0d);
        Mockito.when(mQuadratic.getB()).thenReturn(0d);
        Mockito.when(mQuadratic.getC()).thenReturn(0d);
        Assert.assertEquals(QuadraticPerform.checkParameter(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), R.string.quadratic_text_equation_countless_solutions);

        Mockito.when(mQuadratic.getA()).thenReturn(0d);
        Mockito.when(mQuadratic.getB()).thenReturn(0d);
        Mockito.when(mQuadratic.getC()).thenReturn(1d);
        Assert.assertEquals(QuadraticPerform.checkParameter(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), R.string.quadratic_text_equation_no_solution);

        Mockito.when(mQuadratic.getA()).thenReturn(0d);
        Mockito.when(mQuadratic.getB()).thenReturn(1d);
        Mockito.when(mQuadratic.getC()).thenReturn(1d);
        Assert.assertEquals(QuadraticPerform.checkParameter(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), R.string.quadratic_text_equation_one_solution);

        Mockito.when(mQuadratic.getA()).thenReturn(2d);
        Mockito.when(mQuadratic.getB()).thenReturn(3d);
        Mockito.when(mQuadratic.getC()).thenReturn(-1d);
        Assert.assertEquals(QuadraticPerform.checkParameter(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), R.string.quadratic_text_equation_two_distinct_solutions);

        Mockito.when(mQuadratic.getA()).thenReturn(1d);
        Mockito.when(mQuadratic.getB()).thenReturn(-2d);
        Mockito.when(mQuadratic.getC()).thenReturn(1d);
        Assert.assertEquals(QuadraticPerform.checkParameter(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), R.string.quadratic_text_equation_dual_solutions);

        Mockito.when(mQuadratic.getA()).thenReturn(5d);
        Mockito.when(mQuadratic.getB()).thenReturn(-2d);
        Mockito.when(mQuadratic.getC()).thenReturn(1d);
        Assert.assertEquals(QuadraticPerform.checkParameter(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), R.string.quadratic_text_equation_no_solution);
    }

    @Test
    public void getX() {

        // The equation has two distinct solutions
        Mockito.when(mQuadratic.getX1()).thenReturn((-2 + Math.sqrt(8)) / 2);
        Mockito.when(mQuadratic.getX2()).thenReturn((-2 - Math.sqrt(8)) / 2);
        Mockito.when(mQuadratic.getA()).thenReturn(1d);
        Mockito.when(mQuadratic.getB()).thenReturn(2d);
        Mockito.when(mQuadratic.getC()).thenReturn(-1d);
        double[] x = {mQuadratic.getX1(), mQuadratic.getX2()};
        Assert.assertArrayEquals(QuadraticPerform.getX1X2(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), x, 0);

        // The equation has dual solutions
        Mockito.when(mQuadratic.getX1()).thenReturn(-1d);
        Mockito.when(mQuadratic.getX2()).thenReturn(-1d);
        Mockito.when(mQuadratic.getA()).thenReturn(1d);
        Mockito.when(mQuadratic.getB()).thenReturn(2d);
        Mockito.when(mQuadratic.getC()).thenReturn(1d);
        double[] y = {mQuadratic.getX1(), mQuadratic.getX2()};
        Assert.assertArrayEquals(QuadraticPerform.getX1X2(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), y, 0);

        // The equation has one solution
        Mockito.when(mQuadratic.getX1()).thenReturn(-2d);
        Mockito.when(mQuadratic.getA()).thenReturn(0d);
        Mockito.when(mQuadratic.getB()).thenReturn(1d);
        Mockito.when(mQuadratic.getC()).thenReturn(2d);
        Assert.assertEquals(QuadraticPerform.getX(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), mQuadratic.getX1(), 0);
    }
}
