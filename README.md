# DragoLandia
## Introducción
Este proyecto pretende probar Hybernate a través de su utilización en un pequeño sistema de peleas automatizado. El programa consiste en que un mago se enfrenta contra un monstruo (ambos elegidos aleatoriamente) y aquel que sobreviva es el ganador.

## Análisis
### Diagrama de Clases

```mermaid
classDiagram
direction TB
    class Monstruo {
	    +int id
	    +String nombre
	    +int vida
	    +Tipo tipo
	    +int fuerza
	    +Monstruo()
	    +Monstruo(id:int, nombre:String, vida:int, tipo:TipoMonstruo, fuerza:int)
	    +atacar(objetivo:Mago)
    }

    class Tipo {
	    ogro
	    troll
	    espectro
    }

    class Bosque {
	    +int id
	    +String nombre
	    +int nivelPeligro
	    +List~Monstruo~ monstruosJefes
	    +Monstruo monstruoJefe
	    +Bosque()
	    +Bosque(id:int, nombre:String, nivelPeligro:int, monstruoJefe:Monstruo)
	    +mostrarJefe()
	    +cambiarJefe(nuevoJefe:Monstruo)
    }

    class Mago {
	    +int id
	    +String nombre
	    +int vida
	    +int nivelMagia
	    +Mago()
	    +Mago(id:int, nombre:String, vida:int, nivelMagia:int)
	    +lanzarHechizo(objetivo:Monstruo)
    }

    class Vista {
	    +imprimirMensaje(String mensaje)
    }

    class Controlador {
	    +Controlador(vista:Vista, modelo:Modelo)
	    +iniciarSimulacion()
	    +manejarAccion(String accion)
	    +getVista() : Vista
	    +getModelo() : Modelo
    }

    class Principal {
	    +main()
    }

    class Modelo {
	    -static Modelo instancia$
	    -List~Monstruo~ listaMonstruos
	    -List~Mago~ listaMagos
	    -List~Bosque~ listaBosques
	    -Monstruo monstruo
	    -Mago mago
	    -Bosque bosque
	    +Modelo()
	    +static getInstancia() : Modelo
	    +inicializarJuego()
    }

	<<enumeration>> Tipo

    Monstruo --> Tipo
    Bosque o-- Monstruo : monstruoJefe
    Principal ..> Controlador : usa
    Controlador --> Vista
    Controlador --> Modelo
    Modelo --> Monstruo
    Modelo --> Mago
    Modelo --> Bosque
```

## Diseño
### Diagrama E-R

```mermaid
erDiagram
    direction TB

    MAGO {
        int id PK
        string nombre
        int vida
        int nivelMagia
    }

    MONSTRUO {
        int id PK
        string nombre
        int vida
        string tipo
        int fuerza
    }

    BOSQUE {
        int id PK
        string nombre
        int nivelPeligro
    }

    BOSQUE ||--|| MONSTRUO : "tiene jefe"
    BOSQUE ||--o{ MONSTRUO : "contiene"
    MAGO }o--o{ BOSQUE : "pelea"
```

