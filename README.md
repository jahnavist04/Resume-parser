# Resume Parser (Java)

A beginner-friendly Java project that extracts basic information from a text resume.

## Features

- Extracts the candidate's name
- Finds an email address
- Finds a phone number
- Detects a set of common technical skills
- Extracts the education line

## Requirements

- Java 11 or later
- No external libraries required

## Project structure

```text
Resume-Parser/
├── src/
│   └── ResumeParser.java
├── resumes/
│   └── sample.txt
├── README.md
└── .gitignore
```

## Run

Run these commands from the project root:

```bash
javac src/ResumeParser.java
java -cp src ResumeParser
```

The program reads `resumes/sample.txt`. Edit that file to try another resume.

## Note

This starter version reads plain-text (`.txt`) resumes. PDF parsing can be added later with a Java PDF library.
