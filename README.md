# colpix-empleados
Challengue para la empresa Colpix
##

### Requisitos
1. java 21
2. maven
3. docker-compose


### Instrucciones de uso

1. Ubicarse en el raiz y hacer docker-compose up -d para levantar la bd y sonarqube
2. ejecutar EmpleadosApplication

###
### Para ejecutar SonarQube y ver la cobertura del codigo

1. Ejecutar para el analisis de codigo
mvn clean verify sonar:sonar \
-Dsonar.projectKey=colpix-empleados \
-Dsonar.host.url=http://localhost:9000 \
-Dsonar.token=sqp_6247f6687f9746b4d818b2e97f2acba2639e20cb
2. revisar el reporte en localhost:9000

### Alternativa a SonarQube, 
1. abrir el proyecto con IntelliJ
2. click derecho en la raiz del proyecto y elegir "more run debug" -> Run All test with coverage 