pipeline {
    agent any

    stages {
        stage('git checkout') {
            steps {
                git credentialsId: 'GitCredentials', url: 'https://github.com/buddhabhushanbhagat/microservice-project.git'
                bat 'mvn clean install -DskipTests=true'
                echo 'build is done'
            }
        }
    }
}
