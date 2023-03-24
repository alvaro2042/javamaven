job('Java Maven App DSL 2') {
    description('Java Maven App con DSL para el curso de Jenkins')
    scm {
        git('https://github.com/alvaro2042/javamaven.git', 'main') { node ->
            node / gitConfigName('alvaro2042')
            node / gitConfigEmail('acamargo@lucasian.com')
        }
    }
    steps {
        maven {
          mavenInstallation('MavenJenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('JavenJenkins')
          goals('test')
        }
        shell('''
          echo "Entrega: Desplegando la aplicación" 
          java -jar "/var/jenkins_home/workspace/Java Maven App DSL 2/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
       }
    }
}
