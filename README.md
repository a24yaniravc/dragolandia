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
    +Monstruo(id:int, nombre:String, vida:int, tipo:Tipo, fuerza:int)
    +atacar(objetivo:Mago)
}

class Tipo {
    <<enumeration>>
    ogro
    troll
    espectro
}

class Bosque {
    +int id
    +String nombre
    +int nivelPeligro
    +List~Monstruo~ monstruos
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
    +List~Hechizo~ hechizos
    +Mago()
    +Mago(id:int, nombre:String, vida:int, nivelMagia:int)
    +lanzarHechizo(objetivo:Monstruo)
    +aprenderHechizo(h:Hechizo)
}

class Hechizo {
    +int id
    +String nombre
    +Hechizo()
    +Hechizo(nombre:String)
    +aplicar(objetivo:Monstruo)
}

class Dragon {
    +int id
    +String nombre
    +int intensidadFuego
    +int resistencia
    +Dragon()
    +Dragon(nombre:String, intensidadFuego:int, resistencia:int)
    +atacar(objetivo:Mago)
}

class Vista {
    +imprimirMensaje(mensaje:String)
}

class Controlador {
    +Controlador()
    +comenzarCombate()
    +getModelo():Modelo
    +getVista():Vista
}

class Modelo {
    -static Modelo instancia
    -List~Monstruo~ listaMonstruos
    -List~Mago~ listaMagos
    -List~Bosque~ listaBosques
    -Monstruo monstruo
    -Mago mago
    -Bosque bosque
    +Modelo()
    +static getInstancia():Modelo
    +inicializarJuego()
    +addMagoToLista(m:Mago)
    +addMonstruoToLista(m:Monstruo)
    +addBosqueToLista(b:Bosque)
    +getListaMagos():List~Mago~
}

Monstruo --> Tipo
Bosque o-- Monstruo : monstruoJefe
Bosque o-- "0..*" Monstruo : contiene
Mago o-- "0..*" Hechizo : hechizos
Bosque o-- Dragon
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
    int dragon_id FK
}

DRAGON {
    int id PK
    string nombre
    int intensidadFuego
    int resistencia
}

HECHIZO {
    int id PK
    string nombre
}

MAGOS_HECHIZOS {
    int mago_id FK
    int hechizo_id FK
}

BOSQUE ||--|| MONSTRUO : "tiene jefe"
BOSQUE ||--o{ MONSTRUO : "contiene"
BOSQUE ||--|| DRAGON : "tiene"
MAGO }o--o{ HECHIZO : "aprende"
```

