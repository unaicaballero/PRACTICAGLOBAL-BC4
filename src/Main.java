import java.sql.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String usuario = "RIBERA";
        String contraseña = "ribera";

        Scanner sc = new Scanner(System.in);
        int opcion;
        Connection conexion = null;

        try { conexion = DriverManager.getConnection(url, usuario, contraseña);
            ArtistaDAO artistaDAO = new ArtistaDAO(conexion);
            ConciertoDAO conciertoDAO = new ConciertoDAO(conexion);
            EntradaDAO entradaDAO = new EntradaDAO(conexion);

            do {
                System.out.println("Elige una opcion");
                System.out.println("1. Añadir un artista");
                System.out.println("2. Eliminar un artista");
                System.out.println("3. Listar artistas");
                System.out.println("4. Añadir un concierto");
                System.out.println("5. Eliminar un concierto");
                System.out.println("6. Listar conciertos");
                System.out.println("7. Registrar entrada");
                System.out.println("8. Listar entradas");
                System.out.println("0. Salir");

                opcion = sc.nextInt();
                sc.nextLine();


                switch (opcion) {

                    case 1:

                        System.out.println("Nombre:");
                        String nombre = sc.nextLine();

                        System.out.println("Genero musical:");
                        String genero = sc.nextLine();

                        System.out.println("Pais:");
                        String pais = sc.nextLine();

                        Artista artista = new Artista(0, nombre, genero, pais);

                        artistaDAO.insertar(artista);
                        break;

                    case 2:
                        System.out.println("ID del artista a eliminar:");
                        int idEliminarArtista = sc.nextInt();
                        artistaDAO.eliminar(idEliminarArtista);
                        break;

                    case 3:
                        artistaDAO.listar();
                        break;

                    case 4:

                    System.out.println("ID del artista:");
                    int idArtista = sc.nextInt();
                    sc.nextLine();

                    Artista art = artistaDAO.buscarPorId(idArtista);

                    if (art == null) {
                        System.out.println("El artista no existe");
                        break;
                    }

                    System.out.println("Fecha (YYYY-MM-DD):");
                    String fecha = sc.nextLine();

                    System.out.println("Lugar:");
                    String lugar = sc.nextLine();

                    System.out.println("Precio:");
                    double precio = sc.nextDouble();
                    sc.nextLine();

                    Concierto concierto = new Concierto(0, art, fecha, lugar, precio);
                    conciertoDAO.insertar(concierto);
                    break;

                    case 5:

                    System.out.println("ID del concierto a eliminar:");
                    int idEliminarConcierto = sc.nextInt();
                    conciertoDAO.eliminar(idEliminarConcierto);
                    break;

                    case 6:

                        conciertoDAO.listar();
                        break;

                    case 7:
                        System.out.println("ID del concierto:");
                        int idConcierto = sc.nextInt();
                        sc.nextLine();

                        Concierto conc = conciertoDAO.buscarPorId(idConcierto);

                        if (conc == null) {
                            System.out.println("El concierto no existe");
                            break;
                        }

                        System.out.println("Comprador:");
                        String comprador = sc.nextLine();

                        System.out.println("Cantidad:");
                        int cantidad = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Fecha de compra (YYYY-MM-DD):");
                        String fechaCompra = sc.nextLine();

                        Entrada entrada = new Entrada(0, conc, comprador, cantidad, fechaCompra);
                        entradaDAO.insertar(entrada);
                        break;

                    case 8:
                        entradaDAO.listar();
                        break;

                    default:
                        System.out.println("Opcion no valida");
                }

            } while (opcion != 0);


        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }
}
