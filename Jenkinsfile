pipeline {
    agent any

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    tools {
        maven 'Maven'
        nodejs 'NodeJs'
    }

    environment {
        BACKEND_DIR = 'backend'
        FRONTEND_DIR = 'frontend'
        CI = 'true'
    }

    stages {

        /* ===============================
           STAGE 1 - GITHUB CHECKOUT
           =============================== */
        stage('Github - Kodlari Cek') {
            steps {
                echo 'Github repository cekiliyor...'
                checkout scm
            }
        }

        /* ===============================
           STAGE 2 - BUILD
           =============================== */
        stage('Build - Backend & Frontend') {
            steps {
                script {
                    echo 'Backend build ediliyor...'
                    dir(BACKEND_DIR) {
                        bat 'mvn clean compile'
                    }

                    echo 'Frontend build ediliyor...'
                    dir(FRONTEND_DIR) {
                        bat 'npm install'
                        bat 'npm run build'
                    }
                }
            }
        }

        /* ===============================
           STAGE 3 - UNIT TESTS
           =============================== */
        stage('Birim Testleri') {
            steps {
                script {
                    echo 'Birim testleri calistiriliyor...'
                    dir(BACKEND_DIR) {
                        bat 'mvn -B test -Dsurefire.excludes=**/*SeleniumTest.java'
                    }
                }
            }
            post {
                always {
                    dir(BACKEND_DIR) {
                        junit allowEmptyResults: true,
                              testResults: '**/target/surefire-reports/*.xml'
                    }
                }
            }
        }

        /* ===============================
           STAGE 4 - INTEGRATION TESTS
           =============================== */
        stage('Entegrasyon Testleri') {
            steps {
                script {
                    echo 'Entegrasyon testleri calistiriliyor...'
                    dir(BACKEND_DIR) {
                        bat 'mvn -B verify -DskipUnitTests=true'
                    }
                }
            }
            post {
                always {
                    dir(BACKEND_DIR) {
                        junit allowEmptyResults: true,
                              testResults: '**/target/failsafe-reports/*.xml'
                    }
                }
            }
        }

        /* ===============================
           STAGE 5 - DOCKER UP
           =============================== */
        stage('Docker - Servisleri Baslat') {
            steps {
                script {
                    echo 'Docker containerlar baslatiliyor...'

                    bat 'docker-compose down -v || exit 0'
                    bat 'docker-compose build'
                    bat 'docker-compose up -d'

                    echo 'Servislerin ayaga kalkmasi bekleniyor...'
                    sleep(time: 20, unit: 'SECONDS')
                }
            }
        }

        /* ===============================
           STAGE 6 - SELENIUM TESTS
           =============================== */
        stage('Selenium Testleri') {
            steps {
                timeout(time: 30, unit: 'MINUTES') {
                    script {
                        echo 'Selenium testleri calistiriliyor...'
                        dir(BACKEND_DIR) {
                            bat 'mvn -B -Dtest=*SeleniumTest test'
                        }
                    }
                }
            }
            post {
                always {
                    dir(BACKEND_DIR) {
                        junit allowEmptyResults: true,
                              testResults: '**/target/surefire-reports/*.xml'
                    }
                }
                success {
                    echo 'Selenium testleri BASARILI'
                }
                failure {
                    echo 'Selenium testleri BASARISIZ'
                }
            }
        }
    }

    /* ===============================
       POST ACTIONS
       =============================== */
    post {
        always {
            echo 'Docker containerlar kapatiliyor...'
            bat 'docker-compose down -v || exit 0'
        }
        success {
            echo 'PIPELINE BASARIYLA TAMAMLANDI'
        }
        failure {
            echo 'PIPELINE HATA ILE BITTI'
        }
    }
}
