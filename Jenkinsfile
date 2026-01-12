pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
        nodejs 'NodeJS'
    }

    environment {
        FRONTEND_PORT = "3000"
    }

    stages {

        stage('Github - Kodlari Cek') {
            steps {
                echo 'Github repository cekiliyor...'
                checkout scm
            }
        }

        stage('Backend - Build') {
            steps {
                echo 'Backend build ediliyor...'
                dir('backend') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage('Frontend - Build') {
            steps {
                echo 'Frontend build ediliyor...'
                dir('frontend') {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }

        // 🔥 EN KRİTİK STAGE
        stage('Frontend - Uygulamayi Baslat') {
            steps {
                echo 'Frontend baslatiliyor (npm start)...'
                dir('frontend') {
                    bat '''
                    start cmd /c "npm start"
                    '''
                }

                echo 'Frontend ayaga kalkmasi icin bekleniyor...'
                sleep(time: 20, unit: 'SECONDS')
            }
        }

        stage('Birim Testleri') {
            steps {
                echo 'Backend unit testleri calisiyor...'
                dir('backend') {
                    bat 'mvn test -DskipITs=true'
                }
            }
        }

        stage('Entegrasyon Testleri') {
            steps {
                echo 'Backend entegrasyon testleri calisiyor...'
                dir('backend') {
                    bat 'mvn test -DskipUnitTests=true'
                }
            }
        }

        stage('Selenium Testleri') {
            steps {
                echo 'Selenium testleri calisiyor...'
                dir('backend') {
                    bat 'mvn -Dtest=*SeleniumTest test'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline tamamlandi.'
        }

        failure {
            echo 'PIPELINE HATA ILE BITTI'
        }

        success {
            echo 'PIPELINE BASARIYLA TAMAMLANDI'
        }
    }
}
