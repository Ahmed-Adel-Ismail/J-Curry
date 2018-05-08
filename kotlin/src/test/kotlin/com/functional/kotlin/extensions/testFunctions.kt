package com.functional.kotlin.extensions

import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer

fun oneParameterFunction(p1: Int): Int = p1 * p1
fun twoParametersFunction(p1: Int, p2: Int): Int = p1 + p2
fun twoDifferentParametersFunction(p1: Int, p2: String): String = "$p1$p2"
fun threeParametersFunction(p1: Int, p2: Int, p3: Int): Int = p1 + p2 + p3
fun fourParametersFunction(p1: Int, p2: Int, p3: Int, p4: Int): Int = p1 + p2 + p3 + p4

class BiFunctionOfInts : BiFunction<Int, Int, Int> {
    override fun apply(t1: Int, t2: Int): Int {
        return t1 + t2
    }
}

