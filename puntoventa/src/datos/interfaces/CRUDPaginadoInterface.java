/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package datos.interfaces;
import java.util.List;

/**
 *
 * @author sneyker
 */
public interface CRUDPaginadoInterface<T> {
    public List<T> getAll(String list, int totalPorPagina, int numPagina);
    public boolean insert ( T object );
    public boolean update ( T object );
    public boolean onVariable(int id);
    public boolean offVariable( int id);
    public boolean exist(String text);
    public int total();
    public int getID();  
}