package com.example.demo.simplespec

import org.opentest4j.MultipleFailuresError
import java.lang.AssertionError
import junit.framework.ComparisonFailure as Junit4FrameworkComparisonFailure
import org.junit.ComparisonFailure as Junit4ComparisonFailure

class SimpleSpec(private val name: String = "") {
    private val steps: MutableList<String> = mutableListOf()

    fun getName(): String = name
    fun getSteps(): List<String> = steps.toList()

    operator fun <T> String.invoke(block: SimpleSpec.() -> T): T {
        steps += this
        try {
            return block()
        } catch(all: Throwable) {
            val heading = this

            throw when (all) {
                is AssertionError -> MultipleFailuresError(heading, listOf(all))
                else -> all
            }
        }
    }
}

fun simpleSpec(name: String = "", block: SimpleSpec.() -> Unit) {

    val specName: String = if (name.isNotBlank()) {
        name
    } else {
        val t = Throwable()
        val trace = t.stackTrace
        val caller = trace.find {
            (!it.isNativeMethod) && (!it.methodName.contains("simpleSpec"))
        }
        when (caller) {
            null -> "$block"
            else -> "${caller.className}.${caller.methodName}"
        }
    }

    val spec = SimpleSpec(name = specName)
    try {
        spec.apply(block)
    } catch (all: Throwable) {
        val heading = listOf(
            "Spec Failed!",
            "", "======== Spec Trace ======", "",
            "- spec.name: ${spec.getName()}",
            "",
            "- steps: ",
            "",
            spec.getSteps().map { "* $it" }.joinToString(lineSeparator),
            "",
            "- cause:",
            ""
        ).joinToString(lineSeparator)

        throw when (all) {
            is AssertionError -> MultipleFailuresError(heading, listOf(all))
            else -> all
        }
    }
}

internal val lineSeparator = System.getProperty("line.separator")
