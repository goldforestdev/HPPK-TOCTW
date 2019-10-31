package com.hppk.toctw.data.source.local

import com.hppk.toctw.data.model.Gender
import org.junit.Assert
import org.junit.Test

class ConvertersTest {

    @Test
    fun stringToGenderTest() {
        Assert.assertEquals(Gender.BOY, Converters().stringToGender(Gender.BOY.name))
        Assert.assertEquals(Gender.GIRL, Converters().stringToGender(Gender.GIRL.name))
    }

    @Test
    fun genderToStringTest() {
        Assert.assertEquals(Gender.BOY.name, Converters().genderToString(Gender.BOY))
        Assert.assertEquals(Gender.GIRL.name, Converters().genderToString(Gender.GIRL))
    }

}