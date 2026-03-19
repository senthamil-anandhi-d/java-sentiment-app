# Developer Structure Report

This document provides a high-level overview of the application architecture, directory structure, and technology stack for developers working on the `java-sentiment-app` project.

## Technology Stack

- **Backend Framework**: SparkJava (Micro web framework)
- **NLP Engine**: Stanford CoreNLP (Sentiment Analysis)
- **Frontend**: Vanilla HTML / CSS / JS
- **Build Tool**: Maven (Java 11)

---

## Directory Structure Overview

The project follows a standard Maven directory structure.

```text
c:\Users\Senthamil Anandhi D\IdeaProjects\Sent_Front\
│
├── src/                                  # All source code files
│   ├── main/
│   │   ├── java/org/sentiment/           # Backend Java source files
│   │   │   ├── Application.java          # Entry point and API routing configuration
│   │   │   └── SentimentAnalyzer.java    # Core NLP initialization and sentiment logic
│   │   │
│   │   └── resources/public/             # Frontend static web files served by SparkJava
│   │       ├── index.html                # Main UI structure
│   │       ├── style.css                 # Frontend styling
│   │       └── script.js                 # Frontend API calls and interaction logic
│   │
│   └── test/                             # Unit tests (Currently empty)
│       └── java/
│
├── pom.xml                               # Maven project dependencies and plugins
├── README.md                             # End-user documentation and run instructions
└── run.cmd                               # Local utility script to boot the application
```

---

## Component Breakdown

### 1. Backend (`src/main/java/...`)
The backend exposes a lightweight REST API running on port `4567`.
- **`Application.java`**: The main execution point (`public static void main`). It initializes the SparkJava server, configures the frontend static file mapping to `/public`, and sets up the `/analyze` POST endpoint which accepts and returns JSON using the GSON library.
- **`SentimentAnalyzer.java`**: Responsible for configuring the `StanfordCoreNLP` pipeline. The `init()` method pre-loads the heavy natural language models into memory, and `findSentiment()` returns the longest sentence's sentiment score (e.g., *Negative, Positive, Neutral*).

### 2. Frontend (`src/main/resources/public/...`)
The frontend is hosted directly by the SparkJava server and requires no separate bundler (like Webpack/Vite).
- The `index.html` file provides a simple textbox interface. 
- When the user submits text, `script.js` captures it and sends a standard `fetch` POST request to the `/analyze` endpoint, then dynamically updates the result in the UI based on the response class defined in `style.css`.

### 3. Build & Configuration
- **`pom.xml`**: Manages all libraries. Key dependencies are `spark-core`, `gson`, `slf4j-simple`, and `stanford-corenlp` (both the core engine and the `models` classifier).
- **`run.cmd`**: Because this application relies heavily on the environment correctly picking up Java 11 and installing Maven, `run.cmd` explicitly sets `JAVA_HOME` and wraps the Maven execution command (`mvn clean compile exec:java`) to provide a frictionless local startup experience.
