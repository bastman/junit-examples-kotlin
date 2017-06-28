package com.example.demo.testutils.junit5

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
            val failures = ArrayList<Throwable>()
            val multipleFailuresError = when (all) {
                is AssertionError -> {
                    val heading = this
                    failures.add(all)
                    MultipleFailuresError(heading, failures)
                }
                else -> {
                    val failure = AssertionFailedError(
                        "$all", all
                    )
                    val heading = this
                    failures.add(failure)
                    MultipleFailuresError(heading, failures)
                }
            }

            throw multipleFailuresError
        }
    }
}

fun simpleSpec(name: String = "", block: SimpleSpec.() -> Unit) {

    val specName:String = if(name.isNotBlank()) {
        name
    } else {
        val t = Throwable()
        val trace = t.stackTrace
        val caller = trace.find {
            (! it.isNativeMethod) && (! it.methodName.contains("simpleSpec"))
        }
        when(caller) {
            null -> "$block"
            else -> "${caller.className}.${caller.methodName}"
        }
    }

    val spec = SimpleSpec(name = specName)
    try {
        spec.apply(block)
    } catch (all: Throwable) {
        val lineSeparator = System.getProperty("line.separator");
        val messages: List<String> = listOf(
            "Spec Failed! spec.name=${spec.getName()}",
            "",
            "- steps: ",
            "",
            spec.getSteps().joinToString(lineSeparator),
            "",
            "- cause:",
            ""
        )
        val heading = messages.joinToString(lineSeparator)

        val failures = ArrayList<Throwable>()
        val multipleFailuresError = when (all) {
            is AssertionError -> {
                failures.add(all)
                MultipleFailuresError(heading, failures)
            }
            else -> {
                val failure = AssertionFailedError(
                    "$all", all
                )
                failures.add(failure)
                MultipleFailuresError(heading, failures)
            }
        }

        throw multipleFailuresError
    }

}
