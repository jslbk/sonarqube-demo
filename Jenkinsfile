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

        stage('Quality Gate') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            junit testResults: 'build/test-results/test/*.xml', allowEmptyResults: true
            publishHTML(target: [
                reportDir: 'build/reports/jacoco/test/html',
                reportFiles: 'index.html',
                reportName: 'JaCoCo Coverage Report',
                keepAll: true,
                alwaysLinkToLastBuild: true
            ])

            archiveArtifacts artifacts: 'build/reports/**', fingerprint: true
        }
    }
}