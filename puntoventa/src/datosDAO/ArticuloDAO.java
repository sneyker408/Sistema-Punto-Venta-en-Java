/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datosDAO;

import database.Conexion;
import datos.interfaces.CRUDPaginadoInterface;
import entidades.Articulo;
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

public class ArticuloDAO implements CRUDPaginadoInterface<Articulo> {

    private final Conexion conectar;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public ArticuloDAO() {
        conectar = Conexion.getInstance();
    }

    @Override
    public List<Articulo> getAll(String list, int totalPorPagina, int numPagina) {
        List<Articulo> registros = new ArrayList<>();
        try {
            ps = conectar.conectar().prepareStatement(
                    "SELECT "
                    + "a.idArticulo, "
                    + "a.categoria_id, "
                    + "c.nombre AS categoria_nombre, "
                    + "a.codigo, "
                    + "a.nombre, "
                    + "a.precio_venta, "
                    + "a.stock, "
                    + "a.descripcion, "
                    + "a.imagen, "
                    + "a.estado "
                    + " FROM articulo a "
                    + " INNER JOIN categoria c ON a.categoria_id = c.id "
                    + " WHERE a.nombre LIKE ? "
                    + " ORDER BY a.idArticulo ASC "
                    + " LIMIT ?, ?"
            );
            ps.setString(1, "%" + list + "%");
            ps.setInt(2, (numPagina - 1) * totalPorPagina);
            ps.setInt(3, totalPorPagina);

            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Articulo(
                        rs.getInt(1), // idArticulo
                        rs.getInt(2), // categoria_id
                        rs.getString(4), // codigo
                        rs.getString(5), // nombre
                        rs.getDouble(6), // precioVenta
                        rs.getInt(7), // stock
                        rs.getString(8), // descripcion
                        rs.getString(9), // imagen
                        rs.getBoolean(10) // estado
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return registros;
    }

    @Override
    public boolean insert(Articulo object) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement("INSERT INTO articulo "
                    + "(categoria_id, codigo, nombre, precio_venta, stock, descripcion, imagen, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, 1)");
            ps.setInt(1, object.getCategoriaId());
            ps.setString(2, object.getCodigo());
            ps.setString(3, object.getNombre());
            ps.setDouble(4, object.getPrecioVenta());
            ps.setInt(5, object.getStock());
            ps.setString(6, object.getDescripcion());
            ps.setString(7, object.getImagen());

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar el dato: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return resp;
    }
 
    
    @Override
    public boolean update(Articulo object) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement("UPDATE articulo SET "
                    + "categoria_id=?, codigo=?, nombre=?, precio_venta=?, stock=?, descripcion=?, imagen=? "
                    + "WHERE idArticulo=?");
            ps.setInt(1, object.getCategoriaId());
            ps.setString(2, object.getCodigo());
            ps.setString(3, object.getNombre());
            ps.setDouble(4, object.getPrecioVenta());
            ps.setInt(5, object.getStock());
            ps.setString(6, object.getDescripcion());
            ps.setString(7, object.getImagen());
            ps.setInt(8, object.getIdArticulo());

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return resp;
    }

    
    @Override
    public boolean onVariable(int id) {
        return cambiarEstado(id, 1);
    }

    @Override
    public boolean offVariable(int id) {
        return cambiarEstado(id, 0);
    }

    private boolean cambiarEstado(int id, int estado) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement("UPDATE articulo SET estado=? WHERE idArticulo=?");
            ps.setInt(1, estado);
            ps.setInt(2, id);

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return resp;
    }

    @Override
    public boolean exist(String text) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement("SELECT nombre FROM articulo WHERE nombre = ?");
            ps.setString(1, text);
            rs = ps.executeQuery();

            if (rs.next()) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return resp;
    }

    @Override
    public int total() {
        int totalRegistro = 0;
        try {
            ps = conectar.conectar().prepareStatement("SELECT COUNT(idArticulo) FROM articulo");
            rs = ps.executeQuery();

            if (rs.next()) {
                totalRegistro = rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return totalRegistro;
    }

    @Override
    public int getID() {
        int idMayor = -1;
        try {
            ps = conectar.conectar().prepareStatement("SELECT idArticulo FROM articulo ORDER BY idArticulo DESC LIMIT 1");
            rs = ps.executeQuery();

            if (rs.next()) {
                idMayor = rs.getInt("idArticulo");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return idMayor;
    }

    private void cerrarRecursos() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            conectar.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + e.getMessage());
        }
    }
}