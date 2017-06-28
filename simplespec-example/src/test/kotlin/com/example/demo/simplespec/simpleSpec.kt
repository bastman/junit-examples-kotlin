package com.example.demo.simplespec

import org.opentest4j.AssertionFailedError
import org.opentest4j.MultipleFailuresError
import java.lang.AssertionError
import java.util.ArrayList

class SimpleSpec(private val name: String = "") {
    fun getName(): String = name
    private val steps: MutableList<String> = mutableListOf()
    fun getSteps(): List<String> = steps.toList()

    operator fun <T> String.invoke(block: SimpleSpec.() -> T): T {
        steps += this
        try {
            return block()
        } catch(all: Throwable) {

            throw all.toStepFunctionError(heading = this)
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
        val messages: List<String> = listOf(
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
        )
        val heading = messages.joinToString(lineSeparator)

        throw all.toStepFunctionError(heading=heading)
    }
}

internal fun Throwable.toStepFunctionError(heading:String):MultipleFailuresError {
    val failures = ArrayList<Throwable>()
    val multipleFailuresError = when (this) {
        is AssertionError -> {
            failures.add(this)
            MultipleFailuresError(heading, failures)
        }
        else -> {
            val failure = AssertionFailedError(
                "$this", this
            )
            failures.add(failure)
            MultipleFailuresError(heading, failures)
        }
    }

    return multipleFailuresError
}

internal val lineSeparator = System.getProperty("line.separator");
