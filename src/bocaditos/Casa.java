/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

/**
 *
 * @author juangocc
 */
public class Casa extends Objeto {

    private Usuario user;

    public Casa(String nombre, int posX, int posY, int ancho, int alto) {
        super(nombre, posX, posY, ancho, alto);
    }

    /**
     * @return the user
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Usuario user) {
        this.user = user;
    }

}
