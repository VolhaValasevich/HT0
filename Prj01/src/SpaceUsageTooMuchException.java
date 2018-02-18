public class SpaceUsageTooMuchException extends Exception{
    private String message;
    public SpaceUsageTooMuchException(String name, double num){
        message = "Превышение допустимой занимаемой площади в комнате " + name + "! Минимальный процент занятой площади" +
                " = " + Room.MIN_FREE_SPACE_PERCENT + "%, полученный процент занятой площади = " + num*100 + ". Операция не была выполнена.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
