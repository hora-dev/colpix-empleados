# colpix-empleados
Challengue para la empresa Colpix
##

### Requisitos
1. java 21
2. maven
3. docker-compose


### Instrucciones de uso

1. Ubicarse en el raiz y hacer docker-compose up -d para levantar la bd y sonarqube
2. Ejecutar EmpleadosApplication
3. Agregar un usuario admin con password = $2a$10$vnraRAskEtLYOO6QVhM8tuij8A.Zc7pbJAtNzZao/FZdtbSVka7Ym en tabla usuarios
3'. (opcional) Agregar los datos de usuario que estan en el raiz insert-empleados.sql
4. Bajar y volver a ejecutar EmpleadosApplication
5. Loguearse con admin/1234 en -> http://localhost:8080/api/v1/auth/login
6. Ver en el archivo raiz la collection de Postman para importarla.


### Para levantar con docker
1. Ubicarse en la raiz
2. Ejecutar docker-compose build
3. Ejecutar docker-compose up -d
4. Ya deberia estar disponible la app en el puerto 8080.

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