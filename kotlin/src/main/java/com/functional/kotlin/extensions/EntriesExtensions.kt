package com.functional.kotlin.extensions

/**
 * an extension function that makes it possible to pass a [Map.Entry] for a function that expects two
 * parameters, where the first parameter matches the type of [Map.Entry.key] , and the second parameter
 * matches [Map.Entry.value], then this extension function will pass [Map.Entry.key] to the functions
 * first parameter, and [Map.Entry.value] to the functions second parameter
 */
infix fun <T1, T2, R> ((T1, T2) -> R).with(entry: Map.Entry<T1, T2>): R = this(entry.key, entry.value)

/**
 * an extension function that makes it possible to pass a [Map.Entry] for a function that expects two
 * parameters, where the first parameter matches the type of [Map.Entry.value] , and the second parameter
 * matches [Map.Entry.key], then this extension function will pass [Map.Entry.value] to the functions
 * first parameter, and [Map.Entry.key] to the functions second parameter
 */
infix fun <T1, T2, R> ((T1, T2) -> R).flipWith(entry: Map.Entry<T2, T1>): R = this(entry.value, entry.key)