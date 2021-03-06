# junit-examples-kotlin
playground for junit-based testing frameworks

## examples
   - spek
   - junit5 (data-driven-tests)
   - kluent (and json-based assertions)
   - kotlintest
   - simplespec (damn simple bdd style specs)
## run ...

    $ ./gradlew clean test
    
## modules (examples)

### spek-example

   - getting started with spek framework

### junit5-vanilla-example

   - getting started with junit
   - showcases data-driven-testing (dynamic test factory)

### kluent-json-example
   - showcases json-based assertions
          
                    items
                    .sortedBy { it.time }
                    .toJson() shouldEqualJson """
                    [
                        {"id":"B","time":"2017-06-21T02:10:37.100Z"},
                        {"id":"A","time":"2017-06-24T05:10:36.100Z"}
                    ]
                    """

### kotlintest-example
   - specs
   
### simplespec-example
   - showcases junit5 (@Test, @TestFactory)
   - wraps testcode into a flexible spec
   - create proper error messages including steps being executed
   - provides no assertion framework, just use the ones you like
   
   Example:
   
    @Test
    fun testSumAndSubtract() = simpleSpec {

        val calc = "given: a calculator" {
            val calc = calculator
            calculator shouldBeInstanceOf SimpleCalculator::class

            calc
        }

        val sumResult = "when: calling calculator.sum(3,4)" {
            calc.sum(3, 4)
        }

        "it: should return 7" {
            sumResult shouldBe 7
        }

        val subtractResult = "when: calling calculator.subtract(10,4)" {
            calc.subtract(10, 4)
        }

        "it: should return 6" {
            subtractResult shouldBe 6
        }
    }
    
   Error Messages (example):
    
    ======== Spec Trace ======
    
    - spec.name: Test calculator.subtract(5,5) should be 200
    
    - steps:
    
    * given: a calculator
    * when: calling calculator.subtract(5,3)
    * it: should return 200
    
    - cause: (1 failure)
            it: should return 200 (1 failure)
            expected same:<200> was not:<2>
      JUnit Jupiter:DynamicCalculatorTest:suite: calculator.subtract:Test calculator.subtract(5,5) should be 400
        MethodSource [className = 'com.example.demo.DynamicCalculatorTest', methodName = 'subtractTests', methodParameterTypes = '']
        => org.opentest4j.MultipleFailuresError: Spec Failed!
    
    ======== Spec Trace ======
    
    - spec.name: Test calculator.subtract(5,5) should be 400
    
    - steps:
    
    * given: a calculator
    * when: calling calculator.subtract(5,1)
    * it: should return 400
    
    - cause: (1 failure)
            it: should return 400 (1 failure)
            expected same:<400> was not:<4>

        
                    
                   
                    