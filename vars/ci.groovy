
def call() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts=""
    }

    if(env.TAG_NAME ==~ ".*") {
        env.GTAG = "true"
    } else {
        env.GTAG = "false"
    }
    node('workstation') {

        try {

            stage('Check Out Code') {
                cleanWs()
                git branch: 'main', url: "https://github.com/ravi568/${component}"
            }

            sh 'env'

            if (env.BRANCH_NAME != "main") {
                stage('Compile/Build') {
                    common.compile()
                }
            }

            println GTAG
            println BRANCH_NAME

            if(env.GTAG != "true" && env.BRANCH_NAME != "main") {
                stage('Test Cases') {
                    common.testcases()
                }
            }

            if (BRANCH_NAME ==~ "PR.*"){
                stage('Code Quality') {
                    common.codequality()
                }
            }

            if(env.GTAG == "true") {
                stage('package') {
                    common.prepareArtifacts()
                }

                stage('Artifact upload') {
                    common.Artifactupload()
                }
            }



        }  catch (e) {
            mail body: "<h1>${component} - pipeline failed \n ${BUILD_URL}</h1>", from: 'ravidevopsprasad@gmail.com', subject: "${component} - pipeline failed", to: ' ravidevopsprasad@gmail.com', mimeType: 'text/html'

        }
    }

    }




