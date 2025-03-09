
package datosDAO;
import database.Conexion;
import datos.interfaces.CRUDGeneralInterface;
import entidades.Categoria;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author sneyker
 */
public class CategoriaDAO implements CRUDGeneralInterface<Categoria> {

    private final Conexion conectar;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public CategoriaDAO() {
        conectar = Conexion.getInstance();
    }

    @Override
    public List<Categoria> getAll(String list) {
        List<Categoria> registros = new ArrayList();
        try {
            // Especificamos un ResultSet de tipo scrollable
            ps = conectar.conectar().prepareStatement("SELECT * FROM categoria WHERE nombre LIKE ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, "%" + list + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                registros.add(new Categoria(
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)
                ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            conectar.desconectar();
        }
        return registros;
    }

    @Override
    public boolean insert(Categoria object) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement("INSERT INTO categoria(nombre, descripcion, estado) VALUES (?, ?, ?)");
            ps.setString(1, object.getNombre());
            ps.setString(2, object.getDescripcion());
            ps.setBoolean(3, object.isActivo());
            if (ps.executeUpdate() > 0) {
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            conectar.desconectar();
        }
        return resp;
    }

    @Override
    public boolean update(Categoria object) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement("UPDATE categoria SET nombre=?, descripcion=?, estado=? WHERE id=?");
            ps.setString(1, object.getNombre());
            ps.setString(2, object.getDescripcion());
            ps.setBoolean(3, object.isActivo());  // El estado debe ir aquí
            ps.setInt(4, object.getId());  // El ID debe ir al final

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            conectar.desconectar();
        }
        return resp;
    }

    @Override
    public boolean onVariable(int id) {
        try {
            ps = conectar.conectar().prepareStatement(
                    "UPDATE categoria SET estado=1 WHERE id= ?"
            );
            ps.setInt(1, id);  // Establece el ID en el parámetro de la consulta

            // Ejecuta la actualización y verifica si se modificaron filas
            if (ps.executeUpdate() > 0) {
                resp = true;  // Si la actualización fue exitosa, cambia el estado de la variable
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());  // Muestra el error en caso de fallo
        } finally {
            try {
                if (ps != null) {
                    ps.close();  // Asegúrate de cerrar el PreparedStatement después de su uso
                }
            } catch (SQLException e) {
                // Manejo de errores al cerrar el PreparedStatement (opcional)
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            conectar.desconectar();  // Desconecta la base de datos
        }
        return resp;  // Retorna el resultado de la operación
    }

    @Override
    public boolean offVariable(int id) {
        resp = false;
        try {
            // Corrige la sintaxis SQL eliminando la coma extra después de 'estado=0'
            ps = conectar.conectar().prepareStatement(
                    "UPDATE categoria SET estado=0 WHERE id= ?"
            );
            ps.setInt(1, id);  // Establece el ID en el parámetro de la consulta

            // Ejecuta la actualización y verifica si se modificaron filas
            if (ps.executeUpdate() > 0) {
                resp = true;  // Si la actualización fue exitosa, cambia el estado de la variable
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());  // Muestra el error en caso de fallo
        } finally {
            try {
                if (ps != null) {
                    ps.close();  // Asegúrate de cerrar el PreparedStatement después de su uso
                }
            } catch (SQLException e) {
                // Manejo de errores al cerrar el PreparedStatement (opcional)
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            conectar.desconectar();  // Desconecta la base de datos
        }
        return resp;  // Retorna el resultado de la operación
    }

    @Override
    public boolean exist(String text) {
        resp = false;
        try {
            // Especificamos un ResultSet de tipo scrollable
            ps = conectar.conectar().prepareStatement("select nombre from categoria where id = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, text);
            rs = ps.executeQuery();

            // Verificamos si el resultado tiene al menos una fila
            if (rs.next()) {
                resp = true;
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            conectar.desconectar();
        }
        return resp;
    }

    @Override
    public int total() {
        int totalRegistro = 0;
        try {
            // Especificamos un ResultSet de tipo scrollable
            ps = conectar.conectar().prepareStatement("select count(id) from categoria",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();

            // Nos aseguramos de que haya al menos una fila para obtener el resultado
            if (rs.next()) {
                totalRegistro = rs.getInt(1); // Obtener el conteo de registros
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            conectar.desconectar();
        }
        return totalRegistro;
    }

    @Override
    public int getID() {
        int idMayor = -1; // Valor por defecto en caso de que no haya registros

        try {
            ps = conectar.conectar().prepareStatement(
                    "SELECT id FROM categoria ORDER BY id DESC LIMIT 1",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            rs = ps.executeQuery();
            if (rs.next()) {
                idMayor = rs.getInt("id"); // Obtiene el ID más alto
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            conectar.desconectar();
        }
        return idMayor;
    }
}