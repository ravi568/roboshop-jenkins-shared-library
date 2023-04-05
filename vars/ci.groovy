def call() {
    pipeline {
        agent any

        stages {

            stage('compile/build') {
                steps {
                    echo 'compile/build'
                }
            }

            stage('test cases') {
                steps {
                    echo 'test cases'
                }
            }
        }

    }
}