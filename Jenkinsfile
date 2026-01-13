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

        stage('Docker - Build') {
            steps {
                echo 'Docker image leri build ediliyor...'
                bat 'docker-compose build'
            }
        }

        stage('Docker - Baslat') {
            steps {
                echo 'Docker container lari baslatiliyor...'
                bat 'docker-compose up -d'
                echo 'Container larin hazir olmasi bekleniyor...'
                sleep(time: 60, unit: 'SECONDS')
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
        always {
            echo 'Docker container lari durduruluyor ve temizleniyor...'
            bat 'docker-compose down || exit 0'
        }
        failure {
            echo 'PIPELINE HATA ILE BITTI'
        }
        success {
            echo 'PIPELINE BASARILI'
        }
    }
}
