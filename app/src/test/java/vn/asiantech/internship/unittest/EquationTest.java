package vn.asiantech.internship.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import vn.asiantech.internship.models.Quadratic;
import vn.asiantech.internship.others.EquationValidation;

/**
 * Equation Unit Test With Mockito.
 * Created by huypham on 18-Jul-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class EquationTest {

    @Mock
    private Quadratic mQuadratic;

    @Test
    public void testNumberA() {
        Mockito.when(mQuadratic.getA()).thenReturn("173");
        Assert.assertTrue(EquationValidation.checkInputCharacter(mQuadratic.getA()));
        Mockito.when(mQuadratic.getA()).thenReturn("ah173");
        Assert.assertFalse(EquationValidation.checkInputCharacter(mQuadratic.getA()));
    }

    @Test
    public void testNumberB() {
        Mockito.when(mQuadratic.getB()).thenReturn("173");
        Assert.assertTrue(EquationValidation.checkInputCharacter(mQuadratic.getB()));
        Mockito.when(mQuadratic.getB()).thenReturn("ah173");
        Assert.assertFalse(EquationValidation.checkInputCharacter(mQuadratic.getB()));
    }

    @Test
    public void testNumberC() {
        Mockito.when(mQuadratic.getC()).thenReturn("173");
        Assert.assertTrue(EquationValidation.checkInputCharacter(mQuadratic.getC()));
        Mockito.when(mQuadratic.getC()).thenReturn("ah173");
        Assert.assertFalse(EquationValidation.checkInputCharacter(mQuadratic.getC()));
    }

    @Test
    public void testDelta() {
        Mockito.when(mQuadratic.getDelta()).thenReturn(10.0);
        Assert.assertEquals(EquationValidation.checkDelta(mQuadratic.getDelta()), 2);
        Mockito.when(mQuadratic.getDelta()).thenReturn(0.0);
        Assert.assertEquals(EquationValidation.checkDelta(mQuadratic.getDelta()), 1);
        Mockito.when(mQuadratic.getDelta()).thenReturn(-10.0);
        Assert.assertEquals(EquationValidation.checkDelta(mQuadratic.getDelta()), 0);
    }

    @Test
    public void inputData() {
        Assert.assertEquals(EquationValidation.checkInput(0, 0, 0), "Surd Root");
        Assert.assertEquals(EquationValidation.checkInput(0, 0, 1), "No Root");
        Assert.assertEquals(EquationValidation.checkInput(0, 1, 1), "One Root");
        Assert.assertEquals(EquationValidation.checkInput(1, 0, 0), "Double Root");
    }

    @Test
    public void testOneRoot() {
        Assert.assertEquals(EquationValidation.checkOneRoot(10, 16), String.valueOf((float) -4 / 5));
    }

    @Test
    public void testDoubleRoot() {
        List<Float> root = EquationValidation.checkDoubleRoot(2, 5, 17);
        Assert.assertEquals(root.get(0).toString(), String.valueOf((float) (-5 + Math.sqrt(17)) / 4));
        Assert.assertEquals(root.get(1).toString(), String.valueOf((float) (-5 - Math.sqrt(17)) / 4));
    }
}
