public class SpaceUsageTooMuchException extends Exception{
    private String message;
    public SpaceUsageTooMuchException(String name, double num){
        message = "Превышение допустимой занимаемой площади в комнате " + name + "! Максимальный процент занятой площади" +
                " = 70%, полученный процент занятой площади = " + num*100 + ". Операция не была выполнена.";
        Exception ex = new Exception();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
