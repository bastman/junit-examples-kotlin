package com.example.demo.testutils.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.amshove.kluent.shouldEqual

fun simpleJsonSerializer(): ObjectMapper {
    return jacksonObjectMapper()
        .registerModules(
            JavaTimeModule(),
            Jdk8Module(),
            ParameterNamesModule()
        )
        .disable(
            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
            SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS,
            SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS,
            SerializationFeature.WRITE_ENUMS_USING_INDEX
        )
}

fun String.toNormalizedJson(): String {
    val mapper = jacksonObjectMapper()
    val decoded = mapper.readTree(this)

    return mapper
        .writeValueAsString(decoded)
        .trim()
}

infix fun String.shouldEqualJson(theOther: String) =
    this.toNormalizedJson() shouldEqual theOther.toNormalizedJson()

fun Any?.toJson(mapper: ObjectMapper = simpleJsonSerializer()): String {
    return mapper.writeValueAsString(this)
}
