import java.util.*;

public class SistemaCompras {
    private static List<Proveedor> proveedores = new ArrayList<>();
    private static List<Producto> productos = new ArrayList<>();
    private static List<SolicitudCompra> solicitudes = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            System.out.println("\n===== SISTEMA DE GESTIÓN DE COMPRAS ERP =====");
            System.out.println("1. Registrar proveedor");
            System.out.println("2. Registrar producto");
            System.out.println("3. Registrar solicitud de compra");
            System.out.println("4. Listar proveedores");
            System.out.println("5. Listar productos");
            System.out.println("6. Listar solicitudes de compra");
            System.out.println("7. Buscar proveedor por ID");
            System.out.println("8. Buscar producto por nombre");
            System.out.println("9. Buscar solicitud por número");
            System.out.println("10. Aprobar / Rechazar solicitud de compra");
            System.out.println("11. Calcular total de una solicitud");
            System.out.println("12. Salir");

            while (true) {
                System.out.print("Seleccione una opción: ");
                if (sc.hasNextInt()) {
                    opcion = sc.nextInt(); sc.nextLine();
                    if (opcion >= 1 && opcion <= 12) break;
                    else System.out.println("Opcion No Valida");
                } else {
                    System.out.println("Opcion No Valida");
                    sc.next();
                }
            }

