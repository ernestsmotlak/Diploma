# Project's explanation

- py.py = croatian_Lemmatizator.py
- In src/
    -   Main.java
    -   Jugo.java
    -   py.py / croatian_Lemmatizator.py
        
    <br>
    
    Jugo.java uses py.py as a lemmatization tool, the python program returns the lemmatized string. 
    Java program then cleans it, and calculates the sentiment of it.

    -   Line 85 of .java has the path to the 'py.py'.

# How to run
 -	First run `Javac Main.java`, then `java Main`
 -	Main.java starts the python program
