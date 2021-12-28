package Application.Results;

import java.util.concurrent.Callable;

public class ResultsHelper {
    public static <T> ObjectResult<T> tryDo(Callable<T> callable){
        SimpleObjectResult<T> result = new SimpleObjectResult<>();
        try {
            result.setResultObject(callable.call());
            result.setState(ResultState.SUCCESS);
        }
        catch (Exception e){
            result.setState(ResultState.FAIL);
            result.setException(e);
        }

        return result;
    }

    public static Result tryDo(Runnable runnable){
        SimpleResult result = new SimpleResult();
        try {
            runnable.run();
            result.setState(ResultState.SUCCESS);
        }
        catch (Exception e){
            result.setState(ResultState.FAIL);
            result.setException(e);
        }

        return result;
    }
}
