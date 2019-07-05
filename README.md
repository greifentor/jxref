# jxref
A tool to get an cross reference table for a Java source code. First step will cover class usages only.

The application creates a cross reference table from the Java source code and allows to process the result in a special 
class which implements the interface `de.ollie.jxref.writer.JXRefWriter`.

In the standard configuration the `de.ollie.jxref.writer.JXRefConsoleWriter` writes the cross reference table content to the
console.


## Limits

To use JXRef with your source code properly its necessary to respect some requirements:

- Imports should not end with a `*`. Import each class by a single import.
- Avoid equal class names in different packages.
- Currently static import will not be processed correctly.
- Does not scan for unused variables.
- Maybe not all calls are hit. We work on it :o)


## Build

Build project with `mvn clean install`.


## Run

Run program e. g. from project folder with:

Call of JXRef: `<java-call> [Parameters] <SOURCE-PATH>`

UX: `java -cp lib/antlr4-runtime-4.7.1.jar:target/jxref-0.0.1.jar de.ollie.jxref.JXRef -v <SOURCE-PATH>`

Windows: `java -cp "lib\antlr4-runtime-4.7.1.jar;target\jxref-0.0.1.jar" de.ollie.jxref.JXRef -v <SOURCE-PATH>`

Example:

UX: `java -cp lib/antlr4-runtime-4.7.1.jar:target/jxref-0.0.1.jar de.ollie.jxref.JXRef -v src/main/java/de/ollie`

Windows: `java -cp "lib\antlr4-runtime-4.7.1.jar;target\jxref-0.0.1.jar" de.ollie.jxref.JXRef -v src\main\java\de\ollie`


### Parameters

The following parameters could be passed to a call of the JXRef tool:

* **-v**, **--verbose** (verbose) - prints some output to the console.

* **-w**, **--writer** <class-name> (alternate writer) - uses the alternate writer defined by the passed class name.


## Todos

x T013 - Change list of referencing class to a set (will cover order problem and uniqueness).
  `OLI 03.07.2019: Solved.`

o T012 - Solve problem with empty class names in reference list.

x T011 - Activate the "verbose" option by command line parameter. Put regular output to nothing.
  `OLI 02.07.2019: Solved.`

o T010 - Allow more than one source path.

o T009 - Allow more than one writer.

o T008 - Check if application works for inner classes and friend classes which are stored in a class file of another public class.

o T007 - "@Service" & "@Component" classes should not be in the list of unreferenced classes if an interface is implemented which is referenced.

o T006 - Check if application handles class castings correctly.

o T005 - Classes with a "main" method should not be in the list of unreferenced classes.

o T004 - "@RestController" should not be in the list of unreferenced classes. Extend the stored information per class with a type of annotation.

x T003 - Make the application working with "enum" and "interface" files also.
  `OLI 05.07.2019: Solved.`

x T002 - Make the JXRefWriter instance configurable which is to use.
  `OLI 04.07.2019: Solved.`
  
x T001 - Create a Class which reads the command line parameters into a JXRefParameter object.
  `OLI 02.07.2019: Solved.`
