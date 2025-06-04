# Sistema de Gestión de Compras - ERP

Este proyecto simula un módulo de gestión de compras dentro de un sistema ERP utilizando Programación Orientada a Objetos en Java.

## Integrantes

- Dave Fernando Sigüenza Vallejo
- Emilio Andres Villalta Pardo

## Descripción del Proyecto

La aplicación permite gestionar proveedores, productos y solicitudes de compra. Se puede registrar, buscar, listar y cambiar el estado de las solicitudes. Además, calcula el costo total de cada solicitud de forma polimórfica utilizando interfaces.

## Requisitos Técnicos Implementados

- ✅ Herencia (`Persona` → `Proveedor`)
- ✅ Clase abstracta (`Persona`)
- ✅ Interfaz funcional (`Calculable`)
- ✅ Enumeración (`EstadoSolicitud`)
- ✅ Polimorfismo (implementación de `calcularCostoTotal()` en `Producto` y `SolicitudCompra`)
- ✅ Menú interactivo por consola

## Instrucciones para Ejecutar

1. Asegúrate de tener Java instalado (JDK 8+).
2. Compila los archivos:
   ```bash
   javac SistemaCompras.java
   ```
3. Ejecuta el sistema:
   ```bash
   java SistemaCompras
   ```

## Funcionalidades del Menú
```bash
1. Registrar proveedor  
2. Registrar producto  
3. Registrar solicitud de compra  
4. Listar proveedores  
5. Listar productos  
6. Listar solicitudes de compra  
7. Buscar proveedor por ID  
8. Buscar producto por nombre  
9. Buscar solicitud por número  
13. Aprobar / Rechazar solicitud de compra  
14. Calcular total de una solicitud  
15. Salir  
```
## Repositorio
Enlace -> https://github.com/AndrssWV/EXAMEN_POO.git
