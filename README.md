# Examen Belatrix

## Instrucciones
El siguiente código trata de refactorizar el código proporcionado para hacerlo mas legible y a su vez usar pruebas unitarias, así mismo se ha agregado algunas mejoras arquitectónicas.

## Observaciones al código inicial
En la clase JobLogger se han agregado comentarios de que podría mejorarse.


## Mejoras realizadas
* Se definio un arquetipo para la estructura de carpetas.
* Se utilizo el patrón singleton propio de spring, builder con lombok.
* La solución esta orientada a servicios REST exponiendo un API que podría ser consumida por cualquier otro servicio de esta forma este microservicio es transversal.
* Se contruyo un pequeño microservicio.
* Se externalizo la conexión a la bd utilizando spring boot y JPA.
* Se crearon 7 test unitarios.
* Se refactorizo el código haciendo el código mas legible y usando principios SOLID.
* Se agrego documentación con swagger.



