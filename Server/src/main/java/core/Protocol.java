package core;

public class Protocol {
    public static final String ok = "1/"; //No toma parametros

    public static final String notOkay = "2/"; //No toma parametros

    public static final String subirAnimal = "3/"; //Toma todos los parametros como atributos tiene animal

    public static final String crearPeticionFeedback = "4/"; //Toma 3 parametros, animal, usuario e información adicional

    public static final String responderPeticionFeedback = "5/"; //Toma 2 parametros, id peticion y url del medio

    public static final String actualizarCalendarioVacunacion = "6/"; //Toma 2 parametros, id calendario y Json del mismo

    public static final String crearCalendarioDeVacunacion = "7//"; //Toma 1 parametro, su Json

    public static final String solicitarAdopcion = "8/"; //Toma 1 parametro, id animal

    public static final String modificarPerfil = "9/"; //Toma 1 Json del perfil actualizado

    public static final String crearPerfil = "10/"; //Toma 1 Json del perfil a crear

    public static final String verInformesDeDatos = "11/"; //No toma parametros

    public static final String verSolicitudesDeAdopcion = "14/"; //No toma parametros

    public static final String aceptarSolicitudAdopcion = "12/"; //Toma 1 argumento, id de solicitud

    public static final String rechazarSolicitudAdopcion = "13/"; //Toma 1 argumento, id de solicitud
}
