import java.sql.*;

public class ConciertoDAO {

    private Connection conn;
    private ArtistaDAO artistaDAO;

    public ConciertoDAO(Connection conn) {
        this.conn = conn;
        this.artistaDAO = new ArtistaDAO(conn);
    }

    public void eliminar(int id) {
        try {
            String sql1 = "DELETE FROM CONCIERTO WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Concierto eliminado");

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }

    public void insertar(Concierto concierto) {
        try {
            String sql2 = "INSERT INTO CONCIERTO (ARTISTA_ID, FECHA, LUGAR, PRECIOENTRADA) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql2);

            ps.setInt(1, concierto.getArtista().getId());
            ps.setDate(2, java.sql.Date.valueOf(concierto.getFecha()));
            ps.setString(3, concierto.getLugar());
            ps.setDouble(4, concierto.getPrecioEntrada());

            ps.executeUpdate();
            System.out.println("Concierto insertado");

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }

    public void listar() {
        try {
            String sql3 = "SELECT * FROM CONCIERTO";
            PreparedStatement ps = conn.prepareStatement(sql3);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int artistaId = rs.getInt("artista_id");

                Artista artista = artistaDAO.buscarPorId(artistaId);

                String nombreArtista;

                if (artista != null) {
                    nombreArtista = artista.getNombre();
                } else {
                    nombreArtista = "No encontrado";
                }
                System.out.println(
                        "ID: " + id + " || Artista: " + nombreArtista + " || Fecha: " + rs.getString("fecha") + " || Lugar: " + rs.getString("lugar") + " || Precio: " + rs.getDouble("precioEntrada")
                );
            }
        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }


    public Concierto buscarPorId(int idBuscar) {
        Concierto concierto = null;
        try {
            String sql4 = "SELECT * FROM CONCIERTO WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql4);
            ps.setInt(1, idBuscar);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int artistaId = rs.getInt("artista_id");
                Artista artista = artistaDAO.buscarPorId(artistaId);

                concierto = new Concierto(
                        rs.getInt("id"),
                        artista,
                        rs.getString("fecha"),
                        rs.getString("lugar"),
                        rs.getDouble("precioEntrada")
                );
            }

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }

        return concierto;
    }
}