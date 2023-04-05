def call() {
    if (!sonar_extra_opts){
        env.sonar_extra_opts= ""
    }
    pipeline {
        agent any

        stages {

            stage('compile/build') {
                steps {
                    script{
                          common.compile()
                    }

                }
            }

            stage('test cases') {
                steps {
                    script{
                        common.testcases()
                    }
                }
            }

            stage('code quality') {
                steps {
                    script{
                        common.codequality()
                    }
                }
            }

        }

    }
}