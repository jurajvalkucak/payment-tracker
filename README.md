Payment Tracker Program

Author: Juraj Valkucak
Email: juraj.valkucak@gmail.com
Date: 20190120

See Code Documentation under javadoc/ folder.

1. How to build the program (it will produce jar file in target folder -> payment-tracker-1.0-SNAPSHOT.jar)
mvn install

2. How to run the program (add jar to classpath) - without file input
java -classpath payment-tracker-1.0-SNAPSHOT.jar com.jvalkucak.bsc.Main

3. How to run the program (add jar to classpath) - with file input
   java -classpath payment-tracker-1.0-SNAPSHOT.jar com.jvalkucak.bsc.Main <FILE_PATH>

3. Assumptions
Program allows to read payments from command line or file (optional).
Program provides API to set US dollar exchange rates for currency codes.
For demonstration only program sets some exchange rates when started (for codes HUF, CZK, GBP, HKD).
Program creates 3 concurrent threads at startup.
Program is stopped when all below threads finish.

Thread 1 - Reads payments from file input (optional).
         - File path is passed as argument to main process
         - In case of the error (bad input), thread continues with next payment record from the input file.
         - Thread doesn't show error messages on the standard output in case of payment record error processing.
         - Thread is stopped when all payments are processed from the input file.
         - When stopped it prints total number of payment records processed from the input file.

Thread 2 - Reads payments from user command line.
         - In case of the error (bad input) the error message is displayed along with help message how to insert payment.
         - Program continues and waits for next user input.
         - Thread is stopped when user types "quit".
         - When stopped it prints total number of payment records processed from the user command line.

Thread 3 - Prints total amounts for all tracked currency codes (from both standard and file input)
         - Prints in specified interval 60 secs
         - Thread is stopped when the user types "quit".

Program comes with JUnit tests to test some basic scenarios.

JDK Version: 1.8
Maven Version: 3.5
IDE: IntelliJ IDEA Community 2018.3

