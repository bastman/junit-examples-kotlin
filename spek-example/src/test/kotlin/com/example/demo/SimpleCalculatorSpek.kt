package com.example.demo

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

// to make it work from intellij
@RunWith(JUnitPlatform::class)
internal class SimpleCalculatorSpek : Spek({
    given("a calculator") {
        val calculator = SimpleCalculator()
        on("addition") {
            val result = calculator.sum(2, 4)
            it("should return the result of adding the first number to the second number") {
                result shouldEqual 6
            }
        }
        on("subtract") {
            val result = calculator.subtract(4, 1)
            it("should return the result of subtracting a-b") {
                result shouldEqual 3
            }
        }
    }
})