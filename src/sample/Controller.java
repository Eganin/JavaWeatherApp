package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_people;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text temp_pass;

    @FXML
    void initialize() {
        getData.setOnAction(actionEvent -> {
            String getUserCity = city.getText().trim();// получаем данные от пользователя
            if (!getUserCity.equals("")) {// если данные от пользователя не пустые
                // используем API
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=ac0408e4e39747d67068b81850960e69&units=metric");
                //System.out.println(output);
                System.out.println(output);

                // Получаем данные если такой город есть
                if (!output.isEmpty()) { // работа с json
                    JSONObject obj = new JSONObject(output);// создаем объект JSON для извлечения данных
                    temp_info.setText("Температура: " + obj.getJSONObject("main").getDouble("temp"));// выбираем данные
                    temp_people.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like"));
                    temp_max.setText("Максиум: " + obj.getJSONObject("main").getDouble("temp_max"));
                    temp_min.setText("Миниум: " + obj.getJSONObject("main").getDouble("temp_min"));
                    temp_pass.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
                }
            }
        });

    }

    private static String getUrlContent(String urlAdress) {// обращенеи к API
        /*
        Метод отвечает за обращение к API и получение JSON
        */

        StringBuffer content = new StringBuffer();// для хранения json

        try {
            URL url = new URL(urlAdress);                // соединяемся с веб-страницей
            URLConnection urlConn = url.openConnection();//

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
        } catch (Exception e) {
            System.out.println("Введенный город не был наден");
        }

        return content.toString();
    }
}
