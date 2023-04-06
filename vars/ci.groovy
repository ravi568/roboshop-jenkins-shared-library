def call() {
    if (!env.sonar_extra_opts){
        env.sonar_extra_opts= ""
    }
    pipeline {
        agent any

        stages {

            stage('compile/build') {
                steps {
                    sh 'exit1'
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

        post{
            failure{
                mail body: "${component} - pipeline failed \n ${BUILD_URL}", from: ' ravidevopsprasad@gmail.com',subject: "${component} - pipeline failed" , to: ' ravidevopsprasad@gmail.com'
            }
        }

    }
}