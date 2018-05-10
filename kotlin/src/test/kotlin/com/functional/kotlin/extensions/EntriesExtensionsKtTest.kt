package com.functional.kotlin.extensions

import org.junit.Assert.*
import org.junit.Test

class EntriesExtensionsKtTest {

    @Test
    fun withTwoParameterFunction() {

        val result = mapOf(1 to 1)
                .map { ::twoParametersFunction with it }
                .first()

        assertEquals(2, result)
    }

    @Test
    fun flipWithTwoParameterFunction() {

        val result = mapOf("-" to 1)
                .map { ::twoDifferentParametersFunction flipWith it }
                .first()

        assertEquals("1-", result)
    }

}