package com.android.application.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.ui.testcase.QuadraticFormula;

/**
 * Copyright © 2017 AsianTech inc.
 *
 * @author Thanh Thien
 *         Created on 7/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuadraticFormulaUnitTest {
    private static final double DELTA = 0.005;
    private QuadraticFormula mQuadraticFormula = new QuadraticFormula();

    @Test
    public void checkGetPeakX() {
        Assert.assertEquals(mQuadraticFormula.findPeakX(2, 3), -0.75, DELTA);
    }

    @Test
    public void checkGetPeakY() {
        Assert.assertEquals(mQuadraticFormula.findPeakY(2, 3, 1), -0.125, DELTA);
    }

    @Test
    public void checkGetDelta() {
        Assert.assertEquals(mQuadraticFormula.getDelta(3, -3, 2), -15, DELTA);
    }

    @Test
    public void checkGetX1() {
        Assert.assertEquals(mQuadraticFormula.getX1(2, 3, 1), -1, DELTA);
    }

    @Test
    public void checkGetX2() {
        Assert.assertEquals(mQuadraticFormula.getX2(2, 3, 1), -0.5, DELTA);
    }
}
