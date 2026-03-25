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
                } else {
                    echo 'JaCoCo HTML report not found'
                }
            }

            script {
                if (fileExists('build/allure-results')) {
                    def allureHome = tool 'Allure'
                    env.PATH = "${allureHome}/bin:${env.PATH}"

                    allure([
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'build/allure-results']]
                    ])
                } else {
                    echo 'Skipping Allure report: build/allure-results not found'
                }
            }

            archiveArtifacts artifacts: 'build/reports/**', fingerprint: true, allowEmptyArchive: true
        }
    }
}