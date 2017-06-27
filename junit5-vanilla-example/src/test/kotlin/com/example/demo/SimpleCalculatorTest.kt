package com.example.demo

import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
internal class SimpleCalculatorTest {
    val calculator = SimpleCalculator()

    @Test @DisplayName("should return the result of adding the first number to the second number")
    fun testSum() {
        val result = calculator.sum(2, 4)
        result shouldEqual 6
    }
}