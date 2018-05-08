package com.functional.kotlin.extensions

import org.junit.Test

import org.junit.Assert.*

class SwapCurryExtensionsKtTest {

    @Test
    fun flipTwoDifferentParametersFunction() {
        val result = ::twoDifferentParametersFunction.flip()("-", 1)
        assertEquals("1-", result)
    }

    @Test
    fun flipWithTwoDifferentParametersFunction() {
        val result = ::twoDifferentParametersFunction flipWith "-" with 1
        assertEquals("1-", result)
    }
}