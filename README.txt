INTEGRANTES: DANIEL GALVIS Y SEBASTIÁN ARANGO


Para montar el proyecto, se debe tener configurado FraSCAti y se deben seguir los siguientes pasos:

1)Estando en src, compilar los proyectos: 	

frascati compile ProcesadorImagenes-rmi procesador
frascati compile coordinador coordinador
frascati compile ManejadorImagenes-rmi manejador

2) Correr los proyectos en el siguiente orden, manteniendose en la carpeta src:

frascati run procesador-rmi -libpath procesador.jar -s r -m run

frascati run coordinador-rmi -libpath coordinador.jar -s r -m run

frascati run ManejadorImagenes-rmi -libpath manejador.jar -s r -m run


Dentro del componente Coordinador hay una constante que indica cuantos devices de procesamiento hay.
De igual forma, una constante en ManejadorImagenes indica cuantos componentes de procesamiento hay por device.
