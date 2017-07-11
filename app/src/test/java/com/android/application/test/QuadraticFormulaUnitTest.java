package com.android.application.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.ui.testcase.QuadraticFormula;

/**
 * Created by Thanh Thien on 7/11/2017.
 * QuadraticFormulaUnitTest
 */
@RunWith(MockitoJUnitRunner.class)
public class QuadraticFormulaUnitTest {
    private static final double DELTA = 0.005;

    @Test
    public void checkGetPeakX() {
        Assert.assertEquals(QuadraticFormula.findPeakX(2, 3), -0.75, DELTA);
    }

    @Test
    public void checkGetPeakY() {
        Assert.assertEquals(QuadraticFormula.findPeakY(2, 3, 1), -0.125, DELTA);
    }

    @Test
    public void checkGetDelta() {
        Assert.assertEquals(QuadraticFormula.getDelta(3, -3, 2), -15, DELTA);
    }

    @Test
    public void checkGetX1() {
        Assert.assertEquals(QuadraticFormula.getX1(2, 3, 1), -1, DELTA);
    }

    @Test
    public void checkGetX2() {
        Assert.assertEquals(QuadraticFormula.getX2(2, 3, 1), -0.5, DELTA);
    }
}
