package Application.Utils;

public interface ParameterizedCallable <TParam, TOutput> {
    TOutput call(TParam param);
}
