# Examen Belatrix

## Instrucciones
El siguiente código trata de refactorizar el código proporcionado para hacerlo mas legible y a su vez usar pruebas unitarias, así mismo se ha agregado algunas mejoras

## Mejoras realizadas
* Se separo la lógica en metodos independientes para poder ser testados (validateConfiguration, connectDB, disConnectDB, levelLog, 
writeLogConsole, writeLogBD, writeLogFile).
* La lógica de los metodos para registrar el log (file, console, db) fueron separados.
* Se configuró una bd H2 en memoria para escribir el log en la bd.
* Se procedio a crear la tabla y grabar en dicha tabla cada vez que el parametro sea true.
* Se agrego un properties para configurar los parametros de conexión a la bd y la ubicación del path.
* Se crearon 9 test unitarios.
* Se agrego comentarios a cada método.


