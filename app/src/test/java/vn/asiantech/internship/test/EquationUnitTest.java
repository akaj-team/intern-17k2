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
    public void checkFactor() {
        Mockito.when(mEquation.getA()).thenReturn("123");
        Assert.assertTrue(EquationValidation.checkFactorDigit(mEquation.getA()));
        Mockito.when(mEquation.getA()).thenReturn("a123");
        Assert.assertFalse(EquationValidation.checkFactorDigit(mEquation.getA()));

        Mockito.when(mEquation.getB()).thenReturn("123");
        Assert.assertTrue(EquationValidation.checkFactorDigit(mEquation.getB()));
        Mockito.when(mEquation.getB()).thenReturn("a123");
        Assert.assertFalse(EquationValidation.checkFactorDigit(mEquation.getB()));

        Mockito.when(mEquation.getC()).thenReturn("123");
        Assert.assertTrue(EquationValidation.checkFactorDigit(mEquation.getC()));
        Mockito.when(mEquation.getC()).thenReturn("a123");
        Assert.assertFalse(EquationValidation.checkFactorDigit(mEquation.getC()));
    }

    @Test
    public void checkDenta() {
        Mockito.when(mEquation.getDenta()).thenReturn(5);
        Assert.assertEquals(EquationValidation.checkDenta(mEquation.getDenta()), 2);
        Mockito.when(mEquation.getDenta()).thenReturn(0);
        Assert.assertEquals(EquationValidation.checkDenta(mEquation.getDenta()), 1);
        Mockito.when(mEquation.getDenta()).thenReturn(-5);
        Assert.assertEquals(EquationValidation.checkDenta(mEquation.getDenta()), 0);
    }

    @Test
    public void checkCondition() {
        Assert.assertEquals(EquationValidation.checkDataInput(0, 0, 0), "VoSoNghiem");
        Assert.assertEquals(EquationValidation.checkDataInput(0, 0, 6), "0");
        Assert.assertEquals(EquationValidation.checkDataInput(0, 5, 6), "1");
        Assert.assertEquals(EquationValidation.checkDataInput(1, 0, 0), "TinhDenta");
    }

    @Test
    public void checkRoot() {
        Assert.assertEquals(EquationValidation.checkOneRoot(3, -4), String.valueOf((float) 2 / 3));
    }

    @Test
    public void checkRoots() {
        List<Float> roots = EquationValidation.checkTwoRoot(1, 2, 4);
        Assert.assertEquals(roots.get(0).toString(), String.valueOf((float) (-2 + Math.sqrt(4) / (2))));
        Assert.assertEquals(roots.get(1).toString(), String.valueOf((float) (-2 - Math.sqrt(4) / (2))));
    }
}
