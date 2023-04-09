import groovy.lang.groovydoc.GroovydocTag

def call() {
    if (!env.sonar_extra_opts){
        env.sonar_extra_opts= ""
    }

    if (env.TAG_NAME ==~ ".*"){
        env.GTAG = "true"
    } else {
        env.GTAG = "false"
    }
    node('workstation') {

     try {

         stage('check out code ') {
             cleanWs()
             git branch: 'main', url: 'https://github.com/ravi568/cart'
         }

         sh 'env'

       if (env.BRANCH_NAME != "main" ) {
           stage('compile/build') {
               common.compile()
           }
       }

         println GTAG
         println BRANCH_NAME

       if(env.GTAG != "true" && env.BRANCH_NAME != "main") {
           stage('test cases') {
               common.testcases()
           }
       }

         stage('code quality') {
             common.codequality()
         }
     } catch (e) {
         mail body: "<h1>${component} - pipeline failed \n ${BUILD_URL}</h1>", from: 'ravidevopsprasad@gmail.com',subject: "${component} - pipeline failed" , to: ' ravidevopsprasad@gmail.com', mimeType: 'text/html'

     }

    }

}
