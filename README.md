# SonarQube Demo — Unit Testing & CI Quality Gate

This project demonstrates a **unit testing setup in Java** with a strong focus on **code quality analysis using SonarQube** and **integration into a Jenkins pipeline**.

---

## 🎯 What This Project Demonstrates

* Writing and running **unit tests (JUnit 5)**
* Building and executing tests with **Gradle**
* Generating **Allure reports** for test results
* Running **static code analysis with SonarQube**
* Enforcing **quality gates in CI (Jenkins)**

---

## 🚀 Run Tests Locally

```bash
chmod +x gradlew
./gradlew clean test
```

---

## 📊 Generate Allure Report

```bash
brew install allure
allure generate build/allure-results --clean -o build/allure-report
allure open build/allure-report
```

---

## 🔎 SonarQube Analysis

Run analysis locally (example):

```bash
./gradlew sonarqube \
  -Dsonar.projectKey=sonarqube-demo \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<YOUR_TOKEN>
```

This step:

* analyzes code quality
* calculates coverage
* detects bugs, vulnerabilities, code smells

---

## ⚙️ Jenkins Pipeline Integration (Concept)

Typical pipeline steps:

```text
1. Checkout code
2. Set up JDK 17
3. Run: ./gradlew clean test
4. Run: ./gradlew sonarqube
5. Wait for Quality Gate
6. Fail build if Quality Gate fails
```

This ensures:

* tests must pass
* code must meet quality standards

---

## 💡 Notes

* Requires **Java 17**
* On Windows use `gradlew.bat`
* SonarQube must be running locally or remotely
