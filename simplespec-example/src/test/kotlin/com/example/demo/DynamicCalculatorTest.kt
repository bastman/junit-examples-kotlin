package com.example.demo

import com.example.demo.simplespec.simpleSpec
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
internal class DynamicCalculatorTest {

    @TestFactory @DisplayName("suite: calculator.sum")
    fun sumTests(): List<DynamicTest> = createSumTests()

    @TestFactory @DisplayName("suite: calculator.subtract")
    fun subtractTests(): List<DynamicTest> = createSubtractTests()

    private val calculator = SimpleCalculator()

    private fun createSumTests(): List<DynamicTest> {
        val testName: (TestCase) -> String = {
            "Test calculator.sum(${it.a},${it.a}) should be ${it.expectedResult}"
        }
        val test: (TestCase) -> Unit = { sumTest(it) }

        return listOf(
            TestCase(
                a = 1, b = 1, expectedResult = 2,
                testNameSupplier = testName,
                test = test
            ),
            TestCase(
                a = 5, b = 6, expectedResult = 11,
                testNameSupplier = testName,
                test = test
            )
        ).map { it.toDynamicTest() }
    }

    private fun createSubtractTests(): List<DynamicTest> {
        val testName: (TestCase) -> String = {
            "Test calculator.subtract(${it.a},${it.a}) should be ${it.expectedResult}"
        }
        val test: (TestCase) -> Unit = { subtractTest(it) }

        return listOf(
            TestCase(
                a = 10, b = 1, expectedResult = 9,
                testNameSupplier = testName,
                test = test
            ),
            TestCase(
                a = 5, b = 3, expectedResult = 200,
                testNameSupplier = testName,
                test = test
            )
        ).map { it.toDynamicTest() }
    }

    private fun sumTest(testCase: TestCase) = simpleSpec {
        val calc = "given: a calculator" {
            val calc = calculator
            calculator shouldBeInstanceOf SimpleCalculator::class

            calc
        }

        val sumResult = "when: calling calculator.sum(${testCase.a},${testCase.b})" {
            calc.sum(testCase.a, testCase.b)
        }

        "it: should return ${testCase.expectedResult}" {
            sumResult shouldBe testCase.expectedResult
        }
    }

    private fun subtractTest(testCase: TestCase) = simpleSpec(name = testCase.testName()) {
        val calc = "given: a calculator" {
            val calc = calculator
            calculator shouldBeInstanceOf SimpleCalculator::class

            calc
        }

        val subtractResult = "when: calling calculator.subtract(${testCase.a},${testCase.b})" {
            calc.subtract(testCase.a, testCase.b)
        }

        "it: should return ${testCase.expectedResult}" {
            subtractResult shouldBe testCase.expectedResult
        }
    }

}


internal data class TestCase(
    val a: Int,
    val b: Int,
    val expectedResult: Int,
    val testNameSupplier: (TestCase)->String,
    val test:(TestCase)->Unit
) {

    fun testName():String {
        return testNameSupplier(this)
    }

    fun toDynamicTest(): DynamicTest {
        val testName = testName()
        val block = { test(this) }

        return dynamicTest(testName, block)
    }
}

fun dynamicTest(
    name: String,
    test: () -> Unit
): DynamicTest = DynamicTest.dynamicTest(name, test)