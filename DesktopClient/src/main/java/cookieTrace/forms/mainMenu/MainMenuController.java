package cookieTrace.forms.mainMenu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import cookieTrace.ActualMain;
import cookieTrace.Model.AnimalEntity;
import cookieTrace.Model.EspecieEntity;
import cookieTrace.Model.RazaEntity;
import cookieTrace.netConector.LazyImageLoader;
import cookieTrace.netConector.NetConector;
import cookieTrace.netConector.NetProcess;
import cookieTrace.protocol.Protocol;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import netscape.javascript.JSObject;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.*;


public class MainMenuController implements Initializable {

    File selectedFile;

    NetConector net;

    @FXML
    GridPane loadingPane;

    @FXML
    WebView webView;



    @FXML
    Button botonAnimales;

    @FXML
    public void showAnimales(){
        Protocol prol = new Protocol(0,null,"GetAnimales");

        NetProcess np = new NetProcess(this,net,prol,"successAnimalLoad","errormsg","timeoutmsg");

    }

    public void callAnimales(Protocol prol){
        showAnimales();
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }


    @FXML
    public void registrarAnimalLoadPage(){
        loadingPane.setVisible(true);
        MainMenuController mainMenuController = this;
        Platform.runLater(new Runnable(){

            @Override
            public void run() {

                final WebEngine webEngine = webView.getEngine();

                String page = "";
                webEngine.setUserStyleSheetLocation(getClass().getResource("/HTML/registrarAnimal.css").toString());
                page += "\n" +
                        "<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>\n" +
                        "\n" +
                        "      <!-- use the font -->\n" +
                        "      <style>\n" +
                        "        body {\n" +
                        "          font-family: 'Roboto', sans-serif;\n" +
                        "        }\n" +
                        "      </style>" +
                        "<h1>Registrar animal</h1>\n" +
                        "<br/>\n" +
                        "<p>Nombre <input id=\"nombre\" type=text placeholder=\"Bambi\">\n" +
                        "<div class=\"select\">\n" +
                        "  <select id=\"especie\" onchange=\"app.loadBreeds(value)\" name=\"slct\" id=\"slct\">\n" +
                        "    <option selected disabled>Especie</option>\n" +
                        "    \n" +
                        "  </select>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br/>\n" +
                        "\n" +
                        "<div class=\"select\">\n" +
                        "  <select id=\"raza\" onchange=\"alert(value);\" name=\"slct\" id=\"slct\">\n" +
                        "    <option selected disabled>Raza</option>\n" +
                        "  </select>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br/>\n" +
                        "\n" +
                        "<textarea placeholder=\"Descripcion...\" id=\"description\" name=\"w3review\" rows=\"4\" cols=\"50\"></textarea>\n" +
                        "\n" +
                        "<br/>\n" +
                        "\n" +
                        "<label class=\"pure-material-checkbox\">\n" +
                        "  <input id=\"especial\" type=\"checkbox\">\n" +
                        "  <span>Necesidades especiales?</span>\n" +
                        "</label>\n" +
                        "\n" +
                        "<br/>\n" +
                        "\n" +
                        "<p>Nacimiento (DD-MM-YYYY) <input id=\"nacimiento\" placeholder=\"02-10-1999\" type=text>\n" +
                        "  \n" +
                        "<p>Foto de perfil <button onclick=\"app.loadFile()\">Select file</button>\n" +
                        "  \n" +
                        "  <br/><br/>\n" +
                        "  \n" +
                        "  <button onclick=\"app.sendRegisterAnimal()\" class =\"btn\">ENVIAR</button>\n" +
                        "  \n" +
                        "  ";

                webEngine.loadContent(page);

                LazyImageLoader imageloader = new LazyImageLoader(net);



                webEngine.setJavaScriptEnabled(true);

                webEngine.getLoadWorker().stateProperty().addListener(
                        new ChangeListener<Worker.State>() {
                            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                                ChangeListener observablef = this;
                                if (newState == Worker.State.SUCCEEDED) {

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            JSObject window = (JSObject) webEngine.executeScript("window");
                                            window.setMember("app", mainMenuController);

                                            Gson gson = new Gson();
                                            Protocol prol = net.sendSimple(new Protocol(0,"","GetEspecies"));
                                            List<EspecieEntity> lista = new ArrayList<EspecieEntity>();

                                            String jsonEspecies = gson.toJson(prol.getBody());
                                            EspecieEntity[] especies = gson.fromJson(jsonEspecies, EspecieEntity[].class);
                                            for(EspecieEntity especie : especies){
                                                Element input = webEngine.getDocument().getElementById("especie");
                                                Element elemento= webEngine.getDocument().createElement("option");
                                                elemento.setTextContent(especie.getNombre_comun()+"  ("+especie.getNombre()+")");
                                                elemento.setAttribute("value",String.valueOf(especie.getId()));
                                                input.appendChild(elemento);
                                            }
                                            loadingPane.setVisible(false);

                                            webEngine
                                                    .getLoadWorker()
                                                    .stateProperty()
                                                    .removeListener(observablef);
                                        }
                                    });

                                }



                            }
                        });




            }
        });
    }

    public void sendRegisterAnimal(){
        final WebEngine webEngine = webView.getEngine();

        String name = (String) webView.getEngine().executeScript("document.getElementById('nombre').value;");

        String description = (String) webView.getEngine().executeScript("document.getElementById('description').value;");
        boolean specialCare = (Boolean) webView.getEngine().executeScript(
                "document.getElementById('especial').checked;");

        String birthDate = (String) webView.getEngine().executeScript("document.getElementById('nacimiento').value;");

        System.out.println(birthDate);

        String razeId = (String) webView.getEngine().executeScript("document.getElementById('raza').value;");
        String profilePhoto = "";
        try {
            profilePhoto = Base64.getEncoder().encodeToString(Files.readAllBytes(selectedFile.toPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        NetProcess np = new NetProcess(this,net,new Protocol(0,"","UploadAnimal","razeId",razeId,"description",description,"name",name,"specialCare",String.valueOf(specialCare),"birthDate",birthDate,"profilePhoto",profilePhoto),"callAnimales","callAnimales","callAnimales");

    }

    public void loadBreeds(String id){
        loadingPane.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final WebEngine webEngine = webView.getEngine();
                Gson gson = new Gson();
                Protocol prol = net.sendSimple(new Protocol(0,"","GetRazasByEspecie","id",id));
                String json = gson.toJson(prol.getBody());
                System.out.println(json);
                List<RazaEntity> enums = gson.fromJson(json,new TypeToken<List<RazaEntity>>() {}.getType());
                Element input = webEngine.getDocument().getElementById("raza");
                while (input.hasChildNodes()) {
                    input.removeChild(input.getFirstChild());
                }
                for(RazaEntity especie : enums){

                    Element elemento= webEngine.getDocument().createElement("option");
                    elemento.setTextContent(especie.getNombre());
                    elemento.setAttribute("value",String.valueOf(especie.getId()));
                    input.appendChild(elemento);
                }
                loadingPane.setVisible(false);
            }
        });

    }

    public void loadFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        this.selectedFile = fileChooser.showOpenDialog(loadingPane.getScene().getWindow());
    }

    @FXML
    public void successAnimalLoad(Protocol prol){
        loadingPane.setVisible(true);
        MainMenuController mainMenuController = this;
        Platform.runLater(new Runnable(){

            @Override
            public void run() {

                final WebEngine webEngine = webView.getEngine();
                String page = "";
                webEngine.setUserStyleSheetLocation(getClass().getResource("/HTML/animalList.css").toString());
                page += "<!DOCTYPE HTML> " +
                        "<style>\n" +
                        "        body {\n" +
                        "\n" +
                        "        background: url('"+this.getClass().getResource("/HTML/fondo1.png")+"') repeat center center fixed;\n" +
                        "             }\n" +
                        "    </style>" +
                        "<link href=\"http://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\" type=\"text/css\">" +
                        "<body>" +
                        "<div class=\"fijo\">\n" +
                        "<label class=\"search-box__label\" for=\"search\"><img src=\"https://img.icons8.com/material-sharp/24/000000/search.png\" class=\"search-box__icon material-icons\"></label>\n" +
                        "<input class=\"search-box__input\" id=\"search\" type=\"text\">\n" +
                        "</div>" +
                        " <div class=\"cards-list\">";

                Gson gson = new GsonBuilder().setDateFormat("MM dd, yyyy HH:mm:ss").create();
                List<AnimalEntity> lista = new ArrayList<AnimalEntity>();

                String jsonAnimales = gson.toJson(prol.getBody());
                AnimalEntity[] animales = gson.fromJson(jsonAnimales, AnimalEntity[].class);

                int contador = 0;

                for(AnimalEntity animal : animales) {
                    page += "<div id=\""+animal.getId()+"\" class=\"carta inactive\">\n" +
                            "    \n<div class=\"clicable\" onclick=\"app.loadAnimal(\'"+animal.getId()+"\')\" >" +
                            "        <img onclick=\"app.loadAnimal(\'"+animal.getId()+"\')\" id=\"img"+animal.getId()+"\" src=\""+this.getClass().getResource("/HTML/loading.gif")+"\"><p onclick=\"app.loadAnimal(\'"+animal.getId()+"\')\" class=\"nombre\">"+animal.getNombre()+"</p>\n" +
                            "\n" +
                            "\n" +
                            "    \n" +
                            "    \n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "    \n</div>" +

                            "    <div class=\"menu\">\n" +
                            "<div class=\"grid-container\">\n" +
                            "  <div class=\"Name\">\n" +
                            "    <h1 id=\"menuNombre"+animal.getId()+"\" contenteditable=\"true\">"+animal.getNombre()+"</h1><br/><h6>Especie: "+animal.getRaza().getEspecie().getNombre()+" ("+animal.getRaza().getEspecie().getNombre_comun()+")<br/><br/> Raza: "+animal.getRaza().getNombre()+"</h6>\n" +
                            "</div>\n" +
                            "  <div class=\"Photo\">\n" +
                            "<img id=\"menuImg"+animal.getId()+"\" src=\""+this.getClass().getResource("/HTML/loading.gif")+"\">\n" +
                            "    <h6>"+getDiffYears(animal.getFecha_nacimiento(),new Date())+" años</h6>\n" +
                            "   \n" +
                            "</div>\n" +
                            "  <div onclick=\"app.hideMenu(\'"+animal.getId()+"\')\" class=\"Close\"></div>\n" +
                            "  <div class=\"Edit\">\n" +
                            "<button onclick=\""+"app.edit('"+animal.getId()+"')\">Update</button>\n" +
                            "\n" +
                            "</div>\n" +
                            "  <div class=\"Delete\">\n" +
                            "<button>Borrar</button>\n" +
                            "\n" +
                            "</div>\n" +
                            "  <div class=\"Open-calendar\">\n" +
                            "<button>Calendario vac</button>\n" +
                            "\n" +
                            "</div>\n" +
                            "  <div class=\"Adoptions\">\n" +
                            "    <button>Solicitudes de adopcion</button>\n" +
                            "\n" +
                            "</div>\n" +
                            "  <div class=\"Description\">\n" +
                            "<h5 contenteditable=\"true\" id=\"menuDescripcion"+animal.getId()+"\">"+animal.getDescription()+"</h5>\n" +
                            "</div>\n" +
                            "  <div class=\"Separator\"></div>\n" +
                            "</div>\n" +
                            "\n" +
                            "</div>\n" +
                            "    \n" +
                            "    </div>\n" +
                            "\n" +
                            "    \n" +
                            "\n" +
                            "\n" +
                            "    \n" +
                            "    </div>";
                    contador++;
                }
                page +=  "</body></html>";
                webEngine.loadContent(page);
                LazyImageLoader imageloader = new LazyImageLoader(net);



                webEngine.setJavaScriptEnabled(true);

                webEngine.getLoadWorker().stateProperty().addListener(
                        new ChangeListener<Worker.State>() {
                            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                                ChangeListener observablef = this;
                                if (newState == Worker.State.SUCCEEDED) {
                                    loadingPane.setVisible(false);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            JSObject window = (JSObject) webEngine.executeScript("window");
                                            window.setMember("app", mainMenuController);

                                            for(AnimalEntity animal : animales){
                                                Element elemento = webEngine.getDocument().getElementById("img"+String.valueOf(animal.getId()));
                                                Element elementoDos = webEngine.getDocument().getElementById("menuImg"+String.valueOf(animal.getId()));
                                                imageloader.GetAnimalProfilePhoto(webEngine,elemento,animal.getId());

                                                imageloader.GetAnimalProfilePhoto(webEngine,elementoDos,animal.getId());
                                            }

                                            webEngine
                                                    .getLoadWorker()
                                                    .stateProperty()
                                                    .removeListener(observablef);
                                        }
                                    });

                                }



                            }
                        });




            }
        });

    }

    public void loadAnimal(String id){
        final WebEngine webEngine = webView.getEngine();
        Element elemento = webEngine.getDocument().getElementById(id);
        elemento.setAttribute("class","carta active");

    }

    public void sendTest(String test){
        System.out.println(test);
    }

    public void hideMenu(String id){
        final WebEngine webEngine = webView.getEngine();
        Element elemento = webEngine.getDocument().getElementById(id);
        elemento.setAttribute("class","carta inactive");

    }

    public void edit(String id){
        final WebEngine webEngine = webView.getEngine();
        Element nombre = webEngine.getDocument().getElementById("menuNombre"+id);
        Element descripcion = webEngine.getDocument().getElementById("menuDescripcion"+id);
        Protocol request = new Protocol(0,"","EditAnimal","id",id,"name",nombre.getTextContent(),"description",descripcion.getTextContent());
        NetProcess np = new NetProcess(this,net,request,"okayEdit","errormsg","timeoutmsg");
    }

    public void okayEdit(Protocol prol){
        showAnimales();
    }

    public void errormsg(Protocol prol){

    }

    public void timeoutmsg(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        net = ActualMain.net;
        loadingPane.setVisible(false);
        final WebEngine webEngine = webView.getEngine();
        String page = "";
        page += "<!DOCTYPE html>\n" +
                "<html lang=\"en\" >\n" +
                "<head>\n" +
                "<script src=\"jquery-3.5.1.min.js\"></script>" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Home </title>\n" +
                "<link rel=\"stylesheet\" href=\"CSS/style.css\">\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "  \n" +
                "  \n" +
                "\n" +
                "<!-- Starbackground -->\n" +
                "<div id='stars'></div>\n" +
                "<div id='stars2'></div>\n" +
                "<div id='stars3'></div>\n" +
                "\n" +
                "<!-- parallax text/java -->\n" +
                "<div id=\"parallax\">\n" +
                "  <div class=\"layer\" data-depth=\"0.6\">\n" +
                "  \n" +
                "<!-- text -->\n" +
                "    <div class=\"some-space\">\n" +
                "      <h1>SHELTER</h1>\n" +
                "    </div>\n" +
                "  \n" +
                "  </div>\n" +
                "  <div class=\"layer\" data-depth=\"0.4\">\n" +
                "    <div id=\"particles-js\"></div>\n" +
                "  </div>\n" +
                "\n" +
                "<!-- Button -->\n" +
                "\n" +
                "</body>\n" +
                "<script>" +
                "$('#parallax').parallax({\n" +
                "\tinvertX: true,\n" +
                "\tinvertY: true,\n" +
                "\tscalarX: 15,\n" +
                "\tfrictionY: .1\n" +
                "});\n" +
                "\n" +
                "\n" +
                "particlesJS(\"particles-js\", {\n" +
                "  \"particles\": {\n" +
                "    \"number\": {\n" +
                "      \"value\": 120,\n" +
                "      \"density\": {\n" +
                "        \"enable\": true,\n" +
                "        \"value_area\": 800\n" +
                "      }\n" +
                "    },\n" +
                "    \"color\": {\n" +
                "      \"value\": \"#ffffff\"\n" +
                "    },\n" +
                "    \"shape\": {\n" +
                "      \"type\": \"circle\",\n" +
                "      \"stroke\": {\n" +
                "        \"width\": 0,\n" +
                "        \"color\": \"#000000\"\n" +
                "      },\n" +
                "      \"polygon\": {\n" +
                "        \"nb_sides\": 5\n" +
                "      },\n" +
                "      \"image\": {\n" +
                "        \"src\": \"img/github.svg\",\n" +
                "        \"width\": 100,\n" +
                "        \"height\": 100\n" +
                "      }\n" +
                "    },\n" +
                "    \"opacity\": {\n" +
                "      \"value\": 0.5,\n" +
                "      \"random\": false,\n" +
                "      \"anim\": {\n" +
                "        \"enable\": false,\n" +
                "        \"speed\": 1,\n" +
                "        \"opacity_min\": 0.1,\n" +
                "        \"sync\": false\n" +
                "      }\n" +
                "    },\n" +
                "    \"size\": {\n" +
                "      \"value\": 3,\n" +
                "      \"random\": true,\n" +
                "      \"anim\": {\n" +
                "        \"enable\": false,\n" +
                "        \"speed\": 40,\n" +
                "        \"size_min\": 0.1,\n" +
                "        \"sync\": false\n" +
                "      }\n" +
                "    },\n" +
                "    \"line_linked\": {\n" +
                "      \"enable\": true,\n" +
                "      \"distance\": 150,\n" +
                "      \"color\": \"#ffffff\",\n" +
                "      \"opacity\": 0.4,\n" +
                "      \"width\": 1\n" +
                "    },\n" +
                "    \"move\": {\n" +
                "      \"enable\": true,\n" +
                "      \"speed\": 6,\n" +
                "      \"direction\": \"none\",\n" +
                "      \"random\": false,\n" +
                "      \"straight\": false,\n" +
                "      \"out_mode\": \"out\",\n" +
                "      \"bounce\": false,\n" +
                "      \"attract\": {\n" +
                "        \"enable\": false,\n" +
                "        \"rotateX\": 600,\n" +
                "        \"rotateY\": 1200\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"interactivity\": {\n" +
                "    \"detect_on\": \"canvas\",\n" +
                "    \"events\": {\n" +
                "      \"onhover\": {\n" +
                "        \"enable\": true,\n" +
                "        \"mode\": \"grab\"\n" +
                "      },\n" +
                "      \"onclick\": {\n" +
                "        \"enable\": true,\n" +
                "        \"mode\": \"push\"\n" +
                "      },\n" +
                "      \"resize\": true\n" +
                "    },\n" +
                "    \"modes\": {\n" +
                "      \"grab\": {\n" +
                "        \"distance\": 140,\n" +
                "        \"line_linked\": {\n" +
                "          \"opacity\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      \"bubble\": {\n" +
                "        \"distance\": 400,\n" +
                "        \"size\": 40,\n" +
                "        \"duration\": 2,\n" +
                "        \"opacity\": 8,\n" +
                "        \"speed\": 3\n" +
                "      },\n" +
                "      \"repulse\": {\n" +
                "        \"distance\": 200,\n" +
                "        \"duration\": 0.4\n" +
                "      },\n" +
                "      \"push\": {\n" +
                "        \"particles_nb\": 4\n" +
                "      },\n" +
                "      \"remove\": {\n" +
                "        \"particles_nb\": 2\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"retina_detect\": true\n" +
                "});\n" +
                "\n" +
                "\n" +
                "</script>" +
                "</html>";
        webEngine.setUserStyleSheetLocation(getClass().getResource("/HTML/MainScreen.css").toString());
        webEngine.loadContent(page);

    }
}