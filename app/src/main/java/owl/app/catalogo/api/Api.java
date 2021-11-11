package owl.app.catalogo.api;

public class Api {


    private static final String URL_BASE = "http://192.168.100.10:8080/sistemaWeb/curso/";
    private static final String ROOT_URL = URL_BASE +"views/json_curso/Api.php?apicall=";

    //productos
    public static final String URL_READ_PRODUCTOS = ROOT_URL + "readproducto";

    //galeria
    public static  final String GALERIA = URL_BASE + "backend/";

    //request codes
    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

}
