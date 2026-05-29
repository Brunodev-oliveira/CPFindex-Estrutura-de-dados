package controller;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class DataGeneratorControler {
    Path pathJSON = Paths.get("src/mock.json");
    String contentJasonParser;
    JSONArray contentBaseARRAY;


    public DataGeneratorControler() throws Exception {

        contentJasonParser = new String(Files.readAllBytes(pathJSON));
        contentBaseARRAY = new JSONArray(contentJasonParser);

    }

    public void populateInitialBase(CpfRepository structure, int amount){

        for (int i = 0; i < amount; i++) {

            JSONObject obj = contentBaseARRAY.getJSONObject(i);

            Contribuinte contribuinte = new Contribuinte(
                    obj.getString("cpf"),
                    obj.getString("nome"),
                    obj.getString("situacaoCadastral")
                    );

            structure.insert(contribuinte);

        }

    }



}
