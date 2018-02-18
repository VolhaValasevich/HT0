public class IlluminanceTooLittleException extends Exception{
    private String message;
    public IlluminanceTooLittleException(String name, int num){
        message = "Слишком малая освещенность в комнате " + name + "! Минимальная допустимая освещенность" +
                " = "+ Room.MIN_ILLUMINATION + ", текущая освещенность = " + num + ".";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
