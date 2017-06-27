package com.example.demo

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class MyStringSpecTests : StringSpec() {
    init {

        "hello.length should return size of string" {
            "hello".length shouldBe 5
        }
        "foo.length should return size of string" {
            "foo".length shouldBe 3
        }
    }
}