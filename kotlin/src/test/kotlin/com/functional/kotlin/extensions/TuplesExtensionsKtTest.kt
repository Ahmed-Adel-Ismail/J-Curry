package com.functional.kotlin.extensions

import org.junit.Test

import org.junit.Assert.*

class TuplesExtensionsKtTest {

    @Test
    fun withTwoParameterFunction() {
        val result = ::twoParametersFunction with Pair(1, 1)
        assertEquals(2, result)
    }

    @Test
    fun flipWithTwoParameterFunction() {
        val result = ::twoDifferentParametersFunction flipWith Pair("-", 1)
        assertEquals("1-", result)
    }

    @Test
    fun withThreeParameterFunction() {
        val result = ::threeParametersFunction with Triple(1, 1, 1)
        assertEquals(3, result)
    }
}