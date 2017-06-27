# junit-examples-kotlin
playground for junit-based testing frameworks

## examples
   - spek
   - junit5 (data-driven-tests)
   - kluent (and json-based assertions)

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



                    
                   
                    