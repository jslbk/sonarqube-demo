pipeline {
    agent any

    options {
        timestamps()
    }

      options {
            skipDefaultCheckout()
        }

    environment {
        SONAR_PROJECT_KEY = 'portfolio-aqa-demo'
        SONAR_HOST_URL = 'http://sonarqube:9000'
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests & Coverage') {
            steps {
                sh './gradlew clean test jacocoTestReport'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh './gradlew sonar'
            }
        }

        stage('Prepare Allure Report') {
            steps {
                script {
                    if (fileExists('build/allure-results')) {
                        echo 'Allure results found'
                    } else {
                        echo 'Allure results directory not found: build/allure-results'
                    }
                }
            }
        }
    }

    post {
        always {
            junit testResults: 'build/test-results/test/*.xml', allowEmptyResults: true

            script {
                if (fileExists('build/reports/jacoco/test/html/index.html')) {
                    publishHTML(target: [
                        reportDir: 'build/reports/jacoco/test/html',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Coverage Report',
                        keepAll: true,
                        alwaysLinkToLastBuild: true
                    ])
                }
            }

            script {
                sh 'echo "Allure files:"'
                sh 'find build/allure-results -maxdepth 2 -type f || true'

                if (fileExists('build/allure-results') && sh(script: "find build/allure-results -type f | wc -l", returnStdout: true).trim() != '0') {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'build/allure-results']]
                    ])
                } else {
                    echo 'Allure results directory is missing or empty'
                }
            }
        }
    }
}