            switch (opcion) {
                case 1 : registrarProveedor(); break;
                case 2 : registrarProducto(); break;
                case 3 : registrarSolicitud(); break;
                case 4 : 
                    if (proveedores.size()>0) proveedores.forEach(p -> System.out.println("ID: "+p.getId() + " - Nombre: " + p.getNombre()));
                    else System.out.println("No se han registrado proveedores");
                    break;
                case 5 :
                    if (productos.size()>0) productos.forEach(p -> System.out.println("Producto: "+p.getNombre() + " - Precio: $" + p.getPrecioUnitario()));
                    else System.out.println("No se han registrado productos");
                    break;
                case 6 : 
                    if (solicitudes.size()>0) solicitudes.forEach(s -> s.printSolicitud());
                    else System.out.println("No se han registrado solicitudes");
                    break;
                case 7 : buscarProveedor(); break;
                case 8 : buscarProducto(); break;
                case 9 : buscarSolicitud(); break;
                case 10 : cambiarEstadoSolicitud(); break;
                case 11 : calcularTotalSolicitud(); break;
            }

        } while (opcion != 12);
    }

    private static void registrarProveedor() {
        String id;
        while (true) {
            System.out.print("ID: ");
            String i = sc.nextLine().trim();
            if (!i.isEmpty()) {
                if (proveedores.stream().filter(p -> p.getId().equalsIgnoreCase(i)).count() > 0) {
                    System.out.println("El ID ya se ha registrado.");
                } else {
                    id = i;
                    break;
                }
            } else System.out.println("El ID no puede estar vacío.");
        }

        String nombre;
        while (true) {
            System.out.print("Nombre: ");
            nombre = sc.nextLine();
            if (!nombre.trim().isEmpty()) break;
            else System.out.println("El Nombre no puede estar vacío.");
        }
        proveedores.add(new Proveedor(id, nombre));
        System.out.println("Proveedor registrado.");
    }

    private static void registrarProducto() {
        String nombre;
        while (true) {
            System.out.print("Nombre: ");
            String n = sc.nextLine();
            if (!n.trim().isEmpty()) {
                if (productos.stream().filter(p -> p.getNombre().equalsIgnoreCase(n)).count() > 0) {
                    System.out.println("El Producto ya se ha registrado.");
                } else {
                    nombre = n;
                    break;
                }
            } else System.out.println("El Nombre no puede estar vacío.");
        }
        System.out.print("Precio unitario (si incluye decimales utlizar ,): ");
        double precio = 0;
        while (true) {
            try {
                precio = sc.nextDouble(); sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("¡ERROR! Debe ingresar los decimales utilizando ,");
                return;
                
            }
        }
        productos.add(new Producto(nombre, precio));
        System.out.println("Producto registrado.");
    }

    private static void registrarSolicitud() {
        while (true) {
            System.out.print("ID del proveedor: ");
            String id = sc.nextLine().trim();
            if (!id.isEmpty()) {
                Proveedor prov = proveedores.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
                if (prov != null) {
                    SolicitudCompra sol = new SolicitudCompra(prov);
                    int cantidad;
                    while (true) {
                        System.out.print("Cantidad de productos a agregar: ");
                        if (sc.hasNextInt()) {
                            cantidad = sc.nextInt(); sc.nextLine();
                            if (cantidad > 0) break;
                            else System.out.println("Cantidad No Valida");
                        } else {
                            System.out.println("Cantida No Valida");
                            sc.next();
                        }
                    }
                    for (int i = 0; i < cantidad; i++) {
                        while (true) {
                            System.out.print("Nombre del producto["+(i+1)+"]: ");
                            String nombre = sc.nextLine();
                            if (!nombre.trim().isEmpty()) {
                                Producto prod = productos.stream().filter(p -> p.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
                                if (prod != null) {
                                    int cant;
                                    while (true) {
                                        System.out.print("Cantidad: ");
                                        if (sc.hasNextInt()) {
                                            cant = sc.nextInt(); sc.nextLine();
                                            if (cant > 0) break;
                                            else System.out.println("Cantidad No Valida");
                                        } else {
                                            System.out.println("Cantidad No Valida");
                                            sc.next();
                                        }
                                    }
                                    sol.agregarProducto(prod, cant);
                                    break;
                                } else System.out.println("Producto no encontrado");
                            } else System.out.println("El Nombre no puede estar vacío.");
                        }
                        
                    }
                    solicitudes.add(sol);
                    System.out.println("Solicitud registrada con número: " + sol.getNumero());
                    break;

                } else System.out.println("Proveedor no encontrado.");
            }
            else System.out.println("El ID del proveedor no puede estar vacío.");
        }
    }

    private static void buscarProveedor() {
        while (true) {
            System.out.print("Ingrese ID del proveedor: ");
            String id = sc.nextLine().trim();
            if (!id.isEmpty()) {
                Proveedor prov = proveedores.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
                if (prov != null) {
                    System.out.println("Proveedor: "+prov.getNombre());
                    break;
                } else System.out.println("Proveedor no encontrado");
                
            }
            else System.out.println("El ID no puede estar vacío.");
        }
        
    }

    private static void buscarProducto() {
        while (true) {
            System.out.print("Ingrese nombre del producto: ");
            String nombre = sc.nextLine();
            if (!nombre.trim().isEmpty()) {
                Producto prod = productos.stream().filter(p -> p.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
                if (prod != null) {
                    System.out.println(prod.getNombre()+" - Precio: $"+prod.getPrecioUnitario());
                    break;
                } else System.out.println("Producto no encontrado");
                
            }
            else System.out.println("El ID no puede estar vacío.");
        }
    }

    private static void buscarSolicitud() {
        while (true) {
            System.out.print("Ingrese número de solicitud: ");
            if (sc.hasNextInt()) {
                int num = sc.nextInt(); sc.nextLine();
                SolicitudCompra sol = solicitudes.stream().filter(s -> s.getNumero() == num).findFirst().orElse(null);
                if (sol != null) {
                    sol.printSolicitud();
                    break;    
                } else System.out.println("Solicitud no encontrada");
            } else {
                System.out.println("Numero no valido");
                sc.next();
            }
        }
        
    }

    private static void cambiarEstadoSolicitud() {
        while (true) {
            System.out.print("Número de solicitud: ");
            if (sc.hasNextInt()) {
                int num = sc.nextInt(); sc.nextLine();
                SolicitudCompra sol = solicitudes.stream().filter(s -> s.getNumero() == num).findFirst().orElse(null);
                if (sol != null) {
                    while (true) {
                        System.out.print("Nuevo estado (SOLICITADA, EN_REVISION, APROBADA, RECHAZADA): ");
                        try {
                            EstadoSolicitud estado = EstadoSolicitud.valueOf(sc.nextLine().toUpperCase());
                            sol.cambiarEstado(estado);
                            System.out.println("Estado actualizado.");
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("El estado ingresado no es válido.");
                        }
                    }
                    break;
                } else System.out.println("Solicitud no encontrada");
            } else {
                System.out.println("Numero No Valida");
                sc.next();
            }
        }
    }

    private static void calcularTotalSolicitud() {
        while (true) {
            System.out.print("Número de solicitud: ");
            if (sc.hasNextInt()) {
                int num = sc.nextInt(); sc.nextLine();
                SolicitudCompra sol = solicitudes.stream().filter(s -> s.getNumero() == num).findFirst().orElse(null);
                if (sol != null) {
                    System.out.println("Total: $" + sol.calcularCostoTotal());
                    break;    
                } else System.out.println("Solicitud no encontrada");
            } else {
                System.out.println("Numero no valido");
                sc.next();
            }
        }
    }
}

enum EstadoSolicitud {
    SOLICITADA, EN_REVISION, APROBADA, RECHAZADA
}

interface Calculable {
    double calcularCostoTotal();
}

abstract class Persona {
    protected String id;
    protected String nombre;

    public Persona(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}

class Proveedor extends Persona {
    private List<Producto> productos;

    public Proveedor(String id, String nombre) {
        super(id, nombre);
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public List<Producto> getProductos() {
        return productos;
    }
}

class Producto implements Calculable {
    private String nombre;
    private double precioUnitario;

    public Producto(String nombre, double precioUnitario) {
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double calcularCostoTotal() {
        return precioUnitario;
    }
}

class SolicitudCompra implements Calculable {
    private static int contador = 0;
    private int numero;
    private Proveedor proveedor;
    private Map<Producto, Integer> productos;
    private EstadoSolicitud estado;

    public SolicitudCompra(Proveedor proveedor) {
        this.numero = ++contador;
        this.proveedor = proveedor;
        this.productos = new HashMap<>();
        this.estado = EstadoSolicitud.SOLICITADA;
    }

    public int getNumero() {
        return numero;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        productos.put(producto, cantidad);
    }

    public void cambiarEstado(EstadoSolicitud nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public double calcularCostoTotal() {
        double total = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            total += entry.getKey().getPrecioUnitario() * entry.getValue();
        }
        return total;
    }

    public String toString() {
        return "Solicitud N°" + numero + " - Estado: " + estado;
    }

    public void printSolicitud() {
        System.out.println("Nr° Solicitud: "+numero+" - Estado: "+estado);
        System.out.println("Proveedor: "+proveedor.getNombre()+" ("+proveedor.getId()+")");
        System.out.println("Productos:");
        productos.forEach((key, value) -> System.out.println("- "+key.getNombre()+" ("+value+" unidad/es)"));
        System.out.println();
    }
}