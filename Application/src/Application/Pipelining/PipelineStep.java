package Application.Pipelining;

public interface PipelineStep<TInput, TOutput> {
    TOutput process(TInput input);
}