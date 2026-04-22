import java.sql.*;

public class EntradaDAO {

    private Connection conn;
    private ConciertoDAO conciertoDAO;

    public EntradaDAO(Connection conn) {
        this.conn = conn;
        this.conciertoDAO = new ConciertoDAO(conn);
    }

    public void insertar(Entrada entrada) {
        try {
            String sql1 = "INSERT INTO ENTRADA (CONCIERTO_ID, COMPRADOR, CANTIDAD, FECHACOMPRA) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql1);

            ps.setInt(1, entrada.getConcierto().getId());
            ps.setString(2, entrada.getComprador());
            ps.setInt(3, entrada.getCantidad());
            ps.setDate(4, java.sql.Date.valueOf(entrada.getFechaCompra()));

            ps.executeUpdate();
            System.out.println("Entrada registrada");

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }

    public void listar() {
        try {
            String sql2 = "SELECT * FROM ENTRADA";
            PreparedStatement ps = conn.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int conciertoId = rs.getInt("concierto_id");

                Concierto concierto = conciertoDAO.buscarPorId(conciertoId);

                System.out.println(
                        "Entrada ID: " + rs.getInt("id") + " || Comprador: " + rs.getString("comprador") + " || Cantidad: " + rs.getInt("cantidad") + " || Lugar: " + concierto.getLugar() + " || Artista: " + concierto.getArtista().getNombre()
                );
            }

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }
}