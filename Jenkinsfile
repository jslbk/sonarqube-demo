pipeline {
    agent any

    options {
        timestamps()
        skipDefaultCheckout(true)
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
                ansiColor('xterm') {
                    sh 'chmod +x gradlew'
                    sh './gradlew clean test jacocoTestReport'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh '''
                    ./gradlew sonar \
                      -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                      -Dsonar.host.url=$SONAR_HOST_URL \
                      -Dsonar.token=$SONAR_TOKEN
                '''
            }
        }

        stage('Generate Allure HTML Report') {
            steps {
                script {
                    if (fileExists('build/allure-results')) {
                        def allureHome = tool 'Allure'
                        sh "${allureHome}/bin/allure generate build/allure-results --clean -o build/allure-report"
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
                        alwaysLinkToLastBuild: true,
                        allowMissing: true
                    ])
                } else {
                    echo 'JaCoCo HTML report not found'
                }
            }

            script {
                if (fileExists('build/allure-report/index.html')) {
                    publishHTML(target: [
                        reportDir: 'build/allure-report',
                        reportFiles: 'index.html',
                        reportName: 'Allure Report',
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: true
                    ])
                } else {
                    echo 'Allure HTML report not found'
                }
            }
        }
    }
}