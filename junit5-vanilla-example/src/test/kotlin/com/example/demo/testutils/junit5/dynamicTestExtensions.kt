package com.example.demo.testutils.junit5

import org.junit.jupiter.api.DynamicTest


fun dynamicTest(
    name: String,
    test: () -> Unit
): DynamicTest = DynamicTest.dynamicTest(name, test)

fun dynamicTest(
    name: ()->String,
    test: () -> Unit
): DynamicTest = dynamicTest(name(), test)

fun MutableList<DynamicTest>.addTest(name: String, test: () -> Unit): DynamicTest {
    val dynamicTest = dynamicTest(name=name, test=test)
    this.add(dynamicTest)

    return dynamicTest
}

fun MutableList<DynamicTest>.addTest(
    name: ()->String,
    test: () -> Unit
): DynamicTest = addTest(name, test)

