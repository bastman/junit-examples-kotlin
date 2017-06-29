package com.example.demo

import com.example.demo.simplespec.simpleSpec
import com.example.demo.testutils.json.shouldEqualJson
import com.example.demo.testutils.json.toJson
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.Instant

@RunWith(JUnitPlatform::class)
internal class JsonAssertionTest {

    private val items: List<Item> = listOf(
        Item(
            id = "A",
            time = Instant.parse("2017-06-24T05:10:36.100Z")
        ),
        Item(
            id = "B",
            time = Instant.parse("2017-06-21T02:10:37.100Z")
        )
    ).sortedBy { it.time }

    @Test @DisplayName("test something using json-based assertions")
    fun testSomething() = simpleSpec {

        "it should be something" {
            //"xxx" shouldEqual "yyyy"

            items
                .sortedBy { it.time }
                .toJson() shouldEqualJson """
            [
                {"id":"B","time":"2017-06-21T02:10:37.100Z"},
                {"id":"A","time":"2017-06-24T05:10:36.100Z"}

,
{"id":"B","time":"2017-06-21T02:10:37.100Z"},
{"id":"B","time":"2017-06-21T02:10:37.100Z"},
{"id":"B","time":"2017-06-21T02:10:37.100Z"},
{"id":"B","time":"2017-06-21T02:10:37.100Z"}
            ]
            """
        }

    }

}

data class Item(
    val id: String,
    val time: Instant
)