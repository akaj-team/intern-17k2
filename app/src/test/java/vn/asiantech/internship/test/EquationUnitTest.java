package vn.asiantech.internship.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import vn.asiantech.internship.unittest.Equation;
import vn.asiantech.internship.unittest.EquationValidation;

/**
 * Author AsianTech Inc.
 * Created by sony on 11/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class EquationUnitTest {

    @Mock
    private Equation mEquation;

    @Test
    public void checkFactorA() {
        Mockito.when(mEquation.getA()).thenReturn("123");
        Assert.assertTrue(EquationValidation.checkFactorDigit(mEquation.getA()));
        Mockito.when(mEquation.getA()).thenReturn("a123");
        Assert.assertFalse(EquationValidation.checkFactorDigit(mEquation.getA()));
    }

    @Test
    public void checkFactorB() {
        Mockito.when(mEquation.getB()).thenReturn("123");
        Assert.assertTrue(EquationValidation.checkFactorDigit(mEquation.getB()));
        Mockito.when(mEquation.getB()).thenReturn("a123");
        Assert.assertFalse(EquationValidation.checkFactorDigit(mEquation.getB()));
    }

    @Test
    public void checkFactorC() {
        Mockito.when(mEquation.getC()).thenReturn("123");
        Assert.assertTrue(EquationValidation.checkFactorDigit(mEquation.getC()));
        Mockito.when(mEquation.getC()).thenReturn("a123");
        Assert.assertFalse(EquationValidation.checkFactorDigit(mEquation.getC()));
    }

    @Test
    public void checkDelta() {
        Mockito.when(mEquation.getDelta()).thenReturn(5);
        Assert.assertEquals(EquationValidation.checkDelta(mEquation.getDelta()), 2);
        Mockito.when(mEquation.getDelta()).thenReturn(0);
        Assert.assertEquals(EquationValidation.checkDelta(mEquation.getDelta()), 1);
        Mockito.when(mEquation.getDelta()).thenReturn(-5);
        Assert.assertEquals(EquationValidation.checkDelta(mEquation.getDelta()), 0);
    }

    @Test
    public void checkConditionInputData() {
        Assert.assertEquals(EquationValidation.checkDataInput(0, 0, 0), "UnlessRoots");
        Assert.assertEquals(EquationValidation.checkDataInput(0, 0, 6), "NoRoot");
        Assert.assertEquals(EquationValidation.checkDataInput(0, 5, 6), "OneRoot");
        Assert.assertEquals(EquationValidation.checkDataInput(1, 0, 0), "CalculateDelta");
    }

    @Test
    public void checkOneRoot() {
        Assert.assertEquals(EquationValidation.checkOneRoot(3, -4), String.valueOf((float) 2 / 3));
    }

    @Test
    public void checkTwoRoots() {
        List<Float> roots = EquationValidation.checkTwoRoots(1, 2, 4);
        Assert.assertEquals(roots.get(0).toString(), String.valueOf((float) (-2 + Math.sqrt(4) / (2))));
        Assert.assertEquals(roots.get(1).toString(), String.valueOf((float) (-2 - Math.sqrt(4) / (2))));
    }
}
