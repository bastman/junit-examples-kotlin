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
        return block()
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
        val body = listOf(
            "======== Spec Trace ======", "",
            "- spec.name: ${spec.getName()}",
            "",
            "- steps: ",
            "",
            spec.getSteps().map { "* $it" }.joinToString(lineSeparator),
            "",
            "- error:", "",
            "$all", ""
        ).joinToString(lineSeparator)

        val lastStep: String? = spec.getSteps().lastOrNull()
        val heading = if (lastStep != null) {
            "Spec Failed ! \n $lastStep \n"
        } else {
            "Spec Failed !"
        }

        throw when (all) {
            is AssertionError -> SpecAssertionsFailed(
                heading = heading,
                body = body,
                failures = listOf(all)
            )
            else -> all
        }
    }
}

internal val lineSeparator = System.getProperty("line.separator")
internal fun String.sanitizeXmlCData(): String {
    return this.replace("]]>", "]] >")
}

class SpecAssertionsFailed(
    heading: String,
    val body: String,
    failures: List<Throwable>
) : MultipleFailuresError(heading, failures) {
    /**
     * - message goes into message-attribute of xml output
     * - toString() this goes into CDATA section of xml output
     */
    override fun toString(): String {
        return body.sanitizeXmlCData()
    }
}