package com.functional.kotlin.extensions

/**
 * an extension function that makes it possible to pass a [Pair] for a function that expects two
 * parameters, where the first parameter matches the type of [Pair.first] , and the second parameter
 * matches [Pair.second], then this extension function will pass [Pair.first] to the functions
 * first parameter, and [Pair.second] to the functions second parameter
 */
infix fun <T1, T2, R> ((T1, T2) -> R).with(pair: Pair<T1, T2>): R = this(pair.first, pair.second)

/**
 * an extension function that makes it possible to pass a [Pair] for a function that expects two
 * parameters, where the first parameter matches the type of [Pair.second] , and the second parameter
 * matches [Pair.first], then this extension function will pass [Pair.second] to the functions
 * first parameter, and [Pair.first] to the functions second parameter
 */
infix fun <T1, T2, R> ((T1, T2) -> R).flipWith(pair: Pair<T2, T1>): R = this(pair.second, pair.first)

/**
 * an extension function that makes it possible to pass a [Triple] for a function that expects three
 * parameters, where the first parameter matches the type of [Triple.first] , and the second parameter
 * matches [Triple.second], and the third parameter matches [Triple.third],
 * then this extension function will pass [Triple.first] to the functions
 * first parameter, and [Triple.second] to the functions second parameter, and the
 * [Triple.third] to the third parameter
 */
infix fun <T1, T2, T3, R> ((T1, T2, T3) -> R).with(triple: Triple<T1, T2, T3>): R =
        this(triple.first, triple.second, triple.third)
