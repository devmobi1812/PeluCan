# PeluCan

PeluCan es un software diseñado para la gestión de una peluquería canina, desarrollado en Java y MySQL.

## Requisitos 
Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes requisitos:

- **Git** (para clonar el repositorio)
- **XAMPP** (con Apache y MySQL activados)
- **Java 17.0.9** o superior
- **IDE de tu preferencia** (NetBeans, IntelliJ IDEA, Eclipse, etc.)

## Instalación y Configuración
Sigue estos pasos para configurar y ejecutar el proyecto:

### 1. Clonar el Repositorio

Abre una terminal y ejecuta el siguiente comando para clonar el repositorio del proyecto:

```bash
git clone [URL_DEL_REPOSITORIO]
```

Reemplaza `[URL_DEL_REPOSITORIO]` con la URL del repositorio de Git.

### 2. Configurar la base de datos
1. Abre **XAMPP** y activa **Apache** y **MySQL**.
2. Accede a **phpMyAdmin** y crea una nueva base de datos con el nombre:
   ```sql
   CREATE DATABASE pelucan;
   ```


### 3. Ejecutar el proyecto
1. Abre tu IDE preferido.
2. Importa el proyecto.
3. Asegúrate de que las dependencias y configuraciones de conexión a la base de datos sean correctas.
4. Ejecuta la aplicación.


## Deploy
Para desplegar el proyecto en un entorno de producción, sigue estos pasos:

1. Configurar un servidor SQL y asegurarse de que esté en ejecución.
2. Crear la base de datos con el siguiente comando:
   ```sql
   CREATE DATABASE pelucan;
   ```
3. Ejecutar el archivo **.jar** disponible en el repositorio.