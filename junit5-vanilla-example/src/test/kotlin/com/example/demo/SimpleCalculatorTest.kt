package com.example.demo

import com.example.demo.testutils.junit5.assertThat
import com.example.demo.testutils.junit5.simpleSpec
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.opentest4j.AssertionFailedError
import org.opentest4j.MultipleFailuresError
import java.lang.AssertionError
import java.util.ArrayList

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

    @Test
    fun testSum2() = simpleSpec {

        val calc = "given: a calculator" {
            val calc = calculator
            calculator shouldBeInstanceOf SimpleCalculator::class

            calc
        }

        val sumResult = "when: calling calculator.sum(3,4)" {
            calc.sum(3,4)
        }

        "it: should return 7" {
            sumResult shouldBe 7
        }

        val subtractResult = "when: calling calculator.subtract(10,4)" {
            calc.subtract(10,4)
        }

        "it: should return 6" {
            subtractResult shouldBe 6
        }
    }
}





