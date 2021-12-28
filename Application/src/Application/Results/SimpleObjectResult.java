package Application.Results;

public class SimpleObjectResult<T> extends SimpleResult implements ObjectResult<T>{
    private T resultObject;

    @Override
    public T getObject() {
        return resultObject;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }
}
