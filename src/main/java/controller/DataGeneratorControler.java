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

    public void populateInitialBase(BinarySearchTree tree, int amount){

        for (int i = 0; i < amount; i++) {

            JSONObject obj = contentBaseARRAY.getJSONObject(i);

            Contribuinte contribuinte = new Contribuinte(
                    obj.getString("cpf"),
                    obj.getString("nome"),
                    obj.getString("situacaoCadastral")
                    );

            tree.insert(contribuinte);

        }

    }









//    private Random random;
//    private String[] nomes = {
//            "Lucas",
//            "Mariana",
//            "Rafael",
//            "Beatriz",
//            "Gabriel",
//            "Juliana",
//            "Felipe",
//            "Camila",
//            "Bruno",
//            "Ana",
//    };
//    private String[] situations = {"ativo","suspenso","inativo"};
//
//
//   public DataGeneratorControler(){
//       random = new Random();
//
//   }
//
//   public generateInitialBase(BinarySearchTree tree, int amount){
//       for (int i = 0; i < amount; i++) {
//
//
//       }
//   }
//   public Contribuinte generateRandomContribuinte(){
//



//   }





}
