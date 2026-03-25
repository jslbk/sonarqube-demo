pipeline {
    agent any

    options {
        timestamps()
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

        stage('Verify Java') {
            steps {
                sh 'java -version'
                sh 'chmod +x gradlew'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh './gradlew clean test jacocoTestReport'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh './gradlew sonar'
            }
        }

        stage('Allure Report') {
            steps {
                echo 'Allure results prepared in build/allure-results'
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
                if (fileExists('build/allure-results')) {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'build/allure-results']]
                    ])
                } else {
                    echo 'Allure results directory not found: build/allure-results'
                }
            }

            archiveArtifacts artifacts: 'build/reports/**', fingerprint: true, allowEmptyArchive: true
        }
    }
}