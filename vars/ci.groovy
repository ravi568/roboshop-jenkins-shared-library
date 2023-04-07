def call() {
    if (!env.sonar_extra_opts){
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

        post{
            failure{
                mail body: "<h1>${component} - pipeline failed \n ${BUILD_URL}</h1>", from: 'ravidevopsprasad@gmail.com',subject: "${component} - pipeline failed" , to: ' ravidevopsprasad@gmail.com', mimeType: 'text/html'
            }
        }

    }
}