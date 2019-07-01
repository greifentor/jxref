# jxref
A tool to get an cross reference table for a Java source code. First step will cover class usages only.


## Build

Build project with `mvn clean install`.


## Run

Run program e. g. from project folder with:
`java -cp lib/antlr4-runtime-4.7.1.jar:target/jxref-0.0.1.jar de.ollie.jxref.JXRef <SOURCE-PATH>`

Example:
`java -cp lib/antlr4-runtime-4.7.1.jar:target/jxref-0.0.1.jar de.ollie.jxref.JXRef src/main/java/de/ollie` 
