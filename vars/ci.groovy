def call() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts=""
    }
    node('workstation') {

        try {

            stage('Check Out Code') {
                cleanWs()
                git branch: 'main', url: 'https://github.com/ravi568/cart'
            }

            sh 'env'

            if (env.BRANCH_NAME != "main") {
                stage('Compile/Build') {
                    common.compile()
                }
            }

            stage('Test Cases') {
                common.testcases()
            }

            stage('Code Quality') {
                common.codequality()
            }

        }  catch (e) {
            mail body: "<h1>${component} - pipeline failed \n ${BUILD_URL}</h1>", from: 'ravidevopsprasad@gmail.com', subject: "${component} - pipeline failed", to: ' ravidevopsprasad@gmail.com', mimeType: 'text/html'

        }
    }

    }


