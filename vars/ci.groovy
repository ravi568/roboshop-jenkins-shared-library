



//    if (env.TAG_NAME ==~ ".*"){
//        env.GTAG = "true"
//    } else {
//        env.GTAG = "false"
//    }

def call() {
    if (!env.sonar_extra_opts){
        env.sonar_extra_opts= ""
    }

    node('workstation') {


        try {

                stage('check out code ') {
                    cleanWs()
                    git branch: 'main', url: 'https://github.com/ravi568/cart'
                }

                sh 'env'

                if (env.BRANCH_NAME != "main") {
                    stage('compile/build') {
                        common.compile()
                    }
                }

        } catch (e) {
            mail body: "<h1>${component} - pipeline failed \n ${BUILD_URL}</h1>", from: 'ravidevopsprasad@gmail.com', subject: "${component} - pipeline failed", to: ' ravidevopsprasad@gmail.com', mimeType: 'text/html'

        }
    }

}

    //         println GTAG
    //         println BRANCH_NAME
    //
    //       if (env.GTAG != "true" && env.BRANCH_NAME != "main") {
    //           stage('test cases') {
    //               common.testcases()
    //           }
    //       }

    //       if (BRANCH_NAME ==~ "PR-.*") {
    //           stage('code quality') {
    //               common.codequality()
    //           }
    //       }
    //
    //
    //         if (env.GTAG == "true") {
    //             stage('package') {
    //                 common.testcases()
    //             }
    //
    //             stage('Artifact Upload') {
    //                 common.testcases()
    //             }



