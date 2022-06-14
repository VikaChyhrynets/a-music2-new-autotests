# AMN Banking AQA
https://sonarqube8.andersenlab.com/dashboard?id=A-Music2-new-autotests
# Run:
`mvn clean test`
# Get report
`allure serve target/allure-results`

# Run sonar scan
Export sonar token in `SONAR_TOKEN` env variable before running commands
`mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN -Dsonar.projectKey=A-Music2-new-autotests`