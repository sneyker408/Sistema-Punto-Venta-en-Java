import datos.interfaces.CRUDGeneralInterface;
import entidades.Categoria;
import java.util.List;
import database.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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
            ps = conectar.conectar().prepareStatement("SELECT * FROM categoria WHERE nombre like ?");
            ps.setString(1, "%" + list + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            conectar.desconnectar();
        }
        return registros;
    }

    @Override
    public boolean insert(Categoria object) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement("INSERT INTO categoria(?,?,1)");
            ps.setString(1, object.getNombre());
            ps.setString(2, object.getDescripcion());
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconnectar();
        }
        return resp;
    }

    @Override
    public boolean update(Categoria object) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement
        ("Update categoria SET nombre=?, descripcion =? where id= ?");
            ps.setString(1, object.getNombre());
            ps.setString(2, object.getDescripcion());
            ps.setInt(3, object.getId());
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconnectar();
        }
        return resp;
    }

    @Override
    public boolean onVariable(int id) {
          resp = false;
        try {
            ps = conectar.conectar().prepareStatement
        ("Update categoria SET estado=1, where id= ?");
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconnectar();
        }
        return resp;
    }

    @Override
    public boolean offVaraible(int id) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement
        ("Update categoria SET estado=0, where id= ?");
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconnectar();
        }
        return resp;
    }

    @Override
    public boolean exist(String text) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement
        ("select nombre from categoria where id = ?");
            ps.setString(1, text);
            rs = ps.executeQuery();
            rs.last();
            
            if(rs.getRow()> 0){
                resp = true;
            }
            
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
             ps = null;
             rs = null;
             conectar.desconnectar();
        }
        return resp;
    }

    @Override
    public int total() {
        int totalRegistro = 0;
        try {
            ps = conectar.conectar().prepareStatement
        ("select  count(id) from categoria");
            rs = ps.executeQuery();
            while(rs.next()){
                totalRegistro = rs.getInt("count(id)");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
             ps = null;
             rs = null;
             conectar.desconnectar();
        }
        return totalRegistro;
    }

    @Override
    public boolean offVariable(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
