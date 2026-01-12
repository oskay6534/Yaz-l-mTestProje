pipeline {
    agent any

    stages {

        stage('Github - Kodlari Cek') {
            steps {
                echo 'Github repository cekiliyor...'
                checkout scm
            }
        }

        stage('Backend - Build') {
            steps {
                dir('backend') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage('Frontend - Build') {
            steps {
                dir('frontend') {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }

        stage('Frontend - Baslat') {
            steps {
                dir('frontend') {
                    bat '''
                    start cmd /c "npm start"
                    '''
                }
                sleep(time: 20, unit: 'SECONDS')
            }
        }

        stage('Birim Testleri') {
            steps {
                dir('backend') {
                    bat 'mvn test -DskipITs=true'
                }
            }
        }

        stage('Entegrasyon Testleri') {
            steps {
                dir('backend') {
                    bat 'mvn test -DskipUnitTests=true'
                }
            }
        }

        stage('Selenium Testleri') {
            steps {
                dir('backend') {
                    bat 'mvn -Dtest=*SeleniumTest test'
                }
            }
        }
    }

    post {
        failure {
            echo 'PIPELINE HATA ILE BITTI'
        }
        success {
            echo 'PIPELINE BASARILI'
        }
    }
}
