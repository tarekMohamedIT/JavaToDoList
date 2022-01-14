package Application.Pipelining;

import Application.Results.ObjectResult;
import Application.Results.ResultsHelper;

public class Pipeline <TInput, TOutput>{
    private final PipelineStep<TInput, TOutput> current;
    private TInput originalInput;

    private Pipeline(PipelineStep<TInput, TOutput> current) {
        this.current = current;
    }

    public static <TInput, TOutput> Pipeline<TInput, TOutput> create(PipelineStep<TInput, TOutput> current){
        return new Pipeline<>(current);
    }

    public <NewO> Pipeline<TInput, NewO> pipe(PipelineStep<TOutput, NewO> next) {
        return new Pipeline<>(input -> next.process(current.process(input)));
    }

    public ObjectResult<TOutput> execute(TInput input) {
        originalInput = input;
        return ResultsHelper.tryDo(() -> current.process(input));
    }

    public TInput getOriginalInput() {
        return originalInput;
    }
}
