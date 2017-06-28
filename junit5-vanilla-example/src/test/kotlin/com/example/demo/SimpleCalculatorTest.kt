package com.example.demo

import com.example.demo.testutils.junit5.assertThat
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

        val r1 = assertThat("some stuff1") {
            val result = calculator.sum(2, 4)
            result shouldEqual 6

            result
        }

        assertThat("sum($r1,10) is 16") {
            val result = calculator.sum(r1, 10)
            result shouldEqual 16
        }
    }
}





