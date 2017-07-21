# email-parser
This library provides ability to separate the quotation from the useful content in email messages. The main purpose of this library is to process as much different quotation formats as possible. It is also independent from the language used in email.

Efficiency estimation we have got during testing: **> 97.5 %** correctly processed emails.

# Usage
You can download library sources and add them into your project.

### To run in the console
Clone project via git and change directory to `email-parser`.

### Process .eml file:
Enter `gradlew runProcessing -PemlFile="path"` in the console, where `path` is path to eml file.

**Output format**        
Header of the quotation is in uppercase.         
Quotation is marked with '>' symbol beginning with the first line of the header of the quotation till the end of the message.          
Working time is also provided.            

### Documentation
To get documentation in [dokka](https://github.com/Kotlin/dokka) format enter `gradlew dokka` in the console. Then run `build/dokka/email-parser/index.html`

To get documentation in Javadoc format enter `gradlew dokkaJavadoc` in the console. Then run `build/javadoc/index.html`

### Description
The main package of the library is [quoteParser](src/main/kotlin/ru/ppzh/quoteParser). Its main class is [QuoteParser](src/main/kotlin/ru/ppzh/quoteParser/QuoteParser.kt#L77). 

To use parser call `quoteParserObj.parse()` method. This method will return [Content](src/main/kotlin/ru/ppzh/quoteParser/Content.kt) object with a separate body, header of the quote if exists and quotation itself if exists.

You can use [quoteParserObj.parse(ListOfStrings)](src/main/kotlin/ru/ppzh/quoteParser/QuoteParser.kt#L229) to parse any email text.
 Another way is to use [quoteParserObj.parse(File)](src/main/kotlin/ru/ppzh/quoteParser/QuoteParser.kt#L212) with some email file as a parameter.
 ([email filename extensions](https://en.wikipedia.org/wiki/Email#Filename_extensions)).
 For the second case text/HTML Content-Type isn't supported by now.

For more information, read the documentation.

# Examples
Simple usage example (Kotlin):
```kotlin
val content = QuoteParser.Builder()
        .build()
        .parse(file)
```
You also can parse list of strings and customize parser parameters via different builder methods:
```kotlin
val content = QuoteParser.Builder()
        .deleteQuoteMarks(true)
        .recursive(false)
        .build()
        .parse(emailText.lines())
```
Kotlin-style builder is also supported:
```kotlin
val content = QuoteParser.Builder()
        .build {
            deleteQuoteMarks = true
            recursive = false
        }.parse(email.lines())
```

Usage example (Java):
```java
Content c = new QuoteParser.Builder()
        .build()
        .parse(file);
```
```java
Content c = new QuoteParser.Builder()
        .recursive(true)
        .deleteQuoteMarks(false)
        .build()
        .parse(Arrays.asList(emailText.split("\n")));
```
Complete code of the examples is placed here: [Kotlin](src/main/kotlin/ru/ppzh/examples), [Java](src/main/java/ru/ppzh/examples).
