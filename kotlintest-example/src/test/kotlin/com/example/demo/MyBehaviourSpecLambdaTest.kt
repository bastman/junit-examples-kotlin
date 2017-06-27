package com.example.demo

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.BehaviorSpec


class MyBehaviourSpecLambdaTest : BehaviorSpec({
    given("string.length") {

        val example = "EXAMPLE"

        `when`("using foobar") {
            val subject = "foobar"
            then("length is 6") {
                subject.length shouldBe 6
            }

            then("length is 7") {
                subject shouldBe "foobar"
            }
        }

        `when`("using example") {
            then("length is 7") {
                example.length shouldBe 7
            }
        }


    }
})