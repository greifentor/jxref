# jxref
A tool to get an cross reference table for a Java source code. First step will cover class usages only.


## Build

Build project with `mvn clean install`.


## Run

Run program e. g. from project folder with:
`java -cp lib/antlr4-runtime-4.7.1.jar:target/jxref-0.0.1.jar de.ollie.jxref.JXRef <SOURCE-PATH>`

Example:
`java -cp lib/antlr4-runtime-4.7.1.jar:target/jxref-0.0.1.jar de.ollie.jxref.JXRef src/main/java/de/ollie` 

Change the slashes to back slashes and the ":" to ";" for using the application with windows.


## Todos

o T001 - Create a Class which reads the command line parameters into a JXRefParameter object.

o T002 - Make the JXRefWriter instance configurable which is to use.

o T003 - Make the application working with "enum" and "interface" files also.

o T004 - "@RestController" should not be in the list of unreferenced classes. Extend the stored information per class with a type of annotation.

o T005 - Classes with a "main" method should not be in the list of unreferenced classes.

o T006 - Check if application handles class castings correctly.

o T007 - "@Service" & "@Component" classes should not be in the list of unreferenced classes if an interface is implemented which is referenced.

o T008 - Check if application works for inner classes and friend classes which are stored in a class file of another public class.

o T009 - Allow more than one writer.

o T010 - Allow more than one source path.

o T011 - Activate the "verbose" option by command line parameter. Put regular output to nothing.