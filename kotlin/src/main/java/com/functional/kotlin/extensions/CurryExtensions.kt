package com.functional.kotlin.extensions

/**
 * an extension function that makes it possible to chain multiple calls to "apply" even on the
 * last parameter left for a function, this extension function just invokes the original function
 */
infix fun <T, R> ((T) -> R).with(parameter: T): R = this(parameter)

/**
 * an extension function that partially applies the first parameter of a function, and returns
 * another function that waits for the second parameter, and returns the end result when invoked
 */
infix fun <T1, T2, R> ((T1, T2) -> R).with(parameterOne: T1): (T2) -> R =
        { this(parameterOne, it) }

/**
 * an extension function that partially applies the first parameter of a function, and returns
 * another function that waits for the rest of the parameters, then returns the end result when invoked
 */
infix fun <T1, T2, T3, R> ((T1, T2, T3) -> R).with(parameterOne: T1): (T2, T3) -> R =
        { p2, p3 -> this(parameterOne, p2, p3) }

/**
 * an extension function that partially applies the first parameter of a function, and returns
 * another function that waits for the rest of the parameters, then returns the end result when invoked
 */
infix fun <T1, T2, T3, T4, R> ((T1, T2, T3, T4) -> R).with(parameterOne: T1): (T2, T3, T4) -> R =
        { p2, p3, p4 -> this(parameterOne, p2, p3, p4) }



