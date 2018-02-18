public class IlluminanceTooMuchException extends Exception {
    private String message;
    public IlluminanceTooMuchException(String name, int num){
        message = "Превышение допустимой освещенности в комнате " + name + "! Максимальная допустимая освещенность" +
                " = "+ Room.MAX_ILLUMINATION + ", полученная освещенность = " + num + ". Операция не была выполнена.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
