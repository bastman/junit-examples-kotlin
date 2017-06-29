package com.example.demo

import com.example.demo.simplespec.simpleSpec
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
internal class SimpleCalculatorTest {
    val calculator = SimpleCalculator()

    @Test
    fun testSumAndSubtract() = simpleSpec {

        val calc = "given: a calculator" {
            val calc = calculator
            calculator shouldBeInstanceOf SimpleCalculator::class

            calc
        }

        val sumResult = "when: calling calculator.sum(3,4)" {
            calc.sum(3, 4)
        }

        "it: should return 7" {
            sumResult shouldBe 7
        }

        val subtractResult = "when: calling calculator.subtract(10,4)" {
            calc.subtract(10, 4)
        }

        "it: should return 6" {
            subtractResult shouldBe 6
        }
    }
}





