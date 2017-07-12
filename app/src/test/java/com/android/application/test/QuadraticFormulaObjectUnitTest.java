package com.android.application.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.ui.testcase.Quadratic;
import vn.asiantech.internship.ui.testcase.QuadraticFormula;

/**
 * Copyright © 2017 AsianTech inc.
 *
 * @author Thanh Thien
 *         Created on 7/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuadraticFormulaObjectUnitTest {
    private static final double DELTA = 0.005;

    @Mock
    private Quadratic mQuadratic;
    private QuadraticFormula mQuadraticFormula = new QuadraticFormula();

    @Before
    public void setData() {
        Mockito.when(mQuadratic.getA()).thenReturn(1f);
        Mockito.when(mQuadratic.getB()).thenReturn(2f);
        Mockito.when(mQuadratic.getC()).thenReturn(-1f);
    }

    @Test
    public void checkGetPeakX() {
        Assert.assertEquals(mQuadraticFormula.findPeakX(mQuadratic.getA(), mQuadratic.getB()), -1f, DELTA);
    }

    @Test
    public void checkGetPeakY() {
        Assert.assertEquals(mQuadraticFormula.findPeakY(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), -2, DELTA);
    }

    @Test
    public void checkGetDelta() {
        Assert.assertEquals(mQuadraticFormula.getDelta(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), 8.0f, DELTA);
    }

    @Test
    public void checkGetX1() {
        Assert.assertEquals(mQuadraticFormula.getX1(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), -2.41421f, DELTA);
    }

    @Test
    public void checkGetX2() {
        Assert.assertEquals(mQuadraticFormula.getX2(mQuadratic.getA(), mQuadratic.getB(), mQuadratic.getC()), 0.414213, DELTA);
    }
}
