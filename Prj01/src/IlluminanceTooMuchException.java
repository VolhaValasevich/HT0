public class IlluminanceTooMuchException extends Exception {
    private String message;
    public IlluminanceTooMuchException(String name, int num){
        message = "Превышение допустимой освещенности в комнате " + name + "! Максимальная допустимая освещенность" +
                " = 4000 лк, полученная освещенность = " + num + ". Операция не была выполнена.";
        Exception ex = new Exception();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
