package core;

/**
 * Created by lgermani on 02/01/2018.
 */
public class EnviromentParams {

    private String enviromentBaseURL;

    private EnviromentParams() {
        enviromentBaseURL = System.getenv("enviromentBaseURL");
    }

    //Singleton implementation
    private static EnviromentParams instance;

    public static EnviromentParams getInstance(){
        if (instance == null) {
            instance = new EnviromentParams();
        }
        return instance;
    }

    public String getEnviromentBaseURL() {
        return enviromentBaseURL;
    }
}
