package com.example.demo.testutils.junit5

import org.opentest4j.MultipleFailuresError
import java.lang.AssertionError
import java.util.ArrayList

fun <T>assertThat(heading: String, block: ()->T):T = assertThat(
    heading = {heading},
    block = block
)

fun <T>assertThat(heading: ()->String, block: ()->T):T {
    val failures = ArrayList<Throwable>()
    try {
        return block()
    } catch (assertionError: AssertionError) {
        failures.add(assertionError)
        val multipleFailuresError = MultipleFailuresError(heading(), failures)
        throw multipleFailuresError
    }
}
