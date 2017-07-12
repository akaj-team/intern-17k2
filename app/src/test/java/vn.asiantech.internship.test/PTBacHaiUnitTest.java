package vn.asiantech.internship.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.day31.PTBacHai;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 11/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PTBacHaiUnitTest {

    @Test
    public void checkParameter() {
        Assert.assertEquals(PTBacHai.checkParameter(0, 0, 0), "phuong trinh vo so nghiem");
        Assert.assertEquals(PTBacHai.checkParameter(0, 0, 1), "phuong trinh vo nghiem");
        Assert.assertEquals(PTBacHai.checkParameter(0, 1, 1), "phuong trinh co 1 nghiem");
        Assert.assertEquals(PTBacHai.checkParameter(2, 3, -1), "phuong trinh co 2 nghiem phan biet");
        Assert.assertEquals(PTBacHai.checkParameter(1, -2, 1), "phuong trinh co nghiem kep");
        Assert.assertEquals(PTBacHai.checkParameter(5, -2, 1), "phuong trinh vo nghiem");
    }

    @Test
    public void getX() {
        // PT co 2 nghiem phan biet
        double x[] = {(-2 + Math.sqrt(8)) / 2, (-2 - Math.sqrt(8)) / 2};
        Assert.assertArrayEquals(PTBacHai.getX1X2(1, 2, -1), x, 0);

        // PT co nghiem kep
        double y[] = {-1, -1};
        Assert.assertArrayEquals(PTBacHai.getX1X2(1, 2, 1), y, 0);

        // PT co 1 nghiem(a=0)
        Assert.assertEquals(PTBacHai.getX(0, 1, 2), -2, 0);
    }
}
