/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Alexis Rauda
 */
public class Articulo {

    private int idArticulo;
    private int categoriaId;
    private String codigo;
    private String nombre;
    private double precioVenta;
    private int stock;
    private String descripcion;
    private String imagen;
    private boolean estado;

    public Articulo() {
    }

    public Articulo(int idArticulo, int categoriaId, String codigo, String nombre, double precioventa, int stock, String descripcion, String imagen, boolean estado) {
        this.idArticulo = idArticulo;
        this.categoriaId = categoriaId;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioVenta = precioventa;
        this.stock = stock;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Articulo{" + "idArticulo=" + idArticulo + ", categoriaId=" + categoriaId + ", codigo=" + codigo + ", nombre=" + nombre + ", precioventa=" + precioVenta + ", stock=" + stock + ", descripcion=" + descripcion + ", imagen=" + imagen + ", estado=" + estado + '}';
    }
}