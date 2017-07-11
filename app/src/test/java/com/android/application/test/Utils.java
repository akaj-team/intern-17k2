package com.android.application.test;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Thanh Thien on 7/11/2017.
 * Utils
 */
@RunWith(MockitoJUnitRunner.class)
class Utils {
    String[] trueAllThings = {"ThienNguyen", "thanewehthien", "nguyentwewehanhthien97", "16thiewewennguyen"};
    String[] trueAllPasswordThings = {"Thien$Nguyen19", "than_ Thien97", "nguyen_Thanhthien97", "16Thien Nguyen_"};
    String[] falseAllThings = {"Thien", "l e", "lE", "_Tam"};
    String[] falseLengthPasswords = {"thi", "l e", "lE", "_To"};
    String[] trueCheckMaxLengths = {"nguyenthanhthien", "nguyen thanh thien", "nguyen van c", "Tran Thuy Trang", "Nguyen Van No Name Thien"};
    String[] falseCheckMaxLengths = {"Thang Thien ten Thien ho Nguyen nha", "nguyen thanh thien nguyen thanh thien", "nguyentwewehanhthiennguyentwewehanhthien"};
    String[] checkSymbols = {"thien", "thanhthien", "nguyen thanh thien", "thiennguyen", "nguyen thanh thien", "thiennguyen"};
    String[] falseCheckSymbols = {"thien!", "`thanhthien", "nguyen_thanh_thien", "$thiennguyen", "nguyen thanh; thien"};
    String[] falseCheckAlphabets = {"thien!", "thanhthien ", "nguyen thanh_thien", "thien nguyen", "thiennguyen_"};
    String[] trueHasNumbers = {"13ThienNguyen", "thanh12hthien", "nguyentwewehanhthien97", "16thien nguyen"};
    String[] falseUpChars = {"thanhthien", "nguyenthanhthien97", "19thien thanh"};
}
