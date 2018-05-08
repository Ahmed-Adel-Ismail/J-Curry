package com.functional.kotlin.extensions

/**
 * an extension function that flips the original function parameters (switches the parameters order)
 */
fun <T1, T2, R> ((T1, T2) -> R).flip(): ((T2, T1) -> R) = { p2, p1 -> this(p1, p2) }

/**
 * an extension function that passes it's parameter as the second parameter for the extended function,
 * and returns a function that awaits the first parameter
 */
infix fun <T1, T2, R> ((T1, T2) -> R).flipWith(parameterTwo: T2): ((T1) -> R) = { this(it, parameterTwo) }