package com.functional.kotlin.extensions

import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test


class CurryExtensionsKtTest {

    @Test
    fun withOneParameterFunction() {
        val result = ::oneParameterFunction with 2
        assertEquals(4, result)
    }

    @Test
    fun withTwoParametersFunction() {
        val result = ::twoParametersFunction with 1 with 1
        assertEquals(2, result)
    }


    @Test
    fun withThreeParametersFunction() {
        val result = ::threeParametersFunction with 1 with 1 with 1
        assertEquals(3, result)
    }

    @Test
    fun withFourParametersFunction() {
        val result = ::fourParametersFunction with 1 with 1 with 1 with 1
        assertEquals(4, result)
    }

}

